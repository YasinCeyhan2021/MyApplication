package com.example.myapplication;

import android.app.Application;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.os.Build;

public class App extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        // Uygulama başladığında kanal oluşturuluyor
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            String channelId = "kanal_id";
            CharSequence name = "İlaç Bildirimleri";
            String description = "İlaç hatırlatıcı bildirimleri";
            int importance = NotificationManager.IMPORTANCE_HIGH;

            NotificationChannel channel = new NotificationChannel(channelId, name, importance);
            channel.setDescription(description);

            // Kanalı sisteme kaydediyoruz
            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }
    }
}
