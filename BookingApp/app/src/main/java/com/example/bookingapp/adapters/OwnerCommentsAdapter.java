package com.example.bookingapp.adapters;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.StrictMode;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.FragmentActivity;

import com.example.bookingapp.FragmentTransition;
import com.example.bookingapp.R;
import com.example.bookingapp.clients.ClientUtils;
import com.example.bookingapp.dto.commentsAndRatings.OwnerCommentDTO;
import com.example.bookingapp.dto.users.OwnerDTO;
import com.example.bookingapp.dto.users.UserDTO;
import com.example.bookingapp.fragments.AccommodationViewFragment;
import com.example.bookingapp.fragments.CommentsFragment;
import com.example.bookingapp.fragments.HomeFragment;
import com.example.bookingapp.fragments.UpdateAccommodationFragment;
import com.example.bookingapp.model.Accommodation;
import com.example.bookingapp.model.Address;
import com.example.bookingapp.model.AdminOwnerComment;
import com.example.bookingapp.model.Amenity;
import com.example.bookingapp.model.ApproveAccommodationListing;
import com.example.bookingapp.model.Availability;
import com.example.bookingapp.model.Image;
import com.example.bookingapp.model.Price;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Response;

public class OwnerCommentsAdapter extends ArrayAdapter<AdminOwnerComment> {
    private ArrayList<AdminOwnerComment> comments = new ArrayList<AdminOwnerComment>();
    Context context;

    public OwnerCommentsAdapter(@NonNull Context context, int resource) {
        super(context, resource);
        this.context = context;
    }

    public OwnerCommentsAdapter(Context context, ArrayList<AdminOwnerComment> comments){
        super(context, R.layout.owner_comment_card);
        this.comments = comments;
        this.context = context;
    }

    @Override
    public int getCount() {
        return comments.size();
    }

    @Nullable
    @Override
    public AdminOwnerComment getItem(int position) {
        return comments.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        AdminOwnerComment comment = getItem(position);
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.owner_comment_card,
                    parent, false);
        }
        TextView owner_name = convertView.findViewById(R.id.owner_name);
        TextView commenter_name = convertView.findViewById(R.id.commenter_name);
        TextView date = convertView.findViewById(R.id.comment_date);
        TextView comment_text = convertView.findViewById(R.id.comment_text);
        TextView rating = convertView.findViewById(R.id.rating);
        Button delete = convertView.findViewById(R.id.delete_button);
        Button approve = convertView.findViewById(R.id.approve_button);
        LinearLayout deleteLayout = convertView.findViewById(R.id.delete_layout);
        LinearLayout approveLayout = convertView.findViewById(R.id.approve_layout);
        TextView reported = convertView.findViewById(R.id.reported_label);

        SimpleDateFormat formatter = new SimpleDateFormat("dd.MM.yyyy.");

        if (comment != null) {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
            Call<OwnerDTO> callOwner = ClientUtils.userService.getOwnerById(comment.getOwnerId());
            try{
                Response<OwnerDTO> responseOwner = callOwner.execute();
                OwnerDTO owner = (OwnerDTO) responseOwner.body();
                owner_name.setText(owner.getName() + " " + owner.getSurname());
            }catch(Exception ex){
                System.out.println("EXCEPTION WHILE GETTING OWNER");
                ex.printStackTrace();
            }

            commenter_name.setText(comment.getGuestName()+" "+ comment.getGuestSurname());
            date.setText(formatter.format(comment.getDate()));
            rating.setText(comment.getRating()+" / 5");
            comment_text.setText(comment.getContent());
            if (comment.isApproved()){
                approveLayout.setVisibility(View.GONE);
            } else {
                approveLayout.setVisibility(View.VISIBLE);
                approve.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
                        StrictMode.setThreadPolicy(policy);
                        Call<OwnerCommentDTO> call = ClientUtils.ownerCommentService.approveComment(comment.getId());
                        try{
                            Response<OwnerCommentDTO> response = call.execute();
                            Toast.makeText(context, "Deleted comment!", Toast.LENGTH_SHORT).show();
                            FragmentTransition.to(CommentsFragment.newInstance(), (FragmentActivity) context, false, R.id.fragment_placeholder);
                        }catch(Exception ex){
                            System.out.println("EXCEPTION WHILE COMMENT DELETION");
                            ex.printStackTrace();
                        }
                    }
                });
            }
            if (comment.isReported()){
                deleteLayout.setVisibility(View.VISIBLE);
                delete.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
                        StrictMode.setThreadPolicy(policy);
                        Call<AdminOwnerComment> call = ClientUtils.userService.deleteComment(comment.getId());
                        try{
                            Response<AdminOwnerComment> response = call.execute();
                            AdminOwnerComment comm = (AdminOwnerComment) response.body();
                            Toast.makeText(context, "Approved comment!", Toast.LENGTH_SHORT).show();
                            FragmentTransition.to(CommentsFragment.newInstance(), (FragmentActivity) context, false, R.id.fragment_placeholder);
                        }catch(Exception ex){
                            System.out.println("EXCEPTION WHILE COMMENT DELETION");
                            ex.printStackTrace();
                        }
                    }
                });
            } else {
                deleteLayout.setVisibility(View.GONE);
            }

        }

        return convertView;
    }
}
