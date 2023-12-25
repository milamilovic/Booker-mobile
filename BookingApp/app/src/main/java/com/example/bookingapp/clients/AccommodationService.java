package com.example.bookingapp.clients;

import com.example.bookingapp.model.Accommodation;
import com.example.bookingapp.model.AccommodationListing;
import com.example.bookingapp.model.ApproveAccommodationListing;
import com.example.bookingapp.model.Filter;

import java.util.ArrayList;
import java.util.List;


import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Headers;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface AccommodationService {
    @Headers("Content-Type: application/json")
    @GET("accommodations/{id}")
    Call<Accommodation> findAccommodation(@Path("id") Long id);
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
    @GET("accommodations/admin/unapproved")
    Call<List<ApproveAccommodationListing>> findAllUnapproved();

    @Headers({
            "User-Agent: Mobile-Android",
            "Content-Type:application/json"
    })
    @PUT("accommodations/approve/{accommodationId}")
    Call<String> approveAccommodation(@Path("accommodationId")Long accommodationId);


    @Headers("Content-Type: application/json")
    @GET("accommodations/owner/{ownerId}/active")
    Call<List<ApproveAccommodationListing>> findApprovedForOwner(@Path("ownerId")Long ownerId);

    @Headers("Content-Type: application/json")
    @GET("accommodations/owner/{ownerId}")
    Call<List<ApproveAccommodationListing>> findAllForOwner(@Path("ownerId")Long ownerId);
}
