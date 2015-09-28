package com.dto;

import java.io.Serializable;

public class Car implements Serializable, Cloneable {

    private int carId;
    private int clientId;
    private String make = "";
    private String model = "";
    private String year = "";
    private String VIN = "";

    public int getCarId() {
        return carId;
    }

    public void setCarId(int carId) {
        this.carId = carId;
    }

    public String getMake() {
        return make;
    }

    public void setMake(String make) {
        this.make = make;
    }

    public int getClientId() {
        return clientId;
    }

    public void setClientId(int clientId) {
        this.clientId = clientId;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public String getVIN() {
        return VIN;
    }

    public void setVIN(String VIN) {
        this.VIN = VIN;
    }

    @Override
    public String toString() {
        return "Car{" + "carId=" + carId + ", clientId=" + clientId
                + ", make=" + make + ", model=" + model + ", year=" + year
                + ", VIN=" + getVIN() + "}";
    }
}
