package com.example.bookingapp.dto.accommodation;

public class UpdateAvailabilityDTO {
    public String startDate;
    public String endDate;
    public CreatePriceDTO createPriceDTO;
    public int deadline;

    public UpdateAvailabilityDTO() {
    }

    public UpdateAvailabilityDTO(String startDate, String endDate, CreatePriceDTO createPriceDTO, int deadline) {
        this.startDate = startDate;
        this.endDate = endDate;
        this.createPriceDTO = createPriceDTO;
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

    public CreatePriceDTO getCreatePriceDTO() {
        return createPriceDTO;
    }

    public void setCreatePriceDTO(CreatePriceDTO createPriceDTO) {
        this.createPriceDTO = createPriceDTO;
    }

    public int getDeadline() {
        return deadline;
    }

    public void setDeadline(int deadline) {
        this.deadline = deadline;
    }
}
