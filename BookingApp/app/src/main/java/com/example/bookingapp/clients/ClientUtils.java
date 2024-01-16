package com.example.bookingapp.clients;

import java.util.concurrent.TimeUnit;

import com.example.bookingapp.BuildConfig;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class ClientUtils {

    //EXAMPLE: http://192.168.43.73:8080/api/
    public static final String SERVICE_API_PATH = "http://"+ BuildConfig.IP_ADDR +":8080/api/";


    /*
     * Ovo ce nam sluziti za debug, da vidimo da li zahtevi i odgovori idu
     * odnosno dolaze i kako izgeldaju.
     * */
    public static OkHttpClient test(){
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

        OkHttpClient client = new OkHttpClient.Builder()
                .connectTimeout(120, TimeUnit.SECONDS)
                .readTimeout(120, TimeUnit.SECONDS)
                .writeTimeout(120, TimeUnit.SECONDS)
                .addInterceptor(interceptor).build();

        return client;
    }

    /*
     * Prvo je potrebno da definisemo retrofit instancu preko koje ce komunikacija ici
     * */
    public static Retrofit retrofit = new Retrofit.Builder()
            .baseUrl(SERVICE_API_PATH)
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .client(test())
            .build();

    /*
     * Definisemo konkretnu instancu servisa na intnerntu sa kojim
     * vrsimo komunikaciju
     * */
//    public static ProductService productService = retrofit.create(ProductService.class);
    public static UserService userService = retrofit.create(UserService.class);
    public static AccommodationService accommodationService = retrofit.create(AccommodationService.class);
    public static ReservationRequestService reservationRequestService = retrofit.create(ReservationRequestService.class);
    public static OwnerCommentService ownerCommentService = retrofit.create(OwnerCommentService.class);
    public static AccommodationCommentService accommodationCommentService = retrofit.create(AccommodationCommentService.class);


}
