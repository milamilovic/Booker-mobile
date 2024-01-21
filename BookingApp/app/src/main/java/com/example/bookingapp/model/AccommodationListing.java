package com.example.bookingapp.model;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

public class AccommodationListing  implements Parcelable {
    private Long id;
    private String title;
    private String description;
    private Image image;
    private double totalPrice;
    private double pricePerDay;
    private float rating;

    public AccommodationListing(Long id, String title, String description, Image image, boolean favourite,
                                double totalPrice, double  pricePerDay, float rating) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.image = image;
        this.totalPrice = totalPrice;
        this.pricePerDay = pricePerDay;
        this.rating = rating;
    }

    public AccommodationListing() {
    }

    protected AccommodationListing(Parcel in) {
        id = in.readLong();
        title = in.readString();
        description = in.readString();
        image = in.readParcelable(Image.class.getClassLoader());
        totalPrice = in.readDouble();
        pricePerDay = in.readDouble();
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

    public Image getImage() {
        return image;
    }

    public void setImage(Image image) {
        this.image = image;
    }

    public double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(double totalPrice) {
        this.totalPrice = totalPrice;
    }

    public double getPricePerDay() {
        return pricePerDay;
    }

    public void setPricePerDay(double pricePerDay) {
        this.pricePerDay = pricePerDay;
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
                ", total price='" + totalPrice + '\'' +
                ", price per day='" + pricePerDay + '\'' +
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
        dest.writeParcelable(image, 4);
        dest.writeDouble(totalPrice);
        dest.writeDouble(pricePerDay);
        dest.writeFloat(rating);
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
