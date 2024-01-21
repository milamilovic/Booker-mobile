package com.example.bookingapp.fragments;



import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.content.Context;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.text.InputType;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;




import com.example.bookingapp.BaseActivity;
import com.example.bookingapp.BuildConfig;
import com.example.bookingapp.FragmentTransition;
import com.example.bookingapp.R;
import com.example.bookingapp.clients.ClientUtils;
import com.example.bookingapp.clients.UserService;
import com.example.bookingapp.databinding.FragmentRegisterBinding;
import com.example.bookingapp.dto.users.CreateUserDTO;
import com.example.bookingapp.dto.users.UserDTO;
import com.example.bookingapp.enums.Role;

import androidx.fragment.app.Fragment;

import java.io.IOException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class RegisterFragment extends Fragment {

    private FragmentRegisterBinding binding;
    private EditText firstNameEditText;
    private EditText surnameEditText;
    private EditText addressEditText;
    private EditText phoneEditText;
    private EditText emailEditText;
    private EditText passwordEditText;
    private EditText passwordConfirmEditText;
    private RadioGroup roleRadio;
    private RadioButton radioGuest;
    private RadioButton radioOwner;

    private static final String ACTIVATION_LINK_KEY = "activation_link";

    public static RegisterFragment newInstance() {return new RegisterFragment();}

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentRegisterBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        firstNameEditText = binding.firstName;
        surnameEditText = binding.surname;
        addressEditText = binding.address;
        phoneEditText = binding.phone;
        emailEditText = binding.email;
        passwordEditText = binding.password;
        passwordConfirmEditText = binding.passwordConfirm;
        roleRadio = binding.roleRadio;
        radioGuest = binding.radioGuest;
        radioOwner = binding.radioOwner;

        //setUpRadioListeners();
        Button register = binding.register;
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
                int selectedRoleId = roleRadio.getCheckedRadioButtonId();
                if (selectedRoleId == R.id.radio_guest) {
                    createUserDTO.setRole(Role.GUEST);
                } else if (selectedRoleId == R.id.radio_owner) {
                    createUserDTO.setRole(Role.OWNER);
                }

               Call<CreateUserDTO> call = ClientUtils.userService.saveUser(createUserDTO);
                SharedPreferences sharedPref = getActivity().getPreferences(Context.MODE_PRIVATE);
                call.enqueue(new Callback<CreateUserDTO>() {
                    @Override
                    public void onResponse(Call<CreateUserDTO> call, Response<CreateUserDTO> response) {
                        if (response.code() == 201) {
                            Log.d("REZ", "Message received: " + response.code());
                            Toast.makeText(getContext(), "Activation link is sent to your email address!", Toast.LENGTH_SHORT).show();

                            Call<UserDTO> userDTOCall = ClientUtils.userService.activateProfile(response.body().getActivationLink());
                            userDTOCall.enqueue(new Callback<UserDTO>() {
                                @Override
                                public void onResponse(Call<UserDTO> call, Response<UserDTO> response) {
                                    if (response.code() == 200) {
                                        Log.d("REZ", "Message received: " + response.code());
                                    } else {
                                        Log.d("REZ", "Message received: " + response.code());
                                    }
                                }

                                @Override
                                public void onFailure(Call<UserDTO> call, Throwable t) {
                                    Log.d("REZ", t.getMessage() != null ? t.getMessage() : "error");
                                }
                            });
                        } else {
                            Log.d("REZ", "Message received: " + response.code());
                        }
                    }

                    @Override
                    public void onFailure(Call<CreateUserDTO> call, Throwable t) {
                        Log.d("REZ", t.getMessage() != null ? t.getMessage() : "error");
                    }
                });



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

        ToggleButton togglePasswordConfirmVisibility = binding.togglePasswordConfirmVisibility;
        togglePasswordConfirmVisibility.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                passwordConfirmEditText.setInputType(isChecked ?
                        InputType.TYPE_TEXT_VARIATION_PASSWORD :
                        InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                passwordConfirmEditText.setSelection(passwordConfirmEditText.getText().length());
            }
        });

        Button btnCancel = binding.cancel;
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentTransition.to(HomeFragment.newInstance(), getActivity(), false, R.id.fragment_placeholder);
            }
        });

        TextView logInText = binding.signUpText;
        logInText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentTransition.to(LoginFragment.newInstance(), getActivity(), false, R.id.fragment_placeholder);
            }
        });


        return root;
    }

    private void setUpRadioListeners() {
        RadioGroup roleRadioGroup = binding.roleRadio;
        roleRadioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if(checkedId == R.id.radio_guest) {
                    Toast.makeText(getContext(), "Guest selected", Toast.LENGTH_SHORT).show();
                }
                else if(checkedId == R.id.radio_owner) {
                    Toast.makeText(getContext(), "Owner selected", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }



    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
    

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }


}
