package com.tms.backend.controller;

import com.tms.backend.entity.AvailableTruck;
import com.tms.backend.entity.Transporter;
import com.tms.backend.service.TransporterService;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/transporter")
public class TransporterController {

    private final TransporterService transporterService;

    public TransporterController(TransporterService transporterService) {
        this.transporterService = transporterService;
    }

    @PostMapping
    public Transporter create(@RequestBody Transporter transporter) {
        return transporterService.createTransporter(transporter);
    }

    @GetMapping("/{id}")
    public Transporter get(@PathVariable UUID id) {
        return transporterService.getTransporter(id);
    }

    @PutMapping("/{id}/trucks")
    public Transporter update(@PathVariable UUID id, @RequestBody List<AvailableTruck> trucks) {
        return transporterService.updateTruckCapacity(id, trucks);
    }
}
