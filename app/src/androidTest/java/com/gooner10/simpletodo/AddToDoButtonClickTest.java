package com.gooner10.simpletodo;

import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.test.suitebuilder.annotation.LargeTest;

import com.gooner10.simpletodo.todo.ToDoActivity;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.matcher.ViewMatchers.withId;

/**
 * Espresso UI Test to check the AddTo Dialog
 */

@RunWith(AndroidJUnit4.class)
@LargeTest
public class AddToDoButtonClickTest {

    @Rule
    public ActivityTestRule<ToDoActivity> mToDoActivity = new ActivityTestRule<ToDoActivity>(ToDoActivity.class);

    @Test
    public void onFabButtonClicked(){
        // Click on the add note button
        onView(withId(R.id.fab)).perform(click());

        // Check if the add note screen is displayed
//        onView(withId(R.id.add_note_title)).check(matches(isDisplayed()));
    }
}
