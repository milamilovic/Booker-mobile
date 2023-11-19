package com.example.bookingapp.fragments;

import android.app.DatePickerDialog;
import android.graphics.Color;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.Nullable;


import androidx.annotation.NonNull;
import androidx.fragment.app.ListFragment;


import com.example.bookingapp.FragmentTransition;
import com.example.bookingapp.R;
import com.example.bookingapp.adapters.AccommodationListAdapter;
import com.example.bookingapp.databinding.FragmentAccommodationListingBinding;
import com.example.bookingapp.databinding.FragmentLoginBinding;
import com.example.bookingapp.model.AccommodationListing;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.slider.RangeSlider;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

public class AccommodationListingFragment extends Fragment {

    public static ArrayList<AccommodationListing> accommodations = new ArrayList<AccommodationListing>();
    private FragmentAccommodationListingBinding binding;
    private AccommodationListAdapter adapter;
    final Calendar myCalendar= Calendar.getInstance();

    private static final String ARG_PARAM = "param";

    ListView listView;

    public static AccommodationListingFragment newInstance() {
        return new AccommodationListingFragment();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            accommodations = getArguments().getParcelableArrayList(ARG_PARAM);
            adapter = new AccommodationListAdapter(getActivity(), accommodations);
        }
    }


    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        binding = FragmentAccommodationListingBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        prepareAccommodationList(accommodations);
        listView = root.findViewById(R.id.list);
        adapter = new AccommodationListAdapter(getContext(), accommodations);
        listView.setAdapter(adapter);

        Button btnFilters = binding.filter;
        btnFilters.setOnClickListener(v -> {
            BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(getActivity(), R.style.FullScreenBottomSheetDialog);
            View dialogView = getLayoutInflater().inflate(R.layout.bottom_sheet_filter, null);
            bottomSheetDialog.setContentView(dialogView);
            bottomSheetDialog.show();
        });


        EditText from = binding.from;

        DatePickerDialog.OnDateSetListener fromDate =new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int day) {
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH,month);
                myCalendar.set(Calendar.DAY_OF_MONTH,day);
                updateLabel(from, myCalendar);
            }
        };
        from.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new DatePickerDialog(getContext(),fromDate,myCalendar.get(Calendar.YEAR),myCalendar.get(Calendar.MONTH),myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

        EditText until = binding.until;

        DatePickerDialog.OnDateSetListener toDate =new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int day) {
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH,month);
                myCalendar.set(Calendar.DAY_OF_MONTH,day);
                updateLabel(until, myCalendar);
            }
        };
        until.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new DatePickerDialog(getContext(),toDate,myCalendar.get(Calendar.YEAR),myCalendar.get(Calendar.MONTH),myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });


        return root;
    }
    private void updateLabel(EditText editText, Calendar myCalendar){
        String myFormat="dd.MM.yyyy.";
        SimpleDateFormat dateFormat=new SimpleDateFormat(myFormat);
        editText.setText(dateFormat.format(myCalendar.getTime()));
        editText.setTextColor(Color.parseColor("#603c3c3c"));
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    private void prepareAccommodationList(ArrayList<AccommodationListing> products){
        products.add(new AccommodationListing(1L, "Flower Apartment", "Description 1", R.drawable.apartment_image, false, 180, 60, 4.1f));
        products.add(new AccommodationListing(2L, "Lovely Apartment", "Description 2", R.drawable.room_image, true, 450, 150, 4.6f));
        products.add(new AccommodationListing(3L, "Today Home", "Description 3", R.drawable.apartment_image, false, 110, 20, 5f));
        products.add(new AccommodationListing(4L, "Example Hotel", "Description 4", R.drawable.room_image, false, 360, 120, 4.2f));
        products.add(new AccommodationListing(1L, "Flower Apartment", "Description 1", R.drawable.apartment_image, false, 180, 60, 4.1f));
        products.add(new AccommodationListing(2L, "Lovely Apartment", "Description 2", R.drawable.room_image, true, 450, 150, 4.6f));
        products.add(new AccommodationListing(3L, "Today Home", "Description 3", R.drawable.apartment_image, false, 110, 20, 5f));
        products.add(new AccommodationListing(4L, "Example Hotel", "Description 4", R.drawable.room_image, false, 360, 120, 4.2f));
        products.add(new AccommodationListing(1L, "Flower Apartment", "Description 1", R.drawable.apartment_image, false, 180, 60, 4.1f));
        products.add(new AccommodationListing(2L, "Lovely Apartment", "Description 2", R.drawable.room_image, true, 450, 150, 4.6f));
        products.add(new AccommodationListing(3L, "Today Home", "Description 3", R.drawable.apartment_image, false, 110, 20, 5f));
        products.add(new AccommodationListing(4L, "Example Hotel", "Description 4", R.drawable.room_image, false, 360, 120, 4.2f));
        products.add(new AccommodationListing(1L, "Flower Apartment", "Description 1", R.drawable.apartment_image, false, 180, 60, 4.1f));
        products.add(new AccommodationListing(2L, "Lovely Apartment", "Description 2", R.drawable.room_image, true, 450, 150, 4.6f));
        products.add(new AccommodationListing(3L, "Today Home", "Description 3", R.drawable.apartment_image, false, 110, 20, 5f));
        products.add(new AccommodationListing(4L, "Example Hotel", "Description 4", R.drawable.room_image, false, 360, 120, 4.2f));
        products.add(new AccommodationListing(1L, "Flower Apartment", "Description 1", R.drawable.apartment_image, false, 180, 60, 4.1f));
        products.add(new AccommodationListing(2L, "Lovely Apartment", "Description 2", R.drawable.room_image, true, 450, 150, 4.6f));
        products.add(new AccommodationListing(3L, "Today Home", "Description 3", R.drawable.apartment_image, false, 110, 20, 5f));
        products.add(new AccommodationListing(4L, "Example Hotel", "Description 4", R.drawable.room_image, false, 360, 120, 4.2f));
    }


}