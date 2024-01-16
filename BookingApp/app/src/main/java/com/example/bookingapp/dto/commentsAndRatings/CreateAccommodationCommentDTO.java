package com.example.bookingapp.dto.commentsAndRatings;

public class CreateAccommodationCommentDTO {
    private Long accommodationId;
    private Long guestId;
    private String content;
    private double rating;

    public CreateAccommodationCommentDTO() {
    }

    public CreateAccommodationCommentDTO(Long accommodationId, Long guestId, String content, double rating) {
        this.accommodationId = accommodationId;
        this.guestId = guestId;
        this.content = content;
        this.rating = rating;
    }

    public Long getAccommodationId() {
        return accommodationId;
    }

    public void setAccommodationId(Long accommodationId) {
        this.accommodationId = accommodationId;
    }

    public Long getGuestId() {
        return guestId;
    }

    public void setGuestId(Long guestId) {
        this.guestId = guestId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public double getRating() {
        return rating;
    }

    public void setRating(double rating) {
        this.rating = rating;
    }
}
