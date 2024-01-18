package com.example.bookingapp.reciever;

import android.Manifest;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;

import androidx.core.app.ActivityCompat;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import com.example.bookingapp.R;

public class NotificationReceiver extends BroadcastReceiver {

    private static final int NOTIFICATION_ID = 1;
    private static final String CHANNEL_ID = "Zero channel";

    @Override
    public void onReceive(Context context, Intent intent) {
//        System.out.println("RECEIIIIIIIIIIIIIIIIIIIIIIIIIVEEEEEEEEEEEEERRRRRRRRRRRRRR");
//
//        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(context);
//        NotificationCompat.Builder builder = new NotificationCompat.Builder(context, CHANNEL_ID);
//
//        if (intent.getAction().equals(DriverMainActivity.SYNC_DATA)) {
//            int resultCode = intent.getExtras().getInt(AcceptingRideService.RESULT_CODE);
//
//            if (resultCode == 1) {
//                builder.setContentIntent(createDriverNotificationIntent(context))
//                        .setSmallIcon(R.drawable.ic_launcher_foreground)
//                        .setContentTitle("New ride")
//                        .setContentText("You have a new notification!")
//                        .setPriority(NotificationCompat.PRIORITY_DEFAULT)
//                        .setAutoCancel(true);
//            }
//
//            notificationManager.notify(NOTIFICATION_ID, builder.build());
//        } else if (intent.getAction().equals(PassengerMainActivity.ACCEPTED_DATA)) {
//            int resultCode = intent.getExtras().getInt(AcceptedRideService.RESULT_CODE);
//
//            if (resultCode == 1) {
//                builder.setSmallIcon(R.drawable.ic_launcher_foreground)
//                        .setContentTitle("Notification")
//                        .setContentText("Your ride is accepted!\nThe vehicle will arrive in 2 minutes!")
//                        .setPriority(NotificationCompat.PRIORITY_DEFAULT)
//                        .setAutoCancel(true);
//            } else if (resultCode == 0) {
//                builder.setSmallIcon(R.drawable.ic_launcher_foreground)
//                        .setContentTitle("Notification")
//                        .setContentText("Sorry, Your ride is rejected!\nTry to order a ride again.")
//                        .setPriority(NotificationCompat.PRIORITY_DEFAULT)
//                        .setAutoCancel(true);
//            }
//            // kill service
//            context.stopService(new Intent(context, AcceptedRideService.class));
//            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.POST_NOTIFICATIONS) != PackageManager.PERMISSION_GRANTED) {
//                // TODO: Consider calling
//                //    ActivityCompat#requestPermissions
//                // here to request the missing permissions, and then overriding
//                //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
//                //                                          int[] grantResults)
//                // to handle the case where the user grants the permission. See the documentation
//                // for ActivityCompat#requestPermissions for more details.
//                return;
//            }
//            notificationManager.notify(NOTIFICATION_ID, builder.build());
//        }

//        else if(intent.getAction().equals(PassengerCurrentRideActivity.VEHICLE_DATA))
//            builder.setSmallIcon(R.drawable.ic_launcher_foreground)
//                    .setContentTitle("Started ride")
//                    .setContentText("Your ride has started!")
//                    .setPriority(NotificationCompat.PRIORITY_DEFAULT)
//                    .setAutoCancel(true);

//        notificationManager.notify(NOTIFICATION_ID, builder.build());
    }

//    private PendingIntent createNotificationIntent(Context context) {
//        Intent notifyIntent = new Intent(context, AcceptanceRideActivity.class);
//        // Set the Activity to start in a new, empty task
//        notifyIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK );
//        // Create the PendingIntent
//        return PendingIntent.getActivity(
//                context, 0, notifyIntent, PendingIntent.FLAG_IMMUTABLE);
//    }
}