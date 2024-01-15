package com.example.bookingapp.adapters;


import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.fragment.app.FragmentActivity;


import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.bookingapp.R;
import com.example.bookingapp.dto.commentsAndRatings.OwnerCommentDTO;
import com.example.bookingapp.model.Owner;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Locale;

public class OwnerCommentsAdapter extends ArrayAdapter<OwnerCommentDTO> {
    private ArrayList<OwnerCommentDTO> ownerCommentDTOS;
    Context context;
    private static final String USER_ID_KEY = "user_id";

    public OwnerCommentsAdapter(@NonNull Context context, int resource) {
        super(context, resource);
        this.context = context;
    }

    public OwnerCommentsAdapter(Context context, ArrayList<OwnerCommentDTO> ownerCommentDTOS) {
        super(context, R.layout.owner_comment_item, ownerCommentDTOS);
        this.ownerCommentDTOS = ownerCommentDTOS;
        this.context = context;
    }

    @Nullable
    @Override
    public int getCount() {
        return ownerCommentDTOS.size();
    }

    @Nullable
    @Override
    public OwnerCommentDTO getItem(int position) {
        return ownerCommentDTOS.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        OwnerCommentDTO ownerCommentDTO = getItem(position);
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.owner_comment_item,
                    parent, false);
        }

        LinearLayout card = convertView.findViewById(R.id.owner_comments_card_item);
        ImageView profilePic = convertView.findViewById(R.id.comment_profile_pic);
        TextView name = convertView.findViewById(R.id.name);
        TextView content = convertView.findViewById(R.id.content);
        TextView commentDate = convertView.findViewById(R.id.comment_date);
        TextView commentRating = convertView.findViewById(R.id.comment_date);
        ImageView deleteComment = convertView.findViewById(R.id.delete_comment);
        ImageView ratingStar = convertView.findViewById(R.id.rating_star);

        if (ownerCommentDTO != null) {
            String uri = ownerCommentDTO.getGuestProfilePicture().getPath_mobile();
            Resources resources = getContext().getResources();
            final int resourceId = resources.getIdentifier(uri, "drawable", getContext().getPackageName());
            profilePic.setImageResource(resourceId);
            String nameSurname = ownerCommentDTO.getGuestName() + ownerCommentDTO.getGuestSurname();
            name.setText(nameSurname);
            content.setText(ownerCommentDTO.getContent());
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
            String dateString = sdf.format(ownerCommentDTO.getDate());
            commentDate.setText(dateString);
            String ratingString = ownerCommentDTO.getRating() + "/5";
            commentRating.setText(ratingString);



        }
        return convertView;
    }

}
