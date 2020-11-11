package com.mobiledev.ourapp.whatsfordinner;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;

public class RestaurantViewActivity extends AppCompatActivity{

    RestaurantAdapter adapter;
    DatabaseHelper db;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_restaurant_view);

        ListView resListView = findViewById(R.id.resListView);


        ArrayList<Restaurant> results = new ArrayList<>();

        HashMap<String, String[]> r = new HashMap<>();
        r = (HashMap<String, String[]>) getIntent().getSerializableExtra("restaurants");

        for (HashMap.Entry mapElement : r.entrySet()) {
            String key = (String)mapElement.getKey();

            // Add some bonus marks
            // to all the students and print it
            String[] value = (String[])mapElement.getValue();

            Restaurant rest = new Restaurant(value[0], value[1], Integer.parseInt(value[2]), value[3]);
            results.add(rest);
        }

        adapter = new RestaurantAdapter(this, R.layout.restaurant_info, results);
        resListView.setAdapter(adapter);



    }

    private class RestaurantAdapter extends ArrayAdapter<Restaurant> implements View.OnClickListener {

        private ArrayList<Restaurant> list;

        public RestaurantAdapter(Context context, int textViewResourceId,
                               ArrayList<Restaurant> list) {
            super(context, textViewResourceId, list);
            this.list = new ArrayList<Restaurant>();
            this.list.addAll(list);
            db = DatabaseHelper.getInstance(getApplicationContext());
        }

        @Override
        public void onClick(View view) {
            String tag = (String) view.getTag();
        }

        private class ViewHolder {
            TextView price;
            TextView name;
            TextView location;
            TextView menu;
            Button saveBtn;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            RestaurantAdapter.ViewHolder holder = null;
            Context context = parent.getContext();

            if (convertView == null) {
                LayoutInflater v = (LayoutInflater)context.getSystemService(
                        Context.LAYOUT_INFLATER_SERVICE);
                convertView = v.inflate(R.layout.result_restaurant, null);

                holder = new RestaurantAdapter.ViewHolder();

                holder.name = convertView.findViewById(R.id.name);
                holder.price = convertView.findViewById(R.id.price);
                holder.location = convertView.findViewById(R.id.location);
                holder.saveBtn = convertView.findViewById(R.id.save_rest);
                convertView.setTag(holder);

            }
            else {
                holder = (RestaurantAdapter.ViewHolder) convertView.getTag();
            }

            Restaurant restaurant = list.get(position);
            String priceStr = "";
            for(int i = 0; i < restaurant.price_level; i++){
                priceStr += "$";
            }
            holder.price.setText(priceStr);
            holder.name.setText(restaurant.name);
            holder.location.setText(restaurant.location);
            holder.name.setTag(restaurant);
            holder.saveBtn.setTag(restaurant.name);

            holder.saveBtn.setOnClickListener(this);

            return convertView;

        }

    }
}