package com.example.pawonresto;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.fragment.app.Fragment;

import android.app.Notification;
import android.app.PendingIntent;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;

import com.example.pawonresto.ui.home.HomeFragment;
import com.example.pawonresto.ui.maps.Maps;
import com.example.pawonresto.ui.menu.MenuFragment;
import com.example.pawonresto.ui.penawaran.PenawaranFragment;
import com.example.pawonresto.ui.penawaran.ViewPenawaran;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        String name = getIntent().getStringExtra("name");
        loadFragment(new HomeFragment());

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);
        bottomNavigationView.setOnNavigationItemSelectedListener(this);
    }

    private boolean loadFragment(Fragment fragment){
        if (fragment != null) {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.fl_container, fragment)
                    .commit();
            return true;
        }
        return false;
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        Fragment fragment = null;
        switch (menuItem.getItemId()){
            case R.id.home:
                fragment = new HomeFragment();
                return loadFragment(fragment);
            case R.id.menu:
                fragment = new MenuFragment();
                return loadFragment(fragment);
            case R.id.notification:
                fragment = new ViewPenawaran();
//                fragment = new PenawaranFragment();
                return loadFragment(fragment);
            case R.id.location:
                Intent map = new Intent(MainActivity.this,Maps.class);
                startActivity(map);
                break;
        }
        return true;
    }
}