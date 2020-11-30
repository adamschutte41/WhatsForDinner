package com.mobiledev.ourapp.whatsfordinner;

import androidx.test.ext.junit.runners.AndroidJUnit4;

import static androidx.test.platform.app.InstrumentationRegistry.getInstrumentation;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.*;

@RunWith(AndroidJUnit4.class)
public class DatabaseTest {
    DatabaseHelper db;

    @Before
    public void setUp(){
        db = DatabaseHelper.getInstance(getInstrumentation().getTargetContext());
    }

    @After
    public void tearDown(){
        db.close();
    }

    @Test
    public void precondition(){
        assertNotNull(db);
    }

    @Test
    public void createRestaurant(){
        long id = db.createRestaurant("name", "loc");
        assertNotNull(id);
    }

    @Test
    public void getRestaurant(){
        long id = db.getRestaurant("name", "loc");
        assertNotEquals(-1, id);
    }

    @Test
    public void saveAndDeleteFavoriteRestaurant(){
        long id = db.saveFavoriteRestaurant("name", "loc");
        assertNotEquals(-1, id);
        db.deleteFavoriteRestaurant("name", "loc");
        int newId = db.getFavoriteRestaurant((int)id);
        assertEquals(-1, newId);
    }

    @Test
    public void createUser(){
        User user = User.getInstance();
        user.setUsername("username");
        user.setPassword("password");
        long id = db.createUser(user);
        assertNotEquals(-1, id);

    }

    @Test
    public void editUser() {
        db.editUser("username", "password", "user", "pass");
        long id = db.getUser("username", "password");
        assertEquals(-1, id);
    }




}
