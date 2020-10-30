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

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;


/**
 * A fragment class for the recipe search
 *
 */
public class RecipeSearchFragment extends Fragment implements View.OnClickListener{
    private EditText mIngredients;
    private String TAG = "RecipeSearchFragment";
    private RequestQueue requestQueue;
    private final String[] response_text = new String[1];

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v;
        Activity activity = getActivity();
        requestQueue = Volley.newRequestQueue(getActivity());

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
        SearchIngredients(parsed_ingredients);
    }

    public void SearchIngredients(String[] parsed_ingredients){
        String url_prefix = "https://api.edamam.com/search";
        String name_searches = "";
        String app_id = "&app_id=3658f3fd";
        String API = "&app_key=c067d1f070733bd6232423f5a5fe4b00";
        for(int i = 0; i < parsed_ingredients.length; i++){
            name_searches += "?q="+ parsed_ingredients[i];
        }
        String url = url_prefix+name_searches+app_id+API;

        StringRequest stringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                response_text[0] = response.toString();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                response_text[0] = "That didn't work!";
            }
        });
        requestQueue.add(stringRequest);

        if(response_text[0] != null) {
            Intent i = new Intent(getActivity().getApplicationContext(), RecipeSearchResultsActivity.class);
            i.putExtra("KEY", response_text);
            startActivity(i);
        }
    }

}