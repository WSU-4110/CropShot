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

    public void setPostCropImage() {
        byte[] bytes = getIntent().getByteArrayExtra("cropBytes");
        cropMap = BitmapFactory.decodeByteArray(bytes,0,bytes.length);
        ImageView cropImage = (ImageView) findViewById(R.id.CroppingImg);
        String uriString = getIntent().getStringExtra("precropuri");
        precropuri = Uri.parse(uriString);
        cropImage.setImageBitmap(cropMap);


    }

    public void onDiscardClick(View v)
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
                    mainactivity.putExtra("UriPostCropReset",precropuri);
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

    public void onSaveNewClick(View v) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setCancelable(true);
        builder.setTitle("Are You Sure?");
        builder.setMessage("Do you want to save new?");
        builder.setPositiveButton("Confirm", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                try {
                    saveNewImage(cropMap);
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
    }

    protected void saveNewImage(Bitmap bitmap) {
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
            bitmap.compress(Bitmap.CompressFormat.JPEG, 90, out);
            out.flush();
            out.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    
}
