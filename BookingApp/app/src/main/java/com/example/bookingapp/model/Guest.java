package com.example.bookingapp.model;

import java.util.ArrayList;

public class Guest extends User{
    private boolean reported;
    private boolean blocked;
    private boolean deleted;
    private ArrayList<Long> favouriteAccommodations;
}
