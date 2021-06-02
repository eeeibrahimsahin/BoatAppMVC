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
    private double remainingCharge;
    private BoatStatus status;
    private double chargeLife;

    public Boat() {
    }

    public Boat(int boatId, BoatType type, int seats, double minimumPricePerHour) {
        this.boatId = boatId;
        this.type = type;
        this.seats = seats;
        this.minimumPricePerHour = minimumPricePerHour;
        this.status = BoatStatus.AVAILABLE;
        if (this.getType() == BoatType.ELECTRICALBOAT) this.chargeLife = 120;
    }

    public Boat(int boatId, BoatType type, int seats, int chargingTime, double minimumPricePerHour) {
        this.boatId = boatId;
        this.type = type;
        this.seats = seats;
        this.chargingTime = chargingTime;
        this.minimumPricePerHour = minimumPricePerHour;
        this.status = BoatStatus.AVAILABLE;
        if (this.getType() == BoatType.ELECTRICALBOAT) this.chargeLife = 120;
    }

    public Boat(int boatId, BoatType type, int seats, int chargingTime, double minimumPricePerHour, double remainingCharge) {
        this.boatId = boatId;
        this.type = type;
        this.seats = seats;
        this.chargingTime = chargingTime;
        this.minimumPricePerHour = minimumPricePerHour;
        this.remainingCharge = remainingCharge;
        this.status = BoatStatus.AVAILABLE;
        if (this.getType() == BoatType.ELECTRICALBOAT)
            this.chargeLife = 120;
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

    public double getRemainingCharge() {
        return remainingCharge;
    }

    public void setRemainingCharge(double remainingCharge) {
        this.remainingCharge = remainingCharge;
    }

    public BoatStatus getStatus() {
        return status;
    }

    public void setStatus(BoatStatus status) {
        this.status = status;
    }

    public double getChargeLife() {
        return chargeLife;
    }

    public void setChargeLife(double chargeLife) {
        if (this.getType() == BoatType.ELECTRICALBOAT) {
            this.chargeLife = chargeLife;
        }
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
