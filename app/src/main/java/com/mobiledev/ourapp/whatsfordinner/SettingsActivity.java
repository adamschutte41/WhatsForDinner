package com.mobiledev.ourapp.whatsfordinner;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Surface;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class SettingsActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        int rotation = getWindowManager().getDefaultDisplay().getRotation();

        if(rotation == Surface.ROTATION_90 || rotation == Surface.ROTATION_270){
            setContentView(R.layout.activity_settings_land);
        }else {
            setContentView(R.layout.activity_settings);
        }

        Button favoritesBtn = findViewById(R.id.favoriteRecBtn);
        favoritesBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                startActivity(new Intent(SettingsActivity.this, FavoriteRecipeActivity.class));
            }
        });

        Button favoriteRestBtn = findViewById(R.id.favoriteRestBtn);
        favoriteRestBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                startActivity(new Intent(SettingsActivity.this, FavoriteRestaurantActivity.class));
            }
        });

        Button accountBtn = findViewById(R.id.accountBtn);
        accountBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                startActivity(new Intent(SettingsActivity.this, LoginSettingsActivity.class));
            }
        });

        Button back = findViewById(R.id.backBtn);
        back.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                startActivity(new Intent(SettingsActivity.this, MainActivity.class));
            }
        });
    }
}
