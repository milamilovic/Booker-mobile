package com.example.bookingapp.fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import com.example.bookingapp.R;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class MapFragment extends FragmentActivity implements OnMapReadyCallback {

    GoogleMap googleMap;
    FrameLayout map;

    MapView mapView;

    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        setContentView(R.layout.fragment_map);


        map = findViewById(R.id.map);

        SupportMapFragment mapFragment = (SupportMapFragment)getSupportFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

//        MapsInitializer.initialize(this);

        mapView = findViewById(R.id.mapView);
        mapView.onCreate(savedInstanceState);
        mapView.getMapAsync(googleMap -> {
            LatLng place = new LatLng(0, 0);
            googleMap.addMarker(new MarkerOptions()
                    .position(place)
                    .title("Address"));
            googleMap.animateCamera(
                    CameraUpdateFactory.newLatLngZoom(place, 15));
        });

    }


    @Override
    public void onMapReady(@NonNull GoogleMap googleMap) {

//        MapsInitializer.initialize(this);

        this.googleMap = googleMap;
        LatLng place = new LatLng(0d, 0d);
        googleMap.addMarker(new MarkerOptions()
                .position(place)
                .title("Address"));
        googleMap.animateCamera(
                CameraUpdateFactory.newLatLngZoom(place, 15));
    }

}