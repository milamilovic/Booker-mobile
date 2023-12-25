package com.example.bookingapp.dto.accommodation;

public class AddressDTO {
    private Long id;
    private String street;
    private String city;
    private double latitude;
    private double longitude;

    public AddressDTO() {
    }

    public AddressDTO(Long id, String street, String city, double latitude, double longitude) {
        this.id = id;
        this.street = street;
        this.city = city;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }
}
