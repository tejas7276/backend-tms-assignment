package com.tms.backend.repository;

import com.tms.backend.entity.Transporter;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface TransporterRepository extends JpaRepository<Transporter, UUID> {
}
