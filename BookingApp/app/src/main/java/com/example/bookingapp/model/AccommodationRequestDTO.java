package com.example.bookingapp.model;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

import com.example.bookingapp.enums.ReservationRequestStatus;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class AccommodationRequestDTO implements Parcelable {
    private Long id;
    private Long guestId;
    private Long accommodationId;
    private String fromDate;
    private String toDate;
    private int numberOfGuests;
    private ReservationRequestStatus status;
    private double price;
    private boolean deleted;

    public AccommodationRequestDTO(Long id, Long guestId, Long accId,
                                   int guests, double price, boolean deleted,
                                   ReservationRequestStatus status, String from, String to) {
        this.id = id;
        this.guestId = guestId;
        this.accommodationId = accId;
        this.price = price;
        this.numberOfGuests = guests;
        this.deleted = deleted;
        this.status = status;
        this.fromDate = from;
        this.toDate = to;
    }

    public AccommodationRequestDTO() {
    }

    protected AccommodationRequestDTO(Parcel in) {
        id = in.readLong();
        guestId = in.readLong();
        accommodationId = in.readLong();
        numberOfGuests = in.readInt();
        price = in.readDouble();
        deleted = in.readInt()==0;
        status = (ReservationRequestStatus) in.readSerializable();
        fromDate = in.readString();
        toDate = in.readString();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public ReservationRequestStatus getStatus() {
        return status;
    }

    public void setStatus(ReservationRequestStatus status) {
        this.status = status;
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

    public Date getFromDateDate(){
        SimpleDateFormat s = new SimpleDateFormat("yyyy-MM-dd");
        try{
            return s.parse(getFromDate());
        } catch (ParseException e) {

        }
        return null;
    }

    public Date getToDateDate(){
        SimpleDateFormat s = new SimpleDateFormat("yyyy-MM-dd");
        try{
            return s.parse(getToDate());
        } catch (ParseException e) {

        }
        return null;
    }

    public void setToDate(String toDate) {
        this.toDate = toDate;
    }

    public String getDateRange() {
        return formatDate(getFromDateDate()) + " - " + formatDate(getToDateDate());
    }

    public void setGuestId(Long guestId) {
        this.guestId = guestId;
    }

    public Long getGuestId() {
        return guestId;
    }

    public Long getAccommodationId() {
        return accommodationId;
    }

    public void setAccommodationId(Long accommodationId) {
        this.accommodationId = accommodationId;
    }

    @Override
    public String toString() {
        return "AccommodationRequest{" +
                "guest='" + guestId + '\'' +
                "accommodation='" + accommodationId + '\'' +
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
        dest.writeString(fromDate);
        dest.writeString(toDate);
    }

    public static final Creator<AccommodationRequestDTO> CREATOR = new Creator<AccommodationRequestDTO>() {
        @Override
        public AccommodationRequestDTO createFromParcel(Parcel in) {
            return new AccommodationRequestDTO(in);
        }

        @Override
        public AccommodationRequestDTO[] newArray(int size) {
            return new AccommodationRequestDTO[size];
        }
    };


    public String getStatusFormated() {
        return "status: " + status.name();
    }
}

