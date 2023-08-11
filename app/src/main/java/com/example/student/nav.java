package com.example.student;

import static com.example.student.R.id.profile;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuItem;

import android.view.View;

import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class nav extends AppCompatActivity {

    private DrawerLayout drawerLayout;
    private CardView passOutBtn, StudentCardBtn , MyStuffBtn,StolenGoodsBtn;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nav);
        passOutBtn = findViewById(R.id.openpassactivityId);
        StudentCardBtn = findViewById(R.id.openStudentCardctivityId);
        MyStuffBtn = findViewById(R.id.openMuStuffctivityId);
        StolenGoodsBtn = findViewById(R.id.openGoodsctivityId);


        passOutBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent1 = new Intent(nav.this , PassOutActivity.class);
                startActivity(intent1);
            }
        });
        StudentCardBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent2 = new Intent(nav.this , StudentCardActivity.class);
                startActivity(intent2);
            }
        });
        MyStuffBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent3 = new Intent(nav.this , MyStuffActivity.class);
                startActivity(intent3);
            }
        });
        StolenGoodsBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent4 = new Intent(nav.this , StolenGoodsActivity.class);
                startActivity(intent4);
            }
        });

        drawerLayout = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);


        // Set up the NavigationView
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                // Handle navigation item clicks here
                int itemId = item.getItemId();

                if (itemId == R.id.profile) {
                    profile();
                } else if (itemId == R.id.logOUt) {
                    logout();
                }

                return true;
            }
        });


       //setting up the drawer
        Toolbar toolbar = findViewById(R.id.toolbar);
        drawerLayout = findViewById(R.id.drawer_layout);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("");
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawerLayout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        toolbar.setNavigationIcon(R.drawable.menubar);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drawerLayout.openDrawer(GravityCompat.START);
            }
        });

    }

    private void profile() {
        Intent intent = new Intent(nav.this,ProfileActivity.class);
        startActivity(intent);
    }

    private void logout() {

        SharedPreferences preferences = getSharedPreferences("myPrefs", MODE_PRIVATE);
        preferences.edit().clear().apply();
        Intent intent = new Intent(nav.this ,login.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
    }
}