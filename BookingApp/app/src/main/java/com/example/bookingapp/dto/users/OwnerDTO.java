package com.example.bookingapp.dto.users;

import com.example.bookingapp.enums.Role;
import com.example.bookingapp.model.ProfilePicture;


public class OwnerDTO extends UserDTO{
    private boolean reported;
    private boolean blocked;
    private boolean deleted;
    private boolean requestNotificationEnabled;
    private boolean cancellationNotificationEnabled;
    private boolean ratingNotificationEnabled;
    private boolean accNotificationEnabled;

    public OwnerDTO(Long id, String name, String surname, String email, String password, String address,
                    String phone, Role role, ProfilePicture profilePicture, boolean reported, boolean blocked,
                    boolean deleted, boolean requestNotificationEnabled, boolean cancellationNotificationEnabled,
                    boolean ratingNotificationEnabled, boolean accNotificationEnabled){
        super(id, name, surname, email, password, address, phone, role, profilePicture);
        this.reported = reported;
        this.blocked = blocked;
        this.deleted = deleted;
        this.requestNotificationEnabled = requestNotificationEnabled;
        this.cancellationNotificationEnabled = cancellationNotificationEnabled;
        this.ratingNotificationEnabled = ratingNotificationEnabled;
        this.accNotificationEnabled = accNotificationEnabled;
    }

    public boolean isAccNotificationEnabled() {
        return accNotificationEnabled;
    }

    public boolean isCancellationNotificationEnabled() {
        return cancellationNotificationEnabled;
    }

    public boolean isRequestNotificationEnabled() {
        return requestNotificationEnabled;
    }

    public boolean isRatingNotificationEnabled() {
        return ratingNotificationEnabled;
    }

    public boolean isBlocked() {
        return blocked;
    }
}
