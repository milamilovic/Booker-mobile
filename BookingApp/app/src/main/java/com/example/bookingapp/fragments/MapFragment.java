package com.example.bookingapp.fragments;

import android.content.Context;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;


import com.example.bookingapp.R;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;



public class MapFragment extends Fragment implements OnMapReadyCallback, GoogleMap.OnMapClickListener{

    private MapView mapView;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_map, container, false);

        mapView = view.findViewById(R.id.mapView);
        mapView.onCreate(savedInstanceState);
        Log.d("MyTag", "Nije usao");
        mapView.getMapAsync(googleMap -> {
            Log.d("MyTag", "Usao je!!!");
            LatLng place = new LatLng(47.5189687, 18.9606965);
            googleMap.addMarker(new MarkerOptions()
                    .position(place)
                    .title("Address"));
            googleMap.moveCamera(
                    CameraUpdateFactory.newLatLngZoom(place, 12));
        });
        return view;
    }

    public List<String> getAddressFromLatLng(Context context, LatLng latLng) {
        Geocoder geocoder = new Geocoder(context, Locale.getDefault());
        List<String> addressString = new ArrayList<>();

        try {
            List<Address> addresses = geocoder.getFromLocation(latLng.latitude, latLng.longitude, 1);
            if (!addresses.isEmpty()) {
                Address address = addresses.get(0);

                String street = address.getAddressLine(0);
                String city = address.getLocality();
                String latitude = String.valueOf(latLng.latitude);
                String longitude = String.valueOf(latLng.longitude);

                String formattedAddress = String.format("%s, %s, %s, %s", street, city, latitude, longitude);
                addressString.add(street);
                addressString.add(city);
                addressString.add(latitude);
                addressString.add(longitude);
                mapView.getMapAsync(googleMap -> {
                    Log.d("MyTag", "Usao je!!!");
                    LatLng place = new LatLng(latLng.latitude, latLng.longitude);
                    googleMap.addMarker(new MarkerOptions()
                            .position(place)
                            .title("Address"));
                    googleMap.moveCamera(
                            CameraUpdateFactory.newLatLngZoom(place, 12));
                });

                Toast.makeText(context, "Address: " + formattedAddress, Toast.LENGTH_SHORT).show();


                // Do something with the formatted address, such as displaying it in a TextView
            } else {
                Toast.makeText(context, "Address not found", Toast.LENGTH_SHORT).show();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return addressString;
    }

    private OnMapClickListener onMapClickListener;

    public interface OnMapClickListener {
        void onMapClick(LatLng latLng);
    }

    @Override
    public void onMapClick(@NonNull LatLng latLng) {
        // Notify the callback interface with the clicked LatLng
        if (onMapClickListener != null) {
            onMapClickListener.onMapClick(latLng);
        }
    }

    public void setOnMapClickListener(OnMapClickListener listener) {
        this.onMapClickListener = listener;
    }

    @Override
    public void onMapReady(@NonNull GoogleMap googleMap) {

    }





}