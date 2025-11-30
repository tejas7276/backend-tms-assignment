package com.tms.backend.repository;

import com.tms.backend.entity.Booking;
import com.tms.backend.entity.enums.BookingStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface BookingRepository extends JpaRepository<Booking, UUID> {

    List<Booking> findByLoad_LoadIdAndStatus(UUID loadId, BookingStatus status);
}
