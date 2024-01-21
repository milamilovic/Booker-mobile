package com.example.bookingapp.dto.accommodation;

public class UpdateAvailabilityDTO {
    public String startDate;
    public String endDate;
    public CreatePriceDTO price;
    public int deadline;

    public UpdateAvailabilityDTO() {
    }

    public UpdateAvailabilityDTO(String startDate, String endDate, CreatePriceDTO price, int deadline) {
        this.startDate = startDate;
        this.endDate = endDate;
        this.price = price;
        this.deadline = deadline;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public CreatePriceDTO getPrice() {
        return price;
    }

    public void setPrice(CreatePriceDTO price) {
        this.price = price;
    }

    public int getDeadline() {
        return deadline;
    }

    public void setDeadline(int deadline) {
        this.deadline = deadline;
    }
}
