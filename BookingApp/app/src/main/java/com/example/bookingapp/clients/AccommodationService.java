package com.example.bookingapp.clients;

import android.widget.CheckBox;

import com.example.bookingapp.dto.accommodation.AccommodationViewDTO;
import com.example.bookingapp.dto.accommodation.CreateAccommodationDTO;
import com.example.bookingapp.dto.accommodation.UpdateAvailabilityDTO;
import com.example.bookingapp.dto.users.LoginUserDTO;
import com.example.bookingapp.dto.users.OwnerDTO;
import com.example.bookingapp.dto.users.Token;
import com.example.bookingapp.model.Accommodation;
import com.example.bookingapp.model.AccommodationListing;
import com.example.bookingapp.model.AccommodationName;
import com.example.bookingapp.model.Address;
import com.example.bookingapp.model.AdminAccommodationComment;
import com.example.bookingapp.model.AdminOwnerComment;
import com.example.bookingapp.model.ApproveAccommodationListing;
import com.example.bookingapp.model.FavouriteAccommodationListing;
import com.example.bookingapp.model.Filter;
import com.example.bookingapp.model.ReportDataUnit;
import com.example.bookingapp.model.ReservationRequest;

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
    @GET("accommodation_ratings/all/{id}/ratings")
    Call<List<AccommodationRating>> getRatings(@Path("id") Long id);
    @Headers("Content-Type: application/json")
    @GET("accommodations/search/{startDate}/{endDate}/{location}/{people}")
    Call<List<AccommodationListing>> search(@Path("startDate") String startDate,
                                            @Path("endDate") String endDate,
                                            @Path("location") String location,
                                            @Path("people") int people);

    @Headers("Content-Type: application/json")
    @GET("prices/{id}/{firstDateString}/{secondDateString}/{people}")
    Call<Double> getPrice(@Path("id") Long id,
                          @Path("firstDateString") String firstDateString,
                          @Path("secondDateString") String secondDateString,
                          @Path("people") int people);
  
    @Headers("Content-Type: application/json")
    @GET("accommodations/admin/unapproved")
    Call<List<ApproveAccommodationListing>> findAllUnapproved();

    @Headers({
            "User-Agent: Mobile-Android",
            "Content-Type:application/json"
    })
    @POST("accommodations/create_accommodation")
    Call<AccommodationViewDTO> insert(@Body CreateAccommodationDTO createAccommodationDTO);

    @Headers({
            "User-Agent: Mobile-Android",
            "Content-Type:application/json"
    })
    @PUT("accommodations/update_availability/{id}")
    Call<AccommodationViewDTO> updateAvailability(@Path("id") Long id, @Body UpdateAvailabilityDTO updateAvailabilityDTO);
   @Headers({
            "User-Agent: Mobile-Android",
            "Content-Type:application/json"
    })
    @POST("requests")
    Call<ReservationRequest> makeRequest(@Body ReservationRequest request);
    
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

    @Headers({
            "User-Agent: Mobile-Android",
            "Content-Type:application/json"
    })
    @PUT("accommodations/update/{accommodationId}")
    Call<String> updateAccommodation(@Path("accommodationId")Long accommodationId, @Body Accommodation accommodation);

    @Headers({
            "User-Agent: Mobile-Android",
            "Content-Type:application/json"
    })
    @PUT("accommodations/update/{accommodationId}/address")
    Call<String> updateAccommodationAddress(@Path("accommodationId")Long accommodationId, @Body Address address);

    @Headers("Content-Type: application/json")
    @POST("accommodations/search/{startDate}/{endDate}/{location}/{people}/filter")
    Call<List<AccommodationListing>> searchAndFilter(@Path("startDate") String startDate,
                                                     @Path("endDate") String endDate,
                                                     @Path("location") String location,
                                                     @Path("people") int people,
                                                     @Body ArrayList<Filter> filters);
    @Headers("Content-Type: application/json")
    @GET("guests/favourites/check/{guestId}/{accId}")
    Call<Boolean> isGuestsFavouriteAccommodation(@Path("guestId")Long guestId, @Path("accId")Long accId);

    @Headers({
            "User-Agent: Mobile-Android",
            "Content-Type:application/json"
    })
    @PUT("guests/favouriteAccommodations/add/{guestId}/{accommodationId}")
    Call<Boolean> addToFav(@Path("guestId") Long guestId, @Path("accommodationId")Long accommodationId);


    @Headers({
            "User-Agent: Mobile-Android",
            "Content-Type:application/json"
    })
    @PUT("guests/favouriteAccommodations/remove/{guestId}/{accommodationId}")
    Call<Boolean> removeFromFav(@Path("guestId") Long guestId, @Path("accommodationId")Long accommodationId);

    @Headers("Content-Type: application/json")
    @GET("guests/{guestId}/favouriteAccommodations/all")
    Call<List<FavouriteAccommodationListing>> findFavourite(@Path("guestId")Long guestId);

    @Headers("Content-Type: application/json")
    @GET("accommodations/owner/{ownerId}/accommodationNames")
    Call<List<AccommodationName>> getAccNames(@Path("ownerId")Long ownerId);

    @Headers("Content-Type: application/json")
    @GET("accommodations/name/{accName}")
    Call<Long> getAccId(@Path("accName")String accName);

    @Headers("Content-Type: application/json")
    @GET("report/owner/{ownerId}/accommodation/{year}/{accommodation}")
    Call<List<ReportDataUnit>> getAccReport(@Path("ownerId")Long ownerId,
                                            @Path("year")int year,
                                            @Path("accommodation")Long accommodation);

    @Headers("Content-Type: application/json")
    @GET("report/owner/{ownerId}/interval/{from}/{to}")
    Call<List<ReportDataUnit>> getIntervalReport(@Path("ownerId")Long ownerId,
                                                    @Path("from")String from,
                                                    @Path("to")String to);

    @Headers("Content-Type: application/json")
    @GET("accommodations/images/{id}")
    Call<List<String>> getImages(@Path("id") Long id);

    @Headers("Content-Type: application/json")
    @GET("accommodations/all/id")
    Call<ArrayList<Long>> getAllIds();

    @Headers("Content-Type: application/json")
    @GET("accommodation_comments/{accommodation_id}/comments")
    Call<List<AdminAccommodationComment>> getCommentsForAcc(@Path("accommodation_id") Long accommodation_id);

    @Headers("Content-Type: application/json")
    @PUT("accommodation_comments/delete/{comment_id}")
    Call<AdminAccommodationComment> deleteComment(@Path("comment_id") Long comment_id);
}
