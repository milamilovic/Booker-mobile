package com.example.bookingapp.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.bookingapp.R;
import com.example.bookingapp.adapters.ImageAdapter;
import com.example.bookingapp.model.Accommodation;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link AccommodationViewFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class AccommodationViewFragment extends Fragment {

    private Accommodation accommodation;
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

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_accommodation_view, container, false);


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

        return view;
    }
}