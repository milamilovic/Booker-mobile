package com.example.bookingapp.fragments;



import android.os.AsyncTask;
import android.os.Bundle;
import android.content.Context;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.text.InputType;
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
import com.example.bookingapp.FragmentTransition;
import com.example.bookingapp.R;
import com.example.bookingapp.clients.ClientUtils;
import com.example.bookingapp.databinding.FragmentRegisterBinding;
import com.example.bookingapp.dto.users.CreateUserDTO;
import com.example.bookingapp.enums.Role;

import androidx.fragment.app.Fragment;

import java.io.IOException;

import retrofit2.Call;
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
    private RadioButton radioGuest;
    private RadioButton radioOwner;

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
                getActivity().getSupportFragmentManager().popBackStack();
                Toast.makeText(getContext(), "Register successful", Toast.LENGTH_SHORT).show();
                FragmentTransition.to(HomeFragment.newInstance(), getActivity(), false, R.id.fragment_placeholder);
            } else {
                Toast.makeText(getContext(), "Register failed", Toast.LENGTH_SHORT).show();
            }
        }
    }
}
