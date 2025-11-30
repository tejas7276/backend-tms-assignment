package com.tms.backend.dto;

import java.util.UUID;

public class CreateBidRequest {

    private UUID loadId;
    private UUID transporterId;
    private double proposedRate;
    private int trucksOffered;

    public CreateBidRequest() {}

    public UUID getLoadId() { return loadId; }
    public void setLoadId(UUID loadId) { this.loadId = loadId; }

    public UUID getTransporterId() { return transporterId; }
    public void setTransporterId(UUID transporterId) { this.transporterId = transporterId; }

    public double getProposedRate() { return proposedRate; }
    public void setProposedRate(double proposedRate) { this.proposedRate = proposedRate; }

    public int getTrucksOffered() { return trucksOffered; }
    public void setTrucksOffered(int trucksOffered) { this.trucksOffered = trucksOffered; }
}
