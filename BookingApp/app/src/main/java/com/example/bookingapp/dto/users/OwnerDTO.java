package com.example.bookingapp.dto.users;

import com.example.bookingapp.enums.Role;
import com.example.bookingapp.model.ProfilePicture;


public class OwnerDTO extends UserDTO{
    private boolean reported;
    private boolean blocked;
    private boolean deleted;

    public OwnerDTO(Long id, String name, String surname, String email, String password, String address,
                    String phone, Role role, ProfilePicture profilePicture, boolean reported, boolean blocked,
                    boolean deleted){
        super(id, name, surname, email, password, address, phone, role, profilePicture);
        this.reported = reported;
        this.blocked = blocked;
        this.deleted = deleted;
    }
}
