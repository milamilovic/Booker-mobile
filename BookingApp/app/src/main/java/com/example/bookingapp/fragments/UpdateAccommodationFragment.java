package com.example.bookingapp.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;
import android.widget.ScrollView;

import com.example.bookingapp.FragmentTransition;
import com.example.bookingapp.R;
import com.example.bookingapp.adapters.AmenityAdapter;
import com.example.bookingapp.adapters.ImageAdapter;
import com.example.bookingapp.model.Accommodation;
import com.example.bookingapp.model.Amenity;
import com.example.bookingapp.model.Image;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link UpdateAccommodationFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class UpdateAccommodationFragment extends Fragment {

    private Accommodation accommodation;

    public static ArrayList<Amenity> amenities = new ArrayList<Amenity>();
    ListView listView;

    private static final String ARG_PARAM = "param";
    Button showMap;

    public UpdateAccommodationFragment() {
        // Required empty public constructor
    }

    public UpdateAccommodationFragment(Accommodation accommodation) {
        this.accommodation = accommodation;
    }

    public static UpdateAccommodationFragment newInstance(Accommodation accommodation) {
        UpdateAccommodationFragment fragment = new UpdateAccommodationFragment(accommodation);
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
        View view = inflater.inflate(R.layout.fragment_update_accommodation, container, false);

        showMap = view.findViewById(R.id.show_map);
        showMap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentTransition.to( new MapFragment(), getActivity(), false, R.id.fragment_placeholder);
            }
        });

        RecyclerView recyclerView = view.findViewById(R.id.recycler);
        ArrayList<Image> images = new ArrayList<Image>();

        for(Image image : accommodation.getImages()) {
            images.add(image);
        }

        ImageAdapter adapter = new ImageAdapter(getContext(), images);
        recyclerView.setAdapter(adapter);

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
    }
}