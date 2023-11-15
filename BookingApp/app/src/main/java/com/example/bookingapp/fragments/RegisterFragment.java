package com.example.bookingapp.fragments;



import android.os.Bundle;
import android.content.Context;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;


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
