package com.tms.backend.service;

import com.tms.backend.dto.LoadDetailsResponse;
import com.tms.backend.entity.Bid;
import com.tms.backend.entity.Booking;
import com.tms.backend.entity.Load;
import com.tms.backend.entity.enums.BidStatus;
import com.tms.backend.entity.enums.LoadStatus;
import com.tms.backend.repository.BidRepository;
import com.tms.backend.repository.BookingRepository;
import com.tms.backend.repository.LoadRepository;
import com.tms.backend.exception.ResourceNotFoundException;
import com.tms.backend.exception.InvalidStatusTransitionException;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class LoadService {

    private final LoadRepository loadRepository;
    private final BidRepository bidRepository;
    private final BookingRepository bookingRepository;

    public LoadService(LoadRepository loadRepository, BidRepository bidRepository,
                       BookingRepository bookingRepository) {
        this.loadRepository = loadRepository;
        this.bidRepository = bidRepository;
        this.bookingRepository = bookingRepository;
    }

    public Load getLoadById(UUID loadId) {
        return loadRepository.findById(loadId)
                .orElseThrow(() -> new ResourceNotFoundException("Load not found: " + loadId));
    }

    @Transactional
    public Load cancelLoad(UUID loadId) {
        Load load = getLoadById(loadId);

        if (load.getStatus() == LoadStatus.BOOKED) {
            throw new InvalidStatusTransitionException("Cannot cancel a BOOKED load");
        }

        load.setStatus(LoadStatus.CANCELLED);
        return loadRepository.save(load);
    }

    @Transactional(readOnly = true)
    public List<Bid> getBestBids(UUID loadId) {
        getLoadById(loadId);

        List<Bid> bids = bidRepository.findByLoad_LoadId(loadId).stream()
                .filter(b -> b.getStatus() == BidStatus.PENDING)
                .collect(Collectors.toList());

        bids.sort((b1, b2) -> Double.compare(score(b2), score(b1)));

        return bids;
    }

    private double score(Bid bid) {
        double rateScore = 1.0 / bid.getProposedRate();
        double ratingScore = bid.getTransporter().getRating() / 5.0;
        return rateScore * 0.7 + ratingScore * 0.3;
    }

    public int getRemainingTrucks(UUID loadId) {
        Load load = getLoadById(loadId);

        List<Booking> bookings = bookingRepository.findByLoad_LoadIdAndStatus(loadId,
                com.tms.backend.entity.enums.BookingStatus.CONFIRMED);

        int total = bookings.stream()
                .mapToInt(Booking::getAllocatedTrucks)
                .sum();

        return load.getNoOfTrucks() - total;
    }
    
    @Transactional(readOnly = true)
    public LoadDetailsResponse getLoadWithActiveBids(UUID loadId) {
        Load load = getLoadById(loadId);

        List<Bid> active = bidRepository.findByLoad_LoadIdAndStatus(loadId, BidStatus.PENDING);

        int remaining = getRemainingTrucks(loadId);

        LoadDetailsResponse response = new LoadDetailsResponse();
        response.setLoad(load);
        response.setActiveBids(active);
        response.setRemainingTrucks(remaining);

        return response;
    }

    public List<Bid> getBestOffers(UUID loadId) {

        List<Bid> offers = bidRepository.findPendingOffersForLoad(loadId);

        return offers.stream()
                .sorted((a, b) -> Double.compare(
                        score(b.getProposedRate(), b.getTransporter().getRating()),
                        score(a.getProposedRate(), a.getTransporter().getRating())
                ))
                .toList();
    }

    private double score(double rate, double rating) {
        return (1 / rate) * 0.7 + (rating / 5) * 0.3;
    }

}
