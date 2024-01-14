package com.example.bookingapp.fragments;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.os.StrictMode;
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
import com.example.bookingapp.clients.ClientUtils;
import com.example.bookingapp.model.Accommodation;
import com.example.bookingapp.model.Address;
import com.example.bookingapp.model.Amenity;
import com.example.bookingapp.model.Availability;
import com.example.bookingapp.model.Image;
import com.example.bookingapp.model.Price;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Response;

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
    Button updateAvailability;

    private static final String ACCOMMODATION_ID = "accommodation_id";

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
                FragmentTransition.to( new MapFragment(0.0, 0.0), getActivity(), false, R.id.fragment_placeholder);
            }
        });

        RecyclerView recyclerView = view.findViewById(R.id.recycler);

        ArrayList<Integer> images = new ArrayList<Integer>();
        images.add(R.drawable.apartment_image);
        images.add(R.drawable.paris_image);
        images.add(R.drawable.copenhagen_image);
        images.add(R.drawable.madrid_image);
        images.add(R.drawable.room_image);
        images.add(R.drawable.hotel_image);
        images.add(R.drawable.lisbon_image);
        images.add(R.drawable.london_image);

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

        Button save = view.findViewById(R.id.save_update_button);
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
                StrictMode.setThreadPolicy(policy);
                Call<String> stringCall = ClientUtils.accommodationService.updateAccommodation(accommodation.getId(), accommodation);
                try {
                    Response<String> response = stringCall.execute();
                    System.out.println(response.body());
                } catch (Exception ex) {
                    System.out.println("EXCEPTION WHILE APPROVING ACCOMMODATION");
                    ex.printStackTrace();
                }
                FragmentTransition.to(HomeFragment.newInstance(), (FragmentActivity) getContext(), true, R.id.fragment_placeholder);
            }
        });


        updateAvailability = view.findViewById(R.id.price_availability_button);
        updateAvailability.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences sharedPref = getActivity().getPreferences(Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPref.edit();
                editor.putLong(ACCOMMODATION_ID, accommodation.getId());
                editor.apply();
                FragmentTransition.to(UpdateAccommodationDetailsFragment.newInstance(), getActivity(), false, R.id.fragment_placeholder);
            }
        });
        return view;
    }

    private void prepareAmenityList(ArrayList<Amenity> amenities) {
        amenities.add(new Amenity(1L, "Wi-Fi", "", null));
        amenities.add(new Amenity(2L, "AC", "", null));
        amenities.add(new Amenity(3L, "popular location", "", null));
        amenities.add(new Amenity(4L, "clean", "", null));
    }
}