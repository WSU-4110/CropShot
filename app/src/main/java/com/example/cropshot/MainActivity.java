package com.example.cropshot;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.graphics.Color;

import com.example.cropshot.ui.SettingsActivity;

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

    private Button button;
    private Button b_settings;

    // We need access to our image view
    ImageView imageView;
    Bitmap bitMap;
    Uri contentURI;
    Uri postcropURI;
    Bitmap preCrop;
    Bitmap croppedMap;

    enum DIR {TOP, BOTTOM}

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        // ------------ TEMPLATE CODE --------------

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        b_settings = (Button) findViewById(R.id.Settings);
        b_settings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openSettings();
            }
        });

        button = (Button) findViewById(R.id.mcrop);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openManualCrop();
            }
        });

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


    public void onGalleryClick(View v) {
        Load loadObject = new Load();
        loadObject.accessGallery(this, IMAGE_GALLERY_REQUEST);


    }



    public void onCropClick(View v) {
        try {
            // Create firebase object
            FirebaseDetection firebaseDetectionObject = new FirebaseDetection(this);

            // Create a small version of the bitmap at the top of the screen
            // (Where the words instagram are) to scan for the text "instagram"
            Bitmap instaCrop = Bitmap.createBitmap(bitMap, 0, 0, bitMap.getWidth(), 200);
            firebaseDetectionObject.runTextDetection(instaCrop);



        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void cropIfImageDetected()
    {
        System.out.println("cropIfImageDetected Called!");

        Crop cropImg = new Crop();
        croppedMap = cropImg.cropImage(contentURI, imageView, this);

        if(croppedMap == null)
            return;

        Intent postcrop = new Intent(this, PostCropActivity.class);
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        croppedMap.compress(Bitmap.CompressFormat.JPEG, 100, stream);
        byte[] bytes = stream.toByteArray();
        postcrop.putExtra("cropBytes", bytes);
        postcrop.putExtra("precropuri", contentURI.toString());
        startActivity(postcrop);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Only preform operations if we know that our result has successfully happened
        if (resultCode == RESULT_OK) {
            String postcropURIreturn = getIntent().getStringExtra("UriPostCropReset");
            if (postcropURIreturn != null) {
                try {
                    contentURI = Uri.parse(postcropURIreturn);
                    imageView.setImageURI(contentURI);
                    bitMap = MediaStore.Images.Media.getBitmap(getApplication().getContentResolver(), contentURI);
                    preCrop = MediaStore.Images.Media.getBitmap(getApplication().getContentResolver(), contentURI);
                    imageView.setImageBitmap(bitMap);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            else if (requestCode == IMAGE_GALLERY_REQUEST) {
                try {
                    // Let's get the URI (or address) of the image our user has selected
                    contentURI = data.getData();

                    // Set our imageView to the URI of the selected image from the gallery
                    imageView.setImageURI(contentURI);
                    bitMap = MediaStore.Images.Media.getBitmap(getApplication().getContentResolver(), contentURI);
                    preCrop = MediaStore.Images.Media.getBitmap(getApplication().getContentResolver(), contentURI);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }




    protected void overwriteImage(Bitmap finalBitmap) {
        String root = Environment.getExternalStorageDirectory().toString();
        File myDir = new File(root + "/saved_images");
        myDir.mkdirs();
    }



    protected void saveImage(Bitmap finalBitmap) {

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


    public void openManualCrop(){
        Intent intent = new Intent(this,ManualCrop.class);

        if(contentURI != null)
            intent.putExtra("imageUri", contentURI.toString());
        startActivity(intent);
    }

    public void openSettings(){
        Intent intent = new Intent(this, SettingsActivity.class);
        startActivity(intent);
    }
}

