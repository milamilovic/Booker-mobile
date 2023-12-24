package com.example.bookingapp.clients;

import com.example.bookingapp.dto.users.CreateUserDTO;
import com.example.bookingapp.dto.users.LoginUserDTO;
import com.example.bookingapp.dto.users.Token;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Headers;
import retrofit2.http.POST;

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
}
