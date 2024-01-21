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
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.FragmentActivity;

import com.example.bookingapp.FragmentTransition;
import com.example.bookingapp.R;
import com.example.bookingapp.clients.ClientUtils;
import com.example.bookingapp.fragments.AccommodationViewFragment;
import com.example.bookingapp.fragments.UpdateAccommodationFragment;
import com.example.bookingapp.model.Accommodation;
import com.example.bookingapp.model.Address;
import com.example.bookingapp.model.Amenity;
import com.example.bookingapp.model.ApproveAccommodationListing;
import com.example.bookingapp.model.Availability;
import com.example.bookingapp.model.Image;
import com.example.bookingapp.model.Price;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Response;

public class OwnerApprovedAccommodationAdapter extends ArrayAdapter<ApproveAccommodationListing> {
    private ArrayList<ApproveAccommodationListing> approveAccommodationListings;
    Context context;

    public OwnerApprovedAccommodationAdapter(@NonNull Context context, int resource) {
        super(context, resource);
        this.context = context;
    }

    public OwnerApprovedAccommodationAdapter(Context context, ArrayList<ApproveAccommodationListing> approveAccommodationListing){
        super(context, R.layout.owner_update_accommodation_card, approveAccommodationListing);
        this.approveAccommodationListings = approveAccommodationListing;
        this.context = context;
    }

    @Override
    public int getCount() {
        return approveAccommodationListings.size();
    }

    @Nullable
    @Override
    public ApproveAccommodationListing getItem(int position) {
        return approveAccommodationListings.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        ApproveAccommodationListing accommodation = getItem(position);
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.owner_update_accommodation_card,
                    parent, false);
        }
        TextView title = convertView.findViewById(R.id.accommodation_title);
        TextView description = convertView.findViewById(R.id.accommodation_description);
        RatingBar ratingBar = convertView.findViewById(R.id.accommodation_rating);
        ImageView image = convertView.findViewById(R.id.accommodation_image);
        ImageView update = convertView.findViewById(R.id.update_button);
        ImageView delete = convertView.findViewById(R.id.delete_button);
        LinearLayout card = convertView.findViewById(R.id.approve_accommodation_card);

        //LinearLayout statusLayout = convertView.findViewById(R.id.status);

        if (accommodation != null) {
            //getting images
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
            Call<List<String>> imageCall = ClientUtils.accommodationService.getImages(accommodation.getId());
            try{
                Response<List<String>> response = imageCall.execute();
                List<String> images = (List<String>) response.body();
                if(images!=null && !images.isEmpty()) {
                    byte[] bytes = Base64.decode(images.get(0), Base64.DEFAULT);
                    Bitmap bitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
                    image.setImageBitmap(bitmap);
                }
            }catch(Exception ex){
                System.out.println("EXCEPTION WHILE GETTING IMAGES");
                ex.printStackTrace();
            }
            title.setText(accommodation.getTitle());
            description.setText(accommodation.getDescription());
            ratingBar.setRating(accommodation.getRating());
            //image.setImageResource(accommodation.getImage());
            //statusLayout.setVisibility(View.GONE);
            card.setOnClickListener(v->{
                StrictMode.ThreadPolicy policy2 = new StrictMode.ThreadPolicy.Builder().permitAll().build();
                StrictMode.setThreadPolicy(policy2);
                Call<Accommodation> accommodationCall = ClientUtils.accommodationService.findAccommodation(accommodation.getId());
                try{
                    Response<Accommodation> response = accommodationCall.execute();
                    Accommodation acc = (Accommodation) response.body();
                    FragmentTransition.to(AccommodationViewFragment.newInstance(acc), (FragmentActivity) context, true, R.id.fragment_placeholder);
                }catch(Exception ex){
                    System.out.println("EXCEPTION WHILE GETTING ACCOMMODATION");
                    ex.printStackTrace();
                }
            });
            update.setOnClickListener(v->{
                StrictMode.ThreadPolicy policy2 = new StrictMode.ThreadPolicy.Builder().permitAll().build();
                StrictMode.setThreadPolicy(policy2);
                Call<Accommodation> accommodationCall = ClientUtils.accommodationService.findAccommodation(accommodation.getId());
                try{
                    Response<Accommodation> response = accommodationCall.execute();
                    Accommodation acc = (Accommodation) response.body();
                    FragmentTransition.to(new UpdateAccommodationFragment(acc), (FragmentActivity) context, true, R.id.fragment_placeholder);
                }catch(Exception ex){
                    System.out.println("EXCEPTION WHILE GETTING ACCOMMODATION");
                    ex.printStackTrace();
                }
            });
        }

        return convertView;
    }
}
