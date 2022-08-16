package com.example.hospital_ms;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

public class SafetyActivity extends AppCompatActivity {
Button btnHome2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_safety);
        btnHome2=(Button)findViewById(R.id.btnHome2);

        btnHome2.setOnClickListener(view -> {
            Intent intent=new Intent(SafetyActivity.this,MainActivity.class);
            startActivity(intent);
        });
    }
}