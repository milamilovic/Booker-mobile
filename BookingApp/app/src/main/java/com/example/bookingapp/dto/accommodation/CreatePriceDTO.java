package com.example.bookingapp.dto.accommodation;

import com.example.bookingapp.enums.PriceType;

import java.util.Date;

public class CreatePriceDTO {
    private double cost;
    private Date fromDate;
    private Date toDate;
    private PriceType type;

    public CreatePriceDTO() {
    }

    public CreatePriceDTO(double cost, Date fromDate, Date toDate, PriceType type) {
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

    public Date getFromDate() {
        return fromDate;
    }

    public void setFromDate(Date fromDate) {
        this.fromDate = fromDate;
    }

    public Date getToDate() {
        return toDate;
    }

    public void setToDate(Date toDate) {
        this.toDate = toDate;
    }

    public PriceType getType() {
        return type;
    }

    public void setType(PriceType type) {
        this.type = type;
    }
}
