package com.mobiledev.ourapp.whatsfordinner;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import java.util.ArrayList;

public class DatabaseHelper extends SQLiteOpenHelper {

    // Database Version
    private static final int DATABASE_VERSION = 2;

    // Database Name
    private static final String DATABASE_NAME = "WhatsForDinner";

    // Table Names
    private static final String TABLE_USER = "user";
    private static final String TABLE_RESTAURANT = "restaurant";
    private static final String TABLE_QUESTION = "question";
    private static final String TABLE_RECIPE = "recipe";
    private static final String TABLE_INGREDIENT = "ingredient";
    private static final String TABLE_RECIPE_STEP = "recipe_step";
    private static final String TABLE_FAVORITE_RESTAURANT = "favorite_restaurants";
    private static final String TABLE_FAVORITE_RECIPES = "favorite_recipes";
    private static final String TABLE_INGREDIENT_TO_RECIPE = "ingredient_to_recipe";
    private static final String TABLE_STEP_TO_RECIPE = "step_to_recipe";

    // multi use column names
    private static final String LOCATION = "location";
    private static final String NAME = "name";
    private static final String TYPE = "password";

    // user column names
    private static final String USER_ID = "user_id";
    private static final String USERNAME = "username";
    private static final String PASSWORD = "password";
    // table also includes location from multi use


    // restaurant column names
    private static final String RESTAURANT_ID = "restaurant_id";
    private static final String IS_LOCAL = "is_local";
    private static final String SUBTYPE = "subtype";
    private static final String PRICE_LEVEL = "prive_level";
    private static final String RATING = "rating";
    private static final String IS_TOP_RATED = "is_top_rated";
    // table also includes location from multi use
    // table also includes name from multi use
    // table also includes type from multi use

    // question column names
    private static final String QUESTION_ID = "question_id";
    private static final String QUESTION = "question";

    // recipe column names
    private static final String RECIPE_ID = "recipe_id";
    private static final String U = "u";
    private static final String I = "i";
    // table also includes name from multi use
    // table also includes type from multi use

    // ingredient column names
    private static final String INGREDIENT_ID = "ingredient_id";
    // table also includes name from multi use

    // recipe_step column names
    private static final String RECIPE_STEP_ID = "recipe_step_id";
    private static final String STEP = "step";

    // favorite_restaurants column names
    private static final String FAVORITE_RESTAURANT_ID = "favorite_restaurant_id";

    // favorite_recipe column names
    private static final String FAVORITE_RECIPE_ID = "favorite_recipe_id";

    // ingredient_to_recipe column names
    private static final String INGREDIENT_TO_RECIPE_ID = "ingredient_to_recipe_id";

    // step_to_recipe column names
    private static final String STEP_TO_RECIPE_ID = "step_to_recipe_id";

    private static final String CREATE_TABLE_USER = "CREATE TABLE "
            + TABLE_USER + "(" + USER_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," + USERNAME
            + " TEXT," + PASSWORD + " TEXT," + LOCATION
            + " TEXT" + ")";

    private static final String CREATE_TABLE_RESTAURANT = "CREATE TABLE "
            + TABLE_RESTAURANT + "(" + RESTAURANT_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," + NAME
            + " TEXT," + IS_LOCAL + " BOOLEAN," + LOCATION
            + " TEXT," + TYPE + " TEXT," + SUBTYPE + " TEXT,"
            + PRICE_LEVEL + " INTEGER," + RATING + " INTEGER,"
            + IS_TOP_RATED + " BOOLEAN" + ")";

    private static final String CREATE_TABLE_QUESTION = "CREATE TABLE "
            + TABLE_QUESTION + "(" + QUESTION_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," + QUESTION
            + " TEXT" + ")";

    private static final String CREATE_TABLE_RECIPE = "CREATE TABLE "
            + TABLE_RECIPE + "(" + RECIPE_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," + NAME
            + " TEXT," + U + " TEXT," + I
            + " TEXT" + ")";

    private static final String CREATE_TABLE_INGREDIENT = "CREATE TABLE "
            + TABLE_INGREDIENT + "(" + INGREDIENT_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," + NAME
            + " TEXT" +  ")";

    private static final String CREATE_TABLE_RECIPE_STEP = "CREATE TABLE "
            + TABLE_RECIPE_STEP + "(" + RECIPE_STEP_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," + STEP
            + " TEXT" + ")";

    private static final String CREATE_TABLE_FAVORITE_RESTAURANT = "CREATE TABLE "
            + TABLE_FAVORITE_RESTAURANT + "(" + FAVORITE_RESTAURANT_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," + USER_ID
            + " INTEGER," + RESTAURANT_ID + " INTEGER" + ")";

    private static final String CREATE_TABLE_FAVORITE_RECIPE = "CREATE TABLE "
            + TABLE_FAVORITE_RECIPES+ "(" + FAVORITE_RECIPE_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," + USER_ID
            + " INTEGER," + RECIPE_ID + " INTEGER" + ")";

    private static final String CREATE_TABLE_INGREDIENT_TO_RECIPE = "CREATE TABLE "
            + TABLE_INGREDIENT_TO_RECIPE + "(" + INGREDIENT_TO_RECIPE_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," + RECIPE_ID
            + " INTEGER," + INGREDIENT_ID + " INTEGER" + ")";

    private static final String CREATE_TABLE_STEP_TO_RECIPE = "CREATE TABLE "
            + TABLE_STEP_TO_RECIPE + "(" + STEP_TO_RECIPE_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," + RECIPE_ID
            + " INTEGER," + RECIPE_STEP_ID + " INTEGER" + ")";



    private static DatabaseHelper sInstance;

    public static synchronized DatabaseHelper getInstance(Context context) {

        // Use the application context, which will ensure that you
        // don't accidentally leak an Activity's context.
        // See this article for more information: http://bit.ly/6LRzfx
        if (sInstance == null) {
            sInstance = new DatabaseHelper(context.getApplicationContext());
        }
        return sInstance;
    }

    private DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {

        //creating the required databases
        sqLiteDatabase.execSQL(CREATE_TABLE_USER);
        sqLiteDatabase.execSQL(CREATE_TABLE_RESTAURANT);
        sqLiteDatabase.execSQL(CREATE_TABLE_RECIPE);
        sqLiteDatabase.execSQL(CREATE_TABLE_QUESTION);
        sqLiteDatabase.execSQL(CREATE_TABLE_INGREDIENT);
        sqLiteDatabase.execSQL(CREATE_TABLE_RECIPE_STEP);
        sqLiteDatabase.execSQL(CREATE_TABLE_FAVORITE_RESTAURANT);
        sqLiteDatabase.execSQL(CREATE_TABLE_FAVORITE_RECIPE);
        sqLiteDatabase.execSQL(CREATE_TABLE_INGREDIENT_TO_RECIPE);
        sqLiteDatabase.execSQL(CREATE_TABLE_STEP_TO_RECIPE);

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_USER);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_RESTAURANT);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_RECIPE);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_QUESTION);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_INGREDIENT);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_RECIPE_STEP);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_FAVORITE_RESTAURANT);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_FAVORITE_RECIPES);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_INGREDIENT_TO_RECIPE);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_STEP_TO_RECIPE);

        onCreate(sqLiteDatabase);

    }

    /*
     * Creating a user
     */
    public long createUser(User user) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(USERNAME, user.username);
        values.put(PASSWORD, user.password);

        // insert row
        long user_id = db.insert(TABLE_USER, null, values);

        return user_id;
    }

    /*
     * Creating a restaurant
     */
    public long createRestaurant(String name, String location) {
        SQLiteDatabase db = this.getWritableDatabase();

        String newname = name.replaceAll("'","");

        ContentValues values = new ContentValues();
        values.put(NAME, newname);
        values.put(LOCATION, location);

        // insert row
        long rest_id = db.insert(TABLE_RESTAURANT, null, values);

        return rest_id;
    }

    /*
     * Creating a recipe
     */
    public long createRecipe(String name, String url, String image) {
        SQLiteDatabase db = this.getWritableDatabase();

        String newname = name.replaceAll("'","");
        String newUrl = url.replaceAll("/", "");
        newUrl = newUrl.replaceAll(":", "");
        newUrl = newUrl.replaceAll("-", "");
        newUrl = newUrl.replaceAll("\\.", "");

        ContentValues values = new ContentValues();
        values.put(NAME, newname);
        values.put(U, url);
        values.put(I, image);

        // insert row
        long rest_id = db.insert(TABLE_RECIPE, null, values);

        return rest_id;
    }

    public long createFavoriteRestaurant(int rest_id) {
        SQLiteDatabase db = this.getWritableDatabase();

        User u = User.getInstance();

        //link user to restaurant id
        ContentValues values = new ContentValues();
        values.put(USER_ID, u.id);
        values.put(RESTAURANT_ID, rest_id);

        // insert row
        long fav_id = db.insert(TABLE_FAVORITE_RESTAURANT, null, values);

        return fav_id;
    }

    public long createFavoriteRecipe(int rest_id) {
        SQLiteDatabase db = this.getWritableDatabase();

        User u = User.getInstance();

        //link user to restaurant id
        ContentValues values = new ContentValues();
        values.put(USER_ID, u.id);
        values.put(RECIPE_ID, rest_id);

        // insert row
        long fav_id = db.insert(TABLE_FAVORITE_RECIPES, null, values);

        return fav_id;
    }

    public void editUser(String username, String password, String newUsername, String newPassword){
        SQLiteDatabase db = this.getReadableDatabase();
        String updateQuery = "UPDATE " + TABLE_USER + " SET " + USERNAME + " = " +"'"+newUsername+"' "+  ", " +
                PASSWORD + " = " + "'"+newPassword+"'" + " WHERE " + USERNAME + " LIKE '" + username +
                "' AND " + PASSWORD + " LIKE '" + password + "'";

        db.execSQL(updateQuery);

    }

    /*
     * get gets a user by their username and password
     */
    public int getUser(String username, String password) {
        SQLiteDatabase db = this.getReadableDatabase();

        String selectQuery = "SELECT  * FROM " + TABLE_USER + " WHERE "
                + USERNAME + " LIKE '" + username + "' AND " + PASSWORD + " LIKE '" + password + "'";

        //String selectQuery = "SELECT  * FROM " + TABLE_USER;


        Cursor c = db.rawQuery(selectQuery, null);

        int a = -1;
        if (c != null){

            if(c.getCount() != 0){
                c.moveToFirst();
                a = c.getInt(c.getColumnIndex(USER_ID));
            }

        }

        return a;
    }

    /*
     * get gets a user by their username and password
     */
    public int getRestaurant(String name, String location) {
        SQLiteDatabase db = this.getReadableDatabase();

        String newname = name.replaceAll("'","");

        String selectQuery = "SELECT  * FROM " + TABLE_RESTAURANT + " WHERE "
                + NAME + " LIKE '" + newname + "' AND " + LOCATION + " LIKE '" + location + "'";

        //String selectQuery = "SELECT  * FROM " + TABLE_USER;


        Cursor c = db.rawQuery(selectQuery, null);

        int a = -1;

        if (c != null){
            if(c.getCount() != 0){
                c.moveToFirst();
                a = c.getInt(c.getColumnIndex(RESTAURANT_ID));
            }

        }

        return a;
    }

    /*
     * get gets a user by their username and password
     */
    public String[] getRestaurantById(int rest_id) {
        SQLiteDatabase db = this.getReadableDatabase();

        String [] result = new String[2];

        String selectQuery = "SELECT  * FROM " + TABLE_RESTAURANT + " WHERE "
                + RESTAURANT_ID + " = " + rest_id;

        //String selectQuery = "SELECT  * FROM " + TABLE_USER;


        Cursor c = db.rawQuery(selectQuery, null);

        int a = -1;

        if (c != null){
            if(c.getCount() != 0){
                c.moveToFirst();
                result[0] = c.getString(c.getColumnIndex(NAME));
                result[1] = c.getString(c.getColumnIndex(LOCATION));
            }

        }

        return result;
    }

    /*
     * get gets a user by their username and password
     */
    public String[] getRecipesById(int rest_id) {
        SQLiteDatabase db = this.getReadableDatabase();

        String [] result = new String[3];

        String selectQuery = "SELECT  * FROM " + TABLE_RECIPE + " WHERE "
                + RECIPE_ID + " = " + rest_id;

        Cursor c = db.rawQuery(selectQuery, null);

        int a = -1;

        if (c != null){
            if(c.getCount() != 0){
                c.moveToFirst();
                result[0] = c.getString(c.getColumnIndex(NAME));
                result[1] = c.getString(c.getColumnIndex(U));
                result[2] = c.getString(c.getColumnIndex(I));
            }

        }

        return result;
    }

    /*
     * get gets a user by their username and password
     */
    public int getRecipe(String name) {
        SQLiteDatabase db = this.getReadableDatabase();

        String newname = name.replaceAll("'","");

        String selectQuery = "SELECT  * FROM " + TABLE_RECIPE + " WHERE "
                + NAME + " LIKE '" + newname + "'";

        //String selectQuery = "SELECT  * FROM " + TABLE_USER;


        Cursor c = db.rawQuery(selectQuery, null);

        int a = -1;

        if (c != null){
            if(c.getCount() != 0){
                c.moveToFirst();
                a = c.getInt(c.getColumnIndex(RECIPE_ID));
            }

        }

        return a;
    }

    public int getFavoriteRestaurant(int rest_id) {
        SQLiteDatabase db = this.getReadableDatabase();

        User u = User.getInstance();

        String selectQuery = "SELECT  * FROM " + TABLE_FAVORITE_RESTAURANT + " WHERE "
                + USER_ID + " = '" + u.id + "' AND " + RESTAURANT_ID + " = '" + rest_id + "'";

        //String selectQuery = "SELECT  * FROM " + TABLE_USER;


        Cursor c = db.rawQuery(selectQuery, null);

        int a = -1;

        if (c != null){
            if(c.getCount() != 0){
                c.moveToFirst();
                a = c.getInt(c.getColumnIndex(FAVORITE_RESTAURANT_ID));
            }

        }

        return a;
    }

    public int getFavoriteRecipe(int rest_id) {
        SQLiteDatabase db = this.getReadableDatabase();

        User u = User.getInstance();

        String selectQuery = "SELECT  * FROM " + TABLE_FAVORITE_RECIPES + " WHERE "
                + USER_ID + " = '" + u.id + "' AND " + RECIPE_ID + " = '" + rest_id + "'";

        //String selectQuery = "SELECT  * FROM " + TABLE_USER;


        Cursor c = db.rawQuery(selectQuery, null);

        int a = -1;

        if (c != null){
            if(c.getCount() != 0){
                c.moveToFirst();
                a = c.getInt(c.getColumnIndex(FAVORITE_RECIPE_ID));
            }

        }

        return a;
    }

    public long saveFavoriteRestaurant(String name, String location){
        long fav_id = -1;
        SQLiteDatabase db = this.getWritableDatabase();
        //see if restaurant already exists in db
        int rest_id = getRestaurant(name, location);

        //yes - get id and link to user id
        if(rest_id != -1){

            int savedBefore = getFavoriteRestaurant(rest_id);

            if(savedBefore != -1){
                fav_id = -2;
            } else {
                // insert row
                fav_id = createFavoriteRestaurant(rest_id);
            }


        } else {
            //no - add restaurant to db and link to user
            long id = createRestaurant(name, location);

            rest_id = Math.round(id);

            fav_id = createFavoriteRestaurant(rest_id);

        }

        User u = User.getInstance();
        u.hasRestDBChanged = true;


        return fav_id;
    }

    public long saveFavoriteRecipe(String name, String url, String image){
        long fav_id = -1;
        SQLiteDatabase db = this.getWritableDatabase();
        //see if restaurant already exists in db
        int rest_id = getRecipe(name);

        //yes - get id and link to user id
        if(rest_id != -1){

            int savedBefore = getFavoriteRecipe(rest_id);

            if(savedBefore != -1){
                fav_id = -2;
            } else {
                // insert row
                fav_id = createFavoriteRecipe(rest_id);
            }


        } else {
            //no - add restaurant to db and link to user
            long id = createRecipe(name, url, image);

            rest_id = Math.round(id);

            fav_id = createFavoriteRecipe(rest_id);

        }

        User u = User.getInstance();
        u.hasRecDBChanged = true;

        return fav_id;
    }


    public ArrayList<RecipeObject> getFavoriteRecipes(){
        ArrayList<RecipeObject> recipes = new ArrayList<>();

        SQLiteDatabase db = this.getReadableDatabase();

        User u = User.getInstance();

        String selectQuery = "SELECT  * FROM " + TABLE_FAVORITE_RECIPES + " WHERE "
                + USER_ID + " = '" + u.id + "'";

        //String selectQuery = "SELECT  * FROM " + TABLE_USER;


        Cursor c = db.rawQuery(selectQuery, null);

        int a = -1;

        if (c != null){
            if(c.getCount() != 0){
                int i = 0;
                c.moveToFirst();
                while (i < c.getCount()){
                    a = c.getInt(c.getColumnIndex(RECIPE_ID));
                    if(a > 0){
                        //we have a recipe, need to get its info
                        String [] info = new String[3];
                        info = getRecipesById(a);
                        RecipeObject r = new RecipeObject(info[0], info[1], info[2]);
                        recipes.add(r);
                    }
                    c.moveToNext();
                    i++;
                }

            }

        }

        return recipes;
    }

    public ArrayList<Restaurant> getFavoriteRestaurants(){
        ArrayList<Restaurant> restaurants = new ArrayList<>();

        SQLiteDatabase db = this.getReadableDatabase();

        User u = User.getInstance();

        String selectQuery = "SELECT  * FROM " + TABLE_FAVORITE_RESTAURANT + " WHERE "
                + USER_ID + " = '" + u.id + "'";

        //String selectQuery = "SELECT  * FROM " + TABLE_USER;


        Cursor c = db.rawQuery(selectQuery, null);

        int a = -1;

        if (c != null){
            if(c.getCount() != 0){
                int i = 0;
                c.moveToFirst();
                while (i < c.getCount()){
                    a = c.getInt(c.getColumnIndex(RESTAURANT_ID));
                    if(a > 0){
                        //we have a restaurant, need to get its info
                        String [] info = new String[2];
                        info = getRestaurantById(a);
                        Restaurant r = new Restaurant(info[0], info[1]);
                        restaurants.add(r);
                    }
                    c.moveToNext();
                    i++;
                }

            }

        }

        return restaurants;
    }

    public void deleteFavoriteRestaurant(String name, String location){
        long fav_id = -1;
        SQLiteDatabase db = this.getWritableDatabase();
        //see if restaurant already exists in db
        int rest_id = getRestaurant(name, location);

        int id = getFavoriteRestaurant(rest_id);

        if(id != -1){
            db.delete(TABLE_FAVORITE_RESTAURANT, FAVORITE_RESTAURANT_ID + " = ?",
                    new String[] { String.valueOf(id) });
        }

    }

    public void deleteFavoriteRecipe(String name){
        long fav_id = -1;
        SQLiteDatabase db = this.getWritableDatabase();
        //see if restaurant already exists in db
        int rest_id = getRecipe(name);

        int id = getFavoriteRecipe(rest_id);

        if(id != -1){
            db.delete(TABLE_FAVORITE_RECIPES, FAVORITE_RECIPE_ID + " = ?",
                    new String[] { String.valueOf(id) });
        }

    }
}
