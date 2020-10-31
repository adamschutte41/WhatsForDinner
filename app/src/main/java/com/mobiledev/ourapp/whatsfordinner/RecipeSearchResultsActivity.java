package com.mobiledev.ourapp.whatsfordinner;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.os.Bundle;
import android.widget.Toast;

public class RecipeSearchResultsActivity extends SingleFragmentActivity {
    private String[] parsed_ingredients;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Bundle extras = getIntent().getExtras();
        parsed_ingredients = extras.getStringArray("KEY");
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected Fragment createFragment() {
        Bundle bundle = new Bundle();
        bundle.putStringArray("edttext", parsed_ingredients);
        RecipeSearchResultsFragment fragobj = new RecipeSearchResultsFragment();
        fragobj.setArguments(bundle);
        return fragobj;
    }
}