package com.example.bookingapp.fragments;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.os.StrictMode;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.ScrollView;
import android.widget.ToggleButton;

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
import java.util.List;

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

    public UpdateAccommodationFragment newInstance(Accommodation accommodation) {
        this.accommodation = accommodation;
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

        ArrayList<Bitmap> images = new ArrayList<Bitmap>();

        //getting images
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        Call<List<String>> imageCall = ClientUtils.accommodationService.getImages(this.accommodation.getId());
        try{
            Response<List<String>> response = imageCall.execute();
            List<String> imageStrings = (List<String>) response.body();
            if(imageStrings!=null && !imageStrings.isEmpty()) {
                for(String image : imageStrings) {
                    byte[] bytes = Base64.decode(image, Base64.DEFAULT);
                    Bitmap bitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
                    images.add(bitmap);
                }
            }
        }catch(Exception ex){
            System.out.println("EXCEPTION WHILE GETTING IMAGES");
            ex.printStackTrace();
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

        if (accommodation!= null) {
            EditText title_update = view.findViewById(R.id.update_title);
            EditText update_description = view.findViewById(R.id.update_description);
            EditText update_city = view.findViewById(R.id.update_city);
            EditText update_street = view.findViewById(R.id.update_street);
            EditText update_longitude = view.findViewById(R.id.update_longitude);
            EditText update_latitude = view.findViewById(R.id.update_latitude);

            title_update.setText(accommodation.getTitle());
            update_description.setText(accommodation.getDescription());
            update_city.setText(accommodation.getAddress().getCity());
            update_street.setText(accommodation.getAddress().getStreet());
            double longitude = accommodation.getAddress().getLongitude();
            double latitude = accommodation.getAddress().getLatitude();
            update_longitude.setText(String.valueOf(longitude));
            update_latitude.setText(String.valueOf(latitude));


            Button save = view.findViewById(R.id.save_update_button);
            save.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    accommodation.setTitle(String.valueOf(title_update.getText()));
                    accommodation.setDescription(String.valueOf(update_description.getText()));
                    Address address = accommodation.getAddress();
                    address.setCity(String.valueOf(update_city.getText()));
                    address.setStreet(String.valueOf(update_street.getText()));
                    address.setLatitude(Double.parseDouble(String.valueOf(update_latitude.getText())));
                    address.setLongitude(Double.parseDouble(String.valueOf(update_longitude.getText())));
                    accommodation.setAddress(address);
                    StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
                    StrictMode.setThreadPolicy(policy);
                    Call<String> stringCall = ClientUtils.accommodationService.updateAccommodation(accommodation.getId(), accommodation);
                    try {
                        Response<String> response = stringCall.execute();
                        System.out.println(response.body());
                    } catch (Exception ex) {
                        System.out.println("EXCEPTION WHILE UPDATING ACCOMMODATION");
                        ex.printStackTrace();
                    }
                    StrictMode.setThreadPolicy(policy);
                    Call<String> stringCall1 = ClientUtils.accommodationService.updateAccommodationAddress(accommodation.getId(), address);
                    try {
                        Response<String> response1 = stringCall1.execute();
                        System.out.println(response1.body());
                    } catch (Exception ex) {
                        System.out.println("EXCEPTION WHILE UPDATING ADDRESS");
                        ex.printStackTrace();
                    }
                    FragmentTransition.to(HomeFragment.newInstance(), (FragmentActivity) getContext(), true, R.id.fragment_placeholder);
                }
            });

            ToggleButton toggle = view.findViewById(R.id.requestAcceptingToggle);
            toggle.setChecked(!accommodation.isManual_accepting());
            toggle.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    System.out.println("CLICKED MANUAL: " + !toggle.isChecked());
                    accommodation.setManual_accepting(!toggle.isChecked());
                }
            });
        }

        return view;
    }

    private void prepareAmenityList(ArrayList<Amenity> amenities) {
        amenities.add(new Amenity(1L, "Wi-Fi", "", null));
        amenities.add(new Amenity(2L, "AC", "", null));
        amenities.add(new Amenity(3L, "popular location", "", null));
        amenities.add(new Amenity(4L, "clean", "", null));
    }
}