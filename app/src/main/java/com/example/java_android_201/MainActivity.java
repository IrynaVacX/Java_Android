package com.example.java_android_201;

import android.Manifest;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;
import android.widget.RemoteViews;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.app.NotificationCompat;
import androidx.core.content.ContextCompat;

public class MainActivity extends AppCompatActivity {

    private static final int REQUEST_POST_NOTIFICATIONS_PERMISSION = 1;
    private static final String CHANNEL_ID = "custom_channel";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        checkNotificationsPermission();
    }

    private void checkNotificationsPermission() {
        if (isNotificationsPermissionGranted()) {
            showToast("granted");
        } else {
            requestNotificationsPermission();
        }
    }

    private boolean isNotificationsPermissionGranted() {
        return ContextCompat.checkSelfPermission(this, Manifest.permission.POST_NOTIFICATIONS)
                == PackageManager.PERMISSION_GRANTED;
    }

    private void requestNotificationsPermission() {
        ActivityCompat.requestPermissions(
                this,
                new String[]{Manifest.permission.POST_NOTIFICATIONS},
                REQUEST_POST_NOTIFICATIONS_PERMISSION
        );
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == REQUEST_POST_NOTIFICATIONS_PERMISSION) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                showToast("granted");
            } else {
                showToast("denied");
            }
        }
    }

    private void showToast(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    public void onClick(View view) {
        NotificationManager manager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        NotificationCompat.Builder builder;

        Context context = getApplicationContext();
        Resources res = context.getResources();

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            createNotificationChannel(manager);
            builder = new NotificationCompat.Builder(this, CHANNEL_ID);
        } else {
            builder = new NotificationCompat.Builder(context);
        }

        PendingIntent action = PendingIntent.getActivity(context, 0, new Intent(context, MainActivity.class), PendingIntent.FLAG_IMMUTABLE);


        RemoteViews notificationLayoutSmall = new RemoteViews(getPackageName(), R.layout.custom_notification_small);
        RemoteViews notificationLayoutBig = new RemoteViews(getPackageName(), R.layout.custom_notification_big);

        builder.setContentIntent(action)
                .setLargeIcon(BitmapFactory.decodeResource(res, R.drawable.dragon_fruit))
                .setSmallIcon(R.drawable.dragon_fruit)
                .setStyle(new NotificationCompat.DecoratedCustomViewStyle())
                .setCustomContentView(notificationLayoutSmall)
                .setCustomBigContentView(notificationLayoutBig);

        Notification notification = builder.build();
        long notificationCode = System.currentTimeMillis();
        manager.notify((int) notificationCode, notification);
    }


    private void createNotificationChannel(NotificationManager manager) {
        if (manager.getNotificationChannel(CHANNEL_ID) == null) {
            NotificationChannel channel = new NotificationChannel(CHANNEL_ID, "CustomChannel", NotificationManager.IMPORTANCE_HIGH);
            channel.setDescription("Custom channel description");
            manager.createNotificationChannel(channel);
        }
    }
}