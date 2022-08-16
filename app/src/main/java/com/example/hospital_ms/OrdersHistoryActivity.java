package com.example.hospital_ms;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collections;

public class OrdersHistoryActivity extends AppCompatActivity {
    RecyclerView recyclerView;
    ArrayList<Orders> list;
    DatabaseReference databaseReference;
    Query ref;
    MyAdapter adapter;
    FirebaseUser user;
    String userID;
    ProgressBar progressid;
    Button btnHome;
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        startActivity(new Intent(OrdersHistoryActivity.this,MainActivity.class));
        finish();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_orders_history);

        recyclerView = findViewById(R.id.RecyclerViewOrders);
        progressid=(ProgressBar) findViewById(R.id.progressId);
        btnHome=(Button)findViewById(R.id.btnHome3);

        btnHome.setOnClickListener(view ->
        {
            Intent intent=new Intent(OrdersHistoryActivity.this,MainActivity.class);
            startActivity(intent);
        });

        databaseReference=FirebaseDatabase.getInstance().getReference().child("Orders");
        list=new ArrayList<>();
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter=new MyAdapter(this,list);
        recyclerView.setAdapter(adapter);

        user= FirebaseAuth.getInstance().getCurrentUser();
        userID=user.getUid();
        ref=databaseReference.orderByChild("uid").equalTo(userID);

        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                list.clear();
               for(DataSnapshot dataSnapshot:snapshot.getChildren())
               {
                   Orders order=dataSnapshot.getValue(Orders.class);
                   list.add(order);
               }
                Collections.reverse(list);
               if(progressid!=null) {
                   progressid.setVisibility(View.GONE);
               }
                        adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }
}