package com.example.bookingapp.fragments;

import android.app.DatePickerDialog;
import android.graphics.Color;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ListView;

import com.example.bookingapp.R;
import com.example.bookingapp.adapters.AccommodationListAdapter;
import com.example.bookingapp.adapters.FavouriteAccommodationAdapter;
import com.example.bookingapp.databinding.FragmentAccommodationListingBinding;
import com.example.bookingapp.databinding.FragmentFavouriteAccommodationsBinding;
import com.example.bookingapp.model.AccommodationListing;
import com.example.bookingapp.model.FavouriteAccommodationListing;
import com.google.android.material.bottomsheet.BottomSheetDialog;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

public class FavouriteAccommodationsFragment extends Fragment {

    public static ArrayList<FavouriteAccommodationListing> accommodations = new ArrayList<FavouriteAccommodationListing>();
    private FragmentFavouriteAccommodationsBinding binding;
    private FavouriteAccommodationAdapter adapter;
    final Calendar myCalendar= Calendar.getInstance();

    private static final String ARG_PARAM = "param";

    ListView listView;

    public static FavouriteAccommodationsFragment newInstance() {
        return new FavouriteAccommodationsFragment();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            accommodations = getArguments().getParcelableArrayList(ARG_PARAM);
            adapter = new FavouriteAccommodationAdapter(getActivity(), accommodations);
        }
    }


    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        binding = FragmentFavouriteAccommodationsBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        prepareAccommodationList(accommodations);
        listView = root.findViewById(R.id.list);
        adapter = new FavouriteAccommodationAdapter(getContext(), accommodations);
        listView.setAdapter(adapter);


        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    private void prepareAccommodationList(ArrayList<FavouriteAccommodationListing> products){
        products.add(new FavouriteAccommodationListing(1L, "Flower Apartment", "Description 1", R.drawable.apartment_image, false, 180, 60, 4.1f, "Cinque Terre, Italy"));
        products.add(new FavouriteAccommodationListing(2L, "Lovely Apartment", "Description 2", R.drawable.room_image, true, 450, 150, 4.6f, "Cannaregio, Venice"));
        products.add(new FavouriteAccommodationListing(3L, "Today Home", "Description 3", R.drawable.apartment_image, false, 110, 20, 5f, "Dorsoduro, Venice"));
        products.add(new FavouriteAccommodationListing(4L, "Example Hotel", "Description 4", R.drawable.room_image, false, 360, 120, 4.2f, "Piazza, Peru"));
        products.add(new FavouriteAccommodationListing(1L, "Flower Apartment", "Description 1", R.drawable.apartment_image, false, 180, 60, 4.1f, "Cinque Terre, Italy"));
        products.add(new FavouriteAccommodationListing(2L, "Lovely Apartment", "Description 2", R.drawable.room_image, true, 450, 150, 4.6f, "Cannaregio, Venice"));
        products.add(new FavouriteAccommodationListing(3L, "Today Home", "Description 3", R.drawable.apartment_image, false, 110, 20, 5f, "Dorsoduro, Venice"));
        products.add(new FavouriteAccommodationListing(4L, "Example Hotel", "Description 4", R.drawable.room_image, false, 360, 120, 4.2f, "Piazza, Peru"));
        products.add(new FavouriteAccommodationListing(1L, "Flower Apartment", "Description 1", R.drawable.apartment_image, false, 180, 60, 4.1f, "Cinque Terre, Italy"));
        products.add(new FavouriteAccommodationListing(2L, "Lovely Apartment", "Description 2", R.drawable.room_image, true, 450, 150, 4.6f, "Cannaregio, Venice"));
        products.add(new FavouriteAccommodationListing(3L, "Today Home", "Description 3", R.drawable.apartment_image, false, 110, 20, 5f, "Dorsoduro, Venice"));
        products.add(new FavouriteAccommodationListing(4L, "Example Hotel", "Description 4", R.drawable.room_image, false, 360, 120, 4.2f, "Piazza, Peru"));
        products.add(new FavouriteAccommodationListing(1L, "Flower Apartment", "Description 1", R.drawable.apartment_image, false, 180, 60, 4.1f, "Cinque Terre, Italy"));
        products.add(new FavouriteAccommodationListing(2L, "Lovely Apartment", "Description 2", R.drawable.room_image, true, 450, 150, 4.6f, "Cannaregio, Venice"));
        products.add(new FavouriteAccommodationListing(3L, "Today Home", "Description 3", R.drawable.apartment_image, false, 110, 20, 5f, "Dorsoduro, Venice"));
        products.add(new FavouriteAccommodationListing(4L, "Example Hotel", "Description 4", R.drawable.room_image, false, 360, 120, 4.2f, "Piazza, Peru"));
    }
}