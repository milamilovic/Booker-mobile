package com.example.bookingapp.model;

public class Filter {
    String name;
    Object value;

    public Filter() {}

    public Filter(String name, Object value) {
        this.name = name;
        this.value = value;
    }
}
