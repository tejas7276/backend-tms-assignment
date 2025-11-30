package com.tms.backend.dto;

import java.util.UUID;

public class CreateBookingRequest {

    private UUID bidId;
    private int allocatedTrucks;
    private double finalRate;

    public CreateBookingRequest() {}

    public UUID getBidId() { return bidId; }
    public void setBidId(UUID bidId) { this.bidId = bidId; }

    public int getAllocatedTrucks() { return allocatedTrucks; }
    public void setAllocatedTrucks(int allocatedTrucks) { this.allocatedTrucks = allocatedTrucks; }

    public double getFinalRate() { return finalRate; }
    public void setFinalRate(double finalRate) { this.finalRate = finalRate; }
}
