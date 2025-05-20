package com.example.myapplication;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.util.Log;
import android.Manifest;

import androidx.core.app.ActivityCompat;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import com.google.firebase.firestore.FirebaseFirestore;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Alarm {

    // Alarm kurma, kaydetme ve bildirim gönderme işlemleri
    public static void kurVeKaydetVeBildirimGonder(Context context, String isim, String baslangic, List<String> saatler, int sure, int siklik) {
        Log.d("Log.err", "kurVeKaydetVeBildirimGonder fonksiyonu başlatıldı");

        // Parametre kontrolü
        Log.d("Log.err", "İsim: " + isim);
        Log.d("Log.err", "Başlangıç: " + baslangic);
        Log.d("Log.err", "Saatler: " + saatler.toString());
        Log.d("Log.err", "Süre: " + sure);
        Log.d("Log.err", "Sıklık: " + siklik);

        // Başka kodlar
        Log.d("Log.err", "0-0");

        FirebaseFirestore db = FirebaseFirestore.getInstance();
        Map<String, Object> ilac = new HashMap<>();
        ilac.put("isim", isim);
        ilac.put("baslangic", baslangic);
        ilac.put("saatler", saatler);
        ilac.put("sure", sure);
        ilac.put("siklik", siklik);

        Log.d("Log.err", "0-1");
        // Firebase verilerini kaydet
        db.collection("alarmlar").add(ilac)
                .addOnSuccessListener(ref -> Log.d("FIREBASE", "Firebase'e kaydedildi"))
                .addOnFailureListener(e -> Log.e("FIREBASE", "Firebase'e kayıt HATA", e));

        Log.d("Log.err", "0-2");

        // Tarihi SimpleDateFormat ile parse et
        Date baslangicTarihi = parseDate(baslangic);

        if (baslangicTarihi == null) {
            Log.e("Tarih Hatası", "Başlangıç tarihi geçerli değil.");
            return;  // Eğer tarih geçerli değilse işlemi durdur
        }

        int idSayac = 1000;

        for (int gun = 0; gun < sure; gun += siklik) {
            Date hedefTarih = new Date(baslangicTarihi.getTime() + gun * 24L * 60 * 60 * 1000); // Gün ekleme işlemi
            for (String saatStr : saatler) {
                try {
                    // Saat bilgisi ile Date objesi oluştur
                    SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm");
                    Date saat = timeFormat.parse(saatStr);

                    // Alarmı kur
                    long millis = hedefTarih.getTime() + saat.getTime() % (24L * 60 * 60 * 1000);  // Günün saatini ekle
                    kurAlarm(context, millis, idSayac++, isim);

                    // Alarm saati geldiğinde bildirim gönder
                    bildirimGonder(context, isim);
                } catch (ParseException e) {
                    Log.e("Saat Hatası", "Saat formatı hatalı: " + saatStr);
                }
            }
        }
    }

    // Tarihi SimpleDateFormat ile parse et
    private static Date parseDate(String dateStr) {
        SimpleDateFormat format = new SimpleDateFormat("dd.MM.yyyy"); // Tarih formatınızı buraya ekleyin
        try {
            return format.parse(dateStr);
        } catch (ParseException e) {
            e.printStackTrace();
            return null; // Hata durumunda null dönebilir
        }
    }

    // Alarm kurma işlemi
    private static void kurAlarm(Context context, long zaman, int requestCode, String ilacAdi) {
        Intent intent = new Intent(context, AlarmReceiver.class);
        intent.putExtra("ilacAdi", ilacAdi);

        PendingIntent pendingIntent = PendingIntent.getBroadcast(
                context, requestCode, intent,
                PendingIntent.FLAG_UPDATE_CURRENT | PendingIntent.FLAG_IMMUTABLE
        );

        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            alarmManager.setExactAndAllowWhileIdle(AlarmManager.RTC_WAKEUP, zaman, pendingIntent);
        } else {
            alarmManager.setExact(AlarmManager.RTC_WAKEUP, zaman, pendingIntent);
        }
    }

    // Bildirim gönderme fonksiyonu
    public static void bildirimGonder(Context context, String ilacAdi) {
        try {
            NotificationCompat.Builder builder = new NotificationCompat.Builder(context, "kanal_id")
                    .setSmallIcon(R.drawable.ic_pill)
                    .setContentTitle("İlaç Saati")
                    .setContentText(ilacAdi + " ilacını alma zamanı!")
                    .setPriority(NotificationCompat.PRIORITY_HIGH)
                    .setAutoCancel(true);

            if (ActivityCompat.checkSelfPermission(context, Manifest.permission.POST_NOTIFICATIONS)
                    == PackageManager.PERMISSION_GRANTED) {
                NotificationManagerCompat notificationManager = NotificationManagerCompat.from(context);
                int notificationId = (int) System.currentTimeMillis();
                notificationManager.notify(notificationId, builder.build());
            } else {
                Log.e("Bildirim", "Bildirim izni yok.");
            }
        } catch (SecurityException e) {
            Log.e("Bildirim", "İzin hatası oluştu: " + e.getMessage());
        }
    }

}
