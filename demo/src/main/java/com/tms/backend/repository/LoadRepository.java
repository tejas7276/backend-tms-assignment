package com.tms.backend.repository;

import com.tms.backend.entity.Load;
import com.tms.backend.entity.enums.LoadStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface LoadRepository extends JpaRepository<Load, UUID> {

    Page<Load> findByShipperId(String shipperId, Pageable pageable);

    Page<Load> findByShipperIdAndStatus(String shipperId, LoadStatus status, Pageable pageable);
}
