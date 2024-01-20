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
import androidx.fragment.app.FragmentManager;

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

public class OwnerAllAccommodationAdapter extends ArrayAdapter<ApproveAccommodationListing> {
    private ArrayList<ApproveAccommodationListing> approveAccommodationListings;
    Context context;

    public OwnerAllAccommodationAdapter(@NonNull Context context, int resource) {
        super(context, resource);
        this.context = context;
    }

    public OwnerAllAccommodationAdapter(Context context, ArrayList<ApproveAccommodationListing> approveAccommodationListing){
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
        //TextView status = convertView.findViewById(R.id.accommodation_approved);
        LinearLayout card = convertView.findViewById(R.id.approve_accommodation_card);

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
            /*if (accommodation.getApproved()){
                status.setText("approved");
            } else{
                status.setText("unapproved");
            }*/
            card.setOnClickListener(v->{
                ArrayList<Image> images = new ArrayList<Image>();
                images.add(new Image(1L, "../../../../../res/drawable/paris_image.jpg", "", null));
                images.add(new Image(1L, "../../../../../res/drawable/copenhagen_image.jpg", "", null));
                images.add(new Image(1L, "../../../../../res/drawable/madrid_image.jpg", "", null));
                images.add(new Image(1L, "../../../../../res/drawable/room_image.jpg", "", null));
                images.add(new Image(1L, "../../../../../res/drawable/hotel_image.jpg", "", null));
                images.add(new Image(1L, "../../../../../res/drawable/lisbon_image.jpg", "", null));
                ArrayList<Amenity> amenities = new ArrayList<Amenity>();
                amenities.add(new Amenity(1L, "Wi-Fi", "", null));
                amenities.add(new Amenity(2L, "AC", "", null));
                amenities.add(new Amenity(3L, "popular location", "", null));
                amenities.add(new Amenity(4L, "clean", "", null));
                FragmentTransition.to(AccommodationViewFragment.newInstance(new Accommodation(accommodation.getId(),
                        accommodation.getTitle(), "The units come with parquet floors and feature a fully equipped kitchen with a microwave, a dining area, a flat-screen TV with streaming services, and a private bathroom with walk-in shower and a hair dryer.",
                        images, new ArrayList<Availability>(), new ArrayList<Price>(), new ArrayList<Object>(), new ArrayList<Object>(), 2L, amenities, 1, 5, true, new Address(1L, "Ulica 111", "London", 12.21, 15.55, null), false, 0)), (FragmentActivity) context, true, R.id.fragment_placeholder);
            });

            update.setOnClickListener(v->{
                ArrayList<Image> images = new ArrayList<Image>();
                images.add(new Image(1L, "../../../../../res/drawable/paris_image.jpg", "", null));
                images.add(new Image(1L, "../../../../../res/drawable/copenhagen_image.jpg", "", null));
                images.add(new Image(1L, "../../../../../res/drawable/madrid_image.jpg", "", null));
                images.add(new Image(1L, "../../../../../res/drawable/room_image.jpg", "", null));
                images.add(new Image(1L, "../../../../../res/drawable/hotel_image.jpg", "", null));
                images.add(new Image(1L, "../../../../../res/drawable/lisbon_image.jpg", "", null));
                ArrayList<Amenity> amenities = new ArrayList<Amenity>();
                amenities.add(new Amenity(1L, "Wi-Fi", "", null));
                amenities.add(new Amenity(2L, "AC",  "", null));
                amenities.add(new Amenity(3L, "popular location",  "", null));
                amenities.add(new Amenity(4L, "clean",  "", null));
                FragmentTransition.to(UpdateAccommodationFragment.newInstance(new Accommodation(accommodation.getId(),
                        accommodation.getTitle(), "The units come with parquet floors and feature a fully equipped kitchen with a microwave, a dining area, a flat-screen TV with streaming services, and a private bathroom with walk-in shower and a hair dryer.",
                        images, new ArrayList<Availability>(), new ArrayList<Price>(), new ArrayList<Object>(), new ArrayList<Object>(), 2L, amenities, 1, 5, true, new Address(1L, "Ulica 111", "London", 12.21, 15.55, null), false, 0)), (FragmentActivity) context, true, R.id.fragment_placeholder);
            });
        }

        return convertView;
    }
}
