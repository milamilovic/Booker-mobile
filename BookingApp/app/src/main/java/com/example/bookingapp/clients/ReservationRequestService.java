package com.example.bookingapp.clients;

import com.example.bookingapp.model.AccommodationRequestDTO;

import java.util.ArrayList;
import java.util.List;


import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.Headers;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface ReservationRequestService {
    @Headers("Content-Type: application/json")
    @GET("requests/guest/{guestId}")
    Call<List<AccommodationRequestDTO>> findGuestsReservationRequests(@Path("guestId") Long guestId);

    @Headers("Content-Type: application/json")
    @DELETE("requests/guest/{guestId}/cancel-request/{requestId}")
    Call<Void> cancel(@Path("guestId") Long guestId, @Path("requestId") Long requestId);
}
