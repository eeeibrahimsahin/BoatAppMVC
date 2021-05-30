package com.company.boatApp.Model;

public class Boat {
    /**
     * Two types boat: Gasoline and Electricity
     * hourlyPrice: Hourly price's currency is euro.
     */
    private int boatId; //We can generate automatically
    private BoatType type;
    private int seats;
    private int chargingTime;
    private double minimumPricePerHour;

    public Boat() {
    }

    public Boat(int boatId, BoatType type, int seats, double minimumPricePerHour) {
        this.boatId = boatId;
        this.type = type;
        this.seats = seats;
        this.minimumPricePerHour = minimumPricePerHour;
    }

    public Boat(int boatId, BoatType type, int seats, int chargingTime, double minimumPricePerHour) {
        this.boatId = boatId;
        this.type = type;
        this.seats = seats;
        this.chargingTime = chargingTime;
        this.minimumPricePerHour = minimumPricePerHour;
    }

    public BoatType getType() {
        return type;
    }

    public int getBoatId() {
        return boatId;
    }

    public void setType(BoatType type) {
        this.type = type;
    }

    public int getSeats() {
        return seats;
    }

    public void setSeats(int seats) {
        this.seats = seats;
    }

    public int getChargingTime() {
        return chargingTime;
    }

    public void setChargingTime(int chargingTime) {
        this.chargingTime = chargingTime;
    }

    public double getMinimumPricePerHour() {
        return minimumPricePerHour;
    }

    public void setMinimumPricePerHour(double minimumPricePerHour) {
        this.minimumPricePerHour = minimumPricePerHour;
    }

    @Override
    public String toString() {
        return "Boat{" +
                "boatId=" + boatId +
                ", type=" + type +
                ", seats=" + seats +
                ", chargingTime=" + chargingTime +
                ", minimumPricePerHour=" + minimumPricePerHour +
                '}';
    }
}
