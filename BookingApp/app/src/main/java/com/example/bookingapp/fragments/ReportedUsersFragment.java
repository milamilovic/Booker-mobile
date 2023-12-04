package com.example.bookingapp.fragments;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.example.bookingapp.R;
import com.example.bookingapp.adapters.ReportedUsersAdapter;
import com.example.bookingapp.databinding.FragmentFavouriteAccommodationsBinding;
import com.example.bookingapp.databinding.FragmentReportedUsersBinding;
import com.example.bookingapp.model.Account;
import com.example.bookingapp.model.ReportedUsersListing;
import com.example.bookingapp.model.Role;
import com.example.bookingapp.model.User;

import java.util.ArrayList;
import java.util.Date;

public class ReportedUsersFragment extends Fragment {

    public static ArrayList<ReportedUsersListing> reports = new ArrayList<ReportedUsersListing>();
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

        prepareReportsList(reports);
        listView = root.findViewById(R.id.list);
        adapter = new ReportedUsersAdapter(getContext(), reports);
        listView.setAdapter(adapter);

        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    User senderUser = new User("SenderName", "SenderSurname");
    Account sender = new Account(senderUser, Role.GUEST, null, false);
    User receiverUser = new User("ReceiverName", "ReceiverSurname");
    Account receiver = new Account(receiverUser, Role.OWNER, null, false);
    Date date = new Date();
    String reason = "Lorem ipsum dolor sit amet, consectetur adipiscing elit. " +
            "Morbi vulputate volutpat lacus, ut rutrum eros hendrerit non. Cras volutpat congue auctor. " +
            "Nunc in porta enim. Sed pulvinar mollis mollis. " +
            "Cras quis magna ac mauris egestas rhoncus ac eu eros.";


    private void prepareReportsList(ArrayList<ReportedUsersListing> products){
        products.add(new ReportedUsersListing(sender, receiver, date, reason));
        products.add(new ReportedUsersListing(sender, receiver, date, reason));
        products.add(new ReportedUsersListing(sender, receiver, date, reason));
        products.add(new ReportedUsersListing(sender, receiver, date, reason));
        products.add(new ReportedUsersListing(sender, receiver, date, reason));
        products.add(new ReportedUsersListing(sender, receiver, date, reason));
        products.add(new ReportedUsersListing(sender, receiver, date, reason));
    }
}