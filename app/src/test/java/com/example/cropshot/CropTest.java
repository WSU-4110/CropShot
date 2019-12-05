package com.example.cropshot;

import android.graphics.Bitmap;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;

@RunWith(MockitoJUnitRunner.class)
public class CropTest {

    @Test
    public void checkColor() {
        Crop CropTest = new Crop();

        assertTrue(CropTest.CheckColor(20,25));
        assertTrue(CropTest.CheckColor(25,25));
        assertFalse(CropTest.CheckColor(100,25));
    }

    @Test
    public void findBorder() {

        Crop CropTest = new Crop();
        Bitmap bit = mock(Bitmap.class);
        MainActivity.DIR dir = MainActivity.DIR.BOTTOM;
        assertEquals(0, CropTest.FindBorder(dir,bit));

    }

    @Test
    public void solidRow() {

        Crop CropTest = new Crop();
        Bitmap bit = mock(Bitmap.class);

        assertTrue(CropTest.SolidRow(bit));
    }
}