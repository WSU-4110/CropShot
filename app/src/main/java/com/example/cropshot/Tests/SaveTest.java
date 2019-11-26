package com.example.cropshot.Tests;

import android.graphics.Bitmap;

import com.example.cropshot.Save;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

import static org.junit.Assert.assertFalse;
import static org.mockito.Mockito.mock;


@RunWith(MockitoJUnitRunner.class)
public class SaveTest {
    @Test
    public void saveAsNewTest() {
        Bitmap bitmap = mock(Bitmap.class);
        Save activity = mock(Save.class);
        assertFalse(activity.saveAsNew(bitmap));
    }
}