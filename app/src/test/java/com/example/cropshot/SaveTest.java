package com.example.cropshot;

import android.content.Context;
import android.graphics.Bitmap;
import android.media.MediaScannerConnection;
import android.util.Log;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

import java.io.File;
import java.util.Random;

import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;

@RunWith(MockitoJUnitRunner.class)
public class SaveTest {
    @Test
    public void mainDirectoryTest() {
        Save activity = mock(Save.class);
        Log log = mock(Log.class);
        File testerfile = new File("/sdcard/Pictures");
        File tester2 = new File("/sdcard/notarealdirectory");
        Context context = mock(Context.class);
        assertNotEquals(testerfile, activity.mainDirectory(context));
        assertNotEquals(tester2, activity.mainDirectory(context));
    }

}
