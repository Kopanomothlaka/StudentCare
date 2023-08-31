package com.example.student;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class StudentCardActivity extends AppCompatActivity {
    private ImageView imageView;
    private TextView userNameSt, userStudentNumber,etcourse;
    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_card);
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Loading data...");
        progressDialog.setCanceledOnTouchOutside(false);

        userNameSt=findViewById(R.id.nameView);
        userStudentNumber=findViewById(R.id.studentNumberView);
        etcourse=findViewById(R.id.idcourse);

        imageView=findViewById(R.id.back_home_2);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(StudentCardActivity.this, nav.class);
                startActivity(intent);
                finish();
            }
        });

        FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
        if (currentUser != null) {
            String userId = currentUser.getUid();

            loadUserData(userId);
        }


    }

    private void loadUserData(String userId) {
        progressDialog.show();

        FirebaseFirestore db = FirebaseFirestore.getInstance();

        db.collection("users").document(userId)
                .get()
                .addOnCompleteListener(task -> {

                    progressDialog.dismiss();

                    if (task.isSuccessful()) {
                        DocumentSnapshot document = task.getResult();
                        if (document.exists()) {
                            // Retrieve user data
                            String studentnumber = document.getString("studentNumber");
                            String userName = document.getString("name");
                            String course = document.getString("course");
                            // Debugging: Print retrieved user data
                            Log.d("UserData", "User Name: " + userName);

                            // Populate TextView with user data
                            userNameSt.setText(userName);
                            userStudentNumber.setText(studentnumber);
                            etcourse.setText(course);
                        } else {
                            Log.d("UserData", "User document does not exist");

                            // Handle case where user document doesn't exist
                        }
                    } else {
                        // Handle exception
                    }
                });
    }
}