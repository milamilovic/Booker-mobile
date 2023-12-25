package com.example.bookingapp.clients;

import com.example.bookingapp.dto.users.LoginUserDTO;
import com.example.bookingapp.dto.users.Token;
import com.example.bookingapp.model.Accommodation;
import com.example.bookingapp.model.AccommodationListing;
import com.example.bookingapp.model.Filter;
import com.example.bookingapp.model.ReservationRequest;

import java.util.ArrayList;
import java.util.List;


import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Headers;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface AccommodationService {
    @Headers("Content-Type: application/json")
    @GET("accommodations/{id}")
    Call<Accommodation> findAccommodation(@Path("id") Long id);

    @Headers("Content-Type: application/json")
    @GET("accommodation_ratings/all/{id}/ratings")
    Call<List<AccommodationRating>> getRatings(@Path("id") Long id);
    @Headers("Content-Type: application/json")
    @GET("accommodations/search/{startDate}/{endDate}/{location}/{people}")
    Call<List<AccommodationListing>> search(@Path("startDate") String startDate,
                                            @Path("endDate") String endDate,
                                            @Path("location") String location,
                                            @Path("people") int people);

    @Headers({
            "User-Agent: Mobile-Android",
            "Content-Type:application/json"
    })
    @POST("accommodations/search/{startDate}/{endDate}/{location}/{people}/filter")
    Call<List<AccommodationListing>> userLogin(@Path("startDate") String startDate,
                                  @Path("endDate") String endDate,
                                  @Path("location") String location,
                                  @Path("people") int people,
                                  @Body ArrayList<Filter> filters);

    @Headers("Content-Type: application/json")
    @GET("prices/{id}/{firstDateString}/{secondDateString}/{people}")
    Call<Double> getPrice(@Path("id") Long id,
                          @Path("firstDateString") String firstDateString,
                          @Path("secondDateString") String secondDateString,
                          @Path("people") int people);

    @Headers({
            "User-Agent: Mobile-Android",
            "Content-Type:application/json"
    })
    @POST("requests")
    Call<ReservationRequest> makeRequest(@Body ReservationRequest request);
}
