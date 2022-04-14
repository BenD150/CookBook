package Espresso;

import static androidx.test.espresso.Espresso.*;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.*;


import android.support.test.espresso.intent.rule.IntentsTestRule;
import android.view.View;

import androidx.fragment.app.testing.FragmentScenario;
import androidx.test.core.app.ActivityScenario;
import androidx.test.espresso.action.ViewActions;
import androidx.test.espresso.matcher.ViewMatchers;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.filters.LargeTest;

import com.example.cookbook.*;
import com.example.cookbook.R;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(AndroidJUnit4.class)
@LargeTest

public class EspressoTest {

    @Test
    public void logInWithValidCredentials() {

        ActivityScenario<MainActivity> activityScenario = ActivityScenario.launch(MainActivity.class);
        onView(ViewMatchers.withId(R.id.login)).check(matches(isDisplayed()));

        onView(ViewMatchers.withId(R.id.login)).perform(ViewActions.click());

        onView(ViewMatchers.withId(R.id.login2)).check(matches(isDisplayed()));

        onView(ViewMatchers.withId(R.id.editTextTextEmailAddress2)).perform(ViewActions.typeText("ben@fake.com"));
        onView(ViewMatchers.withId(R.id.editTextTextPassword2)).perform(ViewActions.typeText("1234567"));

        ViewActions.closeSoftKeyboard();

        onView(ViewMatchers.withId(R.id.login2)).perform(ViewActions.click());

        ViewMatchers.withId(R.id.button_upload).matches(isClickable());
    }

    @Test
    public void addFirebaseRecipe() {

        ActivityScenario<HomeActivity> activityScenario = ActivityScenario.launch(HomeActivity.class);

        onView(ViewMatchers.withId(R.id.button_upload)).perform(ViewActions.click());

        onView(ViewMatchers.withId(R.id.addPhoto)).check(matches(isDisplayed()));

        onView(ViewMatchers.withId(R.id.recipeName)).perform(ViewActions.typeText("Espresso"));

        onView(ViewMatchers.withId(R.id.prepTime)).perform(ViewActions.typeText("10"));
        onView(ViewMatchers.withId(R.id.cookTime)).perform(ViewActions.typeText("10"));

        onView(ViewMatchers.isRoot()).perform(ViewActions.closeSoftKeyboard());

        onView(ViewMatchers.withId(R.id.createRecipe)).perform(ViewActions.click());
    }





}
