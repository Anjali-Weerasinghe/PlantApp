package com.example.planthome;

import android.content.Intent;

import androidx.recyclerview.widget.RecyclerView;
import androidx.test.espresso.Espresso;
import androidx.test.espresso.contrib.RecyclerViewActions;
import androidx.test.espresso.intent.rule.IntentsTestRule;
import androidx.test.espresso.matcher.ViewMatchers;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.rule.ActivityTestRule;
import androidx.test.runner.intent.IntentStubber;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.intent.Intents.intended;
import static androidx.test.espresso.intent.matcher.IntentMatchers.hasComponent;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;

@RunWith(AndroidJUnit4.class)

public class AdminProfileNavigationTest{

    private static final int ITEMBELOWFINDINLIST = 5;
    @Rule
    public ActivityTestRule<AdminProfileNavigation> adminProfileNavigationActivityTestRule = new ActivityTestRule<>(AdminProfileNavigation.class);

    public IntentsTestRule<AdminProfileNavigation> intentsTestRule = new IntentsTestRule<AdminProfileNavigation>(AdminProfileNavigation.class);


    @Test
    public void recyclTest(){
       Espresso.onView(withId(R.id.recycler_menu)).perform(RecyclerViewActions.actionOnItemAtPosition(1,click()));

    }

    @Test
    public void testOnNavigationItemSelected() {
        onView(withId(R.id.nav_addpot)).perform(click());
        intended(hasComponent(addnewpot.class.getName()));
    }
}