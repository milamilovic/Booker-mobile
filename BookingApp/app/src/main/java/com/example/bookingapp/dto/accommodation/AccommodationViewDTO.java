package com.example.bookingapp.dto.accommodation;

import com.example.bookingapp.model.AccommodationComment;
import com.example.bookingapp.model.AccommodationRating;
import com.example.bookingapp.model.Address;
import com.example.bookingapp.model.Amenity;
import com.example.bookingapp.model.Image;
import com.example.bookingapp.model.Price;

import java.util.List;

public class AccommodationViewDTO {
    private Long id;
    private String title;
    private String description;
    private Address address;
    private List<Amenity> amenities;
    private List<Image> images;
    private List<AvailabilityDTO> availabilities;
    private List<Price> prices;
    private List<AccommodationRating> ratings;
    private List<AccommodationComment> comments;
    private Long owner_id;
    private int min_capacity;
    private int max_capacity;

    public AccommodationViewDTO() {
    }

    public AccommodationViewDTO(Long id, String title, String description, Address address, List<Amenity> amenities, List<Image> images, List<AvailabilityDTO> availabilities, List<Price> prices, List<AccommodationRating> ratings, List<AccommodationComment> comments, Long owner_id, int min_capacity, int max_capacity) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.address = address;
        this.amenities = amenities;
        this.images = images;
        this.availabilities = availabilities;
        this.prices = prices;
        this.ratings = ratings;
        this.comments = comments;
        this.owner_id = owner_id;
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

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public List<Amenity> getAmenities() {
        return amenities;
    }

    public void setAmenities(List<Amenity> amenities) {
        this.amenities = amenities;
    }

    public List<Image> getImages() {
        return images;
    }

    public void setImages(List<Image> images) {
        this.images = images;
    }

    public List<AvailabilityDTO> getAvailabilities() {
        return availabilities;
    }

    public void setAvailabilities(List<AvailabilityDTO> availabilities) {
        this.availabilities = availabilities;
    }

    public List<Price> getPrices() {
        return prices;
    }

    public void setPrices(List<Price> prices) {
        this.prices = prices;
    }

    public List<AccommodationRating> getRatings() {
        return ratings;
    }

    public void setRatings(List<AccommodationRating> ratings) {
        this.ratings = ratings;
    }

    public List<AccommodationComment> getComments() {
        return comments;
    }

    public void setComments(List<AccommodationComment> comments) {
        this.comments = comments;
    }

    public Long getOwner_id() {
        return owner_id;
    }

    public void setOwner_id(Long owner_id) {
        this.owner_id = owner_id;
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
