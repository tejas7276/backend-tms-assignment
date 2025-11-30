package com.tms.backend.service;

import com.tms.backend.entity.AvailableTruck;
import com.tms.backend.entity.Transporter;
import com.tms.backend.exception.ResourceNotFoundException;
import com.tms.backend.repository.TransporterRepository;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.UUID;

@Service
public class TransporterService {

    private final TransporterRepository transporterRepository;

    public TransporterService(TransporterRepository transporterRepository) {
        this.transporterRepository = transporterRepository;
    }

    public Transporter createTransporter(Transporter transporter) {
        return transporterRepository.save(transporter);
    }

    public Transporter getTransporter(UUID id) {
        return transporterRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Transporter not found: " + id));
    }

    public Transporter updateTruckCapacity(UUID id, List<AvailableTruck> trucks) {
        Transporter transporter = getTransporter(id);
        transporter.setAvailableTrucks(trucks);
        return transporterRepository.save(transporter);
    }
}
