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
        setPostCropImage();

    }

    public void setPostCropImage() {
        byte[] bytes = getIntent().getByteArrayExtra("cropBytes");
        Bitmap cropMap = BitmapFactory.decodeByteArray(bytes,0,bytes.length);
        ImageView cropImage = (ImageView) findViewById(R.id.CroppingImg);
        cropImage.setImageBitmap(cropMap);

    }



    
}
