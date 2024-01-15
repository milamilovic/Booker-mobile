package com.example.bookingapp.adapters;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.StrictMode;
import android.widget.ArrayAdapter;

import androidx.annotation.NonNull;

import com.example.bookingapp.FragmentTransition;
import com.example.bookingapp.R;
import com.example.bookingapp.clients.AccommodationRating;
import com.example.bookingapp.clients.ClientUtils;
import com.example.bookingapp.dto.users.UserDTO;
import com.example.bookingapp.enums.Role;
import com.example.bookingapp.fragments.AccommodationViewFragment;
import com.example.bookingapp.model.Accommodation;
import com.example.bookingapp.model.AccommodationListing;
import com.example.bookingapp.model.Address;
import com.example.bookingapp.model.Amenity;
import com.example.bookingapp.model.Availability;
import com.example.bookingapp.model.Image;
import com.example.bookingapp.model.Price;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;


import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AccommodationListAdapter extends ArrayAdapter<AccommodationListing> {

    private ArrayList<AccommodationListing> aAccommodationListings;
    private static final String USER_ID_KEY = "user_id";
    Context context;
    SharedPreferences sharedPreferences;

    public AccommodationListAdapter(@NonNull Context context, int resource) {
        super(context, resource);
        this.context = context;
    }

    public AccommodationListAdapter(Context context, ArrayList<AccommodationListing> AccommodationListings, SharedPreferences sharedPreferences){
        super(context, R.layout.accommodation_card, AccommodationListings);
        this.aAccommodationListings = AccommodationListings;
        this.context = context;
        this.sharedPreferences = sharedPreferences;
    }
    /*
     * Ova metoda vraca ukupan broj elemenata u listi koje treba prikazati
     * */
    @Override
    public int getCount() {
        return aAccommodationListings.size();
    }

    /*
     * Ova metoda vraca pojedinacan element na osnovu pozicije
     * */
    @Nullable
    @Override
    public AccommodationListing getItem(int position) {
        return aAccommodationListings.get(position);
    }

    /*
     * Ova metoda vraca jedinstveni identifikator, za adaptere koji prikazuju
     * listu ili niz, pozicija je dovoljno dobra. Naravno mozemo iskoristiti i
     * jedinstveni identifikator objekta, ako on postoji.
     * */
    @Override
    public long getItemId(int position) {
        return position;
    }

    /*
     * Ova metoda popunjava pojedinacan element ListView-a podacima.
     * Ako adapter cuva listu od n elemenata, adapter ce u petlji ici
     * onoliko puta koliko getCount() vrati. Prilikom svake iteracije
     * uzece java objekat sa odredjene poziciuje (model) koji cuva podatke,
     * i layout koji treba da prikaze te podatke (view) npr R.layout.AccommodationListing_card.
     * Kada adapter ima model i view, prosto ce uzeti podatke iz modela,
     * popuniti view podacima i poslati listview da prikaze, i nastavice
     * sledecu iteraciju.
     * */
    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        AccommodationListing AccommodationListing = getItem(position);
        if(convertView == null){
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.accommodation_card,
                    parent, false);
        }
        LinearLayout card = convertView.findViewById(R.id.accommodation_listing_card_item);
        ImageView image = convertView.findViewById(R.id.accommodation_image);
        TextView title = convertView.findViewById(R.id.AccommodationListing_title);
        TextView description = convertView.findViewById(R.id.AccommodationListing_description);
        TextView totalPrice = convertView.findViewById(R.id.accommodation_listing_price_total);
        TextView pricePerDay = convertView.findViewById(R.id.accommodation_listing_price_per_day);
        RatingBar ratingBar = convertView.findViewById(R.id.rating);
        ImageButton favorite = convertView.findViewById(R.id.favorite);

        if(AccommodationListing != null){
            //TODO: getting image
            title.setText(AccommodationListing.getTitle());
            description.setText(AccommodationListing.getDescription());
            totalPrice.setText(AccommodationListing.getTotalPrice() + "$");
            pricePerDay.setText(AccommodationListing.getPricePerDay() + "$/day");
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
            Call<List<AccommodationRating>> call = ClientUtils.accommodationService.getRatings(AccommodationListing.getId());
            try{
                Response<List<AccommodationRating>> response = call.execute();
                List<AccommodationRating> ratings = (List<AccommodationRating>) response.body();
                float rating_float = 0;
                for(AccommodationRating a : ratings) {
                    rating_float += a.getRate();
                }
                ratingBar.setRating(rating_float);
            }catch(Exception ex){
                System.out.println("EXCEPTION WHILE GETTING RATINGS");
                ex.printStackTrace();
            }
            //favourite accommodations
            ImageButton favourite = convertView.findViewById(R.id.favourite);
            Long userID = sharedPreferences.getLong(USER_ID_KEY, 0);
            AtomicBoolean isFavourite = new AtomicBoolean(false);
            StrictMode.setThreadPolicy(policy);
            Call<Boolean> userCall = ClientUtils.accommodationService.isGuestsFavouriteAccommodation(userID, AccommodationListing.getId());
            try {
                Response<Boolean> response1 = userCall.execute();
                isFavourite.set(response1.body());
            } catch (Exception ex) {
            }
            if(isFavourite.get()) {
                favourite.setImageResource(R.drawable.icons8_heart_30_selected_favourite);
            } else {
                favourite.setImageResource(R.drawable.icons8_heart_30_not_selected_favourite);
            }
            convertView.findViewById(R.id.favourite).setOnClickListener(v -> {
                StrictMode.setThreadPolicy(policy);
                Call<Boolean> userCall3 = ClientUtils.accommodationService.isGuestsFavouriteAccommodation(userID, AccommodationListing.getId());
                try {
                    Response<Boolean> response1 = userCall3.execute();
                    isFavourite.set(response1.body());
                    if(isFavourite.get()) {
                        Call<Boolean> userCall4 =ClientUtils.accommodationService.removeFromFav(userID, AccommodationListing.getId());
                        try {
                            userCall4.execute();
                            favourite.setImageResource(R.drawable.icons8_heart_30_not_selected_favourite);
                        } catch (Exception ex) {
                        }
                    } else {
                        Call<Boolean> userCall4 =ClientUtils.accommodationService.addToFav(userID, AccommodationListing.getId());
                        try {
                            userCall4.execute();
                            favourite.setImageResource(R.drawable.icons8_heart_30_selected_favourite);
                        } catch (Exception ex) {
                        }
                    }
                } catch (Exception ex) {
                }
            });
            if(userID==0) {
                convertView.findViewById(R.id.favourite_circle).setVisibility(View.GONE);
                convertView.findViewById(R.id.favourite).setVisibility(View.GONE);
            } else {
                StrictMode.setThreadPolicy(policy);
                Call<UserDTO> userCall2 = ClientUtils.userService.getById(userID);
                try {
                    Response<UserDTO> response = userCall2.execute();
                    UserDTO user = (UserDTO) response.body();
                    if (user.getRole() == Role.GUEST) {
                        convertView.findViewById(R.id.favourite_circle).setVisibility(View.VISIBLE);
                        convertView.findViewById(R.id.favourite).setVisibility(View.VISIBLE);
                    } else {
                        convertView.findViewById(R.id.favourite_circle).setVisibility(View.GONE);
                        convertView.findViewById(R.id.favourite).setVisibility(View.GONE);
                    }
                } catch (Exception ex) {
                }
            }
            card.setOnClickListener(v -> {
                StrictMode.setThreadPolicy(policy);
                Call<Accommodation> accommodation = ClientUtils.accommodationService.findAccommodation(AccommodationListing.getId());
                try{
                    Response<Accommodation> response = accommodation.execute();
                    Accommodation acc = (Accommodation) response.body();
                    FragmentTransition.to(AccommodationViewFragment.newInstance(acc), (FragmentActivity) context, true, R.id.fragment_placeholder);
                }catch(Exception ex){
                    System.out.println("EXCEPTION WHILE GETTING ACCOMMODATION");
                    ex.printStackTrace();
                }
            });
        }

        return convertView;
    }

}
