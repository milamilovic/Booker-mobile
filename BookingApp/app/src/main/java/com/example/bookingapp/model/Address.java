package com.example.bookingapp.model;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

public class Address  implements Parcelable {
    private Long id;
    private String street;
    private String city;
    private double latitude;
    private double longitude;
    private Object accommodation;

    public Address(Long id, String street, String city, double latitude, double longitude, Object accommodation) {
        this.id = id;
        this.street = street;
        this.city = city;
        this.longitude = longitude;
        this.latitude = latitude;
        this.accommodation = accommodation;
    }

    public Object getAccommodation() {
        return accommodation;
    }

    public void setAccommodation(Object accommodation) {
        this.accommodation = accommodation;
    }

    public double getLatitude() {
        return latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public String getCity() {
        return city;
    }

    public String getStreet() {
        return street;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }

    protected Address(Parcel in) {
        id = in.readLong();
        street = in.readString();
        city = in.readString();
        latitude = in.readDouble();
        longitude = in.readDouble();
    }

    public Address() {}

    @Override
    public void writeToParcel(@NonNull Parcel dest, int flags) {
        dest.writeLong(id);
        dest.writeString(street);
        dest.writeString(city);
        dest.writeDouble(latitude);
        dest.writeDouble(longitude);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<Address> CREATOR = new Creator<Address>() {
        @Override
        public Address createFromParcel(Parcel in) {
            return new Address(in);
        }

        @Override
        public Address[] newArray(int size) {
            return new Address[size];
        }
    };
}
