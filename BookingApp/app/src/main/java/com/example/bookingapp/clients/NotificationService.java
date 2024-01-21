package com.example.bookingapp.clients;

import com.example.bookingapp.model.FavouriteAccommodationListing;
import com.example.bookingapp.model.NotificationListing;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Path;

public interface NotificationService {

    @Headers("Content-Type: application/json")
    @GET("notifications/{userId}")
    Call<List<NotificationListing>> getUsersNotifications(@Path("userId")Long userId);

    @Headers("Content-Type: application/json")
    @GET("notifications/settings/{userId}/{id}/{checked}")
    Call<Void> changePreferences(@Path("userId")Long userId,
                                                          @Path("id")int id,
                                                          @Path("checked")boolean checked);

    @Headers("Content-Type: application/json")
    @GET("notifications/{userId}/new")
    Call<NotificationListing> getUsersNewNotification(@Path("userId")Long userId);
}
