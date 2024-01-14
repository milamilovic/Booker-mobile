package com.example.bookingapp.clients;

import com.example.bookingapp.dto.commentsAndRatings.CreateOwnerCommentDTO;
import com.example.bookingapp.dto.commentsAndRatings.OwnerCommentDTO;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public interface OwnerCommentService {
    @Headers({
            "User-Agent: Mobile-Android",
            "Content-Type:application/json"
    })
    @POST("owner_comments/add_comment")
    Call<OwnerCommentDTO> add_comment(@Body CreateOwnerCommentDTO createOwnerCommentDTO);
}
