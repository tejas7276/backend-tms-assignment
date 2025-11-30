package com.tms.backend.controller;

import com.tms.backend.dto.CreateBidRequest;
import com.tms.backend.entity.Bid;
import com.tms.backend.entity.enums.BidStatus;
import com.tms.backend.service.BidService;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/bid")
public class BidController {

    private final BidService bidService;

    public BidController(BidService bidService) {
        this.bidService = bidService;
    }

    @PostMapping
    public Bid create(@RequestBody CreateBidRequest req) {
        return bidService.createBid(req.getLoadId(), req.getTransporterId(),
                req.getProposedRate(), req.getTrucksOffered());
    }

    @GetMapping
    public List<Bid> filter(
            @RequestParam(required = false) UUID loadId,
            @RequestParam(required = false) UUID transporterId,
            @RequestParam(required = false) BidStatus status
    ) {
        return bidService.filter(loadId, transporterId, status);
    }

    @GetMapping("/{id}")
    public Bid get(@PathVariable UUID id) {
        return bidService.filter(id, null, null).stream().findFirst().orElse(null);
    }

    @PatchMapping("/{id}/reject")
    public Bid reject(@PathVariable UUID id) {
        return bidService.rejectBid(id);
    }
}
