package com.example.bookingapp;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.PopupMenu;
import androidx.fragment.app.Fragment;

import android.os.StrictMode;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.bookingapp.clients.ClientUtils;
import com.example.bookingapp.dto.users.OwnerDTO;
import com.example.bookingapp.dto.users.UserDTO;

import retrofit2.Call;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link OwnerProfileFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class OwnerProfileFragment extends Fragment {

    private static Long id;
    private OwnerDTO owner;

    public OwnerProfileFragment() {
        // Required empty public constructor
    }

    public OwnerProfileFragment(Long id) {
        this.id = id;
    }

    public static OwnerProfileFragment newInstance(Long id) {
        OwnerProfileFragment fragment = new OwnerProfileFragment(id);
        Bundle args = new Bundle();
        args.putLong("id", id);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
            Call<OwnerDTO> call = ClientUtils.userService.getOwnerById(id);
            try{
                Response<OwnerDTO> response = call.execute();
                owner = (OwnerDTO) response.body();
            }catch(Exception ex){
                System.out.println("EXCEPTION WHILE GETTING OWNER");
                ex.printStackTrace();
            }
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_owner_profile, container, false);
        TextView name = view.findViewById(R.id.name);
        name.setText(owner.getName() + " " + owner.getSurname());

        TextView role = view.findViewById(R.id.role);
        role.setText(owner.getRole().toString());

        TextView email = view.findViewById(R.id.email_profile);
        email.setText(owner.getEmail());

        TextView address = view.findViewById(R.id.address_profile);
        address.setText(owner.getAddress());

        TextView phone = view.findViewById(R.id.phone_profile);
        phone.setText(owner.getPhone());
        RatingBar rb = view.findViewById(R.id.owner_rate);
        rb.setRating(4.1f);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


        ImageView icon = view.findViewById(R.id.report_owner_icon);

        icon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PopupMenu popupMenu = new PopupMenu(requireContext(), v);
                popupMenu.getMenuInflater().inflate(R.menu.menu_report, popupMenu.getMenu());

                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {

                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        final String report = String.valueOf(R.id.report);
                        final String id = String.valueOf(item.getItemId());
                        if (report.equals(id)){
                            Toast.makeText(getActivity(), "Reporting user ...", Toast.LENGTH_SHORT).show();
                            return true;
                        }
                        return false;
                    }
                });

                popupMenu.show();
            }
        });


    }
}