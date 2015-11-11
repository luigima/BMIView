package com.luigima.bmiview;

import android.test.mock.MockContext;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * To work on unit tests, switch the Test Artifact in the Build Variants view.
 */
public class BMIViewTest {
    BMIView bmiView;

    @Before
    public void setUp() throws Exception {
        bmiView = new BMIView(null);
    }

    @Test
    public void bmiValueIsCorrect() throws Exception {
        bmiView.setHeight(1.85f);
        bmiView.setWeight(85f);
        assertEquals(24.83, bmiView.getBmiValue(), 0.001);

        bmiView.setHeight(0);
        bmiView.setWeight(0);
        assertEquals(0, bmiView.getBmiValue(), 0);
    }
}