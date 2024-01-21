package com.example.bookingapp.dto.accommodation;

import java.util.Date;

public class AvailabilityDTO {
    private Date startDate;
    private Date endDate;

    public AvailabilityDTO() {
    }

    public AvailabilityDTO(Date startDate, Date endDate) {
        this.startDate = startDate;
        this.endDate = endDate;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }
}
