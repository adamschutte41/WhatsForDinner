package com.mobiledev.ourapp.whatsfordinner;

import android.content.Context;

public class User {

    private static User user;
    long id;
    String username;
    String password;
    DatabaseHelper db;

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
}
