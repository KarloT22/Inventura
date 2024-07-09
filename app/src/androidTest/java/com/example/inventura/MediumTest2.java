package com.example.inventura;
import static androidx.test.espresso.Espresso.onData;
import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.longClick;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.matcher.RootMatchers.isDialog;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;

import static java.util.regex.Pattern.matches;

import org.junit.Rule;
import org.junit.runner.RunWith;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import androidx.test.core.app.ActivityScenario;
import androidx.test.espresso.Espresso;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import static org.hamcrest.CoreMatchers.anything;
import static org.hamcrest.Matchers.containsString;
import static androidx.test.espresso.assertion.ViewAssertions.matches;

@RunWith(AndroidJUnit4.class)
public class MediumTest2 {
    private void waitFor(long millis) {
        try {
            Thread.sleep(millis);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }}
    @Rule
    public ActivityScenarioRule<MainActivity> activityRule =
            new ActivityScenarioRule<>(MainActivity.class);


    @Test
    public void testUI() {
        onView (withId(R.id.gumb_inventura)).perform(click());
        String TEXT = "Jaja";
        String TEXT1 = "10";
        String TEXT2 = "paket";
        onView (withId(R.id.editText1)).perform(typeText(TEXT));
        onView (withId(R.id.editText2)).perform(typeText(TEXT1));
        onView (withId(R.id.editText3)).perform(typeText(TEXT2));
        Espresso.closeSoftKeyboard();
        onView (withId(R.id.gumb_dodaj)).perform(click());
        onView (withId(R.id.gumb_pregled)).perform(click());

        onData(anything())
                .inAdapterView(withId(R.id.listView1))
                .atPosition(0)
                .perform(longClick());
        onView(withText("Da"))
                .inRoot(isDialog())
                .check(matches(isDisplayed()))
                .perform(click());
        waitFor(5000);
    }
}