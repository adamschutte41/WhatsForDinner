package com.mobiledev.ourapp.whatsfordinner;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Surface;
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

        user = User.getInstance();
        user.setUsername(userIn);
        user.setPassword(passIn);
        user.setContext(getApplicationContext());
        long result = user.getUser(userIn, passIn);

        if(result != -1){
            //Toast.makeText(getApplicationContext(), "WE CAN VIBE", Toast.LENGTH_LONG).show();
            startActivity(new Intent(LoginActivity.this, MainActivity.class));
        }else {
            Toast.makeText(getApplicationContext(), "LOGIN ERROR", Toast.LENGTH_LONG).show();
        }
    }

    public void onCreateAccount(View view){

        String userIn = newUsername.getText().toString();
        String passIn = newPassword.getText().toString();

        if(passIn.length() < 5){
            Toast.makeText(getApplicationContext(), "Password must be more than 5 characters", Toast.LENGTH_SHORT).show();
        } else if (userIn.length() < 1){
            Toast.makeText(getApplicationContext(), "Username can't be empty", Toast.LENGTH_SHORT).show();
        } else if(!containsDigit(passIn)){
            Toast.makeText(getApplicationContext(), "Password must contain a digit", Toast.LENGTH_SHORT).show();
        }else if(!containsChar(passIn)){
            Toast.makeText(getApplicationContext(), "Password must contain a character", Toast.LENGTH_SHORT).show();
        }else {
            user = User.getInstance();
            user.setUsername(userIn);
            user.setPassword(passIn);
            user.setContext(getApplicationContext());
            long result = user.createUser();

            if(result != -1){
//            Toast.makeText(getApplicationContext(), "WE CAN VIBE", Toast.LENGTH_LONG).show();
                startActivity(new Intent(LoginActivity.this, MainActivity.class));
            }else {
                Toast.makeText(getApplicationContext(), "LOGIN ERROR", Toast.LENGTH_LONG).show();
            }
        }

    }

    public final boolean containsDigit(String s) {
        boolean containsDigit = false;

        for (char c : s.toCharArray()) {
            if (Character.isDigit(c)) {
                containsDigit = true;
                break;
            }
        }

        return containsDigit;
    }

    public final boolean containsChar(String s) {
        boolean containsChar = false;

        for (char c : s.toCharArray()) {
            if (!Character.isDigit(c)) {
                containsChar = true;
                break;
            }
        }

        return containsChar;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        int rotation = getWindowManager().getDefaultDisplay().getRotation();

        if(rotation == Surface.ROTATION_90 || rotation == Surface.ROTATION_270){
            setContentView(R.layout.activity_login_land);
        }else {
            setContentView(R.layout.activity_login);
        }


        Button loginBtn = findViewById(R.id.loginBtn);
        Button createAccountBtn = findViewById(R.id.createAccountBtn);
        login = findViewById(R.id.LoginTextView);

        username = findViewById(R.id.username);
        password = findViewById(R.id.password);

        newUsername = findViewById(R.id.newUsername);
        newPassword = findViewById(R.id.newPassword);

    }
}