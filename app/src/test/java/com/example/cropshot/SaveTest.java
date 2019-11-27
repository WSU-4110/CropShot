package com.example.cropshot;

import android.content.Intent;
import android.graphics.Bitmap;
import android.view.View;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;



@RunWith(MockitoJUnitRunner.class)
public class SaveTest {
    @Test
    public void saveAsNewTest() {
        Bitmap bitmap = mock(Bitmap.class);
        Save activity = mock(Save.class);
        assertFalse(activity.saveAsNew(bitmap));
    }
}