package com.example.bookingapp.dto.accommodation;

import com.example.bookingapp.enums.PriceType;

import java.util.Date;

public class CreatePriceDTO {
    private double cost;
    private String fromDate;
    private String toDate;
    private PriceType type;

    public CreatePriceDTO() {
    }

    public CreatePriceDTO(double cost, String fromDate, String toDate, PriceType type) {
        this.cost = cost;
        this.fromDate = fromDate;
        this.toDate = toDate;
        this.type = type;
    }

    public double getCost() {
        return cost;
    }

    public void setCost(double cost) {
        this.cost = cost;
    }

    public String getFromDate() {
        return fromDate;
    }

    public void setFromDate(String fromDate) {
        this.fromDate = fromDate;
    }

    public String getToDate() {
        return toDate;
    }

    public void setToDate(String toDate) {
        this.toDate = toDate;
    }

    public PriceType getType() {
        return type;
    }

    public void setType(PriceType type) {
        this.type = type;
    }
}
