package com.example.bookingapp.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.example.bookingapp.R;
import com.example.bookingapp.adapters.ApproveAccommodationAdapter;
import com.example.bookingapp.databinding.FragmentApproveAccommodationBinding;
import com.example.bookingapp.model.ApproveAccommodationListing;

import java.text.ParseException;
import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ApproveAccommodationFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ApproveAccommodationFragment extends Fragment {

    public static ArrayList<ApproveAccommodationListing> unapprovedAccommodation = new ArrayList<ApproveAccommodationListing>();
    private FragmentApproveAccommodationBinding binding;
    private ApproveAccommodationAdapter adapter;
    private static final String ARG_PARAM = "param";
    ListView listView;

    public static ApproveAccommodationFragment newInstance(){
        return new ApproveAccommodationFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            unapprovedAccommodation = getArguments().getParcelableArrayList(ARG_PARAM);
            adapter = new ApproveAccommodationAdapter(getActivity(), unapprovedAccommodation);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentApproveAccommodationBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        listView = root.findViewById(R.id.list);
        try {
            prepareUnapprovedAccommodationList(unapprovedAccommodation);
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
        adapter = new ApproveAccommodationAdapter(getContext(), unapprovedAccommodation);
        listView.setAdapter(adapter);
        return root;
    }

    private void prepareUnapprovedAccommodationList(ArrayList<ApproveAccommodationListing> unapprovedAccommodation) throws ParseException {
        unapprovedAccommodation.add(new ApproveAccommodationListing(1L, "Flower Apartment",
                "A lovely apartment in the center of the city with a magnificent view of Buckingham pallace and the city square. Has one bedroom and two separate bathrooms. Has a terrace.",
                R.drawable.apartment_image, 4.3F));
        unapprovedAccommodation.add(new ApproveAccommodationListing(2L, "Flower Apartment",
                "A lovely apartment in the center of the city with a magnificent view of Buckingham pallace and the city square. Has one bedroom and two separate bathrooms. Has a terrace.",
                R.drawable.apartment_image, 4.3F));
        unapprovedAccommodation.add(new ApproveAccommodationListing(3L, "Flower Apartment",
                "A lovely apartment in the center of the city with a magnificent view of Buckingham pallace and the city square. Has one bedroom and two separate bathrooms. Has a terrace.",
                R.drawable.apartment_image, 4.3F));
        unapprovedAccommodation.add(new ApproveAccommodationListing(4L, "Flower Apartment",
                "A lovely apartment in the center of the city with a magnificent view of Buckingham pallace and the city square. Has one bedroom and two separate bathrooms. Has a terrace.",
                R.drawable.apartment_image, 4.3F));
        unapprovedAccommodation.add(new ApproveAccommodationListing(5L, "Flower Apartment",
                "A lovely apartment in the center of the city with a magnificent view of Buckingham pallace and the city square. Has one bedroom and two separate bathrooms. Has a terrace.",
                R.drawable.apartment_image, 4.3F));
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}