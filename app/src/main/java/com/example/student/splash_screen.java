package com.example.student;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;

import java.util.Timer;
import java.util.TimerTask;

public class splash_screen extends AppCompatActivity {
    Timer timer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);



        timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                openNewActivity();
                finish();
            }
        }, 5000 );
    }

    private void openNewActivity() {
       /* Intent intent = new Intent(splash_screen.this ,getstarted.class);
        startActivity(intent);*/
        FirebaseAuth auth = FirebaseAuth.getInstance();
        if (auth.getCurrentUser() != null) {
            // User is already logged in
            startActivity(new Intent(this, nav.class));
            finish();
        }
        else {
            Intent intent = new Intent(splash_screen.this,login.class);
            startActivity(intent);
            finish();
        }

    }
}