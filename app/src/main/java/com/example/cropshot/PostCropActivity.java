package com.example.cropshot;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.ColorSpace;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.graphics.Color;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import android.util.Log;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.fragment.app.DialogFragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import java.io.*;
import java.util.Random;


public class PostCropActivity extends AppCompatActivity{

    Bitmap cropMap;
    Uri precropuri;
    Button saveNew;
    Button overwrite;
    Button Discard;
    Button MCROP;
    Uri contentURI;

    @Override
    protected void onCreate (Bundle savedInstanceState) {

        if(SettingsSingleton.getInstance().getDarkMode())
        {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
            setTheme(R.style.darktheme);
        }

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crop_save);
        setPostCropImage();


    }

    public boolean setPostCropImage() {
        byte[] bytes = getIntent().getByteArrayExtra("cropBytes");
        cropMap = BitmapFactory.decodeByteArray(bytes,0,bytes.length);
        ImageView cropImage = (ImageView) findViewById(R.id.CroppingImg);
        String uriString = getIntent().getStringExtra("precropuri");
        precropuri = Uri.parse(uriString);
        cropImage.setImageBitmap(cropMap);

        if (cropMap != null && precropuri != null) return true;

        return false;

    }

    public boolean onDiscardClick(View v)
    {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setCancelable(true);
        builder.setTitle("Are You Sure?");
        builder.setMessage("Do you want to discard?");
        builder.setPositiveButton("Confirm", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                try {
                    Intent mainactivity = new Intent(PostCropActivity.this,MainActivity.class);
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
        if (dialog != null) return true;
        return false;

    }

    public boolean onSaveNewClick(View v) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setCancelable(true);
        builder.setTitle("Are You Sure?");
        builder.setMessage("Do you want to save new?");
        builder.setPositiveButton("Confirm", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                try {
                    File saveFile = Save.mainDirectory(PostCropActivity.this);
                    File file = Save.saver(cropMap,saveFile, PostCropActivity.this);
                    Intent mainactivity = new Intent(PostCropActivity.this,MainActivity.class);
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
        if (dialog != null) return true;
        return false;
    }

    public boolean onOverwriteClick(View v) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setCancelable(true);
        builder.setTitle("Are You Sure?");
        builder.setMessage("Do you want to overwrite?");
        builder.setPositiveButton("Confirm", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                try {
                    Save newsave = new Save();
                    newsave.overwrite(PostCropActivity.this, cropMap, precropuri);
                    Intent mainactivity = new Intent(PostCropActivity.this,MainActivity.class);
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
        if (dialog != null) return true;
        return false;
    }


    public boolean onManualCropClick(View v) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setCancelable(true);
        builder.setTitle("Are You Sure?");
        builder.setMessage("Do you want to manually crop?");
        builder.setPositiveButton("Confirm", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                try {
                    Intent manualcropactivity = new Intent(PostCropActivity.this,ManualCrop.class);
                    manualcropactivity.putExtra("imageUri",precropuri.toString());
                    startActivity(manualcropactivity);
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
        if (dialog != null) return true;
        return false;
    }

    public void openManualCrop(){
        Intent intent = new Intent(this,ManualCrop.class);

        //if(contentURI != null)
          //  intent.putExtra("imageUri", contentURI.toString());

        startActivity(intent);
    }
}
