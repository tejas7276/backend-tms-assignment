package com.tms.backend.service;

import com.tms.backend.entity.*;
import com.tms.backend.entity.enums.*;
import com.tms.backend.exception.*;
import com.tms.backend.repository.*;

import org.springframework.dao.OptimisticLockingFailureException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
public class BookingService {

    private final BookingRepository bookingRepository;
    private final BidRepository bidRepository;
    private final TransporterRepository transporterRepository;
    private final LoadService loadService;
    private final LoadRepository loadRepository;

    public BookingService(BookingRepository bookingRepository, BidRepository bidRepository,
                          TransporterRepository transporterRepository, LoadService loadService,
                          LoadRepository loadRepository) {
        this.bookingRepository = bookingRepository;
        this.bidRepository = bidRepository;
        this.transporterRepository = transporterRepository;
        this.loadService = loadService;
        this.loadRepository = loadRepository;
    }

    @Transactional
    public Booking createBooking(UUID bidId, int allocatedTrucks, double finalRate) {

        try {
            Bid bid = bidRepository.findById(bidId)
                    .orElseThrow(() -> new ResourceNotFoundException("Bid not found"));

            Load load = bid.getLoad();
            Transporter transporter = bid.getTransporter();

            // Ensure only one accepted offer exists
            if (bidRepository.existsByLoad_LoadIdAndStatus(load.getLoadId(), BidStatus.ACCEPTED)) {
                throw new LoadAlreadyBookedException("Another accepted offer already exists for this load");
            }

            // Validate remaining load capacity
            int remaining = loadService.getRemainingTrucks(load.getLoadId());
            if (allocatedTrucks > remaining) {
                throw new InsufficientCapacityException("Not enough capacity left for booking");
            }

            // Validate transporter capacity
            AvailableTruck capacity = transporter.getAvailableTrucks().stream()
                    .filter(t -> t.getTruckType().equalsIgnoreCase(load.getTruckType()))
                    .findFirst()
                    .orElseThrow(() -> new InsufficientCapacityException("No matching truck type"));

            if (allocatedTrucks > capacity.getCount()) {
                throw new InsufficientCapacityException("Transporter does not have enough trucks");
            }

            // Deduct trucks from transporter
            capacity.setCount(capacity.getCount() - allocatedTrucks);
            transporterRepository.save(transporter);

            // Accept the bid
            bid.setStatus(BidStatus.ACCEPTED);
            bidRepository.save(bid);

            // Reject all other PENDING offers on same load
            bidRepository.updateStatusForOthers(load.getLoadId(), bidId, BidStatus.REJECTED);

            // Create booking entry
            Booking booking = new Booking();
            booking.setBid(bid);
            booking.setLoad(load);
            booking.setTransporter(transporter);
            booking.setAllocatedTrucks(allocatedTrucks);
            booking.setFinalRate(finalRate);

            Booking saved = bookingRepository.save(booking);

            // If fully booked -> mark as BOOKED
            if (loadService.getRemainingTrucks(load.getLoadId()) == 0) {
                load.setStatus(LoadStatus.BOOKED);
                loadRepository.save(load);
            }

            return saved;

        } catch (OptimisticLockingFailureException ex) {
            throw new LoadAlreadyBookedException("Load was booked by another transporter, retry");
        }
    }

    @Transactional
    public Booking cancelBooking(UUID bookingId) {

        Booking booking = bookingRepository.findById(bookingId)
                .orElseThrow(() -> new ResourceNotFoundException("Booking not found"));

        booking.setStatus(BookingStatus.CANCELLED);
        bookingRepository.save(booking);

        Load load = booking.getLoad();
        Transporter transporter = booking.getTransporter();

        // Restore transporter trucks
        AvailableTruck capacity = transporter.getAvailableTrucks().stream()
                .filter(t -> t.getTruckType().equalsIgnoreCase(load.getTruckType()))
                .findFirst()
                .orElse(null);

        if (capacity != null) {
            capacity.setCount(capacity.getCount() + booking.getAllocatedTrucks());
            transporterRepository.save(transporter);
        }

        // Reopen load if trucks available
        load.setStatus(LoadStatus.OPEN_FOR_BIDS);
        loadRepository.save(load);

        return booking;
    }
}
