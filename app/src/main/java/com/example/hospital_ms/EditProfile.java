package com.example.hospital_ms;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class EditProfile extends AppCompatActivity {
    EditText editTxtName,editTxtPhone,editTextPassword,editTextAddress;
    Button btnUpdate,buttonCncl;
    FirebaseAuth mAuth;
    FirebaseUser user;
    DatabaseReference databaseReference;
    String userID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);

        editTxtName=(EditText)findViewById(R.id.editTName);
        editTxtPhone=(EditText)findViewById(R.id.editTphone);
        editTextPassword=(EditText)findViewById(R.id.editTpassword);
        editTextAddress=(EditText)findViewById(R.id.editTaddress);

        btnUpdate=(Button)findViewById(R.id.btnUpdate);
        buttonCncl=(Button)findViewById(R.id.buttonCncl);

        Intent data=getIntent();
        String name=data.getStringExtra("name");
        String email=data.getStringExtra("email");
        String phone=data.getStringExtra("phone");
        String address=data.getStringExtra("address");
        String password=data.getStringExtra("password");

        editTxtName.setText(name);
        editTxtPhone.setText(phone);
        editTextAddress.setText(address);
        editTextPassword.setText(password);

        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String Newname = editTxtName.getText().toString().trim();
                String Newphone= editTxtPhone.getText().toString().trim();
                String Newpasswd = editTextPassword.getText().toString().trim();
                String Newaddress = editTextAddress.getText().toString().trim();


                FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
                user= FirebaseAuth.getInstance().getCurrentUser();
                databaseReference = firebaseDatabase.getReference("Users");
                userID=user.getUid();

                databaseReference.child(userID+"/name").setValue(Newname);
                databaseReference.child(userID+"/phone").setValue(Newphone);
                databaseReference.child(userID+"/address").setValue(Newaddress);


if(Newpasswd!=password)
                user.updatePassword(Newpasswd)
                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if (task.isSuccessful()) {
                                    databaseReference.child(userID+"/password").setValue(Newpasswd);
                                }
                            }
                        });
//if(Newemail!=email)
//                user.updateEmail(Newemail)
//                        .addOnCompleteListener(new OnCompleteListener<Void>() {
//                            @Override
//                            public void onComplete(@NonNull Task<Void> task) {
//                                if (task.isSuccessful()) {
//
//                                    databaseReference.child(userID+"/email").setValue(Newemail);
//
//                                }
//                            }
//                        });
                Intent intent=new Intent(EditProfile.this,MyAccountActivity.class);
                startActivity(intent);
            }
        });

        buttonCncl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(EditProfile.this,MyAccountActivity.class);
                startActivity(intent);
            }
        });
    }
}