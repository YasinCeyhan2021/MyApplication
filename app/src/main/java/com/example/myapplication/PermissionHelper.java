package com.example.myapplication;

import android.Manifest;
import android.app.Activity;
import android.content.pm.PackageManager;
import android.os.Build;

import androidx.appcompat.app.AlertDialog;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

public class PermissionHelper {

    public static final int BILDIRIM_IZIN_KODU = 1001;

    private Activity activity;

    public PermissionHelper(Activity activity) {
        this.activity = activity;
    }

    // İzin kontrolü ve gerekirse isteme
    public boolean checkAndRequestNotificationPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            if (ContextCompat.checkSelfPermission(activity, Manifest.permission.POST_NOTIFICATIONS)
                    != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(activity,
                        new String[]{Manifest.permission.POST_NOTIFICATIONS}, BILDIRIM_IZIN_KODU);
                return false; // İzin henüz verilmedi, isteniyor
            } else {
                return true; // İzin zaten var
            }
        } else {
            return true; // Android 13 öncesi izin gerekli değil
        }
    }

    // İzin sonucu kontrolü
    public boolean handlePermissionsResult(int requestCode, int[] grantResults) {
        if (requestCode == BILDIRIM_IZIN_KODU) {
            return grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED;
        }
        return false;
    }

    // İzin reddedilirse kullanıcıya nedenini gösterme
    public void showPermissionRationale() {
        new AlertDialog.Builder(activity)
                .setTitle("Bildirim İzni")
                .setMessage("Bu uygulama, bildirim gönderebilmek için izniniz gerekmektedir. Lütfen izni verin.")
                .setPositiveButton("İzin Ver", (dialog, which) -> {
                    ActivityCompat.requestPermissions(activity,
                            new String[]{Manifest.permission.POST_NOTIFICATIONS}, BILDIRIM_IZIN_KODU);
                })
                .setCancelable(false)
                .show();
    }
}
