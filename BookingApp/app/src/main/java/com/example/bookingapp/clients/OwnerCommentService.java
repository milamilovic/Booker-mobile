package com.example.bookingapp.clients;

import com.example.bookingapp.dto.commentsAndRatings.CreateOwnerCommentDTO;
import com.example.bookingapp.dto.commentsAndRatings.OwnerCommentDTO;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface OwnerCommentService {
    @Headers({
            "User-Agent: Mobile-Android",
            "Content-Type:application/json"
    })
    @POST("owner_comments/add_comment")
    Call<OwnerCommentDTO> add_comment(@Body CreateOwnerCommentDTO createOwnerCommentDTO);

    @Headers({
            "User-Agent: Mobile-Android",
            "Content-Type:application/json"
    })
    @GET("owner_comments/all/{owner_id}/not_deleted")
    Call<List<OwnerCommentDTO>> getAllForOwner(@Path("owner_id") Long owner_id);

    @Headers({
            "User-Agent: Mobile-Android",
            "Content-Type:application/json"
    })
    @PUT("owner_comments/remove/{id}")
    Call<Void> deleteOwnerComment(@Path("id") Long id);

    @Headers({
            "User-Agent: Mobile-Android",
            "Content-Type:application/json"
    })
    @PUT("owner_comments/report/{comment_id}")
    Call<OwnerCommentDTO> report(@Path("comment_id") Long comment_id);
    @Headers({
            "User-Agent: Mobile-Android",
            "Content-Type:application/json"
    })
    @GET("owner_comments/all/{owner_id}/not_deleted")
    Call<List<OwnerCommentDTO>> getAllNotDeleted(@Path("owner_id") Long owner_id);
}
