package com.mobiledev.ourapp.whatsfordinner;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.media.Image;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class FavoriteRecipeActivity extends AppCompatActivity implements View.OnClickListener {

    DatabaseHelper db;
    FavRecipeAdapter adapter;
    ArrayList<RecipeObject> favorites = new ArrayList<>();
    User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favorite_recipe);

        db = DatabaseHelper.getInstance(getApplicationContext());

        Button backBtn = findViewById(R.id.backBtn);
        backBtn.setOnClickListener(this);

        ListView listView = findViewById(R.id.favListView);

        user = User.getInstance();

        if(user.getFavoriteRecipes().size() > 0){
            favorites = user.getFavoriteRecipes();
        } else {
            favorites = db.getFavoriteRecipes();
            user.setFavoriteRecipes(favorites);
        }

        adapter = new FavRecipeAdapter(this, R.layout.fav_recipe_info, favorites);
        listView.setAdapter(adapter);
    }

    public void deleteFavoriteRestaurant(String tag){
        long id = -1;

        for (RecipeObject r : adapter.list) {
            if(r.getLabel().equals(tag)){
                db.deleteFavoriteRecipe(r.getLabel());
                break;
            }
        }

        for (int i = 0; i < favorites.size(); i++) {
            if(favorites.get(i).getLabel().equals(tag)){
                favorites.remove(i);
            }
        }

        finish();
        startActivity(getIntent());
    }

    public void viewFavoriteRestaurant(String tag){
        String result = "";

        for (RecipeObject r : adapter.list) {
            if(r.getLabel().equals(tag)){
                result = r.getUrl();
                break;
            }
        }

        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setData(Uri.parse(result));
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.backBtn:
                startActivity(new Intent(FavoriteRecipeActivity.this, SettingsActivity.class));
                break;
        }
    }


    private class FavRecipeAdapter extends ArrayAdapter<RecipeObject> implements View.OnClickListener, ListView.OnItemClickListener {

        private ArrayList<RecipeObject> list;
        Context mContext;

        public FavRecipeAdapter(Context context, int textViewResourceId,
                                    ArrayList<RecipeObject> list) {
            super(context, textViewResourceId, list);
            this.mContext = context;
            this.list = new ArrayList<RecipeObject>();
            this.list.addAll(list);
            db = DatabaseHelper.getInstance(getApplicationContext());
        }

        @Override
        public void onClick(View view) {
            String tag = (String) view.getTag();
            switch (view.getId()){
                case R.id.deleteFavoriteRecipe:
                    if (mContext instanceof FavoriteRecipeActivity) {
                        ((FavoriteRecipeActivity)mContext).deleteFavoriteRestaurant(tag);
                    }
                    break;
                case R.id.viewFavoriteRecipe:
                    if (mContext instanceof FavoriteRecipeActivity) {
                        ((FavoriteRecipeActivity)mContext).viewFavoriteRestaurant(tag);
                    }
                    break;
            }

        }

        @Override
        public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
            String tag = (String) view.getTag();
            String result = "";

            for (RecipeObject r : adapter.list) {
                if(r.getLabel().equals(tag)){
                    result = r.getUrl();
                    break;
                }
            }

            Intent intent = new Intent(Intent.ACTION_VIEW);
            intent.setData(Uri.parse(result));
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
        }

        private class ViewHolder {
            TextView title;
            ImageView imageView;
            Button deleteBtn;
            Button viewBtn;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            FavRecipeAdapter.ViewHolder holder = null;
            Context context = parent.getContext();

            if (convertView == null) {
                LayoutInflater v = (LayoutInflater)context.getSystemService(
                        Context.LAYOUT_INFLATER_SERVICE);
                convertView = v.inflate(R.layout.fav_recipe_info, null);

                holder = new FavRecipeAdapter.ViewHolder();

                holder.title = convertView.findViewById(R.id.title);
                holder.imageView = convertView.findViewById(R.id.list_image);
                holder.deleteBtn = convertView.findViewById(R.id.deleteFavoriteRecipe);
                holder.viewBtn = convertView.findViewById(R.id.viewFavoriteRecipe);
                convertView.setTag(holder);

            }
            else {
                holder = (FavRecipeAdapter.ViewHolder) convertView.getTag();
            }

            RecipeObject recipe = list.get(position);

            holder.title.setText(recipe.getLabel());
            Picasso.with(context)
                    .load(recipe.getImage())
                    .into(holder.imageView);
            holder.deleteBtn.setTag(recipe.getLabel());
            holder.viewBtn.setTag(recipe.getLabel());

            holder.deleteBtn.setOnClickListener(this);
            holder.viewBtn.setOnClickListener(this);

            return convertView;

        }

    }
}