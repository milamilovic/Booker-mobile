package com.example.bookingapp.fragments;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.bookingapp.R;
import com.example.bookingapp.clients.ClientUtils;
import com.example.bookingapp.clients.UserService;
import com.example.bookingapp.databinding.FragmentReportUserDialogBinding;
import com.example.bookingapp.dto.users.CreateReportUserDTO;
import com.example.bookingapp.dto.users.UserReportDTO;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class ReportUserDialogFragment extends DialogFragment {

    private static Long id;
    private FragmentReportUserDialogBinding binding;
    private static final String JWT_TOKEN_KEY = "jwt_token";
    private static final String USER_ID_KEY = "user_id";
    EditText newReason;

    public ReportUserDialogFragment() {
        // Required empty public constructor
    }

    public ReportUserDialogFragment(Long id) {
        this.id = id;
    }



    public static ReportUserDialogFragment newInstance(Long id) {
        ReportUserDialogFragment fragment = new ReportUserDialogFragment(id);
        Bundle args = new Bundle();
        args.putLong("id", id);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            int userId = getArguments().getInt("id", -1);
            // Do something with the user ID
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentReportUserDialogBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        newReason = binding.newReason;
        Button submit = binding.btnSubmit;
        Button cancel = binding.btnCancel;

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                handleReportUser();
            }
        });

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    private void handleReportUser() {
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

        // Create the service interface using Retrofit
        UserService apiService = retrofit.create(UserService.class);
        CreateReportUserDTO createReportUserDTO = new CreateReportUserDTO();
        createReportUserDTO.setReportedId(id);
        createReportUserDTO.setReporterId(loggedId);
        createReportUserDTO.setReason(newReason.getText().toString());
        Call<UserReportDTO> call = apiService.addReport(createReportUserDTO);
        call.enqueue(new Callback<UserReportDTO>() {
            @Override
            public void onResponse(Call<UserReportDTO> call, Response<UserReportDTO> response) {
                if (response.code() == 201) {
                    Log.d("REZ", "Message received: " + response.code());
                    Toast.makeText(getContext(), "User successfully reported!", Toast.LENGTH_SHORT).show();
                    dismiss();
                } else {
                    Log.d("REZ", "Message received: " + response.code());
                    Toast.makeText(getContext(), "Error in reporting user!", Toast.LENGTH_SHORT);
                    dismiss();
                }
            }

            @Override
            public void onFailure(Call<UserReportDTO> call, Throwable t) {
                Log.d("REZ", t.getMessage() != null?t.getMessage():"error");
                Toast.makeText(getContext(), "Error in reporting user!", Toast.LENGTH_SHORT).show();
            }
        });
    }
}