package com.example.bookingapp.model;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

public class Image implements Parcelable {
    Long id;
    String path_mobile;
    String path_front;
    private Object accommodation;

    public Image(){}

    public Image(Long id, String path_mobile, String path_front, Object accommodation) {
        this.id = id;
        this.accommodation = accommodation;
        this.path_front = path_front;
        this.path_mobile = path_mobile;
    }

    public Long getId() { return this.id; }

    public void setId(Long id) {
        this.id = id;
    }

    public Object getAccommodation() {
        return accommodation;
    }

    public void setAccommodation(Object accommodation) {
        this.accommodation = accommodation;
    }

    public String getPath_front() {
        return path_front;
    }

    public String getPath_mobile() {
        return path_mobile;
    }

    public void setPath_front(String path) {
        this.path_front = path;
    }

    public void setPath_mobile(String path_mobile) {
        this.path_mobile = path_mobile;
    }

    protected Image(Parcel in) {
        id = in.readLong();
        path_mobile = in.readString();
        path_front = in.readString();
    }

    @Override
    public void writeToParcel(@NonNull Parcel dest, int flags) {
        dest.writeLong(id);
        dest.writeString(path_mobile);
        dest.writeString(path_front);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<Image> CREATOR = new Creator<Image>() {
        @Override
        public Image createFromParcel(Parcel in) {
            return new Image(in);
        }

        @Override
        public Image[] newArray(int size) {
            return new Image[size];
        }
    };
}
