package com.example.bookingapp.fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

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

public class MapFragment extends Fragment implements OnMapReadyCallback {

    GoogleMap googleMap;
    private MapView mapView;
    FrameLayout mapContainer;

    public MapFragment() {
        // Required empty public constructor
    }

    public static MapFragment newInstance() {
        MapFragment fragment = new MapFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_map, container, false);


        mapView = (MapView) view.findViewById(R.id.map_view);
        mapContainer = view.findViewById(R.id.map_fragment_containter);

        SupportMapFragment mapFragment = SupportMapFragment.newInstance();
        getParentFragmentManager().beginTransaction().add(R.id.map_fragment_containter, mapFragment).commit();
        mapFragment.getMapAsync(this);

        return view;

    }

    @Override
    public void onMapReady(@NonNull GoogleMap googleMap) {

        MapsInitializer.initialize(getActivity());

        this.googleMap = googleMap;
        LatLng place = new LatLng(0, 0);
        googleMap.addMarker(new MarkerOptions()
                .position(place)
                .title("Address"));
        googleMap.animateCamera(
                CameraUpdateFactory.newLatLngZoom(place, 15));
    }

    @Override
    public void onResume() {
        mapView.onResume();
        super.onResume();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mapView.onDestroy();
    }
}