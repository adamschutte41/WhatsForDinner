package com.mobiledev.ourapp.whatsfordinner;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.os.Bundle;
import android.widget.Toast;

public class RecipeSearchResultsActivity extends SingleFragmentActivity {
    private String response;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle extras = getIntent().getExtras();
        if(extras != null){
            response = extras.getString("KEY");
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected Fragment createFragment() {
        return new RecipeSearchResultsFragment(response);
    }
}