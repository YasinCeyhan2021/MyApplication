package com.example.myapplication;

import android.content.Context;
import android.util.Log;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

public class AlarmKur {
    private String ilacIsmı;
    private List<ModelZamanDoz> saatDozList;
    List<String> saatList = new ArrayList<String>();
    List<String> dozList = new ArrayList<String>();
    private String bTarihi;
    private String tSure;
    private String tFrequency;
    public Boolean alarmKur(Context context, String ilacIsmi, List<ModelZamanDoz> saatDozList, String bTarihi, String tSure, String tFrequency){
        this.ilacIsmı = ilacIsmi;
        this.saatDozList = saatDozList;
        this.bTarihi = bTarihi;
        this.tSure = tSure;
        this.tFrequency = tFrequency;
        kontrol();
        kaydet();
        return false;
    }
    private void kontrol(){
        try {
            this.bTarihi = this.bTarihi.replaceAll("[^0-9.]", "");
            this.tSure = this.tSure.replaceAll("[^0-9]", "");
            this.tFrequency  = this.tFrequency.replaceAll("[^0-9]", "");

            if(this.tSure.equals(""))
                this.tSure = "30";
            if(this.tFrequency.equals(""))
                this.tFrequency = "1";
            
            Log.d("Log.d","Başlangıç Tarihi: " + this.bTarihi);
            Log.d("Log.d","Tedavi Suresi: " + this.tSure);
            Log.d("Log.d","Tedavi Sıklığı: " + this.tFrequency);
            
        } catch (Exception e) {
            Log.e("Log.d", "HATA: ", e);
        }

    }

    private void kaydet(){
        try {
            FirebaseFirestore db = FirebaseFirestore.getInstance();
            int tSure =  Integer.parseInt(this.tSure);
            int tFrequency =  Integer.parseInt(this.tFrequency);


            Map<String, Object> alarm = new HashMap<>();
            alarm.put("userId", FirebaseAuth.getInstance().getCurrentUser().getUid());
            alarm.put("ilacIsmı", this.ilacIsmı);
            alarm.put("bTarihi", this.bTarihi);
            alarm.put("tSuresi", this.tSure);
            alarm.put("tFrequency", this.tFrequency);
            alarm.put("saatDozList", this.saatDozList);




            db.collection("alarms")
                    .add(alarm)
                    .addOnSuccessListener(documentReference -> {
                        String alarmId = documentReference.getId();
                        if(tSure >= 30)
                            createDailyReminders(db, alarmId, 30);
                        else
                            createDailyReminders(db, alarmId,  tSure);
                    })
                    .addOnFailureListener(e -> Log.d("Log.d", "Firebase'e kayıt HATA", e));

        } catch (Exception e) {
            Log.e("Log.d", "Firebase'e kayıt HATA: ", e);
        }
    }
    public void createDailyReminders(FirebaseFirestore db, String medicineId, int days) {
        SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy", Locale.getDefault());

        Date startDate;
        try {
            startDate = sdf.parse(this.bTarihi);
        } catch (ParseException e) {
            e.printStackTrace();
            return;
        }

        Calendar calendar = Calendar.getInstance();
        calendar.setTime(startDate);

        CollectionReference remindersRef = db.collection("alarms")
                .document(medicineId)
                .collection("reminders");

        for (int i = 0; i < days; i++) {
            String date = sdf.format(calendar.getTime());
            for (ModelZamanDoz item : saatDozList) {
                Map<String, Object> reminder = new HashMap<>();
                reminder.put("tarih", date);
                reminder.put("saat", item.getZaman());
                reminder.put("doz", item.getDoz());
                reminder.put("taken", false);

                remindersRef.add(reminder)
                        .addOnSuccessListener(documentReference -> {
                            System.out.println("Hatırlatma eklendi: " + date);
                        })
                        .addOnFailureListener(e -> {
                            System.err.println("Hatırlatma eklenirken hata: " + e.getMessage());
                        });
            }


            calendar.add(Calendar.DAY_OF_YEAR, 1);
        }
    }


}
