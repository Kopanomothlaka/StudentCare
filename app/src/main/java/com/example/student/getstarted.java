package com.example.student;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class getstarted extends AppCompatActivity {

    private Button getstarted;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_getstarted);

        getstarted = (Button) findViewById(R.id.startbntId);
        getstarted.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intentOpen = new Intent(getstarted.this , login.class);
                startActivity(intentOpen);
                finish();
            }
        });
    }
}