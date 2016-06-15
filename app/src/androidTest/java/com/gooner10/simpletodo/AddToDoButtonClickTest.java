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
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;

/**
 * Espresso UI Test to check the AddTo Dialog
 */

@RunWith(AndroidJUnit4.class)
@LargeTest
public class AddToDoButtonClickTest {

    @Rule
    public ActivityTestRule<ToDoActivity> mToDoActivity = new ActivityTestRule<>(ToDoActivity.class);

    @Test
    public void shouldBeAbleToShowDialogFragmentOnFabButtonClicked() {
        // Click on the add note button
        onView(withId(R.id.fab)).perform(click());

        // Check if the add note screen is displayed
        onView(withId(R.id.addToDoText)).check(matches(isDisplayed()));
    }

    @Test
    public void shouldBeAbleToAddNewToDoOnFabButtonClicked() {
        // Click on the add note button
        onView(withId(R.id.fab)).perform(click());

        // Add new ToDoItem in EditText
        onView(withId(R.id.addToDoText)).perform(typeText("My ToDo"));

        // Click on the Add button
        onView(withId(R.id.add_input_btn)).perform(click());

        // Check if view on recycler view has the new ToDoItem
        onView(withText("My ToDo")).check(matches(isDisplayed()));
    }
}
