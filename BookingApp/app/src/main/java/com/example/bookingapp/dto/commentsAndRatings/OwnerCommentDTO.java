package com.example.bookingapp.dto.commentsAndRatings;

import com.example.bookingapp.model.ProfilePicture;

import java.util.Date;

public class OwnerCommentDTO {
    private Long id;
    private Long ownerId;
    private Long guestId;
    private String guestName;
    private String guestSurname;
    private ProfilePicture guestProfilePicture;
    private String content;
    private double rating;
    private Date date;
    private boolean reported;
    private boolean deleted;
    private boolean approved;

    public OwnerCommentDTO() {
    }

    public OwnerCommentDTO(Long id, Long ownerId, Long guestId, String guestName, String guestSurname, ProfilePicture guestProfilePicture, String content, double rating, Date date, boolean reported, boolean deleted, boolean approved) {
        this.id = id;
        this.ownerId = ownerId;
        this.guestId = guestId;
        this.guestName = guestName;
        this.guestSurname = guestSurname;
        this.guestProfilePicture = guestProfilePicture;
        this.content = content;
        this.rating = rating;
        this.date = date;
        this.reported = reported;
        this.deleted = deleted;
        this.approved = approved;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public String getGuestName() {
        return guestName;
    }

    public void setGuestName(String guestName) {
        this.guestName = guestName;
    }

    public String getGuestSurname() {
        return guestSurname;
    }

    public void setGuestSurname(String guestSurname) {
        this.guestSurname = guestSurname;
    }

    public ProfilePicture getGuestProfilePicture() {
        return guestProfilePicture;
    }

    public void setGuestProfilePicture(ProfilePicture guestProfilePicture) {
        this.guestProfilePicture = guestProfilePicture;
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

    public boolean isDeleted() {
        return deleted;
    }

    public void setDeleted(boolean deleted) {
        this.deleted = deleted;
    }
    public boolean isApproved() {
        return approved;
    }

    public void setApproved(boolean approved) {
        this.approved = approved;
    }
}
