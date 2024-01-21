package com.example.bookingapp.clients;

import com.example.bookingapp.dto.commentsAndRatings.AccommodationCommentDTO;
import com.example.bookingapp.dto.commentsAndRatings.CreateAccommodationCommentDTO;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface AccommodationCommentService {
    @Headers({
            "User-Agent: Mobile-Android",
            "Content-Type:application/json"
    })
    @POST("accommodation_comments/add_comment")
    Call<AccommodationCommentDTO> create(@Body CreateAccommodationCommentDTO accommodationCommentDTO);

    @Headers({
            "User-Agent: Mobile-Android",
            "Content-Type:application/json"
    })
    @GET("accommodation_comments/all/{accommodation_id}/not_deleted")
    Call<List<AccommodationCommentDTO>> getAllNotDeleted(@Path("accommodation_id") Long accommodation_id);

    @Headers({
            "User-Agent: Mobile-Android",
            "Content-Type:application/json"
    })
    @PUT("accommodation_comments/remove/{accommodation_comment_id}")
    Call<Void> delete(@Path("accommodation_comment_id") Long accommodation_comment_id);

    @Headers({
            "User-Agent: Mobile-Android",
            "Content-Type:application/json"
    })
    @PUT("accommodation_comments/report/{comment_id}")
    Call<AccommodationCommentDTO> report(@Path("comment_id") Long comment_id);

    @Headers({
            "User-Agent: Mobile-Android",
            "Content-Type:application/json"
    })
    @PUT("accommodation_comments/approve/{comment_id}")
    Call<AccommodationCommentDTO> approveComment(@Path("comment_id") Long comment_id);
}
