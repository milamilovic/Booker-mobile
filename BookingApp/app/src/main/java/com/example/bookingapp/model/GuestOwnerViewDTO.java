package com.example.bookingapp.model;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

public class GuestOwnerViewDTO implements Parcelable {
    Long id;
    String name;
    int numOfCancellations;
    int image;

    public GuestOwnerViewDTO(Long id, String name, int cancellations, int image) {
        this.id = id;
        this.name = name;
        this.numOfCancellations = cancellations;
        this.image = image;
    }

    public GuestOwnerViewDTO() {
    }

    protected GuestOwnerViewDTO(Parcel in) {
        id = in.readLong();
        name = in.readString();
        numOfCancellations = in.readInt();
        image = in.readInt();
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

    public int getNumOfCancellations() {
        return numOfCancellations;
    }

    public void setNumOfCancellations(int cancellations) {
        this.numOfCancellations = cancellations;
    }

    public int getImage() {
        return image;
    }

    public void setImage(int image) {
        this.image = image;
    }

    @Override
    public String toString() {
        return "Amenity{" +
                "name='" + name + '\'' +
                ", cancelations='" + numOfCancellations + '\'' +
                ", image='" + image + '\'' +
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
        dest.writeInt(numOfCancellations);
        dest.writeInt(image);
    }

    public static final Parcelable.Creator<GuestOwnerViewDTO> CREATOR = new Parcelable.Creator<GuestOwnerViewDTO>() {
        @Override
        public GuestOwnerViewDTO createFromParcel(Parcel in) {
            return new GuestOwnerViewDTO(in);
        }

        @Override
        public GuestOwnerViewDTO[] newArray(int size) {
            return new GuestOwnerViewDTO[size];
        }
    };
}
