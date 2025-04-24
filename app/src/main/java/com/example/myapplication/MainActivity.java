package com.example.myapplication;

import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        BottomNavigationView bottomNav = findViewById(R.id.bottom_nav);

        // Başlangıç fragment
        loadFragment(new HomeFragment());
        
        /*
        drawerLayout = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.nav_view);

        // Menüyü dinlemek için NavigationView'e onItemSelectedListener ekleyelim
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                // Seçilen item'ı alıyoruz
                int id = menuItem.getItemId();
                switch (id) {
                    case R.id.nav_home:
                        // Anasayfa seçildiğinde yapılacak işlemler
                        showHomeFragment();
                        break;
                    case R.id.nav_settings:
                        // Ayarlar seçildiğinde yapılacak işlemler
                        Toast.makeText(MainActivity.this, "Ayarlar seçildi", Toast.LENGTH_SHORT).show();
                        break;
                    case R.id.nav_logout:
                        // Çıkış işlemi
                        Toast.makeText(MainActivity.this, "Çıkış yapılıyor", Toast.LENGTH_SHORT).show();
                        break;
                }

                // Çekmeceyi kapat
                drawerLayout.closeDrawers();
                return true;
            }
        });
         */





        // Menü tıklamaları dinle
        bottomNav.setOnItemSelectedListener(item -> {
            Fragment selectedFragment;

            int itemId = item.getItemId();
            if (itemId == R.id.nav_home) {
                selectedFragment = new HomeFragment();
            } else if (itemId == R.id.nav_ilac) {
                selectedFragment = new IlacFragment();
            } else if (itemId == R.id.nav_takip) {
                selectedFragment = new TakipFragment();
            } else {
                return false;
            }

            loadFragment(selectedFragment);
            return true;
        });
    }
    private void loadFragment(Fragment fragment) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.container, fragment);  // container id'sine dikkat!
        fragmentTransaction.commit();
    }
}