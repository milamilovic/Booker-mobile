package com.example.bookingapp.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.bookingapp.R;
import com.example.bookingapp.model.AccommodationRequestOwnerDTO;
import com.example.bookingapp.model.GuestOwnerViewDTO;
import com.example.bookingapp.model.ReportedUsersListing;

import java.util.ArrayList;

public class ReportedUsersAdapter extends ArrayAdapter<ReportedUsersListing> {

    private ArrayList<ReportedUsersListing> reportedUsersListing;
    Context context;

    public ReportedUsersAdapter(@NonNull Context context, int resource) {
        super(context, resource);
        this.context = context;
    }

    public ReportedUsersAdapter(Context context, ArrayList<ReportedUsersListing> reportedUsersListing){
        super(context, R.layout.block_user_request_card, reportedUsersListing);
        this.reportedUsersListing = reportedUsersListing;
        this.context = context;
    }

    @Override
    public int getCount() {
        return reportedUsersListing.size();
    }

    @Nullable
    @Override
    public ReportedUsersListing getItem(int position) {
        return reportedUsersListing.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        ReportedUsersListing request = getItem(position);
        if(convertView == null){
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.block_user_request_card,
                    parent, false);
        }
        LinearLayout card = convertView.findViewById(R.id.block_user_card);
        TextView sender = convertView.findViewById(R.id.sender);
        TextView sender_role = convertView.findViewById(R.id.sender_role);
        TextView receiver = convertView.findViewById(R.id.receiver);
        TextView receiver_role = convertView.findViewById(R.id.receiver_role);
        TextView date = convertView.findViewById(R.id.report_date);
        TextView reason = convertView.findViewById(R.id.reason);
        Button block = convertView.findViewById(R.id.block_button);

        if(request != null){
            sender.setText(request.getSender().getUser().getName() + " " + request.getSender().getUser().getSurname());
            sender_role.setText(request.getSender().getRole().toString());
            receiver.setText(request.getReceiver().getUser().getName() + " " + request.getReceiver().getUser().getSurname());
            receiver_role.setText(request.getReceiver().getRole().toString());
            date.setText(request.getDate().toString());
            reason.setText(request.getReason());
            if(!canUserBeBlocked(request)) {
                block.setBackgroundResource(R.drawable.round_corner_disabled_button);
                block.setText("Unblock user");
            }
        }

        return convertView;
    }

    private boolean canUserBeBlocked(ReportedUsersListing request){
        return request.getReceiver().getBlocked();
    }
 }
