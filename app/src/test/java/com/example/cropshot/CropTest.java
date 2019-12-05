package com.example.cropshot;

import android.graphics.Bitmap;

import org.junit.Test;

import static org.junit.Assert.*;

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
        MainActivity MATEST = mock (MainActivity.class);
        assertTrue(CropTest.FindBorder(MATEST.DIR.TOP,bit));

    }

    @Test
    public void solidRow() {

        Crop CropTest = new Crop();
        Bitmap bit = mock(Bitmap.class);

        assertTrue(CropTest.SolidRow(bit));
    }
}