package com.mobiledev.ourapp.whatsfordinner;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
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
import android.widget.AdapterView;
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
public class RecipeSearchResultsFragment extends Fragment implements ListView.OnItemClickListener{
    private ListView mRecipeListView;
    private ArrayList<SubjectData> list = new ArrayList<>();
    private CustomAdapter customAdapter;

    private RequestQueue requestQueue;
    private String[] parsed_ingredients;
    private JSONObject json_request;
    private RecipeObject[] recipes;

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
        mRecipeListView = v.findViewById(R.id.list);

        customAdapter = new CustomAdapter(getActivity(), list);

        return v;
    }

    @Override
    public void onResume() {
        super.onResume();
        SearchIngredients();
    }

    public void SearchIngredients(){
        String url_prefix = "https://api.edamam.com/search";
        String name_searches = "?q=";
        String app_id = "&app_id=3658f3fd";
        String API = "&app_key=c067d1f070733bd6232423f5a5fe4b00";
        String number = "&from=0&to=100";
        for(int i = 0; i < parsed_ingredients.length; i++){
            if(parsed_ingredients[i] != null){
                name_searches += parsed_ingredients[i];
            }
            if(i < parsed_ingredients.length){
                name_searches += ",";
            }
        }
        String url = url_prefix+name_searches+app_id+API+number;

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                json_request = response;
                ParseResponse();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                list.add(new SubjectData("Uh oh!", null, null));
            }
        });
        requestQueue.add(jsonObjectRequest);
    }

    public void ParseResponse(){
        JSONArray array;
        try{
            array = json_request.getJSONArray("hits");
            recipes = new RecipeObject[array.length()];
            for(int i = 0; i < array.length(); i++){
                recipes[i] = new RecipeObject(array.getJSONObject(i).getJSONObject("recipe"));
            }
            for(RecipeObject recipe : recipes){
                list.add(new SubjectData(recipe.getLabel(), recipe.getUrl(), recipe.getImage()));
            }
        }catch(Exception e){
            list.add(new SubjectData("Uh oh!", null, null));
        }

        mRecipeListView.setAdapter(customAdapter);
        mRecipeListView.setOnItemClickListener(this);
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        String url = recipes[i].getUrl();

        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setData(Uri.parse(url));
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }
}