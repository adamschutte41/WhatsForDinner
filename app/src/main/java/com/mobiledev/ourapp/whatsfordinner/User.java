package com.mobiledev.ourapp.whatsfordinner;

import android.content.Context;

import java.util.ArrayList;
import java.util.HashMap;

public class User {

    private static User user;
    long id;
    String username;
    String password;
    DatabaseHelper db;
    HashMap<String, String[]> lastSearchRestaurants = new HashMap<String, String[]>();
    ArrayList<SubjectData> lastSearchRecipes = new ArrayList<>();
    ArrayList<Restaurant> favoriteRestaurants = new ArrayList<Restaurant>();
    ArrayList<RecipeObject> favoriteRecipes = new ArrayList<RecipeObject>();
    ArrayList<Integer> lastCategories = new ArrayList<Integer>();
    ArrayList<String> lastIngredients = new ArrayList<String>();

    public static synchronized User getInstance() {

        // Use the application context, which will ensure that you
        // don't accidentally leak an Activity's context.
        // See this article for more information: http://bit.ly/6LRzfx
        if (user == null) {
            user = new User();
        }
        return user;
    }

    private User() {



    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setContext(Context context){
        db = DatabaseHelper.getInstance(context);
    }

    public long createUser() {
        long l = db.createUser(this);
        this.id = l;
        return l;
    }

    public long getUser(String username, String password) {
        long l = db.getUser(username, password);

        return l;
    }

    public void editUser(String username, String password, String newUsername, String newPassword){
        db.editUser(username, password, newUsername, newPassword);
    }

    public void setLastSearchRestaurants(HashMap<String, String[]>  restaurants){
        this.lastSearchRestaurants = restaurants;

    }

    public HashMap<String, String[]> getLastSearchRestaurants() {
        return this.lastSearchRestaurants;
    }

    public void setLastCategories(ArrayList<Integer> categories) {
        this.lastCategories = categories;
    }

    public boolean compareCategories(ArrayList<Integer> categories){

        boolean same = true;

        if(lastSearchRestaurants.size() > 0){
            for(int cat : categories){
                if(!lastCategories.contains(cat)){
                    same = false;
                    break;
                }
            }
        } else {
            same = false;
        }



        return same;
    }

    public void setLastSearchRecipes(ArrayList<SubjectData> last){
        this.lastSearchRecipes = last;
    }

    public void setLastIngredients(String[] ingredients) {
        ArrayList<String> temp = new ArrayList<>();

        for(String s : ingredients){
            temp.add(s);
        }

        this.lastIngredients = temp;
    }

    public boolean compareIngredients(String[] ingredients){

        boolean same = true;

        if(lastSearchRecipes.size() > 0){
            for(String ingredient: ingredients){
                if(!lastIngredients.contains(ingredient)){
                    same = false;
                    break;
                }
            }
        } else {
            same = false;
        }



        return same;
    }

    public void setFavoriteRestaurants(ArrayList<Restaurant> restaurants) {
        this.favoriteRestaurants = restaurants;
    }

    public ArrayList<Restaurant> getFavoriteRestaurants() {
        return this.favoriteRestaurants;
    }

    public void setFavoriteRecipes(ArrayList<RecipeObject> recipes) {
        this.favoriteRecipes = recipes;
    }

    public ArrayList<RecipeObject> getFavoriteRecipes() {
        return this.favoriteRecipes;
    }
}
