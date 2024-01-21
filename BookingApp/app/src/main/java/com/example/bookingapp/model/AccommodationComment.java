package com.example.bookingapp.model;

import java.util.Date;

public class AccommodationComment {
    private Long id;
    private Accommodation accommodation;
    private User user;
    private String content;
    private Date date;
    private boolean reported;

    public AccommodationComment() {
    }

    public AccommodationComment(Long id, Accommodation accommodation, User user, String content, Date date, boolean reported) {
        this.id = id;
        this.accommodation = accommodation;
        this.user = user;
        this.content = content;
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

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
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
