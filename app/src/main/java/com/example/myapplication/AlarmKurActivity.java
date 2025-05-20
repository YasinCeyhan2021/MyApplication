package com.example.myapplication;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.TimePicker;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.airbnb.lottie.LottieAnimationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import android.graphics.drawable.ColorDrawable;
import android.graphics.Color;

public class AlarmKurActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    AdapterIlac ilacAdapter;
    List<ModelIlac> ilacList = new ArrayList<>();
    EditText searchBox;
    ImageView btnClose, medicineIcon;
    String soru1 = "", txt1 = "", txt2 = "", selectedIlacIsmi = "";
    TextView textQuestion;
    TextWatcher searchWatcher;
    int durum = 0;
    ProgressBar progressBar;
    Ilac loader;
    FloatingActionButton btnAddTime;
    InputMethodManager imm;
    List<ModelZamanDoz> zamanDozList = new ArrayList<>();
    AdapterZamanDoz zamanDozAdapter;
    List<ModelBaslangicVeSure> baslangicVeSureList = new ArrayList<>();
    AdapterBaslangicVeSure baslangicVeSureAdapter;
    Button btnNext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_alarm_kur);

        imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);

        progressBar = findViewById(R.id.progressBar);
        btnAddTime = findViewById(R.id.btnAddTime);
        recyclerView = findViewById(R.id.medicineRecyclerView);
        searchBox = findViewById(R.id.searchBox);
        btnClose = findViewById(R.id.btnClose);
        medicineIcon = findViewById(R.id.medicineIcon);
        textQuestion = findViewById(R.id.textQuestion);
        btnNext = findViewById(R.id.btnNext);

        soru1 = "Hangi ilacı eklemeyi istiyorsunuz?";
        txt1 = "Zaman ve Doz";
        txt2 = "Tedavi Süresi";

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.addItemDecoration(new ItemDecoration());

        ilacAdapter = new AdapterIlac(ilacList, item -> {
            selectedIlacIsmi = item.getIsim();
            durum++;
            kontroller();
        });

        recyclerView.setAdapter(ilacAdapter);

        zamanDozAdapter = new AdapterZamanDoz(zamanDozList);

        baslangicVeSureList.add(new ModelBaslangicVeSure("Başlangıç Tarihi: " + getCurrentDate(), "Tedavi Süresi: ∞ gün", "Tedavi Sıklığı: Her gün"));
        baslangicVeSureAdapter = new AdapterBaslangicVeSure(baslangicVeSureList, item -> {
            showBaslangicVeSureDialog();
        });

        loader = new Ilac(this, ilacAdapter, ilacList);
        loader.ilacYukleme();

        searchWatcher = new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (loader != null) {
                    loader.filter(s.toString());
                }
            }

            @Override public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
            @Override public void afterTextChanged(Editable s) {}
        };

        searchBox.addTextChangedListener(searchWatcher);

        kontroller();

        btnClose.setOnClickListener(v -> {
            if (durum == 0) {
                finish();
                overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
            } else {
                durum--;
                kontroller();
            }
        });

        btnAddTime.setOnClickListener(v -> {
            showZamanVeDozDialog();
        });

        btnNext.setOnClickListener(v -> {
            if (durum == 1) {
                durum++;
                kontroller();
            } else {
                setAlarm();
            }
        });
    }

    public void kontroller() {
        if (durum == 0) {
            overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
            btnClose.setImageResource(R.drawable.ic_close);
            medicineIcon.setImageResource(R.drawable.ic_medicine);
            textQuestion.setText(soru1);

            searchBox.removeTextChangedListener(searchWatcher);
            searchBox.setText(selectedIlacIsmi);
            searchBox.addTextChangedListener(searchWatcher);

            searchBox.setVisibility(View.VISIBLE);
            btnAddTime.setVisibility(View.GONE);
            btnNext.setVisibility(View.GONE);

            recyclerView.setAdapter(ilacAdapter);

        } else if (durum == 1) {
            btnClose.setImageResource(R.drawable.ic_arrow_left);
            searchBox.setVisibility(View.GONE);
            btnAddTime.setVisibility(View.VISIBLE);
            btnNext.setVisibility(View.VISIBLE);
            btnNext.setText("SONRAKİ");
            checkNextButtonState();
            medicineIcon.setImageResource(R.drawable.ic_time);
            textQuestion.setText(txt1);
            recyclerView.setAdapter(zamanDozAdapter);
        } else if (durum == 2) {
            btnAddTime.setVisibility(View.GONE);
            btnNext.setVisibility(View.VISIBLE);
            btnNext.setText("KAYDET");
            checkNextButtonState();
            medicineIcon.setImageResource(R.drawable.ic_calendar);
            textQuestion.setText(txt2);
            recyclerView.setAdapter(baslangicVeSureAdapter);
        }
        progressBar.setProgress(durum + 1);
    }

    private void showZamanVeDozDialog() {
        Dialog dialog = new Dialog(this);
        dialog.setContentView(R.layout.dialog_zaman_doz);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.setCancelable(true);

        TimePicker timePicker = dialog.findViewById(R.id.timePicker);
        EditText editTextDoz = dialog.findViewById(R.id.editTextDoz);
        Button btnAyarla = dialog.findViewById(R.id.btnAyarla);
        ImageView ivClose = dialog.findViewById(R.id.ivClose);

        timePicker.setIs24HourView(true);

        ivClose.setOnClickListener(v -> dialog.dismiss());

        btnAyarla.setOnClickListener(v -> {
            int hour = timePicker.getHour();
            int minute = timePicker.getMinute();
            String doz = editTextDoz.getText().toString().trim();

            if (doz.isEmpty()) {
                editTextDoz.setError("Doz girin");
                return;
            }

            String zaman = String.format(Locale.getDefault(), "%02d:%02d", hour, minute);
            zamanDozList.add(new ModelZamanDoz(zaman, doz + " Doz"));
            zamanDozAdapter.notifyItemInserted(zamanDozList.size() - 1);
            checkNextButtonState();
            dialog.dismiss();
        });

        dialog.show();
    }

    private void showAlarmKurulduDialog() {
        Dialog dialog = new Dialog(this);
        dialog.setContentView(R.layout.dialog_alarm_kuruldu);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.setCancelable(false);

        LottieAnimationView lottie = dialog.findViewById(R.id.lottieAnimationView);
        if (lottie != null) {
            lottie.setAnimation("success_check.json");
            lottie.playAnimation();
        }

        new Handler().postDelayed(() -> {
            dialog.dismiss();
            startActivity(new Intent(this, MainActivity.class));
            finish();
        }, 3000);

        dialog.show();
    }

    private void showBaslangicVeSureDialog() {
        Dialog dialog = new Dialog(this);
        dialog.setContentView(R.layout.dialog_baslangic_ve_sure);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.setCancelable(true);

        DatePicker datePicker = dialog.findViewById(R.id.datePickerBaslangic);
        EditText editTextSure = dialog.findViewById(R.id.editTextSureBaslangic);
        EditText editTextFrequency = dialog.findViewById(R.id.editTextFrequencyBaslangic);
        Button btnAyarla = dialog.findViewById(R.id.btnAyarBaslangic);
        ImageView ivClose = dialog.findViewById(R.id.ivCloseBaslangicVeSure);

        ivClose.setOnClickListener(v -> dialog.dismiss());

        btnAyarla.setOnClickListener(v -> {
            int year = datePicker.getYear();
            int month = datePicker.getMonth();
            int day = datePicker.getDayOfMonth();
            String sure = editTextSure.getText().toString().trim();
            String frequency = editTextFrequency.getText().toString().trim();

            if (sure.equals("0") || frequency.equals("0")) {
                editTextSure.setError("Geçersiz değer");
                return;
            }

            String tarih = String.format(Locale.getDefault(), "%02d.%02d.%04d", day, month + 1, year);

            if (!baslangicVeSureList.isEmpty()) {
                baslangicVeSureList.set(0, new ModelBaslangicVeSure(
                        "Başlangıç Tarihi: " + tarih,
                        "Tedavi Süresi: " + (sure.isEmpty() ? "∞ gün" : sure + " gün"),
                        "Tedavi Sıklığı: " + (frequency.isEmpty() || frequency.equals("1") ? "Her gün" : frequency + " günde bir defa")
                ));
                baslangicVeSureAdapter.notifyItemChanged(0);
            }

            dialog.dismiss();
        });

        dialog.show();
    }
    private void setAlarm() {
        AlarmKur alarm = new AlarmKur();
        Boolean durum =  alarm.alarmKur(
                AlarmKurActivity.this,
                selectedIlacIsmi,
                zamanDozList,
                baslangicVeSureList.get(0).getBaslangicTarihi(),
                baslangicVeSureList.get(0).getSure(),
                baslangicVeSureList.get(0).getFrequency()
        );
        if(durum)
            showAlarmKurulduDialog();
    }
    public void checkNextButtonState() {
        boolean aktif = !zamanDozList.isEmpty();
        btnNext.setEnabled(aktif);
        btnNext.setAlpha(aktif ? 1.0f : 0.25f);
    }

    private String getCurrentDate() {
        SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy", Locale.getDefault());
        return sdf.format(System.currentTimeMillis());
    }

}
