package com.example.bookingapp.clients;

import com.example.bookingapp.dto.accommodation.AccommodationViewDTO;
import com.example.bookingapp.dto.accommodation.CreateAccommodationDTO;
import com.example.bookingapp.model.AccommodationRequestDTO;
import com.example.bookingapp.model.Filter;

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

    @Headers("Content-Type: application/json")
    @GET("requests/guest/{guestId}/search/{searchDate}/{accName}")
    Call<List<AccommodationRequestDTO>> searchGuest(@Path("guestId") Long guestId,
                                                    @Path("searchDate") String searchDate,
                                                    @Path("accName") String accName);

    @Headers({
        "User-Agent: Mobile-Android",
                "Content-Type:application/json"
    })
    @POST("requests/guest/{guestId}/filter")
    Call<List<AccommodationRequestDTO>> filterGuest(@Path("guestId") Long guestId,
                                           @Body ArrayList<Filter> createAccommodationDTO);

    @Headers({
        "User-Agent: Mobile-Android",
                "Content-Type:application/json"
    })
    @POST("requests/guest/{guestId}/search/{searchDate}/{accName}/filter")
    Call<List<AccommodationRequestDTO>> searchAndFilterGuest(@Path("guestId") Long guestId,
                                                    @Path("searchDate") String searchDate,
                                                    @Path("accName") String accName,
                                                    @Body ArrayList<Filter> createAccommodationDTO);

    @Headers("Content-Type: application/json")
    @GET("requests/owner/{ownerId}")
    Call<List<AccommodationRequestDTO>> findOwnersReservationRequests(@Path("ownerId") Long ownerId);

    @Headers("Content-Type: application/json")
    @GET("requests/owner/{ownerId}/search/{searchDate}/{accName}")
    Call<List<AccommodationRequestDTO>> searchOwner(@Path("ownerId") Long ownerId,
                                                    @Path("searchDate") String searchDate,
                                                    @Path("accName") String accName);

    @Headers({
            "User-Agent: Mobile-Android",
            "Content-Type:application/json"
    })
    @POST("requests/owner/{ownerId}/filter")
    Call<List<AccommodationRequestDTO>> filterOwner(@Path("ownerId") Long ownerId,
                                                    @Body ArrayList<Filter> createAccommodationDTO);

    @Headers({
            "User-Agent: Mobile-Android",
            "Content-Type:application/json"
    })
    @POST("requests/owner/{ownerId}/search/{searchDate}/{accName}/filter")
    Call<List<AccommodationRequestDTO>> searchAndFilterOwner(@Path("ownerId") Long ownerId,
                                                             @Path("searchDate") String searchDate,
                                                             @Path("accName") String accName,
                                                             @Body ArrayList<Filter> createAccommodationDTO);
}
