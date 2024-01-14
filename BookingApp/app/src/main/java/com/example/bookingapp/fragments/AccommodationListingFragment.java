package com.example.bookingapp.fragments;

import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.os.StrictMode;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CheckBox;
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
import com.example.bookingapp.model.CheckBoxFilter;
import com.example.bookingapp.model.Filter;
import com.example.bookingapp.model.PriceFilter;
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
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
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
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
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
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
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

            //finding all elements
            //amenities
            ArrayList<CheckBox> amenities = new ArrayList<>();
            CheckBox wifi = bottomSheetDialog.findViewById(R.id.wifi);
            CheckBox ac = bottomSheetDialog.findViewById(R.id.ac);
            CheckBox good_location = bottomSheetDialog.findViewById(R.id.location);
            CheckBox cancellation = bottomSheetDialog.findViewById(R.id.cancellation);
            amenities.add(wifi);
            amenities.add(ac);
            amenities.add(good_location);
            amenities.add(cancellation);
            //accommodation types
            ArrayList<CheckBox> types = new ArrayList<>();
            CheckBox hotel = bottomSheetDialog.findViewById(R.id.hotel);
            CheckBox room = bottomSheetDialog.findViewById(R.id.room);
            CheckBox studio = bottomSheetDialog.findViewById(R.id.studio);
            CheckBox villa = bottomSheetDialog.findViewById(R.id.villa);
            types.add(hotel);
            types.add(room);
            types.add(studio);
            types.add(villa);
            EditText min_price = bottomSheetDialog.findViewById(R.id.min_price);
            EditText max_price = bottomSheetDialog.findViewById(R.id.max_price);

            bottomSheetDialog.findViewById(R.id.apply).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    bottomSheetDialog.dismiss();
                }
            });

            //apply filters on close
            bottomSheetDialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
                @Override
                public void onDismiss(DialogInterface dialog) {
                    //find out which ones are checked
                    ArrayList<Filter> filters = new ArrayList<Filter>();
                    //each checked amenity goes into filters
                    for (CheckBox amenity : amenities) {
                        if (amenity.isChecked()) {
                            filters.add(new Filter(amenity.getText().toString(), new CheckBoxFilter(true)));
                        }
                    }
                    //each checked room type goes into filters
                    for (CheckBox type : types) {
                        if (type.isChecked()) {
                            filters.add(new Filter(type.getText().toString(), new CheckBoxFilter(true)));
                        }
                    }
                    //prices if entered
                    String min_price_str = min_price.getText().toString();
                    String max_price_str = max_price.getText().toString();
                    if (!min_price_str.matches("")) {
                        filters.add(new Filter("minPrice", new PriceFilter(Integer.parseInt(min_price_str))));
                    }
                    if (!max_price_str.matches("")) {
                        filters.add(new Filter("maxPrice", new PriceFilter(Integer.parseInt(max_price_str))));
                    }
                    //contact backend
                    accommodations.clear();   //to clear anything left behind
                    StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
                    StrictMode.setThreadPolicy(policy);
                    //call service and get accommodations that are adequate for search
                    SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
                    Call<List<AccommodationListing>> call = ClientUtils.accommodationService.searchAndFilter(formatter.format(fromDate), formatter.format(toDate), location, people, filters);
                    try{
                        Response<List<AccommodationListing>> response = call.execute();
                        ArrayList<AccommodationListing> listings = (ArrayList<AccommodationListing>) response.body();
                        accommodations.addAll(listings);
                        adapter = new AccommodationListAdapter(getContext(), accommodations);
                        listView.setAdapter(adapter);
                    }catch(Exception ex){
                        System.out.println(formatter.format(fromDate));
                        System.out.println(formatter.format(toDate));
                        System.out.println("EXCEPTION WHILE FILTERING ACCOMMODATIONS");
                        ex.printStackTrace();
                    }
                }
            });

            bottomSheetDialog.show();
        });

        EditText location_edit = binding.location;
        location_edit.setText(location);


        EditText from = binding.from;
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        from.setText(formatter.format(fromDate));

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
        until.setText(formatter.format(toDate));

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

        EditText people_edit = binding.people;
        people_edit.setText(Integer.toString(people));

        binding.search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                location = location_edit.getText().toString();
                people = Integer.parseInt(people_edit.getText().toString());

                prepareAccommodationList(accommodations);

                adapter = new AccommodationListAdapter(getContext(), accommodations);
                listView.setAdapter(adapter);
            }
        });


        return root;
    }
    private void updateLabel(EditText editText, Calendar myCalendar){
        String myFormat="yyyy-MM-dd";
        SimpleDateFormat dateFormat=new SimpleDateFormat(myFormat);
        editText.setText(dateFormat.format(myCalendar.getTime()));
        editText.setTextColor(Color.parseColor("#603c3c3c"));
    }

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        savedInstanceState.putString("location", location);
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
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
        products.clear();   //in case it's not initialization but searching
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