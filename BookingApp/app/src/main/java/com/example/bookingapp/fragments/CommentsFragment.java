package com.example.bookingapp.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.os.StrictMode;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.example.bookingapp.R;
import com.example.bookingapp.adapters.AccommodationCommentsAdapter;
import com.example.bookingapp.adapters.OwnerCommentsAdapter;
import com.example.bookingapp.clients.ClientUtils;
import com.example.bookingapp.databinding.FragmentCommentsBinding;
import com.example.bookingapp.dto.users.OwnerDTO;
import com.example.bookingapp.dto.users.UserDTO;
import com.example.bookingapp.model.AdminAccommodationComment;
import com.example.bookingapp.model.AdminOwnerComment;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Response;

public class CommentsFragment extends Fragment {
    public static ArrayList<AdminOwnerComment> ownerComments = new ArrayList<AdminOwnerComment>();
    public static ArrayList<AdminAccommodationComment> accComments = new ArrayList<AdminAccommodationComment>();
    private FragmentCommentsBinding binding;
    private OwnerCommentsAdapter ownerCommentsAdapter;
    private AccommodationCommentsAdapter accommodationCommentsAdapter;
    private static final String ARG_PARAM = "param";
    ListView listViewOwner;
    ListView listViewAcc;

    public CommentsFragment() {
        // Required empty public constructor
    }

    public static CommentsFragment newInstance() {
        CommentsFragment fragment = new CommentsFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            ownerComments = getArguments().getParcelableArrayList(ARG_PARAM);
            ownerCommentsAdapter = new OwnerCommentsAdapter(getActivity(), ownerComments);

            accComments = getArguments().getParcelableArrayList(ARG_PARAM);
            accommodationCommentsAdapter = new AccommodationCommentsAdapter(getActivity(), accComments);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentCommentsBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        listViewOwner = root.findViewById(R.id.list_owner);
        listViewAcc = root.findViewById(R.id.list_accommodation);

        prepareOwnerCommentsList(ownerComments);
        prepareAccCommentsList(accComments);

        ownerCommentsAdapter = new OwnerCommentsAdapter(getContext(), ownerComments);
        listViewOwner.setAdapter(ownerCommentsAdapter);

        accommodationCommentsAdapter = new AccommodationCommentsAdapter(getContext(), accComments);
        listViewAcc.setAdapter(accommodationCommentsAdapter);

        return root;
    }

    private void prepareAccCommentsList(ArrayList<AdminAccommodationComment> products) {
        products.clear();
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        Call<ArrayList<Long>> call1 = ClientUtils.accommodationService.getAllIds();
        try{
            Response<ArrayList<Long>> response1 = call1.execute();
            ArrayList<Long> ids = (ArrayList<Long>) response1.body();
            for(Long u: ids){
                StrictMode.setThreadPolicy(policy);
                Call<List<AdminAccommodationComment>> call2 = ClientUtils.accommodationService.getCommentsForAcc(u);
                try{
                    Response<List<AdminAccommodationComment>> response2 = call2.execute();
                    List<AdminAccommodationComment> accComms = (List<AdminAccommodationComment>) response2.body();
                    for(AdminAccommodationComment ur: accComms){
                        products.add(ur);
                    }
                }catch(Exception ex){
                    System.out.println("EXCEPTION WHILE GETTING ACC COMMENTS");
                    ex.printStackTrace();
                }
            }
        }catch(Exception ex){
            System.out.println("EXCEPTION WHILE GETTING ACC ID");
            ex.printStackTrace();
        }
    }

    private void prepareOwnerCommentsList(ArrayList<AdminOwnerComment> products) {
        products.clear();
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        Call<ArrayList<OwnerDTO>> call1 = ClientUtils.userService.getOwners();
        try{
            Response<ArrayList<OwnerDTO>> response1 = call1.execute();
            ArrayList<OwnerDTO> owners = (ArrayList<OwnerDTO>) response1.body();
            for(OwnerDTO u: owners){
                StrictMode.setThreadPolicy(policy);
                Call<List<AdminOwnerComment>> call2 = ClientUtils.userService.getCommentsForOwner(u.getId());
                try{
                    Response<List<AdminOwnerComment>> response2 = call2.execute();
                    List<AdminOwnerComment> ownerComms = (List<AdminOwnerComment>) response2.body();
                    for(AdminOwnerComment ur: ownerComms){
                        products.add(ur);
                    }
                }catch(Exception ex){
                    System.out.println("EXCEPTION WHILE GETTING OWNER COMMENTS");
                    ex.printStackTrace();
                }
            }
        }catch(Exception ex){
            System.out.println("EXCEPTION WHILE GETTING USER");
            ex.printStackTrace();
        }
    }

}