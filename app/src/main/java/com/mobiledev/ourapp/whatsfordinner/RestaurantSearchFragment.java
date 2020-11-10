package com.mobiledev.ourapp.whatsfordinner;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

//https://medium.com/@howtodoandroid/how-to-get-current-latitude-and-longitude-in-android-example-35437a51052a
//https://wtmimura.com/post/calling-api-on-android-studio/

/**
 * A fragment class for the recipe search
 *
 */
public class RestaurantSearchFragment extends Fragment implements View.OnClickListener{

    MyCustomAdapter adapter;
    private RequestQueue queue;
    LocationManager locationManager;
    LocationListener locationListener;
    Context context;
    Activity activity;

    public void processSelectedRestaurants(){
        //default values for if nothing was selected
        ArrayList<String> categories = new ArrayList<String>();
        ArrayList<Integer> prices = new ArrayList<Integer>();
        prices.add(1);
        int maxPrice = 1;

        ArrayList<Restaurant> finalList = adapter.list;

        for(Restaurant r : finalList){
            String newCat = convertCat(r.type);
            if(!categories.contains(newCat)){
                categories.add(newCat);
            }

            if(r.price_level > maxPrice){
                maxPrice = r.price_level;
                prices.add(maxPrice);
            }
        }

    }


    public void processLocation() {

        locationManager = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);

        locationListener = new LocationListener() {
            @Override
            public void onLocationChanged(@NonNull Location location) {
                Log.i("Location", location.toString());
            }
        };

        if (ContextCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(activity, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1);

        } else {
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, locationListener);
        }
    }

    public String convertCat(String oldCategory){
        String result = "";

        switch (oldCategory){
            case "American":
                result = "tradamerican";
                break;
            case "Mexican":
                result = "mexican";
                break;
            case "Italian":
                result = "italian";
                break;
        }
        return result;
    }

    private StringRequest searchRestaurantRequest(ArrayList<String> categories, ArrayList<Integer> price_level) {
        final String API = "&api_key=bNS6ai0J_4GU6m-lN0qSPpcApJ6tMcl7_CYsYtazHeuOIyU7OkjTj5RkNUY9_5sNqNyaMy0Lj0kKlj_Ex_CA1bgCz7LLlMlFZMDFKiQKuyN5ajlSqrfPlOd1gd6iX3Yx";
        final String TERM = "&term=restaurants";
        final double LONGITUDE;
        final double LATITUDE;
        final String RADIUS = "&radius=40000"; //in meters, 40,000 is the max value
        final String CATEGORIES = "&categories=" + categories.toString();
        final String PRICE = "&price=" + price_level.toString();

//        final String DATA_SOURCE = "&ds=Standard Reference";
//        final String FOOD_GROUP = "&fg=";
//        final String SORT = "&sort=r";
//        final String MAX_ROWS = "&max=25";
//        final String BEGINNING_ROW = "&offset=0";
//        final String URL_PREFIX = "https://api.yelp.com/v3/businesses/search";

        //String url = URL_PREFIX + API + NAME_SEARCH + nameSearch + DATA_SOURCE + FOOD_GROUP + SORT + MAX_ROWS + BEGINNING_ROW;
        String url = "";
        // 1st param => type of method (GET/PUT/POST/PATCH/etc)
        // 2nd param => complete url of the API
        // 3rd param => Response.Listener -> Success procedure
        // 4th param => Response.ErrorListener -> Error procedure
        return new StringRequest(Request.Method.GET, url,
            new Response.Listener<String>() {
                // 3rd param - method onResponse lays the code procedure of success return
                // SUCCESS
                @Override
                public void onResponse(String response) {
                    // try/catch block for returned JSON data
                    // see API's documentation for returned format
                    try {
                        JSONObject result = new JSONObject(response).getJSONObject("list");
                        int maxItems = result.getInt("end");
                        JSONArray resultList = result.getJSONArray("item");


                        // catch for the JSON parsing error
                    } catch (JSONException e) {
                        //Toast.makeText(AddFoodItems.this, e.getMessage(), Toast.LENGTH_LONG).show();
                    }
                } // public void onResponse(String response)
            }, // Response.Listener<String>()
            new Response.ErrorListener() {


                // 4th param - method onErrorResponse lays the code procedure of error return
                // ERROR
                @Override
                public void onErrorResponse(VolleyError error) {
                    // display a simple message on the screen
                    //Toast.makeText(AddFoodItems.this, "Food source is not responding (USDA API)", Toast.LENGTH_LONG).show();
                }
            });
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v;


        if(activity != null){
            v = inflater.inflate(R.layout.fragment_restaurant_search, container, false);
        }
        else{
            v = inflater.inflate(R.layout.fragment_restaurant_search, container, false);
        }

        ListView myListView = v.findViewById(R.id.myListView);

        ArrayList<Restaurant> restaurants = new ArrayList<Restaurant>();
        restaurants.add(new Restaurant("McDonalds", "American", 1));
        restaurants.add(new Restaurant("Wendys", "American", 1));
        restaurants.add(new Restaurant("Taco Bell", "Mexican", 1));
        restaurants.add(new Restaurant("AppleBees", "American", 2));
        restaurants.add(new Restaurant("Bravo", "Italian", 2));

        context = v.getContext();
        queue = Volley.newRequestQueue(context);
        adapter = new MyCustomAdapter(context, R.layout.restaurant_info, restaurants);
        myListView.setAdapter(adapter);

        Activity activity = getActivity();
        processLocation();




        return v;
    }

    private class MyCustomAdapter extends ArrayAdapter<Restaurant> {

        private ArrayList<Restaurant> list;

        public MyCustomAdapter(Context context, int textViewResourceId,
                               ArrayList<Restaurant> list) {
            super(context, textViewResourceId, list);
            this.list = new ArrayList<Restaurant>();
            this.list.addAll(list);
        }

        private class ViewHolder {
            TextView price;
            TextView type;
            CheckBox name;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            ViewHolder holder = null;
            Context context = parent.getContext();

            if (convertView == null) {
                LayoutInflater v = (LayoutInflater)context.getSystemService(
                        Context.LAYOUT_INFLATER_SERVICE);
                convertView = v.inflate(R.layout.restaurant_info, null);

                holder = new ViewHolder();

                holder.name = convertView.findViewById(R.id.checkBox);
                holder.type = convertView.findViewById(R.id.type);
                holder.price = convertView.findViewById(R.id.price);
                convertView.setTag(holder);

                holder.name.setOnClickListener( new View.OnClickListener() {
                    public void onClick(View v) {
                        Context c = v.getContext();
                        CheckBox cb = (CheckBox) v ;
                        Restaurant restaurant = (Restaurant) cb.getTag();
                        Toast.makeText(c.getApplicationContext(),
                                "Clicked on Checkbox: " + cb.getText() +
                                        " is " + cb.isChecked(),
                                Toast.LENGTH_LONG).show();
                        restaurant.setSelected(cb.isChecked());
                    }
                });
            }
            else {
                holder = (ViewHolder) convertView.getTag();
            }

            Restaurant restaurant = list.get(position);
            String priceStr = "";
            for(int i = 0; i < restaurant.price_level; i++){
                priceStr += "$";
            }
            holder.price.setText(priceStr);
            holder.type.setText(restaurant.type);
            holder.name.setText(restaurant.name);
            holder.name.setChecked(restaurant.getSelected());
            holder.name.setTag(restaurant);

            return convertView;

        }

    }

    @Override
    public void onClick(View view) {
        if(activity != null){
            switch (view.getId()){
                case R.id.submitBtn:
                    processSelectedRestaurants();
                    break;
            }
        }
    }
}