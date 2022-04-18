package Espresso;

import static androidx.test.espresso.Espresso.*;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.*;

import androidx.test.core.app.ActivityScenario;
import androidx.test.espresso.action.ViewActions;
import androidx.test.espresso.contrib.RecyclerViewActions;
import androidx.test.espresso.matcher.ViewMatchers;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.filters.LargeTest;

import com.example.cookbook.*;
import com.example.cookbook.R;

import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;

@RunWith(AndroidJUnit4.class)
@LargeTest

@FixMethodOrder(MethodSorters.NAME_ASCENDING)


public class EspressoTest {

    // Logs in with valid credentials which utilizes FirebaseAuth
    @Test
    public void firstLogInWithValidCredentials() {

        ActivityScenario<MainActivity> activityScenario = ActivityScenario.launch(MainActivity.class);
        onView(ViewMatchers.withId(R.id.login)).check(matches(isDisplayed()));

        onView(ViewMatchers.withId(R.id.login)).perform(ViewActions.click());

        onView(ViewMatchers.withId(R.id.login2)).check(matches(isDisplayed()));

        onView(ViewMatchers.withId(R.id.editTextTextEmailAddress2)).perform(ViewActions.typeText("ben@fake.com"));

        onView(ViewMatchers.withId(R.id.editTextTextPassword2)).perform(ViewActions.typeText("1234567"));

        onView(ViewMatchers.isRoot()).perform(ViewActions.closeSoftKeyboard());

        onView(ViewMatchers.withId(R.id.login2)).perform(ViewActions.click());

        ViewMatchers.withId(R.id.button_upload).matches(isClickable());
    }

    // Ensures that a new recipe can be uploaded to Firebase
    @Test
    public void secondAddFirebaseRecipe() {

        ActivityScenario<HomeActivity> activityScenario = ActivityScenario.launch(HomeActivity.class);

        onView(ViewMatchers.withId(R.id.button_upload)).check(matches(isDisplayed()));

        onView(ViewMatchers.withId(R.id.button_upload)).perform(ViewActions.click());

        onView(ViewMatchers.withId(R.id.addPhoto)).check(matches(isDisplayed()));

        onView(ViewMatchers.withId(R.id.recipeName)).perform(ViewActions.typeText("Espresso Test"));

        onView(ViewMatchers.withId(R.id.prepTime)).perform(ViewActions.typeText("10"));
        onView(ViewMatchers.withId(R.id.cookTime)).perform(ViewActions.typeText("10"));

        onView(ViewMatchers.isRoot()).perform(ViewActions.closeSoftKeyboard());

        onView(ViewMatchers.withId(R.id.createRecipe)).perform(ViewActions.click());
    }

    // Ensures that a recipe can be removed from a user's saved recipes
    @Test
    public void thirdRemoveSavedRecipe() {

        ActivityScenario<HomeActivity> activityScenario = ActivityScenario.launch(HomeActivity.class);

        onView(ViewMatchers.withId(R.id.button_viewsaved)).check(matches(isDisplayed()));

        onView(ViewMatchers.withId(R.id.button_viewsaved)).perform(ViewActions.click());

        onView(ViewMatchers.withId(R.id.mRecyclerView)).check(matches(isDisplayed()));

        onView(ViewMatchers.withId(R.id.nameSearch)).check(matches(isDisplayed()));

        onView(ViewMatchers.withId(R.id.nameSearch)).perform(ViewActions.typeText("Espresso Test"));

        onView(ViewMatchers.isRoot()).perform(ViewActions.closeSoftKeyboard());

        onView(withId(R.id.mRecyclerView)).perform(RecyclerViewActions.actionOnItemAtPosition(0, ViewActions.click()));

        onView(ViewMatchers.withId(R.id.removeBtn)).check(matches(isDisplayed()));

        onView(ViewMatchers.withId(R.id.removeBtn)).perform(ViewActions.click());

    }





}
