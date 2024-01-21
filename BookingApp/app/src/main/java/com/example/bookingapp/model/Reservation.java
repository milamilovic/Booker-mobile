package com.example.bookingapp.model;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

import com.example.bookingapp.enums.ReservationRequestStatus;
import com.example.bookingapp.enums.ReservationStatus;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Reservation implements Parcelable{
    private Long id;
    private Long guestId;
    private Long accommodationId;
    private String fromDate;
    private String toDate;
    private int numberOfGuests;
    private ReservationRequestStatus requestStatus;
    private ReservationStatus status;
    private boolean deleted;
    private double price;
    private String toTime;

    public Reservation(Long id, Long guestId, Long accommodation, String from, String to,
                                   int guests, ReservationRequestStatus requestStatus,
                       ReservationStatus status, boolean deleted, double price, String toTime) {
        this.id = id;
        this.guestId = guestId;
        this.accommodationId = accommodation;
        this.price = price;
        this.numberOfGuests = guests;
        this.deleted = deleted;
        this.status = status;
        this.fromDate = from;
        this.toDate = to;
        this.toTime = toTime;
        this.requestStatus = requestStatus;
    }

    public Reservation() {
    }

    protected Reservation(Parcel in) {
        id = in.readLong();
        guestId = in.readLong();
        accommodationId = in.readLong();
        numberOfGuests = in.readInt();
        price = in.readDouble();
        deleted = in.readInt()==0;
        status = (ReservationStatus) in.readSerializable();
        requestStatus = (ReservationRequestStatus) in.readSerializable();
        fromDate = in.readString();
        toDate = in.readString();
        toTime = in.readString();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public ReservationRequestStatus getRequestStatus() {
        return requestStatus;
    }

    public void setRequestStatus(ReservationRequestStatus status) {
        this.requestStatus = status;
    }

    public void setStatus(ReservationStatus status) {
        this.status = status;
    }

    public ReservationStatus getStatus() {
        return status;
    }

    public int getNumberOfGuests() {
        return numberOfGuests;
    }

    public void setNumberOfGuests(int image) {
        this.numberOfGuests = image;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double totalPrice) {
        this.price = totalPrice;
    }

    public boolean isDeleted() {
        return deleted;
    }

    public void setDeleted(boolean pricePerDay) {
        this.deleted = pricePerDay;
    }

    public String getFromDate() {
        return fromDate;
    }

    public void setFromDate(String fromDate) {
        this.fromDate = fromDate;
    }

    public String getToDate() {
        return toDate;
    }

    public void setToDate(String toDate) {
        this.toDate = toDate;
    }


    public void setGuestId(Long guestId) {
        this.guestId = guestId;
    }

    public Long getGuestId() {
        return guestId;
    }

    public Long getAccommodation() {
        return accommodationId;
    }

    public void setAccommodationId(Long accommodation) {
        this.accommodationId = accommodation;
    }

    @Override
    public String toString() {
        return "Reservation{" +
                "guest='" + guestId + '\'' +
                ", status='" + status + '\'' +
                ", from='" + fromDate + '\'' +
                ", to='" + toDate + '\'' +
                '}';
    }

    private String formatDate(Date date) {
        String myFormat="dd.MM.yyyy.";
        SimpleDateFormat dateFormat=new SimpleDateFormat(myFormat);
        return dateFormat.format(date);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(@NonNull Parcel dest, int flags) {
        dest.writeLong(id);
        dest.writeLong(guestId);
        dest.writeLong(accommodationId);
        dest.writeInt(numberOfGuests);
        dest.writeDouble(price);
        if(deleted) {
            dest.writeInt(0);
        } else {
            dest.writeInt(1);
        }
        dest.writeSerializable(status);
        dest.writeSerializable(requestStatus);
        dest.writeString(fromDate);
        dest.writeString(toDate);
        dest.writeString(toTime);
    }

    public static final Parcelable.Creator<Reservation> CREATOR = new Parcelable.Creator<Reservation>() {
        @Override
        public Reservation createFromParcel(Parcel in) {
            return new Reservation(in);
        }

        @Override
        public Reservation[] newArray(int size) {
            return new Reservation[size];
        }
    };

    public String getStatusFormated() {
        return "status: " + status.name();
    }

}
