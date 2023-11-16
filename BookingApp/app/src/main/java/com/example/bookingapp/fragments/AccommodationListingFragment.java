package com.example.bookingapp.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
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

import java.util.ArrayList;

public class AccommodationListingFragment extends Fragment {

    public static ArrayList<AccommodationListing> accommodations = new ArrayList<AccommodationListing>();
    private FragmentAccommodationListingBinding binding;
    private AccommodationListAdapter adapter;

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
        AccommodationListAdapter adapter = new AccommodationListAdapter(getContext(), accommodations);
        listView.setAdapter(adapter);

        Button btnFilters = binding.filter;
        btnFilters.setOnClickListener(v -> {
            BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(getActivity(), R.style.FullScreenBottomSheetDialog);
            View dialogView = getLayoutInflater().inflate(R.layout.bottom_sheet_filter, null);
            bottomSheetDialog.setContentView(dialogView);
            bottomSheetDialog.show();
        });

//        Spinner spinner = binding.btnSort;
//        // Create an ArrayAdapter using the string array and a default spinner layout
//        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(getActivity(),
//                android.R.layout.simple_spinner_item,
//                getResources().getStringArray(R.array.sort_array));
//        // Specify the layout to use when the list of choices appears
//        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//        // Apply the adapter to the spinner
//        spinner.setAdapter(arrayAdapter);
//        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
//            @Override
//            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
//
//                AlertDialog.Builder dialog = new AlertDialog.Builder(getActivity());
//                dialog.setMessage("Change the sort option?")
//                        .setCancelable(false)
//                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
//                            public void onClick(DialogInterface dialog, int id) {
//                                Log.v("ShopApp", (String) parent.getItemAtPosition(position));
//                                ((TextView) parent.getChildAt(0)).setTextColor(Color.MAGENTA);
//                            }
//                        })
//                        .setNegativeButton("No", new DialogInterface.OnClickListener() {
//                            public void onClick(DialogInterface dialog, int id) {
//                                dialog.cancel();
//                            }
//                        });
//                AlertDialog alert = dialog.create();
//                alert.show();
//            }
//
//
//            @Override
//            public void onNothingSelected(AdapterView<?> parent) {
//                // TODO Auto-generated method stub
//            }
//        });


        return root;
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