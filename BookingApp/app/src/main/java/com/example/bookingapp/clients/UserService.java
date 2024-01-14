package com.example.bookingapp.clients;

import com.example.bookingapp.dto.users.CreateUserDTO;
import com.example.bookingapp.dto.users.LoginUserDTO;
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
    @GET("users/{id}/user")
    Call<UserDTO> getById(@Path("id") Long id);

    @Headers({
            "User-Agent: Mobile-Android",
            "Content-Type:application/json"
    })
    @PUT("users/activate_profile/{activation_link}")
    Call<UserDTO> activateProfile(@Path("activation_link") String activation_link);
}
