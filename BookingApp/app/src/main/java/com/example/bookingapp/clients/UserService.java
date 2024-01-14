package com.example.bookingapp.clients;

import com.example.bookingapp.dto.users.CreateUserDTO;
import com.example.bookingapp.dto.users.GuestDTO;
import com.example.bookingapp.dto.users.LoginUserDTO;
import com.example.bookingapp.dto.users.OwnerDTO;
import com.example.bookingapp.dto.users.Token;
import com.example.bookingapp.dto.users.UserDTO;

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
}
