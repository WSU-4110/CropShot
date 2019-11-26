package com.example.cropshot;

import android.content.Intent;
import android.graphics.Bitmap;

import org.junit.Test;

import static org.junit.Assert.*;

public class MainActivityTest extends MainActivity{

    @Test
    public void onCropClick() {

        try {
            // If the user disables the useML functionality don't do this check
            if(SettingsSingleton.getInstance().getUseML())
            {
                // Create firebase object
                FirebaseDetection firebaseDetectionObject = new FirebaseDetection(this);

                // Create a small version of the bitmap at the top of the screen
                // (Where the words instagram are) to scan for the text "instagram"
                Bitmap instaCrop = Bitmap.createBitmap(bitMap, 0, 0, bitMap.getWidth(), 200);
                firebaseDetectionObject.runTextDetection(instaCrop);
            }
            else
            {
                cropIfImageDetected();
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void openManualCrop() {

        Intent intent = new Intent(this,ManualCrop.class);

        if(contentURI != null)
            intent.putExtra("imageUri", contentURI.toString());
        startActivity(intent);
    }

    @Test
    public void openSettings() {

        Intent intent = new Intent(this, SettingsActivity.class);
        startActivity(intent);
    }
}