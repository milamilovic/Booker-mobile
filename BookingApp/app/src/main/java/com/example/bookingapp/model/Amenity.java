package com.example.bookingapp.model;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

public class Amenity implements Parcelable{

    private Long id;
    private String name;

    private Object accommodation;
    private String image_path;

    public Amenity(Long id, String name, String image, Object accommodation) {
        this.id = id;
        this.name = name;
        this.image_path = image;
        this.accommodation = accommodation;
    }

    public Amenity() {
    }

    protected Amenity(Parcel in) {
        id = in.readLong();
        name = in.readString();
        image_path = in.readString();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImage_path() {
        return image_path;
    }

    public void setImage_path(String image) {
        this.image_path = image;
    }

    @Override
    public String toString() {
        return "Amenity{" +
                "name='" + name + '\'' +
                '}';
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(@NonNull Parcel dest, int flags) {
        dest.writeLong(id);
        dest.writeString(name);
        dest.writeString(image_path);
    }

    public static final Parcelable.Creator<Amenity> CREATOR = new Parcelable.Creator<Amenity>() {
        @Override
        public Amenity createFromParcel(Parcel in) {
            return new Amenity(in);
        }

        @Override
        public Amenity[] newArray(int size) {
            return new Amenity[size];
        }
    };
}
