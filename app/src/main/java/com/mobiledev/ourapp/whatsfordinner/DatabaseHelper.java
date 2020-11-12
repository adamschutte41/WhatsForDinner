package com.mobiledev.ourapp.whatsfordinner;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import java.util.ArrayList;

public class DatabaseHelper extends SQLiteOpenHelper {

    // Database Version
    private static final int DATABASE_VERSION = 1;

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
            + " TEXT," + PASSWORD + " TEXT," + LOCATION + " TEXT" + ")";

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
            + " TEXT," + TYPE + " TEXT" + ")";

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
     * get gets a user by their username and password
     */
    public int getUser(String username, String password) {
        SQLiteDatabase db = this.getReadableDatabase();

        String selectQuery = "SELECT  * FROM " + TABLE_USER + " WHERE "
                + USERNAME + " LIKE '" + username + "' AND " + PASSWORD + " LIKE '" + password + "'";

        //String selectQuery = "SELECT  * FROM " + TABLE_USER;


        Cursor c = db.rawQuery(selectQuery, null);

        int a = -1;
        if (c.getCount() != 0){
            c.moveToFirst();
            a = c.getInt(c.getColumnIndex(USER_ID));
        }

        return a;
    }
}
