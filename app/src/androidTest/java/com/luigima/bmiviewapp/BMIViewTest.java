package com.luigima.bmiviewapp;

import android.app.Activity;
import android.support.test.InstrumentationRegistry;
import android.support.test.espresso.Espresso;
import android.support.test.espresso.core.deps.guava.collect.Iterables;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.lifecycle.ActivityLifecycleMonitorRegistry;
import android.support.test.runner.lifecycle.Stage;
import android.test.InstrumentationTestCase;
import android.widget.TextView;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.swipeRight;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;


import com.luigima.bmiview.BMIView;
import com.luigima.bmiviewapp.MainActivity;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * To work on unit tests, switch the Test Artifact in the Build Variants view.
 */
public class BMIViewTest {
    private static int MALE = 0;
    private static int FEMALE = 1;

    BMIView bmiView;
    @Rule
    public ActivityTestRule<MainActivity> activityTestRule =
            new ActivityTestRule<MainActivity>(MainActivity.class);

    @Test
    public void bmiValueIsCorrect() throws Exception {
        activityTestRule.getActivity().runOnUiThread(new Runnable() {
            public void run() {
                bmiView = activityTestRule.getActivity().bmiView;

                // First set gender to male
                bmiView.setWeight(80.0f)
                        .setHeight(1.80f)
                        .setGender(MALE);
                assertEquals(24.6f, bmiView.getBmiValue(), 0.1);
                assertEquals("Normal", bmiView.getBodyDescription());

                // Now test female
                bmiView.setGender(FEMALE);
                assertEquals(24.6f, bmiView.getBmiValue(), 0.1);
                assertEquals("Overweight", bmiView.getBodyDescription());

                // Switch back to male
                bmiView.setGender(MALE);
                assertEquals("Normal", bmiView.getBodyDescription());

                // Increase the weight
                bmiView.setWeight(81.0f);
                assertEquals(25.0f, bmiView.getBmiValue(), 0.1);
                assertEquals("Overweight", bmiView.getBodyDescription());

                // Test default values
                bmiView.setWeight(0f)
                        .setHeight(0f)
                        .setGender(MALE);
                assertEquals(0f, bmiView.getBmiValue(), 0.1);
                bmiView.setWeight(1f)
                        .setHeight(0f);
                assertEquals(0f, bmiView.getBmiValue(), 0.1);
                bmiView.setWeight(0f)
                        .setHeight(1f);
                assertEquals(0f, bmiView.getBmiValue(), 0.1);
            }
        });
    }
}