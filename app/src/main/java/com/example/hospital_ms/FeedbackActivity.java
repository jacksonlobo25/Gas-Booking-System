package com.example.hospital_ms;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class FeedbackActivity extends AppCompatActivity {
    EditText editTextNumber,editTextFeedback;
    Button btnSubmit;
    Button Home;
    FirebaseUser user;
    Query ref;
    String userID;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feedback);

        editTextNumber=(EditText)findViewById(R.id.editTextNumber);
        editTextFeedback=(EditText)findViewById(R.id.editTextFeedback);
        btnSubmit=(Button) findViewById(R.id.btnSubmit);
        Home=(Button)findViewById(R.id.btnHome11);
//btnSubmit.setOnClickListener(new View.OnClickListener() {
//    @Override
//    public void onClick(View view) {
//        FirebaseDatabase firebaseDatabase=FirebaseDatabase.getInstance();
//        DatabaseReference databaseReference= firebaseDatabase.getReference().child("Feedback");
//Feedbacks fb=new Feedbacks("hi1","hi2","hi8","hello");
//    databaseReference.setValue(fb);
//
//    }
//});

        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();

        Home.setOnClickListener(view -> {
            Intent intent=new Intent(FeedbackActivity.this,MainActivity.class);
            startActivity(intent);
        });

        btnSubmit.setOnClickListener(new View.OnClickListener() {

            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onClick(View view) {
                user = FirebaseAuth.getInstance().getCurrentUser();
                userID = user.getUid();
                DatabaseReference databaseReference = firebaseDatabase.getReference("Feedbacks");

                        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("uuuu/MM/dd HH:mm:ss");
                        LocalDateTime now = LocalDateTime.now();
                        String datetime = dtf.format(now);
                        String Rating = editTextNumber.getText().toString();
                        String feedback = editTextFeedback.getText().toString().trim();
if(Rating.length()>0 && feedback.length()>0) {
    Feedbacks fb = new Feedbacks(userID, datetime, Rating, feedback);
    databaseReference.push().setValue(fb);

    Toast.makeText(FeedbackActivity.this, "Thank you for your valuable feedback", Toast.LENGTH_SHORT).show();
    Intent intent=new Intent(FeedbackActivity.this,MainActivity.class);
    startActivity(intent);
}
else
{
    Toast.makeText(FeedbackActivity.this, "Please fill Rating and Feedback", Toast.LENGTH_SHORT).show();
}

            }
        });



    }
}