package com.example.bookingapp.model;

import com.example.bookingapp.enums.ReservationRequestStatus;

public class ReservationRequest {
    private Long id;
    private Long guestId;
    private Long accommodationId;
    private String fromDate;
    private String toDate;
    private int numberOfGuests;
    private ReservationRequestStatus status;
    private boolean deleted;
    private double price;

    public ReservationRequest(Long guestId, Long accommodationId, String fromDate, String toDate, int numberOfGuests, ReservationRequestStatus status, boolean deleted, double price) {
        this.guestId = guestId;
        this.accommodationId = accommodationId;
        this.fromDate = fromDate;
        this.toDate = toDate;
        this.numberOfGuests = numberOfGuests;
        this.status = status;
        this.deleted = deleted;
        this.price = price;
    }
}
