package com.example.bookingapp.model;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

import java.util.Date;

public class ReportedUsersListing implements Parcelable {
    private Account sender;
    private Account receiver;
    private Date date;
    private String reason;

    public Account getSender() {
        return sender;
    }

    public void setSender(Account sender) {
        this.sender = sender;
    }

    public Account getReceiver() {
        return receiver;
    }

    public void setReceiver(Account receiver) {
        this.receiver = receiver;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public ReportedUsersListing(Account sender, Account receiver, Date date, String reason) {
        this.sender = sender;
        this.receiver = receiver;
        this.date = date;
        this.reason = reason;
    }

    private ReportedUsersListing() {}

    public ReportedUsersListing(Parcel in) {
        sender = in.readParcelable(User.class.getClassLoader());
        receiver = in.readParcelable(User.class.getClassLoader());
        date = new Date(in.readLong());
        reason = in.readString();
    }

    @Override
    public String toString() {
        return "ReportedUsersListing{" +
                "sender=" + sender.getUser().getName() + sender.getUser().getSurname() +
                ", receiver=" + receiver +
                ", date=" + date +
                ", reason='" + reason + '\'' +
                '}';
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(@NonNull Parcel dest, int flags) {
        dest.writeParcelable(sender, flags);
        dest.writeParcelable(receiver, flags);
        dest.writeLong(date.getTime());
        dest.writeString(reason);
    }

    public static final Creator<ReportedUsersListing> CREATOR = new Creator<ReportedUsersListing>() {
        @Override
        public ReportedUsersListing createFromParcel(Parcel in) {
            return new ReportedUsersListing(in);
        }

        @Override
        public ReportedUsersListing[] newArray(int size) {
            return new ReportedUsersListing[size];
        }
    };
}
