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


import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.ml.vision.FirebaseVision;
import com.google.firebase.ml.vision.common.FirebaseVisionImage;
import com.google.firebase.ml.vision.text.FirebaseVisionText;
import com.google.firebase.ml.vision.text.FirebaseVisionTextRecognizer;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import java.io.*;
import java.util.List;
import java.util.Random;

public class MainActivity extends AppCompatActivity {

    public static final int IMAGE_GALLERY_REQUEST = 20;

    // We need access to our image view
    ImageView imageView;
    Bitmap bitMap;
    Uri contentURI;
    Bitmap preCrop;

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
        imageView = (ImageView) findViewById(R.id.CroppingImg);
    }

    public Bitmap cropImage(Context context, Uri userImage) throws Exception {
        //Convert uri image to bitmap
        Bitmap bitmap = MediaStore.Images.Media.getBitmap(context.getContentResolver(), userImage);
        Bitmap preCrop = MediaStore.Images.Media.getBitmap(context.getContentResolver(), userImage);

        //Crop out top 14 of height off image.
        Bitmap resizedBitmap1 = Bitmap.createBitmap(bitmap, 0, 120, bitmap.getWidth(), bitmap.getHeight() - 200);

        return resizedBitmap1;

    }

    public void onGalleryClick(View v) {
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

    public void onCropClick(View v) {
        try {

            //Convert uri image to bitmap
            bitMap = MediaStore.Images.Media.getBitmap(getApplication().getContentResolver(), contentURI);

            // Create a small version of the bitmap at the top of the screen
            // (Where the words instagram are) to scan for the text "instagram"
            Bitmap instaCrop = Bitmap.createBitmap(bitMap, 0, 0, bitMap.getWidth(), 200);
            RunTextDetection(instaCrop);



        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void cropIfImageDetected()
    {
        // This function only gets called if firebase detects the string instagram in the image

        System.out.println("Cropping");

        // Call the FindBorder function for both top and bottom, to find the top and bottom border heights
        int topCropInt = FindBorder(DIR.TOP);
        int bottomCropInt = FindBorder(DIR.BOTTOM);



        bottomCropInt = bitMap.getHeight() - bottomCropInt;


        // System.out.println("Top = " + topCropInt + "  Bottom = " + bottomCropInt + "  Height = " + (bitMap.getHeight()));

        // Crop the top of the bitmap. Because bitmaps 0,0 starts in upper left, we must insert topCropInt as the
        // Lower bounded value
        Bitmap croppedMap = Bitmap.createBitmap(bitMap, 0, topCropInt, bitMap.getWidth(), bitMap.getHeight() - topCropInt - bottomCropInt);

        //saveImage(bitMap, "IMG300");

        imageView.setImageBitmap(croppedMap);
    }

    public void onDiscardClick(View v)
    {
       try {
           //revert to original display and remove original
           setContentView(R.layout.activity_main);
           imageView.setImageBitmap(preCrop);
       }
       catch(Exception e) {
           e.printStackTrace();
       }
    }

    public void onSaveNewClick (View v)
    {
        try {
            //creates new file
            saveImage(bitMap);
        }
        catch(Exception e) {
            e.printStackTrace();
        }
    }



    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        // Only preform operations if we know that our result has successfully happened
        if (resultCode == RESULT_OK) {
            if (requestCode == IMAGE_GALLERY_REQUEST) {
                try {
                    // Let's get the URI (or address) of the image our user has selected
                    contentURI = data.getData();

                    // Set our imageView to the URI of the selected image from the gallery
                    imageView.setImageURI(contentURI);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }



    private void overwriteImage(Bitmap finalBitmap) {

    }


    private void saveImage(Bitmap finalBitmap) {


        String root = Environment.getExternalStorageDirectory().toString();
        File myDir = new File(root + "/saved_images");
        myDir.mkdirs();
        Random generator = new Random();
        int n = 10000;
        n = generator.nextInt(n);
        String fname = "Image-" + n + ".jpg";
        File file = new File(myDir, fname);
        while (file.exists()) {
            fname = fname+1;
        }
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
    int FindBorder(DIR direction) {
        // Get the middle position of the bitmap
        int middleY = bitMap.getHeight() / 2;

        if (direction == DIR.BOTTOM) {
            // Let's start with I at the middle of the image, and move positively until we reach the bottom
            for (int i = middleY; i < bitMap.getHeight(); i++)
            {

                // Generate the single rowed bitmap
                Bitmap subMap = Bitmap.createBitmap(bitMap, 0, i, bitMap.getWidth(), 1);
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
                Bitmap subMap = Bitmap.createBitmap(bitMap, 0, i, bitMap.getWidth(), 1);
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


    public Bitmap cropSolidRow(Bitmap bitmap, int cropHeight, DIR direction){
        int bitmapWidth = bitmap.getWidth();


        // Crop from solid row upward
        if (direction == DIR.TOP){
            int numOfRows = bitmap.getHeight() - cropHeight;

            Bitmap newBitmap = Bitmap.createBitmap(bitmap, 0, cropHeight, bitmapWidth, numOfRows);
            return newBitmap;
        }

        // Crop from solid row downward
        //else if direction == DIR.BOTTOM
        else {
            // Height - number of rows to delete at end of picture
            int numOfRows = bitmap.getHeight() - (bitmap.getHeight() - cropHeight);

            Bitmap newBitmap = Bitmap.createBitmap(bitmap, 0, 0, bitmapWidth, numOfRows);
            return newBitmap;
        }
    }

    private void RunTextDetection(Bitmap croppedMap)
    {
        FirebaseVisionImage image = FirebaseVisionImage.fromBitmap(croppedMap);
        FirebaseVisionTextRecognizer detector = FirebaseVision.getInstance().getOnDeviceTextRecognizer();

        detector.processImage(image).addOnSuccessListener(
                new OnSuccessListener<FirebaseVisionText>() {
                    @Override
                    public void onSuccess(FirebaseVisionText texts) {
                        System.out.println("Succeeded process image!");
                        ProcessTextRecognitionResults(texts);

                    }
                }

        ).addOnFailureListener(
                new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        System.out.println("Failed to parse image");
                    }
                }
        );
    }

    private void ProcessTextRecognitionResults(FirebaseVisionText texts)
    {
        System.out.println(texts.getText());

        // Get all of the blocks in the current text
        List<FirebaseVisionText.TextBlock> blocks = texts.getTextBlocks();

        if(blocks.size() == 0)
            return;

        for (int i = 0; i < blocks.size(); i++)
        {

            List<FirebaseVisionText.Line> lines = blocks.get(i).getLines();
            for (int j = 0; j < lines.size(); j++)
            {
                List<FirebaseVisionText.Element> elements = lines.get(j).getElements();
                for (int k = 0; k < elements.size(); k++)
                {
                    // The text detection is not perfect, especially in the case of an image that
                    // is slightly more blurry. When weird issues come up, we add a case to account
                    // for those, hence the "instagam"
                    String str = elements.get(k).getText().toLowerCase();
                    if(str.contains("instagram") ||
                            str.contains("instagam") ||
                            str.contains("nstagam"))
                    {
                        System.out.println("Instagram image detected!");
                        cropIfImageDetected();
                        return;
                    }
                }
            }
        }

    }
}