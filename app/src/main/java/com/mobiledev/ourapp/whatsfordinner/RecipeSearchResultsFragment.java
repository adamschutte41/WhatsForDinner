package com.mobiledev.ourapp.whatsfordinner;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.loader.app.LoaderManager;
import androidx.loader.content.Loader;

import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 *
 */
public class RecipeSearchResultsFragment extends Fragment implements View.OnClickListener {
    private ListView mRecipeListView;
    private ArrayList<String> list = new ArrayList<>();
    private ArrayAdapter<String> adapter;

    private TextView mResponseText;
    private RequestQueue requestQueue;
    private String[] parsed_ingredients;
    private JSONObject json_request;
    private RecipeObject[] recipes;
    final int TIMEOUT_MS = 10000;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v;
        Activity activity = getActivity();
        requestQueue = Volley.newRequestQueue(getActivity());

        parsed_ingredients = getArguments().getStringArray("edttext");

        if(activity != null){
            v = inflater.inflate(R.layout.fragment_recipe_search_results, container, false);
        }
        else{
            v = inflater.inflate(R.layout.fragment_recipe_search_results, container, false);
        }
        mResponseText = v.findViewById(R.id.response_text);
        mRecipeListView = v.findViewById(R.id.recipe_list_view);

        adapter = new ArrayAdapter<>(getActivity(), R.layout.list_item_recipe, list);
        mRecipeListView.setAdapter(adapter);

        return v;
    }

    @Override
    public void onResume() {
        super.onResume();
        SearchIngredients();
    }

    public void SearchIngredients(){
        String url_prefix = "https://api.spoonacular.com/recipes/complexSearch";
        String name_searches = "?query=";
        String API = "&apiKey=2a2d2eb2a406445482580f09236687b4";
        String number = "&number=100";
        for(int i = 0; i < parsed_ingredients.length; i++){
            name_searches += parsed_ingredients[i];
            if(i < parsed_ingredients.length-1){
                name_searches += " ";
            }
        }
        String url = url_prefix+name_searches+number+API;

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                json_request = response;
                ParseResponse();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                mResponseText.setText("that didn't work!");
            }
        });
        jsonObjectRequest.setTag("getRequest");
        jsonObjectRequest.setRetryPolicy(new DefaultRetryPolicy(
                TIMEOUT_MS,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT
        ));
        requestQueue.add(jsonObjectRequest);
    }

    public void ParseResponse(){
        JSONArray array;
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        try{
            array = json_request.getJSONArray("results");
            recipes = new RecipeObject[array.length()];
            String RecipeList = "";
            recipes = gson.fromJson(array.toString(), RecipeObject[].class);
            for(RecipeObject recipe : recipes){
                list.add(recipe.getTitle());
            }
            adapter.notifyDataSetChanged();
        }catch(Exception e){
            mResponseText.setText("That didn't work");
        }
    }

    @Override
    public void onClick(View view) {

    }
}