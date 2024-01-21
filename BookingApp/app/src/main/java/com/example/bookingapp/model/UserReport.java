package com.example.bookingapp.model;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

import java.util.Date;

public class UserReport implements Parcelable {
    private Long id;
    private Long reportedId;
    private Long reporterId;
    private String reason;
    private Date date;

    public UserReport(){}

    public UserReport(Long id, Long reportedId, Long reporterId, String reason, Date date) {
        this.id = id;
        this.reportedId = reportedId;
        this.reporterId = reporterId;
        this.reason = reason;
        this.date = date;

    }

    protected UserReport(Parcel in){
        id = in.readLong();
        reportedId = in.readLong();
        reporterId = in.readLong();
        reason = in.readString();
        date = (java.util.Date) in.readSerializable();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getReportedId() {
        return reportedId;
    }

    public void setReportedId(Long reportedId) {
        this.reportedId = reportedId;
    }

    public Long getReporterId() {
        return reporterId;
    }

    public void setReporterId(Long reporterId) {
        this.reporterId = reporterId;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    @Override
    public String toString() {
        return "UserReport{" +
                "id=" + id +
                ", reportedId=" + reportedId +
                ", reporterId=" + reporterId +
                ", reason='" + reason + '\'' +
                ", date=" + date +
                '}';
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(@NonNull Parcel dest, int flags) {
        dest.writeLong(id);
        dest.writeLong(reportedId);
        dest.writeLong(reporterId);
        dest.writeString(reason);
        dest.writeSerializable(date);
    }

    public static final Creator<UserReport> CREATOR = new Creator<UserReport>() {
        @Override
        public UserReport createFromParcel(Parcel in) {
            return new UserReport(in);
        }

        @Override
        public UserReport[] newArray(int size) {
            return new UserReport[size];
        }
    };
}

