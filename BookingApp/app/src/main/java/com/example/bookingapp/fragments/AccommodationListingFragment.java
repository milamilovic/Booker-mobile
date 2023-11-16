package com.example.bookingapp.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.example.bookingapp.R;
import com.example.bookingapp.databinding.FragmentAccommodationListingBinding;
import com.example.bookingapp.databinding.FragmentLoginBinding;

public class AccommodationListingFragment extends Fragment {

    FragmentAccommodationListingBinding binding;
    public AccommodationListingFragment() {
        // Required empty public constructor
    }

    // TODO: Rename and change types and number of parameters
    public static AccommodationListingFragment newInstance() {
        AccommodationListingFragment fragment = new AccommodationListingFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentAccommodationListingBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        return root;
    }
}