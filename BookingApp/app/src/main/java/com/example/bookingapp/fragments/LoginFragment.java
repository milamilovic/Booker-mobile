package com.example.bookingapp.fragments;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.example.bookingapp.databinding.FragmentLoginBinding;


public class LoginFragment extends Fragment {

    private FragmentLoginBinding binding;

    public static LoginFragment newInstance() {
        return new LoginFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        binding = FragmentLoginBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        Button logIn = binding.login;
        logIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(root.getContext(), "Login button", Toast.LENGTH_SHORT).show();
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

        binding.email.setOnFocusChangeListener((v, hasFocus) -> {
            Log.d("LoginFragment", "onFocusChange: " + hasFocus);
        });

        binding.email.setOnTouchListener((v, event) -> {
            Log.d("LoginFragment", "onTouchEvent: " + event.getAction());
            return false; // return true if you consume the event, false otherwise
        });
    }



    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}