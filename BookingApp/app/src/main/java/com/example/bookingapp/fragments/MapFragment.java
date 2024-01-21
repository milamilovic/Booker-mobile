package com.example.bookingapp.fragments;

import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;


import com.example.bookingapp.R;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class MapFragment extends Fragment {

    private MapView mapView;

    public double latitude;
    public double longitude;

    private Geocoder geocoder;

    public MapFragment(double latitude, double longitude) {
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public static MapFragment newInstance(double latitude, double longitude) {
        MapFragment fragment = new MapFragment(latitude, longitude);
        Bundle args = new Bundle();
        args.putDouble("lat", latitude);
        args.putDouble("lon", longitude);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            Bundle args = getArguments();
            latitude = args.getDouble("lat");
            longitude = args.getDouble("lon");
        }


    }

    public interface OnMapMarkerClickListener {
        void onMapMarkerClick(LatLng latLng, String address);
    }

    private OnMapMarkerClickListener onMapMarkerClickListener;

    public void setOnMapMarkerClickListener(OnMapMarkerClickListener listener) {
        this.onMapMarkerClickListener = listener;
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
            LatLng place = new LatLng(latitude, longitude);
            googleMap.addMarker(new MarkerOptions()
                    .position(place)
                    .title("Address"));
            googleMap.moveCamera(
                    CameraUpdateFactory.newLatLngZoom(place, 12));

            // Set a click listener for the map
            googleMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
                @Override
                public void onMapClick(LatLng latLng) {
                    googleMap.clear();
                    // Add a marker at the clicked position
                    Marker marker = googleMap.addMarker(new MarkerOptions()
                            .position(latLng)
                            .title("Clicked Location"));

                    getAddressFromLocation(latLng.latitude, latLng.longitude, marker);

                    if (onMapMarkerClickListener != null) {
                        onMapMarkerClickListener.onMapMarkerClick(latLng, marker.getTitle());
                    }
                }
            });
        });

        geocoder = new Geocoder(requireContext());
        return view;
    }

    private void getAddressFromLocation(double latitude, double longitude, Marker marker) {
        try {
            List<Address> addresses = geocoder.getFromLocation(latitude, longitude, 1);
            if (addresses != null && addresses.size() > 0) {
                Address address = addresses.get(0);
                String fullAddress = address.getAddressLine(0);
                //String fullAddress = address.getAddressLine(0);
                Log.d("REZ", "Address: " + fullAddress);
                marker.setTitle(fullAddress);
            } else {
                Log.d("REZ", "Unable to get full address");
            }
        } catch (IOException | IllegalArgumentException e) {
            e.printStackTrace();
            Log.e("REZ", "Error getting address: " + e.getMessage());
        }
    }

}