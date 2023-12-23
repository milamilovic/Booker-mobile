package com.example.bookingapp.model;

import android.os.Build;
import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

public class Account implements Parcelable {

    private User user;
    private Role role;
    private String password;
    private boolean blocked;

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean getBlocked() {
        return blocked;
    }

    public void setBlocked(boolean blocked) {
        this.blocked = blocked;
    }

    public Account(User user, Role role, String password, boolean blocked) {
        this.user = user;
        this.role = role;
        this.password = password;
        this.blocked = blocked;
    }

    public Account() {}

    protected Account(Parcel in) {
    }

    public static final Creator<Account> CREATOR = new Creator<Account>() {
        @Override
        public Account createFromParcel(Parcel in) {
            return new Account(in);
        }

        @Override
        public Account[] newArray(int size) {
            return new Account[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(@NonNull Parcel dest, int flags) {
        dest.writeParcelable(user, flags);
        dest.writeString(role.name());
        dest.writeString(password);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            dest.writeBoolean(blocked);
        }
    }

    @Override
    public String toString() {
        return "Account{" +
                "user=" + user +
                ", role=" + role +
                ", password='" + password + '\'' +
                ", blocked=" + blocked +
                '}';
    }
}
