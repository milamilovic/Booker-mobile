package com.example.bookingapp.model;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

public class FavouriteAccommodationListing   implements Parcelable {
    Long id;
    String title;
    String shortDescription;
    Image image;
    Double avgPrice;
    float avgRating;
    Address address;

    public FavouriteAccommodationListing(Long id, String title, String description, Image image,
                                double totalPrice, float rating, Address address) {
        this.id = id;
        this.title = title;
        this.shortDescription = description;
        this.image = image;
        this.avgPrice = totalPrice;
        this.avgRating = rating;
        this.address = address;
    }

    public FavouriteAccommodationListing() {
    }

    protected FavouriteAccommodationListing(Parcel in) {
        id = in.readLong();
        title = in.readString();
        shortDescription = in.readString();
        image = in.readParcelable(Image.class.getClassLoader());
        avgPrice = in.readDouble();
        avgRating = in.readFloat();
        address = in.readParcelable(Address.class.getClassLoader());
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

    public String getShortDescription() {
        return shortDescription;
    }

    public void setShortDescription(String description) {
        this.shortDescription = description;
    }

    public Image getImage() {
        return image;
    }

    public void setImage(Image image) {
        this.image = image;
    }

    public double getAvgPrice() {
        return avgPrice;
    }

    public void setAvgPrice(double totalPrice) {
        this.avgPrice = totalPrice;
    }

    public float getAvgRating() {
        return avgRating;
    }

    public void setAvgRating(float rating) {
        this.avgRating = rating;
    }

    public void setAddress(Address address) { this.address = address; }

    public Address getAddress() { return address; }

    @Override
    public String toString() {
        return "AccommodationListing{" +
                "title='" + title + '\'' +
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
        dest.writeString(shortDescription);
        dest.writeParcelable(image, 4);
        dest.writeDouble(avgPrice);
        dest.writeFloat(avgRating);
        dest.writeParcelable(address, 5);
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