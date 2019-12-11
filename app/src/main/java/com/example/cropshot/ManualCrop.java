package com.example.cropshot;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;

import java.io.File;

public class ManualCrop extends AppCompatActivity {
    public static final int IMAGE_GALLERY_REQUEST = 20;

    private Button browser;
    ImageView imageView;
    Uri uri;
    private Button button;
    private Button btsave;
    Bitmap bitmap;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        if(SettingsSingleton.getInstance().getDarkMode())
        {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
            setTheme(R.style.darktheme);
        }

        super.onCreate(savedInstanceState);
        setContentView(R.layout.manual_crop);

        button = (Button) findViewById(R.id.back);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openGOBACK();
            }
        });

        browser = findViewById(R.id.b_browser);
        btsave = findViewById(R.id.save);
        imageView = findViewById(R.id.image_view);

        btsave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onManualSaveNewClick(v);
            }
        });

        Intent intent = getIntent();
        Bundle extras = intent.getExtras();
        if(extras != null)
        {
            uri = Uri.parse(extras.getString("imageUri"));
            startCrop(uri);
        }

    }



    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == IMAGE_GALLERY_REQUEST
                && resultCode == Activity.RESULT_OK) {
            Uri imageuri = CropImage.getPickImageResultUri(this, data);
            if (CropImage.isReadExternalStoragePermissionsRequired(this, imageuri)) {
                uri = imageuri;
                requestPermissions(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}
                        , 0);
            } else {
                startCrop(imageuri);
            }
        }

        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
            CropImage.ActivityResult result = CropImage.getActivityResult(data);
            if (resultCode == RESULT_OK) {
                imageView.setImageURI(result.getUri());
                Toast.makeText(this, "Image Updated"
                        , Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void startCrop(Uri imageuri) {
        CropImage.activity(imageuri)
                .setGuidelines(CropImageView.Guidelines.ON)
                .setMultiTouchEnabled(true)
                .start(this);
    }

    public void openGOBACK(){
        Intent intent = new Intent(this,MainActivity.class);
        startActivity(intent);
    }

    public void onBackClick(View v) {
        Intent intent = new Intent(this,MainActivity.class);
        startActivity(intent);
    }

    public void onManualSaveNewClick(View v) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setCancelable(true);
        builder.setTitle("Are You Sure?");
        builder.setMessage("Do you want to save new?");
        builder.setPositiveButton("Confirm", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                try {
                    File saveFile = Save.mainDirectory(ManualCrop.this);
                    Bitmap cropMap = ((BitmapDrawable)imageView.getDrawable()).getBitmap();
                    File file = Save.saver(cropMap,saveFile, ManualCrop.this);
                    Intent mainactivity = new Intent(ManualCrop.this,MainActivity.class);
                    startActivity(mainactivity);
                }
                catch(Exception e) {
                    e.printStackTrace();
                }
            }
        });
        builder.setNegativeButton(android.R.string.cancel, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        AlertDialog dialog = builder.create();
        dialog.show();
    }

    public void onManualOverwriteClick(View v) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setCancelable(true);
        builder.setTitle("Are You Sure?");
        builder.setMessage("Do you want to overwrite?");
        builder.setPositiveButton("Confirm", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                try {
                    Save newsave = new Save();
                    newsave.overwrite(ManualCrop.this, ((BitmapDrawable)imageView.getDrawable()).getBitmap(), uri);
                    Intent mainactivity = new Intent(ManualCrop.this,MainActivity.class);
                    startActivity(mainactivity);
                }
                catch(Exception e) {
                    e.printStackTrace();
                }
            }
        });
        builder.setNegativeButton(android.R.string.cancel, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        AlertDialog dialog = builder.create();
        dialog.show();
    }
}
