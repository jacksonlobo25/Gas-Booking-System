package com.example.hospital_ms;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;

public class LoginActivity extends AppCompatActivity {
Button btnLogin;
EditText Email,passwd;
TextView register;
FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        btnLogin = (Button)findViewById(R.id.btnLogin);
        Email= (EditText)findViewById(R.id.editTxtEmail);
        passwd= (EditText)findViewById(R.id.editTextPasswd);
        register= (TextView)findViewById(R.id.textViewRegister);

        mAuth=FirebaseAuth.getInstance();

        btnLogin.setOnClickListener(v -> {
                    String email = Email.getText().toString().trim();
                    String Passwrd = passwd.getText().toString().trim();
                    if (email.isEmpty()) {
                        Email.setError("Email is empty");
                        Email.requestFocus();
                        return;
                    }
                    if(Passwrd.isEmpty())
                    {
                        passwd.setError("Password is empty");
                        passwd.requestFocus();
                        return;
                    }
            mAuth.signInWithEmailAndPassword(email,Passwrd).addOnCompleteListener(task -> {
                if(task.isSuccessful())
                {
                    startActivity(new Intent(LoginActivity.this, MainActivity.class));
                }
                else
                {
                    Toast.makeText(LoginActivity.this,
                            "Please Check Your login Credentials",
                            Toast.LENGTH_SHORT).show();
                }

            });
                });
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(LoginActivity.this,RegisterActivity.class);
                startActivity(intent);
            }
        });

    }
}