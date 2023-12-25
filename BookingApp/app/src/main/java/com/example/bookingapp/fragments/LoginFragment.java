package com.example.bookingapp.fragments;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.StrictMode;
import android.text.InputType;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.example.bookingapp.BaseActivity;
import com.example.bookingapp.FragmentTransition;
import com.example.bookingapp.R;
import com.example.bookingapp.clients.ClientUtils;
import com.example.bookingapp.databinding.FragmentLoginBinding;
import com.example.bookingapp.dto.users.LoginUserDTO;
import com.example.bookingapp.dto.users.Token;
import com.example.bookingapp.dto.users.UserDTO;
import com.example.bookingapp.enums.Role;

import java.io.IOException;
import java.lang.ref.WeakReference;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class LoginFragment extends Fragment {

    private FragmentLoginBinding binding;
    private Token token;
    private BaseActivity baseActivity;
    private static SharedPreferences sharedPreferences;

    private static final String JWT_TOKEN_KEY = "jwt_token";
    private static final String USER_ID_KEY = "user_id";
    private static final String USER_ROLE_KEY = "user_role";

    public static LoginFragment newInstance() {
        return new LoginFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        binding = FragmentLoginBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        EditText emailEditText = binding.email;
        EditText passwordEditText = binding.password;
        Button logIn = binding.login;
        logIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
//                StrictMode.setThreadPolicy(policy);

                Log.d("BookingApp", "Login start");
                LoginUserDTO loginUserDTO = new LoginUserDTO(emailEditText.getText().toString(), passwordEditText.getText().toString());
                Call<Token> call = ClientUtils.userService.findByEmailAndPassword(loginUserDTO);

//                try{
//                    Response<Token> response = call.execute();
//                    Token product1 = response.body();
//                    System.out.println(product1);
//                    SharedPreferences sharedPref = getActivity().getPreferences(Context.MODE_PRIVATE);
//                    SharedPreferences.Editor editor = sharedPref.edit();
//                    editor.putString(JWT_TOKEN_KEY, product1.getToken());
//                    editor.putLong(USER_ID_KEY, product1.getUserId());
//                    editor.apply();
//                    getActivity().getSupportFragmentManager().popBackStack();
//                    Toast.makeText(root.getContext(), "Login successful", Toast.LENGTH_SHORT).show();
//                    FragmentTransition.to(HomeFragment.newInstance(), getActivity(), false, R.id.fragment_placeholder);
//                }catch(Exception ex){
//                    ex.printStackTrace();
//                }

                call.enqueue(new Callback<Token>() {
                    @Override
                    public void onResponse(Call<Token> call, Response<Token> response) {
                        Log.d("REZ", "onResponse entered");
                        if (response.code() == 200) {
                            Log.d("REZ","Meesage recieved");
                            System.out.println(response.body());
                            Token product1 = response.body();
                            System.out.println(product1);
                            SharedPreferences sharedPref = getActivity().getPreferences(Context.MODE_PRIVATE);
                            SharedPreferences.Editor editor = sharedPref.edit();
                            editor.putString(JWT_TOKEN_KEY, product1.getToken());
                            editor.putLong(USER_ID_KEY, product1.getUserId());
                            editor.apply();
                            Call<UserDTO> userDTOCall = ClientUtils.userService.getById(product1.getUserId());
                            userDTOCall.enqueue(new Callback<UserDTO>() {
                                @Override
                                public void onResponse(Call<UserDTO> call, Response<UserDTO> response) {
                                    UserDTO userDTO = response.body();
                                    Role role = userDTO.getRole();
                                    SharedPreferences sharedPref = getActivity().getPreferences(Context.MODE_PRIVATE);
                                    SharedPreferences.Editor editor = sharedPref.edit();
                                    editor.putString(USER_ROLE_KEY, String.valueOf(role));
                                    editor.apply();
                                }

                                @Override
                                public void onFailure(Call<UserDTO> call, Throwable t) {
                                    Log.d("REZ", "Error in retrieving user role!");
                                }
                            });
                            getActivity().getSupportFragmentManager().popBackStack();
                            Toast.makeText(root.getContext(), "Login successful", Toast.LENGTH_SHORT).show();
                            FragmentTransition.to(HomeFragment.newInstance(), getActivity(), false, R.id.fragment_placeholder);
                        } else {
                            Log.d("REZ","Meesage recieved: "+response.code());
                        }
                    }

                    @Override
                    public void onFailure(Call<Token> call, Throwable t) {
                        Log.d("REZ", t.getMessage() != null?t.getMessage():"error");
                    }
                });

            }
        });


        ToggleButton togglePasswordVisibility = binding.togglePasswordVisibility;
        togglePasswordVisibility.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                passwordEditText.setInputType(isChecked ?
                        InputType.TYPE_TEXT_VARIATION_PASSWORD :
                        InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                passwordEditText.setSelection(passwordEditText.getText().length());
            }
        });

        Button btnCancel = binding.cancel;
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentTransition.to(HomeFragment.newInstance(), getActivity(), false, R.id.fragment_placeholder);
            }
        });

        TextView signUpText = binding.signUpText;
        signUpText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentTransition.to(RegisterFragment.newInstance(), getActivity(), false, R.id.fragment_placeholder);
            }
        });
        return root;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }



    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


    }



    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }



}



