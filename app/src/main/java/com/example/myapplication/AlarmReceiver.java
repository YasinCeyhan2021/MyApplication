package com.example.myapplication;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.util.Log;
import android.Manifest;


import androidx.core.app.ActivityCompat;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.core.content.ContextCompat;

public class AlarmReceiver extends BroadcastReceiver {

    private static final String KANAL_ID = "alarm_kanali_id";

    @Override
    public void onReceive(Context context, Intent intent) {

        // Bildirim Kanalı oluştur (Android 8.0+ için zorunlu)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence ad = "Alarm Kanalı";
            String aciklama = "İlaç hatırlatma bildirimleri için kullanılır.";
            int oncelik = NotificationManager.IMPORTANCE_HIGH;

            NotificationChannel kanal = new NotificationChannel(KANAL_ID, ad, oncelik);
            kanal.setDescription(aciklama);

            NotificationManager bildirimYoneticisi = context.getSystemService(NotificationManager.class);
            if (bildirimYoneticisi != null) {
                bildirimYoneticisi.createNotificationChannel(kanal);
            }
        }

        // Bildirimi oluştur
        NotificationCompat.Builder builder = new NotificationCompat.Builder(context, KANAL_ID)
                .setSmallIcon(R.drawable.ic_launcher_foreground) // kendi ikonunu kullan
                .setContentTitle("İlaç Zamanı")
                .setContentText("Belirlediğin ilaç saatine ulaşıldı!")
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setAutoCancel(true);

        // Bildirimi göster
        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(context);
        if (ContextCompat.checkSelfPermission(context, Manifest.permission.POST_NOTIFICATIONS) == PackageManager.PERMISSION_GRANTED) {
            notificationManager.notify(1001, builder.build());
        }

        // Alarmı tekrar kurmak istiyorsan:
        //AlarmKurActivity alarmKur = new AlarmKurActivity();
        //alarmKur.tekrarlayanAlarmKur(context);
    }
}
