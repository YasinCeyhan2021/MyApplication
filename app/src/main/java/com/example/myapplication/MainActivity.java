package com.example.myapplication;

import android.content.Intent;
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
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class MainActivity extends AppCompatActivity {
    BottomNavigationView bottomNav;
    DrawerLayout drawerLayout;
    Toolbar toolbar;
    FloatingActionButton btnAddPill;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        // Başlangıç fragment
        loadFragment(new FragmentHome());
        bottomNav = (BottomNavigationView) findViewById(R.id.bottom_nav);
        // Toolbar bağlantısı
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        drawerLayout = findViewById(R.id.drawer_layout);
        btnAddPill = findViewById(R.id.btnAddPill);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);

        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        btnAddPill.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, AlarmKurActivity.class);
            startActivity(intent);
        });

        bottomNav.setOnItemSelectedListener(item -> {

            int itemId = item.getItemId();
            if (itemId == R.id.nav_home) {
                loadFragment(new FragmentHome());
            } else if (itemId == R.id.nav_more) {
                loadFragment(new FragmentHome());
            } else {
                return false;
            }
            return true;
        });
    }
    private void loadFragment(Fragment fragment) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.container, fragment);
        fragmentTransaction.commit();
    }
}