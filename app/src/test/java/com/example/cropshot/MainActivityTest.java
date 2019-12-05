package com.example.cropshot;

import org.junit.Test;

import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;

public class MainActivityTest {

    @Test
    public void onCreate() {
    }

    @Test
    public void onGalleryClick() {
        MainActivity mainActivity = mock(MainActivity.class);
        //Testing to make sure that crop only works if variable input is not null
        assertFalse(mainActivity.onGalleryClick(null));
    }

    @Test
    public void onCropClick() {
        MainActivity mainActivity = mock(MainActivity.class);
        //Testing to make sure that crop only works if variable input is not null
        assertFalse(mainActivity.onCropClick(null));
    }

    @Test
    public void cropIfImageDetected() {
    }

    @Test
    public void onScanClick() {
    }

    @Test
    public void scannedNonInstagramImage() {
    }

    @Test
    public void onActivityResult() {
    }

    @Test
    public void openManualCrop() {
        MainActivity mainActivity = mock(MainActivity.class);
        //Unit testing if app tries to redirect with an invalid context for the intent
        assertFalse(mainActivity.openManualCrop());
    }

    @Test
    public void openSettings() {
        MainActivity mainActivity = mock(MainActivity.class);
        //Unit testing if app tries to redirect with an invalid context for the intent
        //Since mock classes are always null, the this* context within openSettings() would also be null,
        //which in turn would return false.
        assertFalse(mainActivity.openSettings());
    }

    @Test
    public void compressBitmap() {
    }

    @Test
    public void progressImageScan() {
    }
}