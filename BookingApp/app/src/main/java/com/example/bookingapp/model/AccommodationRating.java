package com.example.bookingapp.model;

import java.util.Date;

public class AccommodationRating {
    private Long id;
    private Accommodation accommodation;
    private User guest;
    private float rate;
    private Date date;
    private boolean reported;

    public AccommodationRating() {
    }

    public AccommodationRating(Long id, Accommodation accommodation, User guest, float rate, Date date, boolean reported) {
        this.id = id;
        this.accommodation = accommodation;
        this.guest = guest;
        this.rate = rate;
        this.date = date;
        this.reported = reported;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Accommodation getAccommodation() {
        return accommodation;
    }

    public void setAccommodation(Accommodation accommodation) {
        this.accommodation = accommodation;
    }

    public User getGuest() {
        return guest;
    }

    public void setGuest(User guest) {
        this.guest = guest;
    }

    public float getRate() {
        return rate;
    }

    public void setRate(float rate) {
        this.rate = rate;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public boolean isReported() {
        return reported;
    }

    public void setReported(boolean reported) {
        this.reported = reported;
    }
}
