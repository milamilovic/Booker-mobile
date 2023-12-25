package com.example.bookingapp.dto.accommodation;

import com.example.bookingapp.enums.AccommodationType;

import java.util.Date;

public class CreateAccommodationDTO {
    private Long id;
    private String title;
    private String description;
    private String shortDescription;
    private AddressDTO address;
    private String[] amenities;
    private String[] images;
    private AccommodationType type;
    private Date startDate;
    private Date endDate;
    private CreatePriceDTO price;
    private int min_capacity;
    private int max_capacity;

    public CreateAccommodationDTO() {
    }

    public CreateAccommodationDTO(Long id, String title, String description, String shortDescription, AddressDTO address, String[] amenities, String[] images, AccommodationType type, Date startDate, Date endDate, CreatePriceDTO price, int min_capacity, int max_capacity) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.shortDescription = shortDescription;
        this.address = address;
        this.amenities = amenities;
        this.images = images;
        this.type = type;
        this.startDate = startDate;
        this.endDate = endDate;
        this.price = price;
        this.min_capacity = min_capacity;
        this.max_capacity = max_capacity;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getShortDescription() {
        return shortDescription;
    }

    public void setShortDescription(String shortDescription) {
        this.shortDescription = shortDescription;
    }

    public AddressDTO getAddress() {
        return address;
    }

    public void setAddress(AddressDTO address) {
        this.address = address;
    }

    public String[] getAmenities() {
        return amenities;
    }

    public void setAmenities(String[] amenities) {
        this.amenities = amenities;
    }

    public String[] getImages() {
        return images;
    }

    public void setImages(String[] images) {
        this.images = images;
    }

    public AccommodationType getType() {
        return type;
    }

    public void setType(AccommodationType type) {
        this.type = type;
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

    public CreatePriceDTO getPrice() {
        return price;
    }

    public void setPrice(CreatePriceDTO price) {
        this.price = price;
    }

    public int getMin_capacity() {
        return min_capacity;
    }

    public void setMin_capacity(int min_capacity) {
        this.min_capacity = min_capacity;
    }

    public int getMax_capacity() {
        return max_capacity;
    }

    public void setMax_capacity(int max_capacity) {
        this.max_capacity = max_capacity;
    }
}
