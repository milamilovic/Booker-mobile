package com.example.bookingapp.model;

import android.os.Build;
import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

import java.util.ArrayList;
import java.util.List;

public class Accommodation implements Parcelable {
    private Long id;
    private String title;
    private String description;
    private Address address;
    private List<Amenity> amenities;
    private List<Image> images;
    private List<Availability> availabilities;
    private List<Price> prices;
    private List<Object> comments;
    private List<Object> ratings;
    private Long ownerId;
    private int min_capacity;
    private int max_capacity;
    private boolean approved;

    public Accommodation(Long id, String title, String description, List<Image> images, List<Availability> availabilities,
                         List<Price> prices, List<Object> comments, List<Object> ratings, Long ownerId,
                         ArrayList<Amenity> amenities, int max_capacity, int min_capacity, boolean approved, Address address) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.images = images;
        this.availabilities = availabilities;
        this.prices = prices;
        this.comments = comments;
        this.ratings = ratings;
        this.ownerId = ownerId;
        this.max_capacity = max_capacity;
        this.min_capacity = min_capacity;
        this.amenities = amenities;
        this.approved = approved;
        this.address = address;
    }

    public Accommodation() {
    }

    protected Accommodation(Parcel in) {
        id = in.readLong();
        title = in.readString();
        description = in.readString();
        images = in.readArrayList(Image.class.getClassLoader());
        prices = in.readArrayList(Price.class.getClassLoader());
        availabilities = in.readArrayList(Availability.class.getClassLoader());
        comments = in.readArrayList(Object.class.getClassLoader());
        ratings = in.readArrayList(Object.class.getClassLoader());
        address = in.readParcelable(Address.class.getClassLoader());
        ownerId = in.readLong();
        min_capacity = in.readInt();
        max_capacity = in.readInt();
        amenities = in.readArrayList(Amenity.class.getClassLoader());
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

    public List<Image> getImages() {
        return images;
    }

    public void setImages(List<Image> image) {
        this.images = images;
    }


    public List<Price> getPrices() {
        return prices;
    }

    public void setPrices(List<Price> rating) {
        this.prices = rating;
    }


    public List<Availability> getAvailabilities() {
        return availabilities;
    }

    public void setAvailabilities(List<Availability> rating) {
        this.availabilities = rating;
    }

    public int getMin_capacity() {
        return min_capacity;
    }

    public void setMin_capacity(int totalPrice) {
        this.min_capacity = totalPrice;
    }

    public int getMax_capacity() {
        return max_capacity;
    }

    public void setMax_capacity(int pricePerDay) {
        this.max_capacity = pricePerDay;
    }


    public Long getOwnerId() {
        return ownerId;
    }

    public void setOwnerId(Long id) {
        this.ownerId = id;
    }


    public List<Object> getComments() {
        return comments;
    }

    public void setComments(List<Object> rating) {
        this.comments = rating;
    }


    public List<Object> getRatings() {
        return ratings;
    }

    public void setRatings(List<Object> rating) {
        this.ratings = rating;
    }

    public boolean getApproved(){
        return approved;
    }

    public void setApproved(boolean approved){
        this.approved = approved;
    }

    @Override
    public String toString() {
        return "AccommodationListing{" +
                "title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", approved='" + approved + '\'' +
                "}";
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
        dest.writeList(images);
        dest.writeList(prices);
        dest.writeList(availabilities);
        dest.writeList(comments);
        dest.writeList(ratings);
        dest.writeParcelable(address, 5);
        dest.writeLong(ownerId);
        dest.writeInt(min_capacity);
        dest.writeInt(max_capacity);
        dest.writeList(amenities);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            dest.writeBoolean(approved);
        }
    }

    public static final Creator<AccommodationListing> CREATOR = new Creator<AccommodationListing>() {
        @Override
        public AccommodationListing createFromParcel(Parcel in) {
            return new AccommodationListing(in);
        }

        @Override
        public AccommodationListing[] newArray(int size) {
            return new AccommodationListing[size];
        }
    };

}
