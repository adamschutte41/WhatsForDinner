package com.mobiledev.ourapp.whatsfordinner;

import static androidx.test.platform.app.InstrumentationRegistry.getInstrumentation;
import android.view.View;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import android.widget.Button;

import androidx.test.annotation.UiThreadTest;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.rule.ActivityTestRule;

import android.os.Bundle;

public class MainActivityTest extends ActivityTestRule<MainActivity>{
    /*@Rule
    public ActivityTestRule<MainActivity> mainActivityTestRule =
            new ActivityTestRule<MainActivity>(MainActivity.class);*/
     private MainActivity mainActivity;

    public MainActivityTest(){
        super(MainActivity.class);
        launchActivity(getActivityIntent());
        mainActivity = getActivity();

    }

    @Before
    public void setup(){
        mainActivity = getActivity();
    }

    @Test
    public void preconditions(){
       assertNotNull(mainActivity);
    }

    @Test
    public void StayInBtnTest() throws Throwable{
        Button btn = mainActivity.findViewById(R.id.stayInBtn);
        assertTrue(btn.performClick());
    }

    @Test
    public void eatOutBtnTest() throws Throwable{
        Button btn = mainActivity.findViewById(R.id.eatOutBtn);
        assertTrue(btn.performClick());
    }

    @Test
    public void settingsBtnTest() throws Throwable{
        Button btn = mainActivity.findViewById(R.id.settingsBtn);
        assertTrue(btn.performClick());
    }

}
