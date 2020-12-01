package com.mobiledev.ourapp.whatsfordinner;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Surface;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class RestaurantSearchActivity extends AppCompatActivity implements View.OnClickListener {

    MyCustomAdapter adapter;
    private RequestQueue queue;
    LocationManager locationManager;
    LocationListener locationListener;
    Location location;
    double latitude;
    double longitude;


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);


        if(grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, locationListener);

                if (locationManager != null) {
                    location = locationManager
                            .getLastKnownLocation(LocationManager.GPS_PROVIDER);

                    if (location != null) {
                        latitude = location.getLatitude();
                        longitude = location.getLongitude();

                        Log.d("Location", String.valueOf(latitude));
                        Log.d("Location", String.valueOf(longitude));
                    }
                }
            }
        }
    }

    public void processSelectedRestaurants(){
        //default values for if nothing was selected
        final ArrayList<Integer> categories = new ArrayList<Integer>();
        ArrayList<Integer> prices = new ArrayList<Integer>();
        prices.add(1);
        int maxPrice = 1;

        ArrayList<Restaurant> finalList = adapter.list;

        for(Restaurant r : finalList){
            if(r.isSelected){
                int newCat = convertCat(r.type);
                if(!categories.contains(newCat)){
                    categories.add(newCat);
                }

                if(r.price_level > maxPrice){
                    maxPrice = r.price_level;
                    prices.add(maxPrice);
                }
            }

        }

        User user = User.getInstance();
        boolean seenBofore = user.compareCategories(categories);
        if(seenBofore && user.lastSearchRestaurants.size() > 0){
            Intent i = new Intent(RestaurantSearchActivity.this, RestaurantViewActivity.class);
            //i.putExtra("EXTRA_RESTAURANTS", results);
            i.putExtra("restaurants", user.lastSearchRestaurants);
            startActivity(i);
        } else {
            //StringRequest stringRequest = searchRestaurantRequest(categories, prices);
            //queue.add(stringRequest);
            //String url ="https://developers.zomato.com/api/v2.1/search?lat=39.992981666666665&lon=-83.00122&radius=40000&cuisines=american";
            String base = "https://developers.zomato.com/api/v2.1/search?";
            String lat = "lat=" + latitude;
            String lon = "&lon=" + longitude;
            String radius = "&radius=40000";
            String cuisines = "&cuisines=" + categories.get(0).toString();
            String url = base + lat + lon + radius + cuisines;

            JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {
                    User u = User.getInstance();
                    HashMap<String, String[]> results = new HashMap<>();
                    try {
                        String rest = response.getString("restaurants");

                        JSONArray restuarants = new JSONArray(rest);

                        for(int i = 0; i<restuarants.length(); i++){
                            JSONObject current = restuarants.getJSONObject(i);
                            JSONObject v = current.getJSONObject("restaurant");

                            String name = v.getString("name");
                            JSONObject location = v.getJSONObject("location");
                            String addr = location.getString("address");
                            int price = v.getInt("price_range");
                            String menu = v.getString("menu_url");

                            String [] info = {name, addr, Integer.toString(price), menu};
                            results.put(name, info);
                        }

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }


                    u.setLastSearchRestaurants(results);
                    u.setLastCategories(categories);
                    //now handle the response
//                Toast.makeText(getApplicationContext(), "HOLY SHIT!!!", Toast.LENGTH_SHORT).show();
                    Intent i = new Intent(RestaurantSearchActivity.this, RestaurantViewActivity.class);
                    //i.putExtra("EXTRA_RESTAURANTS", results);
                    i.putExtra("restaurants", results);
                    startActivity(i);

                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {

                    //handle the error
                    Toast.makeText(getApplicationContext(), "An error occurred", Toast.LENGTH_SHORT).show();
                    error.printStackTrace();

                }
            }) {    //this is the part, that adds the header to the request
                @Override
                public Map<String, String> getHeaders() {
                    Map<String, String> params = new HashMap<String, String>();
                    params.put("user-key", "999236cc9cbf98aeefddb478ef61b27b");
                    params.put("content-type", "application/json");
                    return params;
                }
            };
            queue.add(request);
        }





    }

    public void processLocation() {

        locationManager = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);

        boolean gps_enabled = false;
        boolean network_enabled = false;

        if(!locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)){
            Toast.makeText(getApplicationContext(), "Turn on Location", Toast.LENGTH_SHORT).show();
            startActivity(new Intent(RestaurantSearchActivity.this, MainActivity.class));

        }else if(!locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER)){
            Toast.makeText(getApplicationContext(), "No network provider", Toast.LENGTH_SHORT).show();
            startActivity(new Intent(RestaurantSearchActivity.this, MainActivity.class));
        }else {
            locationListener = new LocationListener() {
                @Override
                public void onLocationChanged(@NonNull Location location) {

                }
            };

            if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1);
            } else {
                locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, locationListener);
                if (locationManager != null) {
                    Location location = locationManager
                            .getLastKnownLocation(LocationManager.GPS_PROVIDER);

                    if (location != null) {
                        latitude = location.getLatitude();
                        longitude = location.getLongitude();
                    }
                }
            }
        }


//        try {
//            network_enabled = locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
//        } catch(Exception ex) {}


    }

    public int convertCat(String oldCategory){
        int result = 1;

        switch (oldCategory){
            case "American":
                result = 1;
                break;
            case "Mexican":
                result = 73;
                break;
            case "Italian":
                result = 55;
                break;
            case "Asian":
                result = 3;
                break;
            case "BBQ":
                result = 193;
                break;
            case "Breakfast":
                result = 162;
                break;
            case "Cajun":
                result = 491;
                break;
            case "German":
                result = 134;
                break;
            case "Greek":
                result = 156;
                break;
            case "Indian":
                result = 148;
                break;
            case "Sushi":
                result = 177;
                break;
            default:
                result = 1;
        }
        return result;
    }

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        int rotation = getWindowManager().getDefaultDisplay().getRotation();

        if(rotation == Surface.ROTATION_90 || rotation == Surface.ROTATION_270){
            setContentView(R.layout.activity_restaurant_search_land);
        }else {
            setContentView(R.layout.activity_restaurant_search);
        }

        ListView myListView = findViewById(R.id.myListView);
        Button submitBtn = findViewById(R.id.submitBtn);
        if(submitBtn != null){
            submitBtn.setOnClickListener(this);
        }



        ArrayList<Restaurant> restaurants = new ArrayList<Restaurant>();
        restaurants.add(new Restaurant("McDonalds", "American", 1));
        restaurants.add(new Restaurant("Wendys", "American", 1));
        restaurants.add(new Restaurant("Taco Bell", "Mexican", 1));
        restaurants.add(new Restaurant("AppleBees", "American", 2));
        restaurants.add(new Restaurant("Bravo", "Italian", 2));
        restaurants.add(new Restaurant("Panda Express", "Asian", 1));
        restaurants.add(new Restaurant("City Barbecue", "BBQ", 2));
        restaurants.add(new Restaurant("IHOP", "Breakfast", 2));
        restaurants.add(new Restaurant("Cajun Food", "Cajun", 1));
        restaurants.add(new Restaurant("Hofbrauhaus", "German", 3));
        restaurants.add(new Restaurant("Gyros", "Greek", 1));
        restaurants.add(new Restaurant("Indian", "Indian", 3));
        restaurants.add(new Restaurant("Fushian Sushi", "Sushi", 2));

        queue = Volley.newRequestQueue(this);
        adapter = new MyCustomAdapter(this, R.layout.restaurant_info, restaurants);
        myListView.setAdapter(adapter);

        processLocation();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.submitBtn:
                processSelectedRestaurants();
                break;
        }
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
//                        Toast.makeText(c.getApplicationContext(),
//                                "Clicked on Checkbox: " + cb.getText() +
//                                        " is " + cb.isChecked(),
//                                Toast.LENGTH_LONG).show();
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


//    @Override
//    protected Fragment createFragment() {
//        return new RestaurantSearchFragment();
//    }
}