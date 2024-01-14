package com.example.bookingapp.dto.users;

import com.example.bookingapp.enums.Role;
import com.example.bookingapp.model.ProfilePicture;

import java.util.ArrayList;

public class GuestDTO extends UserDTO{
    private boolean reported;
    private boolean blocked;
    private boolean deleted;
    private ArrayList<Long> favouriteAccommodations;


    public GuestDTO(Long id, String name, String surname, String email, String password,
                    String address, String phone, Role role, ProfilePicture profilePicture, boolean reported, boolean blocked,
                    boolean deleted, ArrayList<Long> favouriteAccommodations) {
        super(id, name, surname, email, password, address, phone, role, profilePicture);
        this.reported = reported;
        this.blocked = blocked;
        this.deleted = deleted;
        this.favouriteAccommodations = favouriteAccommodations;
    }
}
