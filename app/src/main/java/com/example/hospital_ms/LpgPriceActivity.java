package com.example.hospital_ms;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class LpgPriceActivity extends AppCompatActivity {
    DatabaseReference Reference1;
    FirebaseDatabase firebaseDatabase;
    TextView textViewPrice;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lpg_price);
        textViewPrice=(TextView)findViewById(R.id.textViewPrice);


                firebaseDatabase = FirebaseDatabase.getInstance();
                Reference1 = firebaseDatabase.getReference().child("LpgPrice");
                Reference1.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        LpgPrice amount1 = snapshot.getValue(LpgPrice.class);
                        if(amount1!=null) {
                            String amt = amount1.price;

                            textViewPrice.setText(amt);
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });


    }
}