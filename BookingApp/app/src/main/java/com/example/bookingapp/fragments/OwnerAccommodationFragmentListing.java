package com.example.bookingapp.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.os.StrictMode;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.example.bookingapp.R;
import com.example.bookingapp.adapters.OwnerAllAccommodationAdapter;
import com.example.bookingapp.adapters.OwnerApprovedAccommodationAdapter;
import com.example.bookingapp.clients.ClientUtils;
import com.example.bookingapp.databinding.FragmentOwnerAccommodationListingBinding;
import com.example.bookingapp.model.AccommodationListing;
import com.example.bookingapp.model.ApproveAccommodationListing;
import com.example.bookingapp.model.Image;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link OwnerAccommodationFragmentListing#newInstance} factory method to
 * create an instance of this fragment.
 */
public class OwnerAccommodationFragmentListing extends Fragment {

    public static ArrayList<ApproveAccommodationListing> allAccommodation = new ArrayList<ApproveAccommodationListing>();
    public static ArrayList<ApproveAccommodationListing> approvedAccommodation = new ArrayList<ApproveAccommodationListing>();
    private FragmentOwnerAccommodationListingBinding binding;
    private OwnerAllAccommodationAdapter adapterAll;
    private OwnerApprovedAccommodationAdapter adapterApproved;
    private static final String ARG_PARAM = "param";
    ListView listViewAll;
    ListView listViewApproved;


    public static OwnerAccommodationFragmentListing newInstance() {
        return new OwnerAccommodationFragmentListing();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            allAccommodation = getArguments().getParcelableArrayList(ARG_PARAM);
            adapterAll = new OwnerAllAccommodationAdapter(getActivity(), allAccommodation);

            approvedAccommodation = getArguments().getParcelableArrayList(ARG_PARAM);
            adapterApproved = new OwnerApprovedAccommodationAdapter(getActivity(), approvedAccommodation);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentOwnerAccommodationListingBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        listViewAll = root.findViewById(R.id.list_all);
        try {
            prepareAllAccommodationList(allAccommodation);
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
        adapterAll = new OwnerAllAccommodationAdapter(getContext(), allAccommodation);
        listViewAll.setAdapter(adapterAll);

        listViewApproved = root.findViewById(R.id.list_approved);
        try{
            prepareApprovedAccommodationList(approvedAccommodation);
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
        adapterApproved = new OwnerApprovedAccommodationAdapter(getContext(), approvedAccommodation);
        listViewApproved.setAdapter(adapterApproved);
        return root;
    }

    private void prepareApprovedAccommodationList(ArrayList<ApproveAccommodationListing> approvedAccommodation) throws ParseException {
        /*approvedAccommodation.add(new ApproveAccommodationListing(1L, "Flower Apartment",
                "A lovely apartment in the center of the city with a magnificent view of Buckingham palace and the city square. Has one bedroom and two separate bathrooms. Has a terrace.",
                new Image(), 4.3F, true));
        approvedAccommodation.add(new ApproveAccommodationListing(2L, "Flower Apartment",
                "A lovely apartment in the center of the city with a magnificent view of Buckingham palace and the city square. Has one bedroom and two separate bathrooms. Has a terrace.",
                new Image(), 4.3F, true));
        approvedAccommodation.add(new ApproveAccommodationListing(3L, "Flower Apartment",
                "A lovely apartment in the center of the city with a magnificent view of Buckingham palace and the city square. Has one bedroom and two separate bathrooms. Has a terrace.",
                new Image(), 4.3F, true));
        approvedAccommodation.add(new ApproveAccommodationListing(5L, "Flower Apartment",
                "A lovely apartment in the center of the city with a magnificent view of Buckingham palace and the city square. Has one bedroom and two separate bathrooms. Has a terrace.",
                new Image(), 4.3F, true));*/
        approvedAccommodation.clear();
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        Call<List<ApproveAccommodationListing>> accommodations = ClientUtils.accommodationService.findApprovedForOwner(2L);
        try {
            Response<List<ApproveAccommodationListing>> response = accommodations.execute();
            ArrayList<ApproveAccommodationListing> listings = (ArrayList<ApproveAccommodationListing>) response.body();
            for (ApproveAccommodationListing a : listings) {
                approvedAccommodation.add(a);
            }
        } catch (Exception ex) {
            System.out.println("EXCEPTION WHILE GETTING ACCOMMODATIONS");
            ex.printStackTrace();
        }
    }

    private void prepareAllAccommodationList(ArrayList<ApproveAccommodationListing> allAccommodation) throws ParseException {
        /*allAccommodation.add(new ApproveAccommodationListing(1L, "Flower Apartment",
                "A lovely apartment in the center of the city with a magnificent view of Buckingham palace and the city square. Has one bedroom and two separate bathrooms. Has a terrace.",
                new Image(), 4.3F, true));
        allAccommodation.add(new ApproveAccommodationListing(2L, "Flower Apartment",
                "A lovely apartment in the center of the city with a magnificent view of Buckingham palace and the city square. Has one bedroom and two separate bathrooms. Has a terrace.",
                new Image(), 4.3F, true));
        allAccommodation.add(new ApproveAccommodationListing(3L, "Flower Apartment",
                "A lovely apartment in the center of the city with a magnificent view of Buckingham palace and the city square. Has one bedroom and two separate bathrooms. Has a terrace.",
                new Image(), 4.3F, true));
        allAccommodation.add(new ApproveAccommodationListing(4L, "Unapproved Apartment",
                "A lovely apartment in the center of the city with a magnificent view of Buckingham palace and the city square. Has one bedroom and two separate bathrooms. Has a terrace.",
                new Image(), 4.3F, false));
        allAccommodation.add(new ApproveAccommodationListing(5L, "Flower Apartment",
                "A lovely apartment in the center of the city with a magnificent view of Buckingham palace and the city square. Has one bedroom and two separate bathrooms. Has a terrace.",
                new Image(), 4.3F, true));*/

        allAccommodation.clear();
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        Call<List<ApproveAccommodationListing>> accommodations = ClientUtils.accommodationService.findAllForOwner(2L);
        try {
            Response<List<ApproveAccommodationListing>> response = accommodations.execute();
            ArrayList<ApproveAccommodationListing> listings = (ArrayList<ApproveAccommodationListing>) response.body();
            for (ApproveAccommodationListing a : listings) {
                allAccommodation.add(a);
            }
        } catch (Exception ex) {
            System.out.println("EXCEPTION WHILE GETTING ACCOMMODATIONS");
            ex.printStackTrace();
        }
    }
}