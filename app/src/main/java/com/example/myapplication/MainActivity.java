package com.example.myapplication;

import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {
    BottomNavigationView bottomNav;
    DrawerLayout drawerLayout;
    Toolbar toolbar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        // Başlangıç fragment
        loadFragment(new HomeFragment());
        bottomNav = (BottomNavigationView) findViewById(R.id.bottom_nav);
        // Toolbar bağlantısı
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // Drawer bağlantısı
        drawerLayout = findViewById(R.id.drawer_layout);

        // Menü toggle
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawerLayout, toolbar,
                R.string.navigation_drawer_open,
                R.string.navigation_drawer_close);

        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();





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