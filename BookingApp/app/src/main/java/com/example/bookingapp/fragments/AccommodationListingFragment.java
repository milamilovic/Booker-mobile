package com.example.bookingapp.fragments;

import android.app.DatePickerDialog;
import android.graphics.Color;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.os.StrictMode;
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
import com.example.bookingapp.clients.ClientUtils;
import com.example.bookingapp.databinding.FragmentAccommodationListingBinding;
import com.example.bookingapp.databinding.FragmentLoginBinding;
import com.example.bookingapp.dto.users.Token;
import com.example.bookingapp.model.AccommodationListing;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.slider.RangeSlider;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AccommodationListingFragment extends Fragment {

    public static ArrayList<AccommodationListing> accommodations = new ArrayList<AccommodationListing>();
    private FragmentAccommodationListingBinding binding;
    private AccommodationListAdapter adapter;
    final Calendar myCalendar= Calendar.getInstance();

    private static String location = "";
    private static Date fromDate = new Date();
    private static Date toDate = new Date();
    private static int people = 1;

    private static final String ARG_PARAM = "param";
    ListView listView;

    public static AccommodationListingFragment newInstance(String location, Date fromDate, Date toDate, int people) {
        AccommodationListingFragment fragment = new AccommodationListingFragment();
        Bundle args = new Bundle();
        SimpleDateFormat formatter = new SimpleDateFormat("dd.MM.yyyy.");
        args.putString("location", location);
        args.putString("fromDate", formatter.format(fromDate));
        args.putString("toDate", formatter.format(toDate));
        args.putInt("people", people);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (savedInstanceState != null) {
            // Restore value of members from saved state
            location = savedInstanceState.getString("location");
            SimpleDateFormat formatter = new SimpleDateFormat("dd.MM.yyyy.");
            try {
                fromDate = formatter.parse(savedInstanceState.getString("fromDate"));
                toDate = formatter.parse(savedInstanceState.getString("toDate"));
            } catch (ParseException e) {
                throw new RuntimeException(e);
            }
            people = savedInstanceState.getInt("people");
        }
        if (getArguments() != null) {
            // Restore value of members from saved state
            Bundle args = getArguments();
            location = args.getString("location");
            SimpleDateFormat formatter = new SimpleDateFormat("dd.MM.yyyy.");
            try {
                fromDate = formatter.parse(args.getString("fromDate"));
                toDate = formatter.parse(args.getString("toDate"));
            } catch (ParseException e) {
                throw new RuntimeException(e);
            }
            people = args.getInt("people");
            accommodations = getArguments().getParcelableArrayList(ARG_PARAM);
            adapter = new AccommodationListAdapter(getActivity(), accommodations);
        }
    }


    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        binding = FragmentAccommodationListingBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        accommodations = new ArrayList<>();
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
    public void onSaveInstanceState(Bundle savedInstanceState) {
        savedInstanceState.putString("location", location);
        SimpleDateFormat formatter = new SimpleDateFormat("dd.MM.yyyy.");
        String from = formatter.format(fromDate);
        String to = formatter.format(toDate);
        savedInstanceState.putString("fromDate", from);
        savedInstanceState.putString("toDate", to);
        savedInstanceState.putInt("people", people);

        // Always call the superclass so it can save the view hierarchy state
        super.onSaveInstanceState(savedInstanceState);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    private void prepareAccommodationList(ArrayList<AccommodationListing> products){
//        products.add(new AccommodationListing(1L, "Flower Apartment", "Description 1", R.drawable.apartment_image, false, 180, 60, 4.1f));
//        products.add(new AccommodationListing(2L, "Lovely Apartment", "Description 2", R.drawable.room_image, true, 450, 150, 4.6f));
//        products.add(new AccommodationListing(3L, "Today Home", "Description 3", R.drawable.apartment_image, false, 110, 20, 5f));
//        products.add(new AccommodationListing(4L, "Example Hotel", "Description 4", R.drawable.room_image, false, 360, 120, 4.2f));
//        products.add(new AccommodationListing(1L, "Flower Apartment", "Description 1", R.drawable.apartment_image, false, 180, 60, 4.1f));
//        products.add(new AccommodationListing(2L, "Lovely Apartment", "Description 2", R.drawable.room_image, true, 450, 150, 4.6f));
//        products.add(new AccommodationListing(3L, "Today Home", "Description 3", R.drawable.apartment_image, false, 110, 20, 5f));
//        products.add(new AccommodationListing(4L, "Example Hotel", "Description 4", R.drawable.room_image, false, 360, 120, 4.2f));
//        products.add(new AccommodationListing(1L, "Flower Apartment", "Description 1", R.drawable.apartment_image, false, 180, 60, 4.1f));
//        products.add(new AccommodationListing(2L, "Lovely Apartment", "Description 2", R.drawable.room_image, true, 450, 150, 4.6f));
//        products.add(new AccommodationListing(3L, "Today Home", "Description 3", R.drawable.apartment_image, false, 110, 20, 5f));
//        products.add(new AccommodationListing(4L, "Example Hotel", "Description 4", R.drawable.room_image, false, 360, 120, 4.2f));
//        products.add(new AccommodationListing(1L, "Flower Apartment", "Description 1", R.drawable.apartment_image, false, 180, 60, 4.1f));
//        products.add(new AccommodationListing(2L, "Lovely Apartment", "Description 2", R.drawable.room_image, true, 450, 150, 4.6f));
//        products.add(new AccommodationListing(3L, "Today Home", "Description 3", R.drawable.apartment_image, false, 110, 20, 5f));
//        products.add(new AccommodationListing(4L, "Example Hotel", "Description 4", R.drawable.room_image, false, 360, 120, 4.2f));
//        products.add(new AccommodationListing(1L, "Flower Apartment", "Description 1", R.drawable.apartment_image, false, 180, 60, 4.1f));
//        products.add(new AccommodationListing(2L, "Lovely Apartment", "Description 2", R.drawable.room_image, true, 450, 150, 4.6f));
//        products.add(new AccommodationListing(3L, "Today Home", "Description 3", R.drawable.apartment_image, false, 110, 20, 5f));
//        products.add(new AccommodationListing(4L, "Example Hotel", "Description 4", R.drawable.room_image, false, 360, 120, 4.2f));

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
    //call service and get accommodations that are adequate for search
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        Call<List<AccommodationListing>> accommodations = ClientUtils.accommodationService.search(formatter.format(fromDate), formatter.format(toDate), location, people);
        try{
            Response<List<AccommodationListing>> response = accommodations.execute();
            ArrayList<AccommodationListing> listings = (ArrayList<AccommodationListing>) response.body();
            for(AccommodationListing a : listings) {
                products.add(a);
            }
        }catch(Exception ex){
            System.out.println(formatter.format(fromDate));
            System.out.println(formatter.format(toDate));
            System.out.println("EXCEPTION WHILE GETTING ACCOMMODATIONS");
            ex.printStackTrace();
        }
    }


}