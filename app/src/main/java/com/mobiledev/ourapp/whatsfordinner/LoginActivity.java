package com.mobiledev.ourapp.whatsfordinner;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class LoginActivity extends AppCompatActivity {

    User user;
    EditText username;
    EditText password;
    EditText newUsername;
    EditText newPassword;
    TextView login;

    public void onLogin(View view){


        String userIn = username.getText().toString();
        String passIn = password.getText().toString();

        user = User.getInstance(userIn, passIn, getApplicationContext());
        long result = user.getUser(userIn, passIn);

        if(result != -1){
            Toast.makeText(getApplicationContext(), "WE CAN VIBE", Toast.LENGTH_LONG).show();
            startActivity(new Intent(LoginActivity.this, MainActivity.class));
        }else {
            Toast.makeText(getApplicationContext(), "LOGIN ERROR", Toast.LENGTH_LONG).show();
        }
    }

    public void onCreateAccount(View view){

        String userIn = newUsername.getText().toString();
        String passIn = newPassword.getText().toString();

        user = User.getInstance(userIn, passIn, getApplicationContext());
        long result = user.createUser();

        if(result != -1){
            Toast.makeText(getApplicationContext(), "WE CAN VIBE", Toast.LENGTH_LONG).show();
            startActivity(new Intent(LoginActivity.this, MainActivity.class));
        }else {
            Toast.makeText(getApplicationContext(), "LOGIN ERROR", Toast.LENGTH_LONG).show();
        }


    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        Button loginBtn = findViewById(R.id.loginBtn);
        Button createAccountBtn = findViewById(R.id.createAccountBtn);
        login = findViewById(R.id.LoginTextView);

        username = findViewById(R.id.username);
        password = findViewById(R.id.password);

        newUsername = findViewById(R.id.newUsername);
        newPassword = findViewById(R.id.newPassword);


    }
}