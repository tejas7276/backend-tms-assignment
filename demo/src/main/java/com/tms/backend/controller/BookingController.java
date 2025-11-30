package com.tms.backend.controller;

import com.tms.backend.dto.CreateBookingRequest;
import com.tms.backend.entity.Booking;
import com.tms.backend.entity.enums.BookingStatus;
import com.tms.backend.repository.BookingRepository;
import com.tms.backend.service.BookingService;

import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/booking")
public class BookingController {

    private final BookingService bookingService;
    private final BookingRepository bookingRepository;

    public BookingController(BookingService bookingService, BookingRepository bookingRepository) {
        this.bookingService = bookingService;
        this.bookingRepository = bookingRepository;
    }

    @PostMapping
    public Booking create(@RequestBody CreateBookingRequest req) {
        return bookingService.createBooking(req.getBidId(), req.getAllocatedTrucks(), req.getFinalRate());
    }

    @GetMapping("/{id}")
    public Booking get(@PathVariable UUID id) {
        return bookingRepository.findById(id).orElse(null);
    }

    @PatchMapping("/{id}/cancel")
    public Booking cancel(@PathVariable UUID id) {
        return bookingService.cancelBooking(id);
    }
   
    @GetMapping
    public List<Booking> list(
            @RequestParam(required = false) UUID loadId,
            @RequestParam(required = false) BookingStatus status
    ) {
        if (loadId != null && status != null) {
            return bookingRepository.findByLoad_LoadIdAndStatus(loadId, status);
        }
        return bookingRepository.findAll();
    }

}
