package com.example.bookingapp.dto.commentsAndRatings;

public class CreateOwnerCommentDTO {
    private Long ownerId;
    private Long guestId;
    private String content;
    private double rating;

    public CreateOwnerCommentDTO() {
    }

    public CreateOwnerCommentDTO(Long ownerId, Long guestId, String content, double rating) {
        this.ownerId = ownerId;
        this.guestId = guestId;
        this.content = content;
        this.rating = rating;
    }

    public Long getOwnerId() {
        return ownerId;
    }

    public void setOwnerId(Long ownerId) {
        this.ownerId = ownerId;
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
