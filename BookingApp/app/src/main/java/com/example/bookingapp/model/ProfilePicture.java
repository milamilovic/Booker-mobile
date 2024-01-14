package com.example.bookingapp.model;

public class ProfilePicture {

    Long id;
    String path;
    String path_mobile;
    private User user;

    public ProfilePicture() {
    }

    public String getPath_mobile() {
        return path_mobile;
    }
}
