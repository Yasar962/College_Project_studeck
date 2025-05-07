package com.yasar.collegeproject;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.Firebase;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class loginActivity extends AppCompatActivity {
    TextInputEditText   edit_email,edit_password;
    Button button_login;
    FirebaseAuth mAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_login);
        edit_email = findViewById(R.id.email);
        edit_password = findViewById(R.id.password);
        button_login = findViewById(R.id.login);

        mAuth=FirebaseAuth.getInstance();

        button_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
             String email_s,password_s;
             email_s=String.valueOf(edit_email.getText());
             password_s=String.valueOf(edit_password.getText());

             if(TextUtils.isEmpty(email_s)){
                 Toast.makeText(loginActivity.this,"Enter E-Mail",Toast.LENGTH_SHORT).show();
                 return;
             }
             else if(TextUtils.isEmpty(password_s)){
                Toast.makeText(loginActivity.this,"Enter Password",Toast.LENGTH_SHORT).show();
                return;
             }
                mAuth.signInWithEmailAndPassword(email_s, password_s)
                        .addOnCompleteListener( new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {
                                    // Sign in success, update UI with the signed-in user's information
                                    FirebaseUser user = mAuth.getCurrentUser();
                                    Intent intent=new Intent(loginActivity.this, MainActivity.class);
                                    startActivity(intent);
                                    finish();

                                } else {
                                    // If sign in fails, display a message to the user.
                                    Toast.makeText(loginActivity.this, "Authentication failed.",
                                            Toast.LENGTH_SHORT).show();

                                }
                            }
                        });

            }
        });
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }
}