package com.example.bookingapp.fragments;



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
import com.example.bookingapp.databinding.FragmentRegisterBinding;
import androidx.fragment.app.Fragment;


public class RegisterFragment extends Fragment {

    private FragmentRegisterBinding binding;

    public static RegisterFragment newInstance() {return new RegisterFragment();}

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentRegisterBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        setUpRadioListeners();
        Button register = binding.register;
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(root.getContext(), "Register successful", Toast.LENGTH_SHORT).show();
                FragmentTransition.to(HomeFragment.newInstance(), getActivity(), false, R.id.fragment_placeholder);
            }
        });

        EditText editTextPassword = binding.password;
        ToggleButton togglePasswordVisibility = binding.togglePasswordVisibility;

        togglePasswordVisibility.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                editTextPassword.setInputType(isChecked ?
                        InputType.TYPE_TEXT_VARIATION_PASSWORD :
                        InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                editTextPassword.setSelection(editTextPassword.getText().length());
            }
        });

        EditText editTextPasswordConfirm = binding.passwordConfirm;
        ToggleButton togglePasswordConfirmVisibility = binding.togglePasswordConfirmVisibility;
        togglePasswordConfirmVisibility.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                editTextPasswordConfirm.setInputType(isChecked ?
                        InputType.TYPE_TEXT_VARIATION_PASSWORD :
                        InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                editTextPasswordConfirm.setSelection(editTextPasswordConfirm.getText().length());
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
