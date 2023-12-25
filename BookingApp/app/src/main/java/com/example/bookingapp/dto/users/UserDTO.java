package com.example.bookingapp.dto.users;

import com.example.bookingapp.enums.Role;
import com.example.bookingapp.model.ProfilePicture;
import com.example.bookingapp.model.User;

public class UserDTO {
    private Long id;

    private String name;

    private String surname;

    private String email;
    private String password;

    private String address;

    private String phone;
    private Role role;
    private ProfilePicture profilePicture;

//    public UserDTO(User user) {
//        this(user.getId(), user.getName(), user.getSurname(), user.getEmail(), user.getPassword(), user.getAddress(), user.getPhone(), user.getRole(), user.getProfilePicture());
//
//    }

    public UserDTO(Long id, String name, String surname, String email, String password, String address, String phone, Role role, ProfilePicture profilePicture) {
        this.id = id;
        this.name = name;
        this.surname = surname;
        this.email = email;
        this.password = password;
        this.address = address;
        this.phone = phone;
        this.role = role;
        this.profilePicture = profilePicture;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public ProfilePicture getProfilePicture() {
        return profilePicture;
    }

    public void setProfilePicture(ProfilePicture profilePicture) {
        this.profilePicture = profilePicture;
    }
}
