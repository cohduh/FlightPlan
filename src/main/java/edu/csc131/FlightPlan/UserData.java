package edu.csc131.FlightPlan;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity 
public class UserData {
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Integer id;

    private String address;//remove Me After Testing
    private String carType;
    private double travelTimeByCar;
    private double travelTimeByWalking;
    private double travelTimeByTransit;
    private double travelTimeByBike;

    private double travelDistanceByCar;
    private double travelDistanceByWalking;
    private double travelDistanceByTransit;
    private double travelDistanceByBike;

    private double insurenceRate;//Remove Me
    private double mpg; //Remove Me
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public double gettravelTimeByCar() {
        return travelTimeByCar;
    }

    public void settravelTimeByCar(double travelTimeByCar) {
        this.travelTimeByCar = travelTimeByCar;
    }

    public double getInsurenceRate() {
        return insurenceRate;
    }

    public void setInsurenceRate(double insurenceRate) {
        this.insurenceRate = insurenceRate;
    }

    public double getMpg() {
        return mpg;
    }

    public void setMpg(double mpg) {
        this.mpg = mpg;
    }

    public double getTravelTimeByWalking() {
        return travelTimeByWalking;
    }

    public void setTravelTimeByWalking(double travelTimeByWalking) {
        this.travelTimeByWalking = travelTimeByWalking;
    }

    public String getCarType() {
        return carType;
    }

    public void setCarType(String carType) {
        this.carType = carType;
    }

    public double getTravelTimeByCar() {
        return travelTimeByCar;
    }

    public void setTravelTimeByCar(double travelTimeByCar) {
        this.travelTimeByCar = travelTimeByCar;
    }

    public double getTravelTimeByTransit() {
        return travelTimeByTransit;
    }

    public void setTravelTimeByTransit(double travelTimeByTransit) {
        this.travelTimeByTransit = travelTimeByTransit;
    }

    public double getTravelTimeByBike() {
        return travelTimeByBike;
    }

    public void setTravelTimeByBike(double travelTimeByBike) {
        this.travelTimeByBike = travelTimeByBike;
    }

    public double getTravelDistanceByCar() {
        return travelDistanceByCar;
    }

    public void setTravelDistanceByCar(double travelDistanceByCar) {
        this.travelDistanceByCar = travelDistanceByCar;
    }

    public double getTravelDistanceByWalking() {
        return travelDistanceByWalking;
    }

    public void setTravelDistanceByWalking(double travelDistanceByWalking) {
        this.travelDistanceByWalking = travelDistanceByWalking;
    }

    public double getTravelDistanceByTransit() {
        return travelDistanceByTransit;
    }

    public void setTravelDistanceByTransit(double travelDistanceByTransit) {
        this.travelDistanceByTransit = travelDistanceByTransit;
    }

    public double getTravelDistanceByBike() {
        return travelDistanceByBike;
    }

    public void setTravelDistanceByBike(double travelDistanceByBike) {
        this.travelDistanceByBike = travelDistanceByBike;
    }
    
}
