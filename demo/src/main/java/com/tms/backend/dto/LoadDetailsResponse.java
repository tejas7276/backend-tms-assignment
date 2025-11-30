package com.tms.backend.dto;

import com.tms.backend.entity.Bid;
import com.tms.backend.entity.Load;

import java.util.List;

public class LoadDetailsResponse {

    private Load load;
    private List<Bid> activeBids;
    private int remainingTrucks;

    public LoadDetailsResponse() {}

    public Load getLoad() {
        return load;
    }

    public void setLoad(Load load) {
        this.load = load;
    }

    public List<Bid> getActiveBids() {
        return activeBids;
    }

    public void setActiveBids(List<Bid> activeBids) {
        this.activeBids = activeBids;
    }

    public int getRemainingTrucks() {
        return remainingTrucks;
    }

    public void setRemainingTrucks(int remainingTrucks) {
        this.remainingTrucks = remainingTrucks;
    }
}