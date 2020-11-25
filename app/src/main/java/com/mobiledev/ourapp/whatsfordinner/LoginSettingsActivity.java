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

public class LoginSettingsActivity extends AppCompatActivity implements View.OnClickListener {

    User user;
    EditText username;
    EditText password;
    EditText newUsername;
    EditText newPassword;
    TextView submit;

    public void onSubmit(View view){


        String userIn = username.getText().toString();
        String passIn = password.getText().toString();
        String newUserIn = newUsername.getText().toString();
        String newPassIn = newPassword.getText().toString();

        user = User.getInstance();
        user.setUsername(userIn);
        user.setPassword(passIn);
        user.setContext(getApplicationContext());

        long result = user.getUser(userIn, passIn);

        if(result != -1){
            user.editUser(userIn, passIn, newUserIn, newPassIn);
            Toast.makeText(getApplicationContext(), "CREDENTIALS CHANGED", Toast.LENGTH_LONG).show();
            startActivity(new Intent(LoginSettingsActivity.this, MainActivity.class));
        }else {
            Toast.makeText(getApplicationContext(), "ERROR", Toast.LENGTH_LONG).show();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        int rotation = getWindowManager().getDefaultDisplay().getRotation();

        if(rotation == Surface.ROTATION_90 || rotation == Surface.ROTATION_270){
            setContentView(R.layout.activity_login_settings_land);
        }else {
            setContentView(R.layout.activity_login_settings);
        }


        Button submitBtn = findViewById(R.id.submitBtn);
        submit = findViewById(R.id.submitTextView);

        username = findViewById(R.id.username);
        password = findViewById(R.id.password);

        newUsername = findViewById(R.id.newUsername);
        newPassword = findViewById(R.id.newPassword);

        Button backBtn = findViewById(R.id.backBtn);
        backBtn.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.backBtn:
                startActivity(new Intent(LoginSettingsActivity.this, SettingsActivity.class));
                break;
        }
    }
}
