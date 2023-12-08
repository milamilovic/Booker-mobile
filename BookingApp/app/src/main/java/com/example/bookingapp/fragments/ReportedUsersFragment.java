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

import java.text.ParseException;
import java.text.SimpleDateFormat;
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

        listView = root.findViewById(R.id.list);
        try {
            prepareReportsList(reports);
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
        adapter = new ReportedUsersAdapter(getContext(), reports);
        listView.setAdapter(adapter);



        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    private void prepareReportsList(ArrayList<ReportedUsersListing> products) throws ParseException {
        User senderUser = new User("Maria", "Jones");
        Account sender = new Account(senderUser, Role.GUEST, null, false);
        User receiverUser = new User("Sara", "West");
        Account receiver = new Account(receiverUser, Role.OWNER, null, false);
        String reason = "Lorem ipsum dolor sit amet, consectetur adipiscing elit. " +
                "Morbi vulputate volutpat lacus, ut rutrum eros hendrerit non. Cras volutpat congue auctor. ";

        User receiverUser2 = new User("John", "Doe");
        Account receiver2 = new Account(receiverUser2, Role.OWNER, null, true);

        SimpleDateFormat formatter = new SimpleDateFormat("dd.MM.yyyy. HH:mm");

        products.add(new ReportedUsersListing(sender, receiver, formatter.parse("01.12.2023. 17:23"), reason));
        products.add(new ReportedUsersListing(sender, receiver, formatter.parse("08.12.2023. 17:23"), reason));
        products.add(new ReportedUsersListing(sender, receiver, formatter.parse("08.12.2023. 17:23"), reason));
        products.add(new ReportedUsersListing(sender, receiver, formatter.parse("08.12.2023. 17:23"), reason));
        products.add(new ReportedUsersListing(sender, receiver2, formatter.parse("08.12.2023. 17:23"), reason));
        products.add(new ReportedUsersListing(sender, receiver, formatter.parse("08.12.2023. 17:23"), reason));
        products.add(new ReportedUsersListing(sender, receiver, formatter.parse("08.12.2023. 17:23"), reason));
    }
}