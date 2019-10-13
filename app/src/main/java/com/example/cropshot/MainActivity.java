package com.example.cropshot;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.ColorSpace;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.View;
import android.widget.ImageView;
import android.graphics.Color;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import android.util.Log;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import java.io.*;
import java.util.Random;

public class MainActivity extends AppCompatActivity {

    public static final int IMAGE_GALLERY_REQUEST = 20;

    // We need access to our image view
    ImageView imageView;
    Bitmap bitMap;
    Uri contentURI;

    enum DIR {TOP, BOTTOM}

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        // ------------ TEMPLATE CODE --------------

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        BottomNavigationView navView = findViewById(R.id.nav_view);
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
                R.id.navigation_home, R.id.navigation_dashboard, R.id.navigation_notifications)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
        NavigationUI.setupWithNavController(navView, navController);

        // ------------ TEMPLATE CODE --------------

        // Get access to the Cropping image image view, and store it in a variable
        imageView = (ImageView)findViewById(R.id.CroppingImg);
    }

    public void onGalleryClick(View v)
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

        startActivityForResult(photoPickerintent, IMAGE_GALLERY_REQUEST);
    }

    public void onCropClick(View v)
    {
        try {


            // Send uri of image to get cropped
            bitMap = cropImage(getApplicationContext(), contentURI);
            saveImage(bitMap, "IMG300");

            imageView.setImageBitmap(bitMap);


        }catch (Exception e){
            e.printStackTrace();
        }
    }



    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        // Only preform operations if we know that our result has successfully happened
        if(resultCode == RESULT_OK)
        {
            if(requestCode == IMAGE_GALLERY_REQUEST)
            {
                try
                {
                    // Let's get the URI (or address) of the image our user has selected
                    contentURI = data.getData();

                    // Set our imageView to the URI of the selected image from the gallery
                    imageView.setImageURI(contentURI);
                }catch(Exception e)
                {
                    e.printStackTrace();
                }
            }
        }
    }

    public Bitmap cropImage(Context context, Uri userImage) throws Exception {


        //Convert uri image to bitmap
        Bitmap bitmap = MediaStore.Images.Media.getBitmap(context.getContentResolver(), userImage);

        //Crop out top 14 of height off image.
        Bitmap resizedBitmap1 = Bitmap.createBitmap(bitmap, 0, 120, bitmap.getWidth(), bitmap.getHeight() - 200);

        return resizedBitmap1;

    }

    private void saveImage(Bitmap finalBitmap, String image_name) {


        String root = Environment.getExternalStorageDirectory().toString();
        File myDir = new File(root + "/saved_images");
        myDir.mkdirs();
        Random generator = new Random();
        int n = 10000;
        n = generator.nextInt(n);
        String fname = "Image-"+ n +".jpg";
        File file = new File(myDir, fname);
        if (file.exists())
            file.delete();
        Log.i("LOAD", root + fname);
        try {
            FileOutputStream out = new FileOutputStream(file);
            finalBitmap.compress(Bitmap.CompressFormat.JPEG, 90, out);
            out.flush();
            out.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Checks the color of the left and right pixel
    // Returns true if the pixels are in a similar range
    // And false otherwise
    boolean CheckColor(Color left, Color right)
    {
        return true;
    }

    // Takes the bitmap, and given a direction (Top or bottom)
    // Scans for pixels that are identical on the same line
    // In order to find the top/bottom of an image
    int FindBorder(DIR direction)
    {
        // Get the middle position of the bitmap
        int middleY = bitMap.getHeight() / 2;

        if(direction == DIR.TOP)
        {
            // Let's start with I at the middle of the image, and move up until we reach the top
            for (int i = middleY; i < bitMap.getHeight(); i++)
            {
                // Get the color of the left pixel
                Color leftColor;
                //leftColor = bitMap.getPixel(0, i);
                // Get the color of the right pixel
                Color rightColor;
                //rightColor = bitMap.getPixel(bitMap.getWidth() - 1, i)

                // if the colors are the same we want to return our i value for the top
                //if(CheckColor(leftColor, rightColor))
                {
                    return i;
                }
            }

        }
        else
        {
            // Let's start with I at the middle of the image, and move down until we reach the bottom
            for (int i = middleY; i > 0; i--)
            {
                // Get the color of the left pixel
                Color leftColor;
                //leftColor = bitMap.getPixel(0, i);
                // Get the color of the right pixel
                Color rightColor;
                //rightColor = bitMap.getPixel(bitMap.getWidth() - 1, i)

                // if the colors are the same we want to return our i value for the top
                //if(CheckColor(leftColor, rightColor))
                {
                    return i;
                }
            }
        }
        return 0;
    }

    // Given a row of pixels in a bitmap, return true if all pixels
    // Are the same color, otherwise return false.
    boolean SolidRow(Bitmap row)
    {

        int length = row.getWidth();
        int height = row.getHeight();
        int max = length - 1;
        int check = 0;

        for(int i = 0; i < length; i++){

            int left_pixel = row.getPixel(i,height);
            int right_pixel = row.getPixel(max,height);

            int leftRed = Color.red(left_pixel);
            int leftBlue = Color.blue(left_pixel);
            int leftGreen = Color.green(left_pixel);

            int rightRed = Color.red(left_pixel);
            int rightBlue = Color.blue(left_pixel);
            int rightGreen = Color.green(left_pixel);

            if((leftRed == rightRed) && (leftBlue == rightBlue) && (leftGreen == rightGreen)){

                max = max -1;
                check = 1;
            }

            else{
                check = 0;
                break;
            }


        }

        if(check == 0)
            return false;
        else
            return true;
    }


    }



