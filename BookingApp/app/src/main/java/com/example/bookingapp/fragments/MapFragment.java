package com.example.bookingapp.fragments;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;

import com.example.bookingapp.R;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class MapFragment extends Fragment {

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

}