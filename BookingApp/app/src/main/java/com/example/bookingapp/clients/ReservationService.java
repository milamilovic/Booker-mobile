package com.example.bookingapp.clients;

import com.example.bookingapp.model.AccommodationRequestDTO;
import com.example.bookingapp.model.Reservation;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Path;

public interface ReservationService {

    @Headers("Content-Type: application/json")
    @GET("reservations/accommodation/{accommodationId}")
    Call<List<Reservation>> findReservationsForAccommodation(@Path("accommodationId") Long accommodationId);
}
