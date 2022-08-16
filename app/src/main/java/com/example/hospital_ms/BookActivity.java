package com.example.hospital_ms;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.net.HttpURLConnection;
import java.net.URL;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class BookActivity extends AppCompatActivity {

    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference,Reference1,re;
    Query ref;
    Button btnConfirm,btnCancel;
    FirebaseUser user;
    String userID,amt;
    TextView textViewPrice1;
    int pendFlag=0;
    String name;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book);
        firebaseDatabase = FirebaseDatabase.getInstance();
        btnConfirm=(Button)findViewById(R.id.btnConfirm);
        btnCancel=(Button)findViewById(R.id.btnCancel);
        textViewPrice1=(TextView)findViewById(R.id.textViewPrice1);

        firebaseDatabase = FirebaseDatabase.getInstance();
        Reference1 = firebaseDatabase.getReference().child("LpgPrice");

        Reference1.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                LpgPrice amount1 = snapshot.getValue(LpgPrice.class);
                if(amount1!=null) {
                     amt = amount1.price;

                    textViewPrice1.setText(amt);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        btnCancel.setOnClickListener(view -> {
            Intent intent=new Intent(BookActivity.this,MainActivity.class);
            startActivity(intent);
        });

        btnConfirm.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onClick(View view) {

                user= FirebaseAuth.getInstance().getCurrentUser();
                userID=user.getUid();

                //Reference1.child("price").setValue(apiAmount);
                Reference1.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {

                        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("uuuu/MM/dd HH:mm:ss");
                        LocalDateTime now = LocalDateTime.now();
                        String datetime=dtf.format(now);


                        databaseReference=FirebaseDatabase.getInstance().getReference().child("Orders");
                        ref=databaseReference.orderByChild("uid").equalTo(userID);
                        ref.addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot snapshot)
                            {
                                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                                    Orders order = dataSnapshot.getValue(Orders.class);
                                    if (order.getStatus().equals("Pending"))
                                    {
                                        pendFlag = 1;
                                        break;
                                    }
                                }
                                if (pendFlag == 0) {

                                    Orders order1 = new Orders(userID, datetime, amt, "Pending");
                                    databaseReference = FirebaseDatabase.getInstance().getReference().child("Orders");
                                    databaseReference.push().setValue(order1);
                                    pendFlag = 2;
                                    Toast.makeText(BookActivity.this, "Success\nBook-Status:Pending", Toast.LENGTH_SHORT).show();
                                    Intent intent=new Intent(BookActivity.this,Invoice.class);
                                    databaseReference = firebaseDatabase.getReference("Users");
                                    databaseReference.child(userID).addValueEventListener(new ValueEventListener() {
                                        @Override
                                        public void onDataChange(@NonNull DataSnapshot dsnapshot) {
                                            for(DataSnapshot snapshot : dsnapshot.getChildren()){
                                                if(snapshot.getKey().equals("name")){
                                                    name = snapshot.getValue(String.class);
                                                }
                                            }
                                            intent.putExtra("User",name);
                                            intent.putExtra("datetime",datetime);
                                            intent.putExtra("amt",amt);
                                            startActivity(intent);
//                                            User userProfile=snapshot.getValue(User.class);
//                                            if(userProfile!=null)
//                                            {
//                                                Toast.makeText(BookActivity.this, "kkkkk", Toast.LENGTH_SHORT).show();
//
//                                                name = userProfile.name.toString();
//                                            }
                                        }
                                        @Override
                                        public void onCancelled(@NonNull DatabaseError error) {
                                        }
                                    });

                                }
                                else {
                                        Toast.makeText(BookActivity.this, "Previous booking on progress", Toast.LENGTH_SHORT).show();
                                        finish();
                                }

                            }
                                @Override
                                public void onCancelled(@NonNull DatabaseError error) {
                                }
                            });
                    }
                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                    }
                });
            }
        });
    }
}