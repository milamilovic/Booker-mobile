package com.example.bookingapp.adapters;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.StrictMode;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentActivity;

import com.example.bookingapp.FragmentTransition;
import com.example.bookingapp.ProfileFragment;
import com.example.bookingapp.R;
import com.example.bookingapp.clients.ClientUtils;
import com.example.bookingapp.dto.users.GuestDTO;
import com.example.bookingapp.dto.users.OwnerDTO;
import com.example.bookingapp.dto.users.UserDTO;
import com.example.bookingapp.dto.users.UserReportDTO;
import com.example.bookingapp.enums.Role;
import com.example.bookingapp.fragments.ReportedUsersFragment;
import com.example.bookingapp.model.ReportedUsersListing;
import com.example.bookingapp.model.User;
import com.example.bookingapp.model.UserReport;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Response;

public class ReportedUsersAdapter extends ArrayAdapter<UserReport> {

    private ArrayList<UserReport> reports;
    Context context;

    public ReportedUsersAdapter(@NonNull Context context, int resource) {
        super(context, resource);
    }

    public ReportedUsersAdapter(Context context, ArrayList<UserReport> reportedUsersListing){
        super(context, R.layout.block_user_request_card);
        this.reports = reportedUsersListing;
        this.context = context;
    }

    @Override
    public int getCount() {
        return reports.size();
    }

    @Nullable
    @Override
    public UserReport getItem(int position) {
        return reports.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public int getNumberOfReports(Long reportedId){
        int number = 0;
        for (UserReport u : reports){
            if (u.getReportedId() == reportedId){
                number++;
            }
        }
        return number;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        UserReport request = getItem(position);
        if(convertView == null){
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.block_user_request_card,
                    parent, false);
        }
        TextView sender = convertView.findViewById(R.id.sender);
        TextView receiver = convertView.findViewById(R.id.receiver);
        TextView date = convertView.findViewById(R.id.report_date);
        TextView reason = convertView.findViewById(R.id.report_reason);
        Button block = convertView.findViewById(R.id.block_button);
        TextView numberOfReports = convertView.findViewById(R.id.number_of_reports);

        SimpleDateFormat formatter = new SimpleDateFormat("dd.MM.yyyy.");

        UserDTO reported = null;
        UserDTO reporter = null;
        Boolean blocked = false;

        if(request != null){
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
            Call<UserDTO> call1 = ClientUtils.userService.getById(request.getReportedId());
            try{
                Response<UserDTO> response1 = call1.execute();
                reported = (UserDTO) response1.body();
                receiver.setText(reported.getName() + " " + reported.getSurname());
                numberOfReports.setText("Number of reports: " + getNumberOfReports(reported.getId()));
                // btn style
                if (reported.getRole() == Role.GUEST){
                    StrictMode.setThreadPolicy(policy);
                    Call<GuestDTO> call12 = ClientUtils.userService.getGuestById(request.getReportedId());
                    try {
                        Response<GuestDTO> response12 = call12.execute();
                        GuestDTO reportedGuest = (GuestDTO) response12.body();
                        if (reportedGuest.isBlocked()){
                            blocked = reportedGuest.isBlocked();
                            block.setBackgroundColor(ContextCompat.getColor(getContext(), R.color.transparent_60_dark_gray));
                            block.setTextColor(ContextCompat.getColor(getContext(), R.color.dark_gray));
                            block.setText("Unblock user");
                        }
                        else{
                            blocked = reportedGuest.isBlocked();
                            block.setBackgroundColor(ContextCompat.getColor(getContext(), R.color.dark_gray));
                            block.setTextColor(ContextCompat.getColor(getContext(), R.color.light_gray));
                            block.setText("Block user");
                        }
                    } catch(Exception ex){
                        System.out.println("EXCEPTION WHILE GETTING REPORTED");
                        ex.printStackTrace();
                    }
                } else if (reported.getRole() == Role.OWNER) {
                    StrictMode.setThreadPolicy(policy);
                    Call<OwnerDTO> call11 = ClientUtils.userService.getOwnerById(request.getReportedId());
                    try {
                        Response<OwnerDTO> response11 = call11.execute();
                        OwnerDTO reportedOwner = (OwnerDTO) response11.body();
                        if (reportedOwner.isBlocked()){
                            blocked = reportedOwner.isBlocked();
                            block.setBackgroundColor(ContextCompat.getColor(getContext(), R.color.transparent_60_dark_gray));
                            block.setTextColor(ContextCompat.getColor(getContext(), R.color.dark_gray));
                            block.setText("Unblock user");
                        }
                        else{
                            blocked = reportedOwner.isBlocked();
                            block.setBackgroundColor(ContextCompat.getColor(getContext(), R.color.dark_gray));
                            block.setTextColor(ContextCompat.getColor(getContext(), R.color.light_gray));
                            block.setText("Block user");
                        }
                    } catch(Exception ex){
                        System.out.println("EXCEPTION WHILE GETTING REPORTED");
                        ex.printStackTrace();
                    }
                }
            }catch(Exception ex){
                System.out.println("EXCEPTION WHILE GETTING REPORTED");
                ex.printStackTrace();
            }
            // getting reporter
            StrictMode.setThreadPolicy(policy);
            Call<UserDTO> call2 = ClientUtils.userService.getById(request.getReporterId());
            try{
                Response<UserDTO> response2 = call2.execute();
                reporter = (UserDTO) response2.body();
                sender.setText(reporter.getName() + " " + reporter.getSurname());
            }catch(Exception ex){
                System.out.println("EXCEPTION WHILE GETTING REPORTER");
                ex.printStackTrace();
            }

            date.setText(formatter.format(request.getDate()));
            reason.setText(request.getReason());
            receiver.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    FragmentTransition.to(ProfileFragment.newInstance(request.getReportedId()), (FragmentActivity) context, true, R.id.fragment_placeholder);
                }
            });

            Boolean finalBlocked = blocked;
            block.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    StrictMode.setThreadPolicy(policy);
                    Call<Void> callBlock = ClientUtils.userService.blockOrUnblock(request.getReportedId(), !finalBlocked);
                    try{
                        Response<Void> responseBlock = callBlock.execute();
                        if (finalBlocked) {
                            Toast.makeText(context, "User with id " + request.getReportedId() + " is unblocked!", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(context, "User with id " + request.getReportedId() + " is blocked!", Toast.LENGTH_SHORT).show();
                        }
                        FragmentTransition.to(ReportedUsersFragment.newInstance(), (FragmentActivity) context, false, R.id.fragment_placeholder);
                    }catch(Exception ex){
                        System.out.println("EXCEPTION WHILE GETTING REPORTER");
                        ex.printStackTrace();
                    }
                }
            });
        }

        return convertView;
    }

 }
