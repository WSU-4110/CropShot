package com.example.cropshot;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.net.Uri;
import android.provider.MediaStore;
import android.widget.ImageView;

import java.io.ByteArrayOutputStream;
import java.net.URI;

public class Crop {

    public Bitmap cropImage(Uri contentURI, ImageView imageView, Activity activity)
    {
        try {

            // This function only gets called if firebase detects the string instagram in the image
            Bitmap bitmap = MediaStore.Images.Media.getBitmap(activity.getApplication().getContentResolver(), contentURI);
            System.out.println("Cropping");

            // Call the FindBorder function for both top and bottom, to find the top and bottom border heights
            int topCropInt = FindBorder(MainActivity.DIR.TOP, bitmap);
            int bottomCropInt = FindBorder(MainActivity.DIR.BOTTOM, bitmap);


            bottomCropInt = bitmap.getHeight() - bottomCropInt;

            // Crop the top of the bitmap. Because bitmaps 0,0 starts in upper left, we must insert topCropInt as the
            // Lower bounded value
            Bitmap croppedMap = Bitmap.createBitmap(bitmap, 0, topCropInt, bitmap.getWidth(), bitmap.getHeight() - topCropInt - bottomCropInt);

            //saveImage(bitMap, "IMG300");
            imageView.setImageBitmap(croppedMap);

            return croppedMap;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    // Checks the color of the left and right pixel
    // Returns true if the pixels are in a similar range
    // And false otherwise
    boolean CheckColor(int left, int right){
        int leftSum = Color.red(left) + Color.green(left) + Color.blue(left);
        int rightSum = Color.red(right) + Color.green(right) + Color.blue(right);
        int diff = leftSum - rightSum;


        if (diff < 10 && diff > -10) {
            return true;
        }
        else
            return false;
    }

    // Takes the bitmap, and given a direction (Top or bottom)
    // Scans for pixels that are identical on the same line
    // In order to find the top/bottom of an image
    int FindBorder(MainActivity.DIR direction, Bitmap bitmap) {
        // Get the middle position of the bitmap
        int middleY = bitmap.getHeight() / 2;

        if (direction == MainActivity.DIR.BOTTOM) {
            // Let's start with I at the middle of the image, and move positively until we reach the bottom
            for (int i = middleY; i < bitmap.getHeight(); i++)
            {

                // Generate the single rowed bitmap
                Bitmap subMap = Bitmap.createBitmap(bitmap, 0, i, bitmap.getWidth(), 1);
                int pixel = subMap.getPixel(0,0);

                if(SolidRow(subMap) && Color.red(pixel) == Color.blue(pixel) && Color.red(pixel) == Color.green(pixel))
                {
                    return i;
                }
            }

            //DIR == TOP
        } else {
            // Let's start with I at the middle of the image, and move negatively until we reach the top
            for (int i = middleY; i > 0; i--)
            {

                // Generate the single rowed bitmap
                Bitmap subMap = Bitmap.createBitmap(bitmap, 0, i, bitmap.getWidth(), 1);
                int pixel = subMap.getPixel(0,0);

                // if the colors are the same we want to return our i value for the top
                // Also if the color is white (255,255,255), as that designates top of image.
                if(SolidRow(subMap))
                {
                    return i;
                }
            }
        }
        return 0;
    }

    // NOTE: This doesn't actually check if all the pixels are the same!
    // It only checks if the pixels across from eachother are, i.e.
    // (left, right), (left + 1, right - 1)

    // Given a row of pixels in a bitmap, return true if all pixels
    // Are the same color, otherwise return false.
    boolean SolidRow(Bitmap row)
    {
        //Getting length and height for the bitmap
        int length = row.getWidth();
        int height = row.getHeight();
        int max = length - 1;
        int pixel = row.getPixel(0,0);

        //Iterates through the bitmap row
        for(int i = 1; i < (length - 1); i++) {
            //Gets variables
            int left_pixel = row.getPixel(0, height - 1);
            int right_pixel = row.getPixel(i, height - 1);


            //Checks if the pixels are the same color or if the pixels meet
            if (!CheckColor(left_pixel, right_pixel)) {
                return false;
            }
        }
        return true;
    }

}
