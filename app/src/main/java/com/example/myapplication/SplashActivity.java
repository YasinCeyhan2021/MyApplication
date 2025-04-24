package com.example.myapplication;

import android.animation.ObjectAnimator;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.ScaleAnimation;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class SplashActivity extends AppCompatActivity {
    TextView appName;
    ImageView splashLogo;
    String fullText;
    int index = 0;
    private Handler handler = new Handler();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_splash);

        fullText = getString(R.string.app_name);
        appName = (TextView) findViewById(R.id.appName);
        splashLogo = (ImageView) findViewById(R.id.splashLogo);


        // Sola kaydırma (X: 0'dan -1000'e), Y sabit
        TranslateAnimation moveLeft = new TranslateAnimation(0, -300, 0, 0);

        // Ölçeklendirme (X ve Y %100'den %50'ye)
        ScaleAnimation scaleDown = new ScaleAnimation(
                1.0f, 0.7f,  // X yönünde %100 → %50
                1.0f, 0.7f,  // Y yönünde %100 → %50
                Animation.RELATIVE_TO_SELF, 0.5f,  // X pivot: ortası
                Animation.RELATIVE_TO_SELF, 0.5f); // Y pivot: ortası

        // Ortak süre ve son konumda kalma
        moveLeft.setDuration(500);
        scaleDown.setDuration(500);
        moveLeft.setFillAfter(true);
        scaleDown.setFillAfter(true);

        // İkisini birleştirelim
        AnimationSet animationSet = new AnimationSet(true);
        animationSet.addAnimation(moveLeft);
        animationSet.addAnimation(scaleDown);
        animationSet.setFillAfter(true);

        // Başlatalım!
        splashLogo.startAnimation(animationSet);

        handler.postDelayed(() -> {
            appName.setVisibility(View.VISIBLE);
            appName.setText("");
            startTextAnimation();
        }, 500);

        // 3 saniye sonra MainActivity'e geçiş yap
        handler.postDelayed(() -> {
            Intent intent = new Intent(SplashActivity.this, MainActivity.class);
            startActivity(intent);
            finish();
        }, 2500);
    }

    private void startTextAnimation() {
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                if (index <= fullText.length()) {
                    appName.setText(fullText.substring(0, index));
                    index++;
                    handler.postDelayed(this, 50);
                }
            }
        }, 50);
    }
}