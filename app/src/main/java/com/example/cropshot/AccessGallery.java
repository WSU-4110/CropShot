package com.example.cropshot;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Environment;

import java.io.File;

public class AccessGallery {
    private static AccessGallery INSTANCE = null;

    private AccessGallery() {};

    // If the class doesn't already exist, create a new instance of it
    // otherwise, return the currently existing instance.
    public static AccessGallery getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new AccessGallery();
        }
        return(INSTANCE);
    }

    public void getImageFromGallery(Activity activity, int requestCode)
    {
        // Invoke the image gallery using an implicit intent
        Intent photoPickerintent = new Intent(Intent.ACTION_PICK);

        // Where do we want to find the data?
        File pictureDirectory = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);

        String pictureDirectoryPath = pictureDirectory.getPath();

        // Finally, get a URI representation
        Uri data = Uri.parse(pictureDirectoryPath);

        // Set the data and type Get all images types
        photoPickerintent.setDataAndType(data, "image/*");

        activity.startActivityForResult(photoPickerintent, requestCode);
    }

    public void scanImageGallery()
    {

    }

}
