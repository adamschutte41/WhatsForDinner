package com.mobiledev.ourapp.whatsfordinner;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;


/**
 * A fragment class for the recipe search
 *
 */
public class RecipeSearchFragment extends Fragment implements View.OnClickListener{
    private EditText mIngredients;
    private String TAG = "RecipeSearchFragment";

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v;
        Activity activity = getActivity();

        if(activity != null){
            v = inflater.inflate(R.layout.fragment_recipe_search, container, false);
        }
        else{
            v = inflater.inflate(R.layout.fragment_recipe_search, container, false);
        }

        mIngredients = v.findViewById(R.id.edit_ingredient_name);
        Button searchButton = v.findViewById(R.id.search_button);
        if(searchButton != null){
            searchButton.setOnClickListener(this);
        }

        return v;
    }

    @Override
    public void onResume() {
        super.onResume();
        try{
            AppCompatActivity activity = (AppCompatActivity) getActivity();
            if(activity != null){
                ActionBar actionBar = ((AppCompatActivity) getActivity()).getSupportActionBar();
                if(actionBar != null){
                    actionBar.setSubtitle("Recipe Search");
                }
            }
        }
        catch (NullPointerException npe){
            Log.d(TAG, "Could not set subtitle");
        }
    }

    @Override
    public void onClick(View view) {
        Activity activity = getActivity();
        if(activity != null){
            switch (view.getId()){
                case R.id.search_button:
                    ParseIngredients(view);
                    break;
            }
        }
    }

    public void ParseIngredients(View view){
        String[] parsed_ingredients = new String[mIngredients.getLineCount()];
        String ingredients = mIngredients.getText().toString();
        int count = 0;
        parsed_ingredients[0] = "";
        for(int i = 0; i < ingredients.length(); i++){
            char c = ingredients.charAt(i);
            if(c != '\n' && c != ','){
                parsed_ingredients[count] += c;
            }
            else{
                char c_next = ingredients.charAt(i+1);
                if(c_next == ' ' || c_next == '\n'){
                    i++;
                }
                count++;
                parsed_ingredients[count] = "";
            }
        }
        Intent i = new Intent(getActivity().getApplicationContext(), RecipeSearchResultsActivity.class);
        i.putExtra("KEY", parsed_ingredients);
        startActivity(i);
    }

}