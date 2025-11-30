package com.tms.backend.entity;

import jakarta.persistence.Embeddable;

@Embeddable
public class AvailableTruck {

    private String truckType;
    private int count;

    public AvailableTruck() {}

    public AvailableTruck(String truckType, int count) {
        this.truckType = truckType;
        this.count = count;
    }

    public String getTruckType() {
        return truckType;
    }

    public void setTruckType(String truckType) {
        this.truckType = truckType;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }
}
