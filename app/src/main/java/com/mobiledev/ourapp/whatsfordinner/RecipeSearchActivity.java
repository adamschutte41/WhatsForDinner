package com.mobiledev.ourapp.whatsfordinner;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.os.Bundle;
import android.util.Log;

public class RecipeSearchActivity extends SingleFragmentActivity {
    @Override
    protected Fragment createFragment() {
        return new RecipeSearchFragment();
    }
}