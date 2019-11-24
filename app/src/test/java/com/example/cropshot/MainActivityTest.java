package com.example.cropshot;

import android.graphics.Bitmap;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;


@RunWith(MockitoJUnitRunner.class)
public class MainActivityTest {

    @Test
    public void cropIfImageDetectedTest() {

        Bitmap img_Bmp = mock(Bitmap.class);

        MainActivity activity =  mock(MainActivity.class);

        assertFalse(activity.cropIfImageDetected(img_Bmp));
    }

    @Test
    public void startPostCropTest() {
        byte[] nullBytes = null;

        MainActivity activity =  mock(MainActivity.class);

        assertFalse(activity.startPostCrop(nullBytes));

        byte[] bytes = "TestData".getBytes();

        assertFalse(activity.startPostCrop(bytes));
    }

    @Test
    public void compressBitmapTest() {
        Bitmap img_Bmp = mock(Bitmap.class);

        MainActivity activity =  mock(MainActivity.class);

        assertNull(activity.compressBitmap(img_Bmp));

    }
}