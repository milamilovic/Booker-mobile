package com.example.bookingapp.fragments;

import android.app.AlertDialog;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.os.Bundle;

import androidx.annotation.DrawableRes;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ScrollView;

import com.applandeo.materialcalendarview.CalendarDay;
import com.applandeo.materialcalendarview.CalendarUtils;
import com.applandeo.materialcalendarview.CalendarView;
import com.applandeo.materialcalendarview.EventDay;
import com.applandeo.materialcalendarview.listeners.OnDayClickListener;
import com.applandeo.materialcalendarview.listeners.OnDayLongClickListener;
import com.example.bookingapp.FragmentTransition;
import com.example.bookingapp.R;
import com.example.bookingapp.adapters.AccommodationListAdapter;
import com.example.bookingapp.adapters.AmenityAdapter;
import com.example.bookingapp.adapters.ImageAdapter;
import com.example.bookingapp.model.Accommodation;
import com.example.bookingapp.model.AccommodationListing;
import com.example.bookingapp.model.Amenity;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.material.datepicker.DayViewDecorator;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Random;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link AccommodationViewFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class AccommodationViewFragment extends Fragment {

    private Accommodation accommodation;

    public static ArrayList<Amenity> amenities = new ArrayList<Amenity>();
    ListView listView;

    private static final String ARG_PARAM = "param";
    Button showMap;
    public AccommodationViewFragment() {
        // Required empty public constructor
    }

    public AccommodationViewFragment(Accommodation accommodation) {
        this.accommodation = accommodation;
    }


    public static AccommodationViewFragment newInstance(Accommodation accommodation) {
        AccommodationViewFragment fragment = new AccommodationViewFragment(accommodation);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            amenities = getArguments().getParcelableArrayList(ARG_PARAM);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_accommodation_view, container, false);

        showMap = view.findViewById(R.id.show_map);
        showMap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentTransition.to( new SupportMapFragment(), getActivity(), false, R.id.fragment_placeholder);
            }
        });

        RecyclerView recyclerView = view.findViewById(R.id.recycler);
        ArrayList<Integer> images = new ArrayList<Integer>();

        for(int image : accommodation.getImages()) {
            images.add(image);
        }

        ImageAdapter adapter = new ImageAdapter(getContext(), images);
        recyclerView.setAdapter(adapter);

        adapter.setOnItemClickListener(new ImageAdapter.OnItemClickListener() {
            @Override
            public void onClick(ImageView imageView, String path) {
                //Do something like opening the image in new activity or showing it in full screen or something else.
            }
        });

        CalendarView calendarView = view.findViewById(R.id.calendarView);
        EditText dates = view.findViewById(R.id.dates);
        calendarView.setOnDayClickListener(new OnDayClickListener() {
            @Override
            public void onDayClick(EventDay eventDay) {
                List<Calendar> selectedDates = calendarView.getSelectedDates();
                if(selectedDates.size()==1) {
                    //set text box dates
                    Calendar firstDate = selectedDates.get(0);
                    SimpleDateFormat formattedDate
                            = new SimpleDateFormat("dd.MM.yyyy.");
                    String firstDateString = formattedDate.format(firstDate.getTime());
                    Calendar secondDate = eventDay.getCalendar();
                    String secondDateString = formattedDate.format(secondDate.getTime());
                    String datesString = firstDateString + " - " + secondDateString;
                    dates.setText(datesString);

                    //set price
                    EditText price = view.findViewById(R.id.price);
                    Random random = new Random();
                    int priceNum = random.nextInt(1000);
                    price.setText(String.valueOf(priceNum) + "$");

                }
            }
        });

        ScrollView scrollView = view.findViewById(R.id.scroll_view);

        prepareAmenityList(amenities);
        listView = view.findViewById(R.id.amenity_list);
        AmenityAdapter amenityAdapter = new AmenityAdapter(getContext(), amenities);
        listView.setAdapter(amenityAdapter);
        listView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                scrollView.requestDisallowInterceptTouchEvent(true);
                int action = event.getActionMasked();
                switch (action) {
                    case MotionEvent.ACTION_UP:
                        scrollView.requestDisallowInterceptTouchEvent(false);
                        break;
                }
                return false;
            }
        });

        return view;
    }

    private void prepareAmenityList(ArrayList<Amenity> amenities) {
        amenities.add(new Amenity(1L, "Wi-Fi", R.drawable.icons8_settings_24));
        amenities.add(new Amenity(2L, "AC", R.drawable.icons8_calendar_32));
        amenities.add(new Amenity(3L, "popular location", R.drawable.icons8_location_32));
        amenities.add(new Amenity(4L, "clean", R.drawable.icons8_help_24));
        amenities.add(new Amenity(1L, "Wi-Fi", R.drawable.icons8_settings_24));
        amenities.add(new Amenity(2L, "AC", R.drawable.icons8_calendar_32));
        amenities.add(new Amenity(3L, "popular location", R.drawable.icons8_location_32));
        amenities.add(new Amenity(4L, "clean", R.drawable.icons8_help_24));
        amenities.add(new Amenity(1L, "Wi-Fi", R.drawable.icons8_settings_24));
        amenities.add(new Amenity(2L, "AC", R.drawable.icons8_calendar_32));
        amenities.add(new Amenity(3L, "popular location", R.drawable.icons8_location_32));
        amenities.add(new Amenity(4L, "clean", R.drawable.icons8_help_24));
    }
}