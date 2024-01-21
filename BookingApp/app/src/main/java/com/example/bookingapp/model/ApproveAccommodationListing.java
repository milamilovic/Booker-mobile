package com.example.bookingapp.model;

import android.os.Build;
import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

public class ApproveAccommodationListing implements Parcelable {
    private Long id;
    private String title;
    private String description;
    private Image image;
    private float rating;
    private boolean approved;

    public ApproveAccommodationListing(Long id, String title, String description, Image image, float rating, boolean approved) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.image = image;
        this.rating = rating;
        this.approved = approved;
    }

    public ApproveAccommodationListing() {
    }

    protected ApproveAccommodationListing(Parcel in) {
        id = in.readLong();
        title = in.readString();
        description = in.readString();
        image = in.readParcelable(Image.class.getClassLoader());
        rating = in.readFloat();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            approved = in.readBoolean();
        }
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

    public Image getImage() {
        return image;
    }

    public void setImage(Image image) {
        this.image = image;
    }

    public float getRating() {
        return rating;
    }

    public void setRating(float rating) {
        this.rating = rating;
    }

    public boolean getApproved(){ return approved;}

    public void setApproved(boolean approved){this.approved = approved;}

    @Override
    public String toString() {
        return "AccommodationListing{" +
                "title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", approved='" + approved + '\'' +
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
        dest.writeParcelable(new Image(), flags);
        dest.writeFloat(rating);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            dest.writeBoolean(approved);
        }
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
