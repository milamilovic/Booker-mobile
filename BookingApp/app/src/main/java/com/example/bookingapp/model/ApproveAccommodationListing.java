package com.example.bookingapp.model;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

public class ApproveAccommodationListing implements Parcelable {
    private Long id;
    private String title;
    private String description;
    private int image;
    private float rating;

    public ApproveAccommodationListing(Long id, String title, String description, int image, float rating) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.image = image;
        this.rating = rating;
    }

    public ApproveAccommodationListing() {
    }

    protected ApproveAccommodationListing(Parcel in) {
        id = in.readLong();
        title = in.readString();
        description = in.readString();
        image = in.readInt();
        rating = in.readFloat();
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

    public int getImage() {
        return image;
    }

    public void setImage(int image) {
        this.image = image;
    }

    public float getRating() {
        return rating;
    }

    public void setRating(float rating) {
        this.rating = rating;
    }

    @Override
    public String toString() {
        return "AccommodationListing{" +
                "title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", image='" + image + '\'' +
                ", rating='" + rating + '\'' +
                '}';
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(@NonNull Parcel dest, int flags) {
        dest.writeLong(id);
        dest.writeString(title);
        dest.writeString(description);
        dest.writeInt(image);
        dest.writeFloat(rating);
    }

    public static final Creator<ApproveAccommodationListing> CREATOR = new Creator<ApproveAccommodationListing>() {
        @Override
        public ApproveAccommodationListing createFromParcel(Parcel in) {
            return new ApproveAccommodationListing(in);
        }

        @Override
        public ApproveAccommodationListing[] newArray(int size) {
            return new ApproveAccommodationListing[size];
        }
    };
}
