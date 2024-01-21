package com.example.bookingapp.adapters;

import android.content.Context;
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
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.FragmentActivity;

import com.example.bookingapp.FragmentTransition;
import com.example.bookingapp.R;
import com.example.bookingapp.clients.ClientUtils;
import com.example.bookingapp.dto.users.OwnerDTO;
import com.example.bookingapp.fragments.CommentsFragment;
import com.example.bookingapp.model.Accommodation;
import com.example.bookingapp.model.AdminAccommodationComment;
import com.example.bookingapp.model.AdminOwnerComment;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Response;

public class AccommodationCommentsAdapter extends ArrayAdapter<AdminAccommodationComment> {
    private ArrayList<AdminAccommodationComment> comments = new ArrayList<AdminAccommodationComment>();
    Context context;

    public AccommodationCommentsAdapter(@NonNull Context context, int resource) {
        super(context, resource);
        this.context = context;
    }

    public AccommodationCommentsAdapter(Context context, ArrayList<AdminAccommodationComment> comments){
        super(context, R.layout.accommodation_comment_card);
        this.comments = comments;
        this.context = context;
    }

    @Override
    public int getCount() {
        return comments.size();
    }

    @Nullable
    @Override
    public AdminAccommodationComment getItem(int position) {
        return comments.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        AdminAccommodationComment comment = getItem(position);
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.accommodation_comment_card,
                    parent, false);
        }
        TextView acc_title = convertView.findViewById(R.id.acc_title);
        ImageView acc_image = convertView.findViewById(R.id.acc_image);
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
            Call<Accommodation> callAcc = ClientUtils.accommodationService.findAccommodation(comment.getAccommodationId());
            try{
                Response<Accommodation> responseAcc = callAcc.execute();
                Accommodation accommodation = (Accommodation) responseAcc.body();
                acc_title.setText(accommodation.getTitle());
                // getting profile picture
                StrictMode.setThreadPolicy(policy);
                Call<List<String>> imageCall = ClientUtils.accommodationService.getImages(comment.getAccommodationId());
                try{
                    Response<List<String>> response = imageCall.execute();
                    List<String> images = (List<String>) response.body();
                    if(images!=null && !images.isEmpty()) {
                        byte[] bytes = Base64.decode(images.get(0), Base64.DEFAULT);
                        Bitmap bitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
                        acc_image.setImageBitmap(bitmap);
                    }
                }catch(Exception ex){
                    System.out.println("EXCEPTION WHILE GETTING IMAGES");
                    ex.printStackTrace();
                }
            }catch(Exception ex){
                System.out.println("EXCEPTION WHILE GETTING ACC");
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
            }
            if (comment.isReported()){
                deleteLayout.setVisibility(View.VISIBLE);
                delete.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
                        StrictMode.setThreadPolicy(policy);
                        Call<AdminAccommodationComment> call = ClientUtils.accommodationService.deleteComment(comment.getId());
                        try{
                            Response<AdminAccommodationComment> response = call.execute();
                            AdminAccommodationComment comm = (AdminAccommodationComment) response.body();
                            Toast.makeText(context, "Deleted accommodation comment!", Toast.LENGTH_SHORT).show();
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
