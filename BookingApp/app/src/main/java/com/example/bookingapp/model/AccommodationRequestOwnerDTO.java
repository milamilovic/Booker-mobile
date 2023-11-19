package com.example.bookingapp.model;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

import com.example.bookingapp.R;

import java.text.SimpleDateFormat;
import java.util.Date;

public class AccommodationRequestOwnerDTO  implements Parcelable {
    private Long id;
    private String title;
    private int image;
    private int totalPrice;
    private int pricePerDay;
    private float rating;

    private String status;
    private Date fromDate;
    private Date toDate;
    private int numOfPeople;
    private Long guestId;

    public AccommodationRequestOwnerDTO(Long id, String title, int image,
                                        int totalPrice, int  pricePerDay, float rating, String status, Date from, Date to, int people, long guest) {
        this.id = id;
        this.title = title;
        this.image = image;
        this.totalPrice = totalPrice;
        this.pricePerDay = pricePerDay;
        this.rating = rating;
        this.status = status;
        this.fromDate = from;
        this.toDate = to;
        this.numOfPeople = people;
        this.guestId = guest;
    }

    public AccommodationRequestOwnerDTO() {
    }

    protected AccommodationRequestOwnerDTO(Parcel in) {
        id = in.readLong();
        title = in.readString();
        image = in.readInt();
        totalPrice = in.readInt();
        pricePerDay = in.readInt();
        rating = in.readFloat();
        status = in.readString();
        fromDate = (java.util.Date) in.readSerializable();
        toDate = (java.util.Date) in.readSerializable();
        guestId = in.readLong();
        numOfPeople = in.readInt();
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

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
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

    public Date getFromDate() {
        return fromDate;
    }

    public void setFromDate(Date fromDate) {
        this.fromDate = fromDate;
    }

    public Date getToDate() {
        return toDate;
    }

    public void setToDate(Date toDate) {
        this.toDate = toDate;
    }

    public int getNumOfPeople() {
        return numOfPeople;
    }

    public void setNumOfPeople(int numOfPeople) {
        this.numOfPeople = numOfPeople;
    }

    public Long getGuestId() {
        return guestId;
    }

    public void setGuestId(Long guestId) {
        this.guestId = guestId;
    }

    public float getRating() {
        return rating;
    }

    public void setRating(float rating) {
        this.rating = rating;
    }

    public String getDateRange() {
        return formatDate(fromDate) + " - " + formatDate(toDate);
    }

    @Override
    public String toString() {
        return "AccommodationRequest{" +
                "title='" + title + '\'' +
                ", status='" + status + '\'' +
                ", image='" + image + '\'' +
                ", total price='" + totalPrice + '\'' +
                ", price per day='" + pricePerDay + '\'' +
                ", rating='" + rating + '\'' +
                ", from='" + formatDate(fromDate) + '\'' +
                ", to='" + formatDate(toDate) + '\'' +
                ", number of people='" + numOfPeople + '\'' +
                ", guest id='" + guestId + '\'' +
                '}';
    }

    private String formatDate(Date date) {
        String myFormat="dd.MM.yyyy.";
        SimpleDateFormat dateFormat=new SimpleDateFormat(myFormat);
        return dateFormat.format(date);
    }

    public GuestOwnerViewDTO getGuest(Long guestId) {
        if (guestId == 1L) {
            return new GuestOwnerViewDTO(1L, "Jane Austen", 2, R.drawable.profile_image);
        } else if (guestId == 2L) {
            return new GuestOwnerViewDTO(2L, "Maria Blue", 0, R.drawable.hotel_image);
        } else if (guestId == 3L) {
            return new GuestOwnerViewDTO(3L, "Jack Fisherman", 5, R.drawable.london_image);
        } else {
            return new GuestOwnerViewDTO(4L, "Tom Sawyer", 1, R.drawable.villa_image);
        }
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(@NonNull Parcel dest, int flags) {
        dest.writeLong(id);
        dest.writeString(title);
        dest.writeInt(image);
        dest.writeInt(totalPrice);
        dest.writeInt(pricePerDay);
        dest.writeFloat(rating);
        dest.writeString(status);
        dest.writeSerializable(fromDate);
        dest.writeSerializable(toDate);
        dest.writeLong(guestId);
        dest.writeInt(numOfPeople);
    }

    public static final Creator<AccommodationRequestOwnerDTO> CREATOR = new Creator<AccommodationRequestOwnerDTO>() {
        @Override
        public AccommodationRequestOwnerDTO createFromParcel(Parcel in) {
            return new AccommodationRequestOwnerDTO(in);
        }

        @Override
        public AccommodationRequestOwnerDTO[] newArray(int size) {
            return new AccommodationRequestOwnerDTO[size];
        }
    };


    public String getStatusFormated() {
        return "status: " + status.toUpperCase();
    }
}

