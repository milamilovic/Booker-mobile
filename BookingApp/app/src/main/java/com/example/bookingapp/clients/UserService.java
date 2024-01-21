package com.example.bookingapp.clients;

import com.example.bookingapp.dto.users.CreateReportUserDTO;
import com.example.bookingapp.dto.users.CreateUserDTO;
import com.example.bookingapp.dto.users.GuestDTO;
import com.example.bookingapp.dto.users.LoginUserDTO;
import com.example.bookingapp.dto.users.OwnerDTO;
import com.example.bookingapp.dto.users.Token;
import com.example.bookingapp.dto.users.UserDTO;
import com.example.bookingapp.dto.users.UserReportDTO;
import com.example.bookingapp.model.AdminOwnerComment;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface UserService {
    @Headers({
            "User-Agent: Mobile-Android",
            "Content-Type:application/json"
    })
    @POST("users/login")
    Call<Token> findByEmailAndPassword(@Body LoginUserDTO loginUserDTO);

    @Headers({
            "User-Agent: Mobile-Android",
            "Content-Type:application/json"
    })
    @POST("users/signup")
    Call<CreateUserDTO> saveUser(@Body CreateUserDTO createUserDTO);

    @Headers({
            "User-Agent: Mobile-Android",
            "Content-Type:application/json"
    })
    @GET("users/{id}")
    Call<UserDTO> getById(@Path("id") Long id);

    @Headers({
            "User-Agent: Mobile-Android",
            "Content-Type:application/json"
    })
    @GET("guests/{guestId}")
    Call<GuestDTO> getGuestById(@Path("guestId") Long guestId);

    @Headers({
            "User-Agent: Mobile-Android",
            "Content-Type:application/json"
    })
    @GET("owners/{ownerId}")
    Call<OwnerDTO> getOwnerById(@Path("ownerId") Long ownerId);

    @Headers({
            "User-Agent: Mobile-Android",
            "Content-Type:application/json"
    })
    @PUT("users/activate_profile/{activation_link}")
    Call<UserDTO> activateProfile(@Path("activation_link") String activation_link);

    @Headers({
            "User-Agent: Mobile-Android",
            "Content-Type:application/json"
    })

    @PUT("guests/{guestId}")
    Call<GuestDTO> updateGuest(@Path("guestId") Long guestId, @Body GuestDTO guest);

    @Headers({
            "User-Agent: Mobile-Android",
            "Content-Type:application/json"
    })
    @PUT("owners/{ownerId}")
    Call<OwnerDTO> updateOwner(@Path("ownerId") Long owner_Id, @Body OwnerDTO owner);

    @Headers({
            "User-Agent: Mobile-Android",
            "Content-Type:application/json"
    })
    @PUT("admins/{adminId}")
    Call<UserDTO> updateAdmin(@Path("adminId") Long adminId, @Body UserDTO user);

    @GET("guests/{id}/cancelled")
    Call<Integer> getNumOfCancellations(@Path("id") Long id);

    @Headers({
            "User-Agent: Mobile-Android",
            "Content-Type:application/json"
    })
    @POST("report_user/add_report")
    Call<UserReportDTO> addReport(@Body CreateReportUserDTO createReportUserDTO);

    @Headers("Content-Type: application/json")
    @GET("users/image/{id}")
    Call<List<String>> getImage(@Path("id") Long id);

    @Headers("Content-Type: application/json")
    @POST("users/image/upload/{id}")
    Call<String> saveImage(@Path("id") Long id,
                           @Body List<String> images);

    @Headers("Content-Type: application/json")
    @GET("owners/all")
    Call<ArrayList<OwnerDTO>> getOwners();

    @Headers("Content-Type: application/json")
    @GET("owner_comments/all/{owner_id}/comments")
    Call<List<AdminOwnerComment>> getCommentsForOwner(@Path("owner_id") Long owner_id);

    @Headers("Content-Type: application/json")
    @PUT("owner_comments/delete/{comment_id}")
    Call<AdminOwnerComment> deleteComment(@Path("comment_id") Long comment_id);
}
