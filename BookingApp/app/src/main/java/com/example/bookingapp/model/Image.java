package com.example.bookingapp.model;

public class Image {
    Long id;
    String path_mobile;
    String path;
    private Object accommodation;

    public Image(){}

    public Image(Long id, String path_mobile, String path_front, Object accommodation) {
        this.id = id;
        this.accommodation = accommodation;
        this.path = path_front;
        this.path_mobile = path_mobile;
    }

    public Long getId() { return this.id; }

    public void setId(Long id) {
        this.id = id;
    }

    public Object getAccommodation() {
        return accommodation;
    }

    public void setAccommodation(Object accommodation) {
        this.accommodation = accommodation;
    }

    public String getPath() {
        return path;
    }

    public String getPath_mobile() {
        return path_mobile;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public void setPath_mobile(String path_mobile) {
        this.path_mobile = path_mobile;
    }
}
