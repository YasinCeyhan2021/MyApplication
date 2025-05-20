package com.example.myapplication;

import android.Manifest;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.os.Build;
import android.util.Log;
import android.os.Bundle;
import android.widget.Toast;
import android.content.Intent;
import android.os.Handler;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.ScaleAnimation;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import com.google.firebase.auth.FirebaseUser;

public class SplashActivity extends AppCompatActivity {

    TextView appName;
    ImageView splashLogo;
    String fullText;
    int index = 0, logoAnimMiliSn = 500;
    private Handler handler = new Handler();
    private User user;

    private PermissionHelper permissionHelper;
    private static final int PERMISSION_REQUEST_CODE = 1001; // İzin istek kodu

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        permissionHelper = new PermissionHelper(this);
        if (permissionHelper.checkAndRequestNotificationPermission()) {
            onPermissionGranted();
        }
    }


    // İzin verildiğinde yapılacak işlem
    private void onPermissionGranted() {
        userKontrol();

        fullText = getString(R.string.app_name);
        appName = findViewById(R.id.appName);
        splashLogo = findViewById(R.id.splashLogo);

        startLogoAnimation();
    }

    // Firebase kullanıcı kontrolü
    private void userKontrol() {
        user = new User();

        user.checkOrCreateUser(task -> {
            if (task.isSuccessful()) {
                anaEkranaGec();
            } else {
                Log.e("Auth", "Kullanıcı giriş hatası", task.getException());
            }
        });
    }


    // Logo animasyonu
    private void startLogoAnimation(){
        TranslateAnimation moveLeft = new TranslateAnimation(0, -300, 0, 0);
        ScaleAnimation scaleDown = new ScaleAnimation(1.0f, 0.7f, 1.0f, 0.7f, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);

        moveLeft.setDuration(logoAnimMiliSn);
        scaleDown.setDuration(logoAnimMiliSn);
        moveLeft.setFillAfter(true);
        scaleDown.setFillAfter(true);

        AnimationSet animationSet = new AnimationSet(true);
        animationSet.addAnimation(moveLeft);
        animationSet.addAnimation(scaleDown);
        animationSet.setFillAfter(true);

        splashLogo.startAnimation(animationSet);

        handler.postDelayed(() -> {
            appName.setVisibility(View.VISIBLE);
            appName.setText("");
            startTextAnimation();
        }, logoAnimMiliSn);
    }

    // Yazı animasyonu
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

    // Ana ekrana geçiş
    private void anaEkranaGec(){
        handler.postDelayed(() -> {
            Intent intent = new Intent(SplashActivity.this, MainActivity.class);
            startActivity(intent);
            finish();
        }, 1600);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (permissionHelper.handlePermissionsResult(requestCode, grantResults)) {
            onPermissionGranted();
        } else {
            permissionHelper.showPermissionRationale();
        }
    }


}
