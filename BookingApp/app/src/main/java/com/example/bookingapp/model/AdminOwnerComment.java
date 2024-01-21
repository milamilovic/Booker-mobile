package com.example.bookingapp.model;

import android.os.Parcelable;
import android.os.Parcel;
import androidx.annotation.NonNull;

import java.util.Date;

public class AdminOwnerComment implements Parcelable {
    private Long id;
    private Long ownerId;
    private Long guestId;
    private String guestName;
    private String guestSurname;
    private ProfilePicture guestProfilePicture;
    private String content;
    private double rating;
    private Date date;
    private boolean reported;
    private boolean deleted;
    private boolean approved;

    public AdminOwnerComment() {}

    public AdminOwnerComment(Long id, Long ownerId, Long guestId, String guestName, String guestSurname, ProfilePicture guestProfilePicture, String content, double rating, Date date, boolean reported, boolean deleted, boolean approved) {
        this.id = id;
        this.ownerId = ownerId;
        this.guestId = guestId;
        this.guestName = guestName;
        this.guestSurname = guestSurname;
        this.guestProfilePicture = guestProfilePicture;
        this.content = content;
        this.rating = rating;
        this.date = date;
        this.reported = reported;
        this.deleted = deleted;
        this.approved = approved;
    }

    protected AdminOwnerComment(Parcel in) {
        id = in.readLong();
        ownerId = in.readLong();
        guestId = in.readLong();
        guestName = in.readString();
        guestSurname = in.readString();
        guestProfilePicture = in.readParcelable(ProfilePicture.class.getClassLoader());
        content = in.readString();
        rating = in.readDouble();
        date = (Date) in.readSerializable();
        reported = in.readByte() != 0;
        deleted = in.readByte() != 0;
        approved = in.readByte() != 0;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getOwnerId() {
        return ownerId;
    }

    public void setOwnerId(Long ownerId) {
        this.ownerId = ownerId;
    }

    public Long getGuestId() {
        return guestId;
    }

    public void setGuestId(Long guestId) {
        this.guestId = guestId;
    }

    public String getGuestName() {
        return guestName;
    }

    public void setGuestName(String guestName) {
        this.guestName = guestName;
    }

    public String getGuestSurname() {
        return guestSurname;
    }

    public void setGuestSurname(String guestSurname) {
        this.guestSurname = guestSurname;
    }

    public ProfilePicture getGuestProfilePicture() {
        return guestProfilePicture;
    }

    public void setGuestProfilePicture(ProfilePicture guestProfilePicture) {
        this.guestProfilePicture = guestProfilePicture;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public double getRating() {
        return rating;
    }

    public void setRating(double rating) {
        this.rating = rating;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public boolean isReported() {
        return reported;
    }

    public void setReported(boolean reported) {
        this.reported = reported;
    }

    public boolean isDeleted() {
        return deleted;
    }

    public void setDeleted(boolean deleted) {
        this.deleted = deleted;
    }

    public boolean isApproved() {
        return approved;
    }

    public void setApproved(boolean approved) {
        this.approved = approved;
    }

    @Override
    public String toString() {
        return "AdminOwnerComment{" +
                "id=" + id +
                ", ownerId=" + ownerId +
                ", guestId=" + guestId +
                ", guestName='" + guestName + '\'' +
                ", guestSurname='" + guestSurname + '\'' +
                ", guestProfilePicture=" + guestProfilePicture +
                ", content='" + content + '\'' +
                ", rating=" + rating +
                ", date=" + date +
                ", reported=" + reported +
                ", deleted=" + deleted +
                ", approved=" + approved +
                '}';
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(@NonNull Parcel dest, int flags) {
        dest.writeLong(id);
        dest.writeLong(ownerId);
        dest.writeLong(guestId);
        dest.writeString(guestName);
        dest.writeString(guestSurname);
        dest.writeParcelable((Parcelable) guestProfilePicture, flags);
        dest.writeString(content);
        dest.writeDouble(rating);
        dest.writeSerializable(date);
        dest.writeByte((byte) (reported ? 1 : 0));
        dest.writeByte((byte) (deleted ? 1 : 0));
        dest.writeByte((byte) (approved ? 1 : 0));
    }

    public static final Creator<AdminOwnerComment> CREATOR = new Creator<AdminOwnerComment>() {
        @Override
        public AdminOwnerComment createFromParcel(Parcel in) {
            return new AdminOwnerComment(in);
        }

        @Override
        public AdminOwnerComment[] newArray(int size) {
            return new AdminOwnerComment[size];
        }
    };
}
