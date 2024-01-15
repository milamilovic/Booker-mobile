package com.example.bookingapp.adapters;

import static com.example.bookingapp.clients.ClientUtils.retrofit;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.FragmentManager;

import com.example.bookingapp.R;
import com.example.bookingapp.clients.AccommodationService;
import com.example.bookingapp.clients.ClientUtils;
import com.example.bookingapp.clients.OwnerCommentService;
import com.example.bookingapp.dto.commentsAndRatings.OwnerCommentDTO;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Locale;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class OwnerCommentAdapter extends ArrayAdapter<OwnerCommentDTO> {
    private ArrayList<OwnerCommentDTO> aComments;
    private Activity activity;
    private FragmentManager fragmentManager;
    private static final String USER_ID_KEY = "user_id";
    private static final String JWT_TOKEN_KEY = "jwt_token";

    public OwnerCommentAdapter(Activity context, FragmentManager fragmentManager, ArrayList<OwnerCommentDTO> comments) {
        super(context, R.layout.owner_comment_item, comments);
        aComments = comments;
        activity = context;
        fragmentManager = fragmentManager;
    }

    @Override
    public int getCount() {
        return aComments.size();
    }

    @Nullable
    @Override
    public OwnerCommentDTO getItem(int position) {
        return aComments.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        OwnerCommentDTO ownerCommentDTO = getItem(position);
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.owner_comment_item, parent, false);
        }

        LinearLayout commentCard = convertView.findViewById(R.id.comment_card_item);
        ImageView profilePic = convertView.findViewById(R.id.commenter_profile_pic);
        TextView commenterName = convertView.findViewById(R.id.commenter_name);
        TextView commentRating = convertView.findViewById(R.id.rating);
        TextView commentContent = convertView.findViewById(R.id.comment_content);
        ImageButton commentDelete = convertView.findViewById(R.id.delete_comment);
        TextView commentDate = convertView.findViewById(R.id.comment_date);

        if (ownerCommentDTO != null) {
            String nameSurname = ownerCommentDTO.getGuestName() + " " + ownerCommentDTO.getGuestSurname();
            commenterName.setText(nameSurname);
            String ratingString = String.valueOf(ownerCommentDTO.getRating()) + "/5";
            commentRating.setText(ownerCommentDTO.getRating() + "/5");
            commentContent.setText(ownerCommentDTO.getContent());
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
            String dateString = sdf.format(ownerCommentDTO.getDate());
            commentDate.setText(dateString);
        }


        OwnerCommentService apiService = retrofit.create(OwnerCommentService.class);
        commentDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences sharedPref = getContext().getSharedPreferences( "sharedPref", Context.MODE_PRIVATE);
                String jwt = sharedPref.getString(JWT_TOKEN_KEY, "");
                Long loggedId = sharedPref.getLong(USER_ID_KEY, -1);
                Log.d("LOGGED_IN", loggedId + "");
                String jwtToken = jwt; // Replace with your actual JWT token
                String authorizationHeaderValue = "Bearer " + jwtToken;

                // Create a new instance of the OkHttpClient.Builder
                OkHttpClient.Builder clientBuilder = new OkHttpClient.Builder();

                // Add an interceptor to the OkHttpClient to set the Authorization header
                clientBuilder.addInterceptor(chain -> {
                    Request original = chain.request();
                    Request.Builder requestBuilder = original.newBuilder()
                            .header("Authorization", authorizationHeaderValue)
                            .method(original.method(), original.body());

                    Request request = requestBuilder.build();
                    return chain.proceed(request);
                });

                // Build the OkHttpClient
                OkHttpClient client = clientBuilder.build();

                // Create a new Retrofit instance with the modified OkHttpClient
                Retrofit retrofit = new Retrofit.Builder()
                        .baseUrl(ClientUtils.SERVICE_API_PATH) // Replace with your actual base URL
                        .addConverterFactory(GsonConverterFactory.create())
                        .client(client)
                        .build();

                // Create the service interface using Retrofit
                Call<Void> call = apiService.deleteOwnerComment(ownerCommentDTO.getId());
                call.enqueue(new Callback<Void>() {
                    @Override
                    public void onResponse(Call<Void> call, Response<Void> response) {
                        if (response.code() == 200) {
                            Log.d("REZ", "Message received");
                            Toast.makeText(getContext(), "Owner comment successfully deleted!", Toast.LENGTH_SHORT).show();
                        } else {
                            Log.d("REZ", "Message received " + response.code());
                            Toast.makeText(getContext(), "Error", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<Void> call, Throwable t) {
                        Log.d("REZ", t.getMessage() != null?t.getMessage():"error");
                    }
                });
            }
        });



        return convertView;
    }
}
