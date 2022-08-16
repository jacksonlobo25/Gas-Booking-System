package com.example.hospital_ms;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Scanner;

public class MainActivity extends AppCompatActivity {
Button btnMyAc,btnPrice,btnBook,btnPrevBook,btnSafety,btnComplaint;
FirebaseAuth fAuth;
int flag=0;
String exc;
    String apiAmount;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference,Reference1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btnMyAc=(Button)findViewById(R.id.btnMyAC);
        Button  btnLout=(Button)findViewById(R.id.btnLout);
        btnPrice=(Button)findViewById(R.id.btnPrice);
        btnBook=(Button)findViewById(R.id.btnBook);
        btnPrevBook=(Button)findViewById(R.id.btnPrevBook);
        btnSafety=(Button)findViewById(R.id.btnSafety);
        btnComplaint=(Button)findViewById(R.id.btnComplaint);
        fAuth=FirebaseAuth.getInstance();
        firebaseDatabase = FirebaseDatabase.getInstance();
        Reference1 = firebaseDatabase.getReference().child("LpgPrice");


           new Thread(new Runnable() {
               @Override
               public void run() {
                   try {
                       URL url = new URL("http://52.66.203.44:8080/priceMangaluru");
                       HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                       conn.setRequestMethod("GET");
                       conn.connect();
                       if (conn.getResponseCode() == 200) {
                           Scanner scan = new Scanner(url.openStream());
                           String temp = scan.nextLine();
                           apiAmount = temp;
                           if (apiAmount != null) {
                               Reference1.child("price").setValue(apiAmount);
                               flag = 1;
                           }
                           if (flag == 0)
                               Reference1.child("price").setValue("Rs1234");
                       }
                   } catch (Exception e) {
//                       Toast.makeText(MainActivity.this, e.toString(), Toast.LENGTH_SHORT).show();
                     exc=e.toString();
                   }
               }
           }).start();



        btnMyAc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(MainActivity.this,MyAccountActivity.class);
                startActivity(intent);
            }
        });
        btnLout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(MainActivity.this,LoginActivity.class);
                fAuth.signOut();
               startActivity(intent);

            }
        });
        btnPrice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //if(exc!=null) Toast.makeText(MainActivity.this, exc, Toast.LENGTH_SHORT).show();

                Intent intent=new Intent(MainActivity.this,LpgPriceActivity.class);
                startActivity(intent);
            }
        });
        btnBook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent=new Intent(MainActivity.this,BookActivity.class);
                startActivity(intent);
            }
        });
        btnPrevBook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(MainActivity.this,OrdersHistoryActivity.class);
                startActivity(intent);
            }
        });
        btnSafety.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(MainActivity.this,SafetyActivity.class);
                startActivity(intent);
            }
        });
        btnComplaint.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(MainActivity.this,FeedbackActivity.class);
                startActivity(intent);
            }
        });
    }

}