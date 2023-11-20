package com.example.bookingapp.model;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

public class FavouriteAccommodationListing   implements Parcelable {
    private Long id;
    private String title;
    private String description;
    private int image;
    private boolean favourite;
    private int totalPrice;
    private int pricePerDay;
    private float rating;
    private String address;

    public FavouriteAccommodationListing(Long id, String title, String description, int image, boolean favourite,
                                int totalPrice, int  pricePerDay, float rating, String address) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.image = image;
        this.favourite = favourite;
        this.totalPrice = totalPrice;
        this.pricePerDay = pricePerDay;
        this.rating = rating;
        this.address = address;
    }

    public FavouriteAccommodationListing() {
    }

    protected FavouriteAccommodationListing(Parcel in) {
        id = in.readLong();
        title = in.readString();
        description = in.readString();
        image = in.readInt();
        favourite = in.readInt() == 1;
        totalPrice = in.readInt();
        pricePerDay = in.readInt();
        rating = in.readFloat();
        address = in.readString();
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

    public int getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(int totalPrice) {
        this.totalPrice = totalPrice;
    }

    public int getPricePerDay() {
        return pricePerDay;
    }

    public void setPricePerDay(int pricePerDay) {
        this.pricePerDay = pricePerDay;
    }


    public boolean getFavorite() {
        return favourite;
    }

    public void setFavourite(boolean isFavorite) {
        this.favourite = isFavorite;
    }


    public float getRating() {
        return rating;
    }

    public void setRating(float rating) {
        this.rating = rating;
    }

    public void setAddress(String address) { this.address = address; }

    public String getAddress() { return address; }

    @Override
    public String toString() {
        return "AccommodationListing{" +
                "title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", image='" + image + '\'' +
                ", is favourite for user='" + favourite + '\'' +
                ", total price='" + totalPrice + '\'' +
                ", price per day='" + pricePerDay + '\'' +
                ", rating='" + rating + '\'' +
                ", address='" + address + '\'' +
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
        dest.writeInt(favourite ? 1 : 0);
        dest.writeInt(totalPrice);
        dest.writeInt(pricePerDay);
        dest.writeFloat(rating);
        dest.writeString(address);
    }

    public static final Creator<FavouriteAccommodationListing> CREATOR = new Creator<FavouriteAccommodationListing>() {
        @Override
        public FavouriteAccommodationListing createFromParcel(Parcel in) {
            return new FavouriteAccommodationListing(in);
        }

        @Override
        public FavouriteAccommodationListing[] newArray(int size) {
            return new FavouriteAccommodationListing[size];
        }
    };


}