package com.example.cropshot;

import android.Manifest;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
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
import androidx.appcompat.app.AppCompatDelegate;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class MainActivity extends AppCompatActivity {

    public static final int IMAGE_GALLERY_REQUEST = 20;
    private static final int MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE = 30;

    private Button button;
    private Button b_settings;

    // We need access to our image view
    ImageView imageView;
    Bitmap bitMap;
    Uri contentURI;
    Uri postcropURI;
    Bitmap preCrop;
    Bitmap croppedMap;

    // Tracks whether or not we're preforming the image scan operation
    //boolean imageScanning;

    enum DIR {TOP, BOTTOM}

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        if (checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {

            // Should we show an explanation?
            if (shouldShowRequestPermissionRationale(
                    Manifest.permission.READ_EXTERNAL_STORAGE)) {
                // Explain to the user why we need to read the contacts
            }

            requestPermissions(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                    MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE);

            // MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE is an
            // app-defined int constant that should be quite unique

        }

        // ------------ TEMPLATE CODE --------------

        if(SettingsSingleton.getInstance().getDarkMode())
        {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
            setTheme(R.style.darktheme);
        }

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

    public void cropIfImageDetected()
    {
        System.out.println("cropIfImageDetected Called!");

        Crop cropImg = new Crop();
        croppedMap = cropImg.cropImage(contentURI, this);

        if(croppedMap == null)
            return;

        imageView.setImageBitmap(croppedMap);

        // Get a compressed bitmap and pass it into startPostCrop

        startPostCrop(compressBitmap(croppedMap));
    }

    public void onScanClick(View v)
    {
        // If the user clicks this button let's create a popup message asking if they're sure
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setCancelable(true);
        builder.setTitle("Are You Sure?");
        builder.setMessage("Do you want to scan and crop your files? This may take a while.");
        builder.setPositiveButton("Confirm", new DialogInterface.OnClickListener() {
            // If they click yes, we want to start the scan
            @Override
            public void onClick(DialogInterface dialog, int which) {
                try {
                    startImageScan();
                }
                catch(Exception e) {
                    e.printStackTrace();
                }
            }
        });
        // Otherwise we simply dismiss the screen
        builder.setNegativeButton(android.R.string.cancel, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        AlertDialog dialog = builder.create();
        dialog.show();
    }

    private void startImageScan()
    {
        // Find all image URLs in the gallery
        Load load = new Load();

        ArrayList<String> filesScanned = load.scanGallery(this);

        // If there are none simply return
        if(filesScanned.size() == 0)
            return;

        System.out.println("We're starting the image scan");

        for(int i = 0; i < filesScanned.size(); i++)
        {
            // Get a Uri of that file
            String filePath = filesScanned.get(i);
            Bitmap bitmap = BitmapFactory.decodeFile(filePath);
            imageView.setImageBitmap(bitmap);
            System.out.println("Wow, lots of images here " + i);
        }
    }

    private void startPostCrop(byte[] bytes)
    {
        Intent postcrop = new Intent(this, PostCropActivity.class);
        postcrop.putExtra("cropBytes", bytes);
        postcrop.putExtra("precropuri", contentURI.toString());
        startActivity(postcrop);
    }

    private byte[] compressBitmap(Bitmap mapToCompress)
    {
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        croppedMap.compress(Bitmap.CompressFormat.JPEG, 100, stream);
        byte[] bytes = stream.toByteArray();
        return bytes;
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

