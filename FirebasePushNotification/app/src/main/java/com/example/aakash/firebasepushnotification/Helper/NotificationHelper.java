package com.example.aakash.firebasepushnotification.Helper;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.Intent;
import android.graphics.Bitmap;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v4.app.NotificationCompat;

import com.example.aakash.firebasepushnotification.Config.Config;
import com.example.aakash.firebasepushnotification.DisplayActivity;
import com.example.aakash.firebasepushnotification.R;

public class NotificationHelper extends ContextWrapper {

    private static final String AAKASH_CHANNEL_ID = "com.example.aakash.firebasepushnotification.Aakash";
    private static final String AAKASH_CHANNEL_NAME = "Aakash";
    private NotificationManager manager;

    public NotificationHelper(Context base) {
        super(base);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) createChannel();
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private void createChannel() {

        NotificationChannel channel = new NotificationChannel(AAKASH_CHANNEL_ID, AAKASH_CHANNEL_NAME, NotificationManager.IMPORTANCE_DEFAULT);
        channel.enableVibration(true);
        channel.enableLights(true);
        channel.setLockscreenVisibility(Notification.VISIBILITY_PRIVATE);

        getManager().createNotificationChannel(channel);
    }

    public NotificationManager getManager() {

        if (manager == null)
            manager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        return manager;
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public Notification.Builder getChannel(String title, String body, Bitmap bitmap) {

        Notification.Style style = new Notification.BigPictureStyle().bigPicture(bitmap);

        Intent intent = new Intent(getApplicationContext(), DisplayActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
        PendingIntent pendingIntent = PendingIntent.getActivity(getApplicationContext(), 0, intent, 0);

        Uri defaultSound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);

        return new Notification.Builder(getApplicationContext(), AAKASH_CHANNEL_ID).setSmallIcon(R.mipmap.ic_launcher).setContentTitle(Config.title).setAutoCancel(true).setSound(defaultSound).setContentIntent(pendingIntent).setStyle(style);
    }
}
