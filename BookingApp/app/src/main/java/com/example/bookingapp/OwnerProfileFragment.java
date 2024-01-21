package com.example.bookingapp;

import static com.example.bookingapp.clients.ClientUtils.retrofit;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.PopupMenu;
import androidx.fragment.app.Fragment;

import android.util.Base64;
import android.util.Log;
import android.os.StrictMode;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.bookingapp.clients.ClientUtils;
import com.example.bookingapp.clients.OwnerCommentService;
import com.example.bookingapp.dto.commentsAndRatings.CreateOwnerCommentDTO;
import com.example.bookingapp.dto.commentsAndRatings.OwnerCommentDTO;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import com.example.bookingapp.dto.users.OwnerDTO;
import com.example.bookingapp.enums.Role;
import com.example.bookingapp.fragments.ReportUserDialogFragment;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link OwnerProfileFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class OwnerProfileFragment extends Fragment {

    private static Long id;
    private static Long loggedId;
    private OwnerDTO owner;
    private ArrayList<OwnerCommentDTO> comments = new ArrayList<>();

    private static final String JWT_TOKEN_KEY = "jwt_token";
    private static final String USER_ID_KEY = "user_id";
    private static final String OWNER_ID_KEY = "owner_id";
    private static final String USER_ROLE_KEY = "user_role";
    private LinearLayout parentLayout;
    List<OwnerCommentDTO> ownerComments = new ArrayList<>();
    private float total = 0;
    private float average = 0;

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
        parentLayout = view.findViewById(R.id.comment_section);
        parentLayout.removeAllViews();
        fetchOwnerCommentsFromServer();

        ImageView bigProfilePic = view.findViewById(R.id.profile_pic);
        ImageView miniProfilePic = view.findViewById(R.id.mini_profile_pic);
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        Call<List<String>> imageCall = ClientUtils.userService.getImage(owner.getId());
        try{
            Response<List<String>> response = imageCall.execute();
            List<String> images = (List<String>) response.body();
            if(images!=null && !images.isEmpty()) {
                byte[] bytes = Base64.decode(images.get(0), Base64.DEFAULT);
                Bitmap bitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
                miniProfilePic.setImageBitmap(bitmap);
                bigProfilePic.setImageBitmap(bitmap);
            }
        }catch(Exception ex){
            System.out.println("EXCEPTION WHILE GETTING IMAGES");
            ex.printStackTrace();
        }

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
//        float rating = calculateAverageRating();
        RatingBar ratingBar = view.findViewById(R.id.ratingBar);
        TextView ratingText =view.findViewById(R.id.rating_text);
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
//                            adapter.clear();
//                            getCommentsFromClient();
                            // Notify the adapter about the data change
//                            adapter.updateCommentsList(comments);
                            parentLayout.removeAllViews();
                            fetchOwnerCommentsFromServer();
                            Log.d("REZ", "Owner comment successfully created!");
                            Toast.makeText(getContext(), "Owner comment successfully added!", Toast.LENGTH_LONG).show();

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
                        final String itemId = String.valueOf(item.getItemId());
                        if (report.equals(itemId)){
                            //Toast.makeText(getActivity(), "Reporting user ...", Toast.LENGTH_SHORT).show();
                            ReportUserDialogFragment dialogFragment = new ReportUserDialogFragment(id);
                            dialogFragment.show(getActivity().getSupportFragmentManager(), "report_user");
                            return true;
                        }
                        return false;
                    }
                });

                popupMenu.show();
            }
        });
//        getCommentsFromClient();
    }

    @Override
    public void onResume() {
        super.onResume();
//        getCommentsFromClient();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

//    private void getCommentsFromClient() {
//        SharedPreferences sharedPref = getActivity().getPreferences(Context.MODE_PRIVATE);
//        Long ownerId = sharedPref.getLong(OWNER_ID_KEY, 0);
//        Call<List<OwnerCommentDTO>> call = ClientUtils.ownerCommentService.getAllForOwner(id);
//        call.enqueue(new Callback<List<OwnerCommentDTO>>() {
//            @Override
//            public void onResponse(Call<List<OwnerCommentDTO>> call, Response<List<OwnerCommentDTO>> response) {
//                if (response.code() == 200) {
//                    Log.d("REZ", "Message received");
//                    System.out.println(response.body());
//                    ownerComments = response.body();
//
//                    // Update the comments list with the data from the response
//                    comments.clear();
//                    comments.addAll(response.body());
//
//                    // Initialize or update the adapter with the correct data
//                    if (adapter == null) {
//                        adapter = new OwnerCommentAdapter(getActivity(), getActivity().getSupportFragmentManager(), comments);
//                        setListAdapter(adapter);
//                    } else {
//                        adapter.notifyDataSetChanged();
//                    }
//
//                    for (OwnerCommentDTO commentDTO : response.body()) {
//                        System.out.println(commentDTO.toString());
//                    }
//                } else {
//                    Log.d("REZ", "Message received: " + response.code());
//                }
//            }
//
//            @Override
//            public void onFailure(Call<List<OwnerCommentDTO>> call, Throwable t) {
//                Log.d("REZ", t.getMessage() != null ? t.getMessage() : "error");
//            }
//        });
//
//
//
//    }

//    public float calculateAverageRating() {
//        int ratingNum = ownerComments.size();
//        float totalRating = 0;
//        for (OwnerCommentDTO ownerCommentDTO : ownerComments) {
//            totalRating += ownerCommentDTO.getRating();
//        }
//
//        return totalRating / ratingNum;
//    }

    private void fetchOwnerCommentsFromServer() {
        OwnerCommentService apiservice = retrofit.create(OwnerCommentService.class);
        Call<List<OwnerCommentDTO>> call = apiservice.getAllForOwner(id);
        call.enqueue(new Callback<List<OwnerCommentDTO>>() {
            @Override
            public void onResponse(Call<List<OwnerCommentDTO>> call, Response<List<OwnerCommentDTO>> response) {
                if (response.code() == 200) {
                    Log.d("REZ", "Message received " + response.code());
                    List<OwnerCommentDTO> ownerCommentsList = response.body();
                    comments = (ArrayList<OwnerCommentDTO>) response.body();
                    for (OwnerCommentDTO ownerComment : ownerCommentsList) {
                        View ownerCommentItem = createOwnerCommentItem(ownerComment);
                        parentLayout.addView(ownerCommentItem);
                        total += ownerComment.getRating();
                    }
                    average = total / ownerCommentsList.size();
                    // Call a method or update UI components with the new average here
                    updateAverageRating(average);
                } else {
                    Log.d("REZ", "Message received " + response.code());
                }
            }

            @Override
            public void onFailure(Call<List<OwnerCommentDTO>> call, Throwable t) {
                Log.d("REZ", t.getMessage() != null?t.getMessage():"error");
            }
        });

    }


    private View createOwnerCommentItem(OwnerCommentDTO ownerCommentDTO) {
        View ownerCommentItem = LayoutInflater.from(getActivity()).inflate(R.layout.owner_comment_item, null);
        TextView name = ownerCommentItem.findViewById(R.id.name);
        TextView content = ownerCommentItem.findViewById(R.id.commentContent);
        TextView commentDate = ownerCommentItem.findViewById(R.id.commentDate);
        TextView commentRating = ownerCommentItem.findViewById(R.id.rating);
        ImageView deleteComment = ownerCommentItem.findViewById(R.id.deleteComment);
        ImageView reportComment = ownerCommentItem.findViewById(R.id.report_comment);

        String nameString = ownerCommentDTO.getGuestName() + " " + ownerCommentDTO.getGuestSurname();
        name.setText(nameString);
        content.setText(ownerCommentDTO.getContent());
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        String dateString = sdf.format(ownerCommentDTO.getDate());
        commentDate.setText(dateString);
        String ratingString = String.format("%.2f", ownerCommentDTO.getRating()) + "/5";
        commentRating.setText(ratingString);

        SharedPreferences sharedPref = getActivity().getPreferences(Context.MODE_PRIVATE);
        Long loggedId = sharedPref.getLong(USER_ID_KEY, -1);
        String jwt = sharedPref.getString(JWT_TOKEN_KEY, "");
        String roleString = sharedPref.getString(USER_ROLE_KEY, "");
        Log.d("LOGGED_IN", loggedId+"");
        Log.d("ROLE", roleString);

        if (loggedId == ownerCommentDTO.getGuestId()) {
            deleteComment.setVisibility(View.VISIBLE);

            deleteComment.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    handleDeleteComment(ownerCommentDTO.getId());
                }
            });
        } else {
            deleteComment.setVisibility(View.GONE);
        }

        if (loggedId == ownerCommentDTO.getGuestId() && roleString.equals(String.valueOf(Role.OWNER))) {
            reportComment.setVisibility(View.VISIBLE);
            reportComment.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    handleReportComment(ownerCommentDTO.getId());
                }
            });
        } else {
            reportComment.setVisibility(View.GONE);
        }

        return ownerCommentItem;

    }
    private void handleDeleteComment(Long commentId) {
        SharedPreferences sharedPref = getActivity().getPreferences(Context.MODE_PRIVATE);
        String jwt = sharedPref.getString(JWT_TOKEN_KEY, "");
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
        Call<Void> call = apiService.deleteOwnerComment(commentId);
        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if (response.code() == 200) {
                    Log.d("REZ", "Message received: " + response.code());
                    parentLayout.removeAllViews();
                    fetchOwnerCommentsFromServer();
                    Toast.makeText(getContext(), "Successfully deleted!", Toast.LENGTH_SHORT).show();
                } else {
                    Log.d("REZ", "Message received: " + response.code());
                    Toast.makeText(getContext(), "Error!", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                Log.d("REZ", t.getMessage() != null?t.getMessage():"error");
            }
        });

    }

    private void handleReportComment(Long commentId) {
        SharedPreferences sharedPref = getActivity().getPreferences(Context.MODE_PRIVATE);
        String jwt = sharedPref.getString(JWT_TOKEN_KEY, "");
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
        Call<OwnerCommentDTO> call = apiService.report(commentId);

        call.enqueue(new Callback<OwnerCommentDTO>() {
            @Override
            public void onResponse(Call<OwnerCommentDTO> call, Response<OwnerCommentDTO> response) {
                if (response.code() == 200) {
                    Log.d("REZ", "Message received: " + response.code());
                    Toast.makeText(getContext(), "Successfully reported!", Toast.LENGTH_SHORT).show();
                } else {
                    Log.d("REZ", "Message received: " + response.code());
                    Toast.makeText(getContext(), "Error!", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<OwnerCommentDTO> call, Throwable t) {
                Log.d("REZ", t.getMessage() != null?t.getMessage():"error");
            }
        });
    }

    private float calculateAverageRate(List<OwnerCommentDTO> comments) {
        if (comments == null || comments.isEmpty()) {
            return 0;
        }

        float totalRating = 0;

        for (OwnerCommentDTO comment : comments) {
            totalRating += comment.getRating();
        }

        return totalRating / comments.size();
    }

    private void updateAverageRating(float averageRating) {
        // Update UI components with the new average rating
        RatingBar rb = getView().findViewById(R.id.owner_rate);
        rb.setRating(averageRating);
        TextView rt = getView().findViewById(R.id.rating_text);
        rt.setText(averageRating+"");
    }


}