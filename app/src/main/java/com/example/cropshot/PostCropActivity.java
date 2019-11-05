package com.example.cropshot;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
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
import androidx.fragment.app.DialogFragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import java.io.*;
import java.util.Random;


public class PostCropActivity extends AppCompatActivity{
    @Override
    protected void onCreate (Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crop_save);
        String target = getIntent().getStringExtra("ImageViewAsString");
        MainActivity src = gS.fromJson(target,MainActivity.class);
        setPostCropImage();

    }

    public void setPostCropImage() {
        imageView.setImageBitmap(croppedMap);
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
                    //revert to original display and remove original
                    setContentView(R.layout.activity_main);
                    imageView.setImageBitmap(preCrop);
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
