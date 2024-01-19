package com.example.bookingapp.adapters;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.StrictMode;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.FragmentActivity;

import com.example.bookingapp.FragmentTransition;
import com.example.bookingapp.R;
import com.example.bookingapp.clients.ClientUtils;
import com.example.bookingapp.dto.commentsAndRatings.AccommodationCommentDTO;
import com.example.bookingapp.dto.users.UserDTO;
import com.example.bookingapp.enums.Role;
import com.example.bookingapp.fragments.AccommodationViewFragment;
import com.example.bookingapp.model.Accommodation;
import com.example.bookingapp.model.AccommodationListing;
import com.example.bookingapp.model.NotificationListing;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;

import retrofit2.Call;
import retrofit2.Response;

public class NotificationAdapter  extends ArrayAdapter<NotificationListing> {

    private ArrayList<NotificationListing> notifications;
    private static final String USER_ID_KEY = "user_id";
    Context context;
    SharedPreferences sharedPreferences;

    public NotificationAdapter(@NonNull Context context, int resource) {
        super(context, resource);
        this.context = context;
    }

    public NotificationAdapter(Context context, ArrayList<NotificationListing> NotificationListing, SharedPreferences sharedPreferences){
        super(context, R.layout.notification_card, NotificationListing);
        this.notifications = NotificationListing;
        this.context = context;
        this.sharedPreferences = sharedPreferences;
    }
    @Override
    public int getCount() {
        return notifications.size();
    }

    @Nullable
    @Override
    public NotificationListing getItem(int position) {
        return notifications.get(position);
    }
    @Override
    public long getItemId(int position) {
        return position;
    }


    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        NotificationListing notification = getItem(position);
        if(convertView == null){
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.notification_card,
                    parent, false);
        }
        LinearLayout card = convertView.findViewById(R.id.notification_card);
        TextView type = convertView.findViewById(R.id.type);
        TextView date_and_time = convertView.findViewById(R.id.date_and_time);
        TextView content = convertView.findViewById(R.id.content);

        if(notification != null){
            type.setText(notification.getType().toString());
            date_and_time.setText(notification.getTime());
            content.setText(notification.getContent());
        }

        return convertView;
    }
}
