package com.example.bookingapp.clients;

import com.example.bookingapp.dto.commentsAndRatings.AccommodationCommentDTO;
import com.example.bookingapp.dto.commentsAndRatings.CreateAccommodationCommentDTO;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public interface AccommodationCommentService {
    @Headers({
            "User-Agent: Mobile-Android",
            "Content-Type:application/json"
    })
    @POST("accommodation_comments/add_comment")
    Call<AccommodationCommentDTO> create(@Body CreateAccommodationCommentDTO accommodationCommentDTO);
}
