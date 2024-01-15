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
import com.example.bookingapp.clients.AccommodationRating;
import com.example.bookingapp.clients.ClientUtils;
import com.example.bookingapp.fragments.AccommodationViewFragment;
import com.example.bookingapp.fragments.FavouriteAccommodationsFragment;
import com.example.bookingapp.fragments.ReservationRequestsGuestFragment;
import com.example.bookingapp.model.Accommodation;
import com.example.bookingapp.model.AccommodationListing;
import com.example.bookingapp.model.Amenity;
import com.example.bookingapp.model.FavouriteAccommodationListing;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;

import retrofit2.Call;
import retrofit2.Response;

public class FavouriteAccommodationAdapter  extends ArrayAdapter<FavouriteAccommodationListing> {

    private ArrayList<FavouriteAccommodationListing> aAccommodationListings;
    Context context;
    SharedPreferences sharedPreferences;

    private static final String USER_ID_KEY = "user_id";

    public FavouriteAccommodationAdapter(@NonNull Context context, int resource, SharedPreferences preferences) {
        super(context, resource);
        this.context = context;
        this.sharedPreferences = preferences;
    }

    public FavouriteAccommodationAdapter(Context context, ArrayList<FavouriteAccommodationListing> AccommodationListings, SharedPreferences preferences){
        super(context, R.layout.accommodation_card, AccommodationListings);
        this.aAccommodationListings = AccommodationListings;
        this.context = context;
        this.sharedPreferences = preferences;
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
    public FavouriteAccommodationListing getItem(int position) {
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
        FavouriteAccommodationListing AccommodationListing = getItem(position);
        if(convertView == null){
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.favourite_accommodation_card,
                    parent, false);
        }
        LinearLayout card = convertView.findViewById(R.id.accommodation_listing_card_item);
        ImageView image = convertView.findViewById(R.id.accommodation_image);
        TextView title = convertView.findViewById(R.id.AccommodationListing_title);
        TextView description = convertView.findViewById(R.id.AccommodationListing_description);
        TextView address = convertView.findViewById(R.id.AccommodationListing_place);
        RatingBar ratingBar = convertView.findViewById(R.id.rating);
        ImageButton favorite = convertView.findViewById(R.id.favorite);

        if(AccommodationListing != null){
            image.setImageResource(R.drawable.apartment_image);
            title.setText(AccommodationListing.getTitle());
            description.setText(AccommodationListing.getShortDescription());
            address.setText(AccommodationListing.getAddress().getCity());
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
            ImageButton favourite = convertView.findViewById(R.id.favorite);
            favourite.setImageResource(R.drawable.icons8_heart_30_selected_favourite);
            AtomicBoolean isFavourite = new AtomicBoolean(true);
            Long userID = sharedPreferences.getLong(USER_ID_KEY, 0);
            convertView.findViewById(R.id.favorite).setOnClickListener(v -> {
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
                    FragmentTransition.to(FavouriteAccommodationsFragment.newInstance(), (FragmentActivity) context, true, R.id.fragment_placeholder);
                } catch (Exception ex) {
                }
                FragmentTransition.to(FavouriteAccommodationsFragment.newInstance(), (FragmentActivity) context, true, R.id.fragment_placeholder);

            });
        }

        return convertView;
    }

}
