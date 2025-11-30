package com.tms.backend.dto;

import java.time.Instant;

public class CreateLoadRequest {

    private String shipperId;
    private String loadingCity;
    private String unloadingCity;
    private Instant loadingDate;
    private String productType;
    private double weight;
    private String weightUnit;
    private String truckType;
    private int noOfTrucks;

    public CreateLoadRequest() {}

    public String getShipperId() { return shipperId; }
    public void setShipperId(String shipperId) { this.shipperId = shipperId; }

    public String getLoadingCity() { return loadingCity; }
    public void setLoadingCity(String loadingCity) { this.loadingCity = loadingCity; }

    public String getUnloadingCity() { return unloadingCity; }
    public void setUnloadingCity(String unloadingCity) { this.unloadingCity = unloadingCity; }

    public Instant getLoadingDate() { return loadingDate; }
    public void setLoadingDate(Instant loadingDate) { this.loadingDate = loadingDate; }

    public String getProductType() { return productType; }
    public void setProductType(String productType) { this.productType = productType; }

    public double getWeight() { return weight; }
    public void setWeight(double weight) { this.weight = weight; }

    public String getWeightUnit() { return weightUnit; }
    public void setWeightUnit(String weightUnit) { this.weightUnit = weightUnit; }

    public String getTruckType() { return truckType; }
    public void setTruckType(String truckType) { this.truckType = truckType; }

    public int getNoOfTrucks() { return noOfTrucks; }
    public void setNoOfTrucks(int noOfTrucks) { this.noOfTrucks = noOfTrucks; }
}
