package com.example.bookingapp;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;

import android.os.StrictMode;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.bookingapp.clients.ClientUtils;
import com.example.bookingapp.clients.OwnerCommentService;
import com.example.bookingapp.dto.commentsAndRatings.OwnerCommentDTO;
import com.example.bookingapp.dto.users.GuestDTO;
import com.example.bookingapp.dto.users.OwnerDTO;
import com.example.bookingapp.dto.users.UserDTO;
import com.example.bookingapp.enums.Role;
import com.example.bookingapp.fragments.HomeFragment;

import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Locale;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link OwnerMyProfileFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class OwnerMyProfileFragment extends Fragment {

    private static Long id;
    private UserDTO user;
    private OwnerDTO owner;
    private static final String USER_ID_KEY = "user_id";
    private static final String JWT_TOKEN_KEY = "jwt_token";
    private static final String USER_ROLE_KEY = "user_role";
    private LinearLayout parentLayout;
    private float total = 0;
    private float average = 0;

    public OwnerMyProfileFragment() {
        // Required empty public constructor
    }

    public OwnerMyProfileFragment(UserDTO user){
        this.user = user;
    }


    public static OwnerMyProfileFragment newInstance(UserDTO user) {
        OwnerMyProfileFragment fragment = new OwnerMyProfileFragment();
        Bundle args = new Bundle();
        args.putLong("id", user.getId());
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            Bundle args = getArguments();
            SharedPreferences sharedPref = getActivity().getPreferences(Context.MODE_PRIVATE);
            id = sharedPref.getLong(USER_ID_KEY, 0);
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
        View view = inflater.inflate(R.layout.fragment_owner_my_profile, container, false);
        parentLayout = view.findViewById(R.id.comment_section);
        parentLayout.removeAllViews();
        fetchCommentsFromServer();

        // TODO load profile picture path
        /*ImageView bigProfilePic = view.findViewById(R.id.profile_pic);
        bigProfilePic.setImageURI(Uri.parse(user.getProfilePicture().getPath_mobile()));

        ImageView miniProfilePic = view.findViewById(R.id.mini_profile_pic);
        miniProfilePic.setImageURI(Uri.parse(user.getProfilePicture().getPath_mobile()));
        System.out.println(Uri.parse(user.getProfilePicture().getPath_mobile()));*/

        EditText name = view.findViewById(R.id.name);
        name.setText(owner.getName() + " " + owner.getSurname());

        TextView role = view.findViewById(R.id.role);
        role.setText(owner.getRole().toString());

        EditText email = view.findViewById(R.id.email_profile);
        email.setText(owner.getEmail());

        EditText address = view.findViewById(R.id.address_profile);
        address.setText(owner.getAddress());

        EditText phone = view.findViewById(R.id.phone_profile);
        phone.setText(owner.getPhone());

        EditText password = view.findViewById(R.id.password_profile_field);
        EditText confirm_password = view.findViewById(R.id.password_profile_confirm_field);

        RatingBar rb = view.findViewById(R.id.owner_rate);
        rb.setRating(4.1f);

        Button apply_changes_btn = view.findViewById(R.id.apply_profile_changes_button);
        apply_changes_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                owner.setName(name.getText().toString().split(" ")[0]);
                owner.setSurname(name.getText().toString().split(" ")[1]);
                owner.setEmail(email.getText().toString());
                owner.setAddress(address.getText().toString());
                owner.setPhone(phone.getText().toString());
                if (!password.getText().toString().isEmpty() && !confirm_password.getText().toString().isEmpty() &&
                        password.getText().toString().equals(confirm_password.getText().toString())) {
                    owner.setPassword(password.getText().toString());
                    System.out.println("Ovo je sifra " + password.getText().toString());
                } else if (!password.getText().toString().isEmpty() && !confirm_password.getText().toString().isEmpty() &&
                        !password.getText().toString().equals(confirm_password.getText().toString())) {
                    Toast.makeText(getActivity(), "Password and confirm password must be the same!", Toast.LENGTH_SHORT).show();
                } else if ((password.getText().toString().isEmpty() && !confirm_password.getText().toString().isEmpty()) ||
                        (!password.getText().toString().isEmpty() && confirm_password.getText().toString().isEmpty())) {
                    Toast.makeText(getActivity(), "Password and confirm password must be the same!", Toast.LENGTH_SHORT).show();
                } else if (password.getText().toString().isEmpty() && confirm_password.getText().toString().isEmpty()) {
                }

                Call<OwnerDTO> stringCall = ClientUtils.userService.updateOwner(owner.getId(), owner);
                try {
                    Response<OwnerDTO> response = stringCall.execute();
                    System.out.println(response.body());
                } catch (Exception ex) {
                    System.out.println("EXCEPTION WHILE UPDATING OWNER DATA");
                    ex.printStackTrace();
                }
                FragmentTransition.to(HomeFragment.newInstance(), (FragmentActivity) getContext(), true, R.id.fragment_placeholder);

            }
        });

        Button delete_btn = view.findViewById(R.id.delete_profile_button);
        delete_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LayoutInflater inflater = LayoutInflater.from(requireContext());
                View customLayout = inflater.inflate(R.layout.fragment_delete, null);

                AlertDialog.Builder builder = new AlertDialog.Builder(requireContext()).setNegativeButton("Cancel",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        }).setPositiveButton("Delete", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        Intent i = new Intent(getActivity(), BaseActivity.class);
                        startActivity(i);
                        //TODO logout user and delete account
                        // redirection to home page for unregistered user
                        Toast.makeText(getActivity(), "Deleted!", Toast.LENGTH_SHORT).show();
                    }
                });
                builder.setView(customLayout);
                AlertDialog dialog = builder.create();
                dialog.show();
            }
        });

        Button logout_btn = view.findViewById(R.id.logout_profile_button);
        logout_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getActivity(), BaseActivity.class);
                startActivity(i);
                //TODO log out
            }
        });

        return view;
    }

    private void fetchCommentsFromServer() {
        SharedPreferences sharedPref = getActivity().getPreferences(Context.MODE_PRIVATE);
        String jwt = sharedPref.getString(JWT_TOKEN_KEY, "");
        Long loggedId = sharedPref.getLong(USER_ID_KEY, -1);
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
        Call<List<OwnerCommentDTO>> call = apiService.getAllForOwner(loggedId);
        call.enqueue(new Callback<List<OwnerCommentDTO>>() {
            @Override
            public void onResponse(Call<List<OwnerCommentDTO>> call, Response<List<OwnerCommentDTO>> response) {
                if (response.code() == 200) {
                    Log.d("REZ", "Message received: " + response.code());
                    List<OwnerCommentDTO> ownerCommentsList = response.body();
                    for (OwnerCommentDTO ownerCommentDTO : ownerCommentsList) {
                        View ownerCommentItem = createOwnerCommentItem(ownerCommentDTO);
                        parentLayout.addView(ownerCommentItem);
                        total += ownerCommentDTO.getRating();
                    }
                    average = total / ownerCommentsList.size();
                    updateAverageRating(average);
                } else {
                    Log.d("REZ", "Message received: " + response.code());
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

        deleteComment.setVisibility(View.GONE);

        if (loggedId == ownerCommentDTO.getOwnerId()) {
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

    private void updateAverageRating(float averageRating) {
        // Update UI components with the new average rating
        RatingBar rb = getView().findViewById(R.id.owner_rate);
        rb.setRating(averageRating);
        TextView rt = getView().findViewById(R.id.owner_rate_number);
        rt.setText(averageRating+"");
    }
}