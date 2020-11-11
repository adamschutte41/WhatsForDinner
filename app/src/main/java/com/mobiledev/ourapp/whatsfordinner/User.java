package com.mobiledev.ourapp.whatsfordinner;

import android.content.Context;

public class User {

    private static User user;
    long id;
    String username;
    String password;
    DatabaseHelper db;

    public static synchronized User getInstance(String username, String password, Context context) {

        // Use the application context, which will ensure that you
        // don't accidentally leak an Activity's context.
        // See this article for more information: http://bit.ly/6LRzfx
        if (user == null) {
            user = new User(username, password, context);
        }
        return user;
    }

    private User(String username, String password, Context context) {
        this.username = username;
        this.password = password;
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
}
