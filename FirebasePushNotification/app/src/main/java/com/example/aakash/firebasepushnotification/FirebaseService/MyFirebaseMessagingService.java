package com.example.aakash.firebasepushnotification.FirebaseService;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.os.Looper;
import android.support.annotation.RequiresApi;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import com.example.aakash.firebasepushnotification.Config.Config;
import com.example.aakash.firebasepushnotification.DisplayActivity;
import com.example.aakash.firebasepushnotification.Helper.NotificationHelper;
import com.example.aakash.firebasepushnotification.R;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

import java.util.logging.Handler;
import java.util.logging.LogRecord;

public class MyFirebaseMessagingService extends FirebaseMessagingService {

    Target target = new Target() {
        @Override
        public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                showNotificationWithImageLevel26(bitmap);
            } else {
                showNotificationWithImage(bitmap);
            }
        }

        @Override
        public void onBitmapFailed(Drawable errorDrawable) {

        }

        @Override
        public void onPrepareLoad(Drawable placeHolderDrawable) {

        }
    };

    @RequiresApi(api = Build.VERSION_CODES.O)
    private void showNotificationWithImageLevel26(Bitmap bitmap) {

        NotificationHelper helper = new NotificationHelper(getBaseContext());

        Notification.Builder builder = helper.getChannel(Config.title, Config.message, bitmap);
        helper.getManager().notify(0, builder.build());
    }

    private void showNotificationWithImage(Bitmap bitmap) {

        NotificationCompat.BigPictureStyle style = new NotificationCompat.BigPictureStyle();
        style.setSummaryText(Config.message);
        style.bigPicture(bitmap);

        Uri defaultSound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);

        Intent intent = new Intent(getApplicationContext(), DisplayActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
        PendingIntent pendingIntent = PendingIntent.getActivity(getApplicationContext(), 0, intent, 0);

        NotificationCompat.Builder notificationBuilder = (NotificationCompat.Builder) new NotificationCompat.Builder(getApplicationContext()).setSmallIcon(R.mipmap.ic_launcher).setContentTitle(Config.title).setAutoCancel(true).setSound(defaultSound).setContentIntent(pendingIntent).setStyle(style);
        NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.notify(0, notificationBuilder.build());
    }

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        if (remoteMessage.getData() != null) getImage(remoteMessage);
        Log.d("AAKASHTAG", remoteMessage.getNotification().getBody());
    }

    private void getImage(final RemoteMessage remoteMessage) {

        //Set Message and Title
        Config.message = remoteMessage.getNotification().getBody();
        Config.title = remoteMessage.getNotification().getTitle();

        //Create Thread to Fetch Image from Notification
        Config.imageLink = remoteMessage.getData().get("image");
        android.os.Handler uiHandler = new android.os.Handler(Looper.getMainLooper());
        uiHandler.post(new Runnable() {
            @Override
            public void run() {
                //Get Image from Data Notification
                Picasso.with(getApplicationContext()).load(remoteMessage.getData().get("image")).into(target);
            }
        });
    }
}
