package com.example.student;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class generated_passout extends AppCompatActivity {

    private Button passout, done;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_generated_passout);
        passout=findViewById(R.id.view_pass_out);
        done=findViewById(R.id.done);

        passout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(generated_passout.this, View_passout.class);
                startActivity(intent);
            }
        });

        done.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent2=new Intent(generated_passout.this,PassOutActivity.class);
                startActivity(intent2);
            }
        });
    }
}