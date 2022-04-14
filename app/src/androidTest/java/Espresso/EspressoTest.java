package Espresso;

import static androidx.test.espresso.Espresso.*;
import static androidx.test.espresso.assertion.PositionAssertions.isCompletelyLeftOf;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.*;


import android.view.View;

import androidx.test.core.app.ActivityScenario;
import androidx.test.espresso.action.ViewActions;
import androidx.test.espresso.matcher.ViewMatchers;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.filters.LargeTest;

import com.example.cookbook.*;
import com.example.cookbook.R;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(AndroidJUnit4.class)
@LargeTest
public class EspressoTest {


    @Rule
    public ActivityScenarioRule<LoginActivity> activityRule =
            new ActivityScenarioRule<LoginActivity>(LoginActivity.class);

    @Test
    public void logInWithValidCredentials() {

        onView(withText("Log In")).check(matches(isDisplayed()));

        onView(ViewMatchers.withId(R.id.editTextTextEmailAddress2)).perform(ViewActions.typeText("ben@fake.com"));
        onView(ViewMatchers.withId(R.id.editTextTextPassword2)).perform(ViewActions.typeText("1234567"));

        onView(ViewMatchers.withId(R.id.login2)).perform(ViewActions.click());

    }
}
