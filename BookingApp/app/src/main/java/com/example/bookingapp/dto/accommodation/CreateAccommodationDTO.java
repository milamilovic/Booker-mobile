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
}
