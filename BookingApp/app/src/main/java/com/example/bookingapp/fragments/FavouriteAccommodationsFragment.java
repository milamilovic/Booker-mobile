package com.example.bookingapp.fragments;

import static android.content.Context.SENSOR_SERVICE;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.os.StrictMode;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.bookingapp.R;
import com.example.bookingapp.adapters.AccommodationListAdapter;
import com.example.bookingapp.adapters.FavouriteAccommodationAdapter;
import com.example.bookingapp.clients.ClientUtils;
import com.example.bookingapp.databinding.FragmentAccommodationListingBinding;
import com.example.bookingapp.databinding.FragmentFavouriteAccommodationsBinding;
import com.example.bookingapp.model.AccommodationListing;
import com.example.bookingapp.model.FavouriteAccommodationListing;
import com.google.android.material.bottomsheet.BottomSheetDialog;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import retrofit2.Call;
import retrofit2.Response;

public class FavouriteAccommodationsFragment extends Fragment {
    private static final String USER_ID_KEY = "user_id";

    public static ArrayList<FavouriteAccommodationListing> accommodations = new ArrayList<FavouriteAccommodationListing>();
    private FragmentFavouriteAccommodationsBinding binding;
    private FavouriteAccommodationAdapter adapter;
    final Calendar myCalendar= Calendar.getInstance();

    private static final String ARG_PARAM = "param";
    private static String lightSensorMode = "dark";
    private static float lightSensorTreshold = 30f;

    private FrameLayout background;
    private TextView favText;

    ListView listView;

    public static FavouriteAccommodationsFragment newInstance() {
        return new FavouriteAccommodationsFragment();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            accommodations = getArguments().getParcelableArrayList(ARG_PARAM);
            adapter = new FavouriteAccommodationAdapter(getActivity(), accommodations, getActivity().getPreferences(Context.MODE_PRIVATE));
        }
    }


    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        binding = FragmentFavouriteAccommodationsBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        prepareAccommodationList(accommodations);
        listView = root.findViewById(R.id.list);
        adapter = new FavouriteAccommodationAdapter(getContext(), accommodations, getActivity().getPreferences(Context.MODE_PRIVATE));
        listView.setAdapter(adapter);

        SensorManager mySensorManager = (SensorManager) getActivity().getSystemService(SENSOR_SERVICE);
        this.background = root.findViewById(R.id.background);
        this.favText = root.findViewById(R.id.title);

        Sensor lightSensor = mySensorManager.getDefaultSensor(Sensor.TYPE_LIGHT);
        if(lightSensor != null){
            mySensorManager.registerListener(
                    lightSensorListener,
                    lightSensor,
                    SensorManager.SENSOR_DELAY_NORMAL);
            Toast.makeText(getContext(),"Sensor.TYPE_LIGHT Available", Toast.LENGTH_LONG).show();

        } else {
            Toast.makeText(getContext(),"Sensor.TYPE_LIGHT NOT Available", Toast.LENGTH_LONG).show();
        }


        return root;
    }


    private final SensorEventListener lightSensorListener
            = new SensorEventListener(){

        @Override
        public void onAccuracyChanged(Sensor sensor, int accuracy) {
            // TODO Auto-generated method stub

        }

        @Override
        public void onSensorChanged(SensorEvent event) {
            if(event.sensor.getType() == Sensor.TYPE_LIGHT){
                if(event.values[0] > lightSensorTreshold && lightSensorMode.equals("dark")) {
                    lightSensorMode = "light";
                    background.setBackground(getResources().getDrawable(R.drawable.phone_background_1));
//                    favText.setTextColor(getResources().getColor(R.color.light_gray));
                    Toast.makeText(getContext(), "Lighting changed to light: " + event.values[0], Toast.LENGTH_SHORT).show();
                } else if (event.values[0] < lightSensorTreshold && lightSensorMode.equals("light")) {
                    lightSensorMode = "dark";
                    background.setBackground(new ColorDrawable(getResources().getColor(R.color.dark_gray)));
//                    favText.setTextColor(getResources().getColor(R.color.dark_gray));
                    Toast.makeText(getContext(), "Lighting changed to dark: " + event.values[0], Toast.LENGTH_SHORT).show();
                }
            }
        }

    };

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
        SensorManager mySensorManager = (SensorManager) getActivity().getSystemService(SENSOR_SERVICE);

        Sensor lightSensor = mySensorManager.getDefaultSensor(Sensor.TYPE_LIGHT);
        if(lightSensor != null){
            mySensorManager.unregisterListener(lightSensorListener);
        }
    }

    private void prepareAccommodationList(ArrayList<FavouriteAccommodationListing> products){
        products.clear();   //in case it's not initialization but searching
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        SharedPreferences sharedPref = getActivity().getPreferences(Context.MODE_PRIVATE);
        Long userID = sharedPref.getLong(USER_ID_KEY, 0);
        Call<List<FavouriteAccommodationListing>> accommodations = ClientUtils.accommodationService.findFavourite(userID);
        try{
            Response<List<FavouriteAccommodationListing>> response = accommodations.execute();
            ArrayList<FavouriteAccommodationListing> listings = (ArrayList<FavouriteAccommodationListing>) response.body();
            for(FavouriteAccommodationListing a : listings) {
                products.add(a);
            }
        }catch(Exception ex){
            System.out.println("EXCEPTION WHILE GETTING FAVOURITE ACCOMMODATIONS");
            ex.printStackTrace();
        }
    }
}