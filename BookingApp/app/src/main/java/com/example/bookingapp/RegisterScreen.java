package com.example.bookingapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.example.bookingapp.clients.ClientUtils;
import com.example.bookingapp.databinding.FragmentRegisterBinding;
import com.example.bookingapp.dto.users.CreateUserDTO;
import com.example.bookingapp.dto.users.UserDTO;
import com.example.bookingapp.enums.Role;
import com.example.bookingapp.fragments.HomeFragment;
import com.example.bookingapp.fragments.LoginFragment;
import com.example.bookingapp.fragments.RegisterFragment;

import java.io.IOException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegisterScreen extends AppCompatActivity {
    private EditText firstNameEditText;
    private EditText surnameEditText;
    private EditText addressEditText;
    private EditText phoneEditText;
    private EditText emailEditText;
    private EditText passwordEditText;
    private EditText passwordConfirmEditText;
    private RadioButton radioGuest;
    private RadioButton radioOwner;
    Button register;
    Button cancel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_screen);

        firstNameEditText = findViewById(R.id.first_name);
        surnameEditText = findViewById(R.id.surname);
        addressEditText = findViewById(R.id.address);
        phoneEditText = findViewById(R.id.phone);
        emailEditText = findViewById(R.id.email);
        passwordEditText = findViewById(R.id.password);
        passwordConfirmEditText = findViewById(R.id.password_confirm);
        radioGuest = findViewById(R.id.radio_guest);
        radioOwner = findViewById(R.id.radio_owner);
        register = findViewById(R.id.register);
        cancel = findViewById(R.id.cancel);

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                CreateUserDTO createUserDTO = new CreateUserDTO();
                createUserDTO.setName(firstNameEditText.getText().toString());
                createUserDTO.setSurname(surnameEditText.getText().toString());
                createUserDTO.setAddress(addressEditText.getText().toString());
                createUserDTO.setPhone(phoneEditText.getText().toString());
                createUserDTO.setEmail(emailEditText.getText().toString());
                createUserDTO.setPassword(passwordEditText.getText().toString());
                if (radioGuest.isSelected()) {
                    createUserDTO.setRole(Role.GUEST);
                } else {
                    createUserDTO.setRole(Role.OWNER);
                }

                new RegisterAsyncTask().execute(createUserDTO);
//                Call<CreateUserDTO> call = ClientUtils.userService.saveUser(createUserDTO);
//
//                try {
//                    Response<CreateUserDTO> response = call.execute();
//                    getActivity().getSupportFragmentManager().popBackStack();
//                    Toast.makeText(root.getContext(), "Register successful", Toast.LENGTH_SHORT).show();
//                    FragmentTransition.to(HomeFragment.newInstance(), getActivity(), false, R.id.fragment_placeholder);
//                } catch (Exception ex) {
//                    ex.printStackTrace();
//                }


            }
        });

        ToggleButton togglePasswordVisibility = findViewById(R.id.togglePasswordVisibility);

        togglePasswordVisibility.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                passwordEditText.setInputType(isChecked ?
                        InputType.TYPE_TEXT_VARIATION_PASSWORD :
                        InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                passwordEditText.setSelection(passwordEditText.getText().length());
            }
        });

        ToggleButton togglePasswordConfirmVisibility = findViewById(R.id.togglePasswordConfirmVisibility);
        togglePasswordConfirmVisibility.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                passwordConfirmEditText.setInputType(isChecked ?
                        InputType.TYPE_TEXT_VARIATION_PASSWORD :
                        InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                passwordConfirmEditText.setSelection(passwordConfirmEditText.getText().length());
            }
        });

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.fragment_placeholder, HomeFragment.newInstance())
                        .addToBackStack(null)
                        .commit();
            }
        });

        TextView logInText = findViewById(R.id.sign_up_text);
        logInText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.fragment_placeholder, LoginFragment.newInstance())
                        .addToBackStack(null)
                        .commit();

            }
        });

        Uri data = getIntent().getData();
        if (data != null) {
            String activationLink = data.getLastPathSegment();
            Log.d("Activation Link: ", activationLink);
            if (activationLink != null) {
                Call<UserDTO> call = ClientUtils.userService.activateProfile(activationLink);
                call.enqueue(new Callback<UserDTO>() {
                    @Override
                    public void onResponse(Call<UserDTO> call, Response<UserDTO> response) {
                        Log.d("REZ", "OnResponse entered");
                        if (response.code() == 200) {
                            Log.d("REZ", "Message received" + response.code());
                            Toast.makeText(getApplicationContext(), "Activation successful", Toast.LENGTH_SHORT).show();
                            getSupportFragmentManager().beginTransaction()
                                    .replace(R.id.fragment_placeholder, HomeFragment.newInstance())
                                    .addToBackStack(null)
                                    .commit();

                        } else {
                            Log.d("REZ", "Message received: " + response.code());
                        }
                    }

                    @Override
                    public void onFailure(Call<UserDTO> call, Throwable t) {
                        Log.d("REZ", t.getMessage() != null?t.getMessage():"error");
                    }
                });
            }
        }


    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onRestart() {
        super.onRestart();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    protected void register() {
        Intent i = new Intent(RegisterScreen.this, BaseActivity.class);
        startActivity(i);
    }

    private class RegisterAsyncTask extends AsyncTask<CreateUserDTO, Void, Boolean> {

        @Override
        protected Boolean doInBackground(CreateUserDTO... createUserDTOs) {
            try {
                Response<CreateUserDTO> response = ClientUtils.userService.saveUser(createUserDTOs[0]).execute();
                return response.isSuccessful();
            } catch (IOException e) {
                e.printStackTrace();
                return false;
            }
        }

        @Override
        protected void onPostExecute(Boolean success) {
            if (success) {
                getSupportFragmentManager().popBackStack();
                Toast.makeText(getApplicationContext(), "Register successful", Toast.LENGTH_SHORT).show();
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.fragment_placeholder, HomeFragment.newInstance())
                        .addToBackStack(null)
                        .commit();
            } else {
                Toast.makeText(getApplicationContext(), "Register failed", Toast.LENGTH_SHORT).show();
            }
        }
    }
}