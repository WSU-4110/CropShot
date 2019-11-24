package com.example.cropshot;

import android.graphics.Bitmap;

import com.google.firebase.ml.vision.text.FirebaseVisionText;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;

@RunWith(MockitoJUnitRunner.class)
public class FirebaseDetectionTest {

    @Test
    public void runTextDetectionTest() {
        Bitmap img_Bmp = mock(Bitmap.class);

        FirebaseDetection firebase = mock(FirebaseDetection.class);

        assertFalse(firebase.runTextDetection(img_Bmp));
    }

    @Test
    public void processTextRecognitionResultsTest() {

        FirebaseVisionText firebaseText = mock(FirebaseVisionText.class);

        FirebaseDetection firebase = mock(FirebaseDetection.class);

        assertFalse(firebase.processTextRecognitionResults(firebaseText));

    }

    @Test
    public void checkForInstagramStrTest() {
        FirebaseDetection firebase = new FirebaseDetection(new MainActivity());

        assertTrue(firebase.checkForInstagramStr("Instagram"));
        assertTrue(firebase.checkForInstagramStr("INSTAGRAM"));
        assertTrue(firebase.checkForInstagramStr("nstagram"));
        assertTrue(firebase.checkForInstagramStr("This does contain Instagram in it!"));
        assertFalse(firebase.checkForInstagramStr("This Does not contain what we're looking for"));
        assertFalse(firebase.checkForInstagramStr(""));
    }
}