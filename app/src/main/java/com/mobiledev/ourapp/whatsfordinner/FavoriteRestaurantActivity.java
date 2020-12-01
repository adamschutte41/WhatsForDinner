package com.mobiledev.ourapp.whatsfordinner;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Surface;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class FavoriteRestaurantActivity extends AppCompatActivity implements View.OnClickListener {

    FavRestaurantAdapter adapter;
    DatabaseHelper db;
    User user;
    ArrayList<Restaurant> favorites = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        int rotation = getWindowManager().getDefaultDisplay().getRotation();

        if(rotation == Surface.ROTATION_90 || rotation == Surface.ROTATION_270){
            setContentView(R.layout.activity_favorite_restaurant_land);
        }else {
            setContentView(R.layout.activity_favorite_restaurant);
        }

        db = DatabaseHelper.getInstance(getApplicationContext());

        Button backBtn = findViewById(R.id.backBtn);
        backBtn.setOnClickListener(this);

        ListView listView = findViewById(R.id.favListView);

        user = User.getInstance();

        if(!user.hasRestDBChanged && user.getFavoriteRestaurants().size() > 0){
            favorites = user.getFavoriteRestaurants();
        } else {
            favorites = db.getFavoriteRestaurants();
            user.setFavoriteRestaurants(favorites);
            user.hasRestDBChanged = false;
        }



        adapter = new FavRestaurantAdapter(this, R.layout.fav_restaurant_info, favorites);
        listView.setAdapter(adapter);
    }


    public void deleteFavoriteRestaurant(String tag){
        long id = -1;

        for (Restaurant r : adapter.list) {
            if(r.name.equals(tag)){
                db.deleteFavoriteRestaurant(r.name, r.location);
                break;
            }
        }

        for (int i = 0; i < favorites.size(); i++) {
            if(favorites.get(i).name.equals(tag)){
                favorites.remove(i);
            }
        }

        user.setFavoriteRestaurants(favorites);
        user.hasRestDBChanged = true;
        finish();
        startActivity(getIntent());
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.backBtn:
                startActivity(new Intent(FavoriteRestaurantActivity.this, SettingsActivity.class));
                break;
        }
    }

    private class FavRestaurantAdapter extends ArrayAdapter<Restaurant> implements View.OnClickListener {

        private ArrayList<Restaurant> list;
        Context mContext;

        public FavRestaurantAdapter(Context context, int textViewResourceId,
                                 ArrayList<Restaurant> list) {
            super(context, textViewResourceId, list);
            this.mContext = context;
            this.list = new ArrayList<Restaurant>();
            this.list.addAll(list);
            db = DatabaseHelper.getInstance(getApplicationContext());
        }

        @Override
        public void onClick(View view) {
            String tag = (String) view.getTag();
            if (mContext instanceof FavoriteRestaurantActivity) {
                ((FavoriteRestaurantActivity)mContext).deleteFavoriteRestaurant(tag);
            }
        }

        private class ViewHolder {
            TextView price;
            TextView name;
            TextView location;
            TextView menu;
            Button deleteBtn;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            FavRestaurantAdapter.ViewHolder holder = null;
            Context context = parent.getContext();

            if (convertView == null) {
                LayoutInflater v = (LayoutInflater)context.getSystemService(
                        Context.LAYOUT_INFLATER_SERVICE);
                convertView = v.inflate(R.layout.fav_restaurant_info, null);

                holder = new FavRestaurantAdapter.ViewHolder();

                holder.name = convertView.findViewById(R.id.name);
                holder.location = convertView.findViewById(R.id.location);
                holder.deleteBtn = convertView.findViewById(R.id.delete_rest);
                convertView.setTag(holder);

            }
            else {
                holder = (FavRestaurantAdapter.ViewHolder) convertView.getTag();
            }

            Restaurant restaurant = list.get(position);

            holder.name.setText(restaurant.name);
            holder.location.setText(restaurant.location);
            holder.name.setTag(restaurant);
            holder.deleteBtn.setTag(restaurant.name);

            holder.deleteBtn.setOnClickListener(this);

            return convertView;

        }

    }
}