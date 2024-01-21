package com.example.bookingapp.fragments;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.os.StrictMode;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.example.bookingapp.R;
import com.example.bookingapp.adapters.ReportedUsersAdapter;
import com.example.bookingapp.clients.ClientUtils;
import com.example.bookingapp.databinding.FragmentFavouriteAccommodationsBinding;
import com.example.bookingapp.databinding.FragmentReportedUsersBinding;
import com.example.bookingapp.dto.users.UserDTO;
import com.example.bookingapp.dto.users.UserReportDTO;
import com.example.bookingapp.model.Account;
import com.example.bookingapp.model.ReportedUsersListing;
import com.example.bookingapp.model.Role;
import com.example.bookingapp.model.User;
import com.example.bookingapp.model.UserReport;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import retrofit2.Call;
import retrofit2.Response;

public class ReportedUsersFragment extends Fragment {

    public static ArrayList<UserReport> reports = new ArrayList<UserReport>();
    private FragmentReportedUsersBinding binding;
    private ReportedUsersAdapter adapter;
    private static final String ARG_PARAM = "param";
    ListView listView;

    public static ReportedUsersFragment newInstance() {
        return new ReportedUsersFragment();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            reports = getArguments().getParcelableArrayList(ARG_PARAM);
            adapter = new ReportedUsersAdapter(getActivity(), reports);
        }
    }

    @Override
    public View onCreateView(@Nullable LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentReportedUsersBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        listView = root.findViewById(R.id.list);
        prepareReportsList(reports);

        adapter = new ReportedUsersAdapter(getContext(), reports);
        listView.setAdapter(adapter);



        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    private void prepareReportsList(ArrayList<UserReport> products) {
        products.clear();
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        Call<ArrayList<UserDTO>> call1 = ClientUtils.userService.getAllReported();
        try{
            Response<ArrayList<UserDTO>> response1 = call1.execute();
            ArrayList<UserDTO> users = (ArrayList<UserDTO>) response1.body();
            for(UserDTO u: users){
                StrictMode.setThreadPolicy(policy);
                Call<List<UserReport>> call2 = ClientUtils.userService.getAllReportsForUser(u.getId());
                try{
                    Response<List<UserReport>> response2 = call2.execute();
                    List<UserReport> userReports = (List<UserReport>) response2.body();
                    for(UserReport ur: userReports){
                        products.add(ur);
                    }
                }catch(Exception ex){
                    System.out.println("EXCEPTION WHILE GETTING USER REPORT");
                    ex.printStackTrace();
                }
            }
        }catch(Exception ex){
            System.out.println("EXCEPTION WHILE GETTING USER");
            ex.printStackTrace();
        }
    }
}