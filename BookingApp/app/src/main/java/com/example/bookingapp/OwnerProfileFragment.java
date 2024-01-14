package com.example.bookingapp;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.PopupMenu;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.os.StrictMode;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.bookingapp.clients.ClientUtils;
import com.example.bookingapp.clients.OwnerCommentService;
import com.example.bookingapp.dto.commentsAndRatings.CreateOwnerCommentDTO;
import com.example.bookingapp.dto.commentsAndRatings.OwnerCommentDTO;
import com.example.bookingapp.fragments.AccommodationViewFragment;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import com.example.bookingapp.dto.users.OwnerDTO;
import com.example.bookingapp.dto.users.UserDTO;

import retrofit2.Call;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link OwnerProfileFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class OwnerProfileFragment extends Fragment {

    private static Long id;
    private OwnerDTO owner;

    private static final String JWT_TOKEN_KEY = "jwt_token";
    private static final String USER_ID_KEY = "user_id";
    private static final String OWNER_ID_KEY = "owner_id";

    public OwnerProfileFragment() {
        // Required empty public constructor
    }

    public OwnerProfileFragment(Long id) {
        this.id = id;
    }

    public static OwnerProfileFragment newInstance(Long id) {
        OwnerProfileFragment fragment = new OwnerProfileFragment(id);
        Bundle args = new Bundle();
        args.putLong("id", id);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
            Call<OwnerDTO> call = ClientUtils.userService.getOwnerById(id);
            try{
                Response<OwnerDTO> response = call.execute();
                owner = (OwnerDTO) response.body();
            }catch(Exception ex){
                System.out.println("EXCEPTION WHILE GETTING OWNER");
                ex.printStackTrace();
            }
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_owner_profile, container, false);
        TextView name = view.findViewById(R.id.name);
        name.setText(owner.getName() + " " + owner.getSurname());

        TextView role = view.findViewById(R.id.role);
        role.setText(owner.getRole().toString());

        TextView email = view.findViewById(R.id.email_profile);
        email.setText(owner.getEmail());

        TextView address = view.findViewById(R.id.address_profile);
        address.setText(owner.getAddress());

        TextView phone = view.findViewById(R.id.phone_profile);
        phone.setText(owner.getPhone());
        RatingBar rb = view.findViewById(R.id.owner_rate);
        rb.setRating(4.1f);
        RatingBar ratingBar = view.findViewById(R.id.ratingBar);
        ratingBar.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
                // Handle the rating change
                // You can perform actions based on the selected rating
                Toast.makeText(getContext(), "Rating: " + rating, Toast.LENGTH_SHORT).show();
            }
        });

        ImageView postComment = view.findViewById(R.id.post_comment);
        postComment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences sharedPref = getActivity().getPreferences(Context.MODE_PRIVATE);
                String jwt = sharedPref.getString(JWT_TOKEN_KEY, "");
                Long commenterId = sharedPref.getLong(USER_ID_KEY, 0);
                Long ownerId = sharedPref.getLong(OWNER_ID_KEY, 0);
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
                OwnerCommentService apiService = retrofit.create(OwnerCommentService.class);
                CreateOwnerCommentDTO createOwnerCommentDTO = new CreateOwnerCommentDTO();
                createOwnerCommentDTO.setGuestId(commenterId);

//                Log.d("Owner ID: ", ownerId + "");
                createOwnerCommentDTO.setOwnerId(ownerId);
                EditText editTextNewComment = view.findViewById(R.id.new_comment);
                createOwnerCommentDTO.setContent(editTextNewComment.getText().toString());
                double selectedRating = ratingBar.getRating();
                createOwnerCommentDTO.setRating(selectedRating);

                Call<OwnerCommentDTO> call = apiService.add_comment(createOwnerCommentDTO);

                call.enqueue(new Callback<OwnerCommentDTO>() {
                    @Override
                    public void onResponse(Call<OwnerCommentDTO> call, Response<OwnerCommentDTO> response) {
                        if (response.code() == 201) {
                            Log.d("REZ", "Owner comment successfully created!");
                            Toast.makeText(getContext(), "Success! Your comment will become visible after admin's approval!", Toast.LENGTH_LONG).show();
                            getActivity().getSupportFragmentManager().popBackStack();
                        } else if (response.code() == 400) {
                            Log.d("REZ", "Validation violated!");
                            Toast.makeText(getContext(), "Validation violated!", Toast.LENGTH_SHORT).show();
                        } else {
                            Log.d("REZ","Meesage recieved: "+response.code());
                            Toast.makeText(getContext(), "Error!", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<OwnerCommentDTO> call, Throwable t) {
                        Log.d("REZ", t.getMessage() != null?t.getMessage():"error");
                    }
                });

            }
        });
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


        ImageView icon = view.findViewById(R.id.report_owner_icon);

        icon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PopupMenu popupMenu = new PopupMenu(requireContext(), v);
                popupMenu.getMenuInflater().inflate(R.menu.menu_report, popupMenu.getMenu());

                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {

                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        final String report = String.valueOf(R.id.report);
                        final String id = String.valueOf(item.getItemId());
                        if (report.equals(id)){
                            Toast.makeText(getActivity(), "Reporting user ...", Toast.LENGTH_SHORT).show();
                            return true;
                        }
                        return false;
                    }
                });

                popupMenu.show();
            }
        });


    }
}