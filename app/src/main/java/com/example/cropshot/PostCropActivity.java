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
    boolean discardchecker = false;

    @Override
    protected void onCreate (Bundle savedInstanceState) {
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
        if (cropImage != null && precropuri != null) return true;
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
                    mainactivity.putExtra("UriPostCropReset",precropuri);
                    discardchecker = true;
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
        return discardchecker;
    }

    public boolean onSaveNewClick(View v) {
        Intent intent = new Intent(this,Save.class);
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        cropMap.compress(Bitmap.CompressFormat.JPEG, 100, stream);
        byte[] bytes = stream.toByteArray();
        intent.putExtra("cropBytes", bytes);
        if (bytes != null) return true;
        return false;
    }

    public boolean onOverwriteClick(View v) {
        Intent intent = new Intent(this,Save.class);
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        cropMap.compress(Bitmap.CompressFormat.JPEG,100,stream);
        byte[] bytes = stream.toByteArray();
        intent.putExtra("cropBytes",bytes);
        intent.putExtra("oguri",precropuri.toString());
        if (bytes != null) return true;
        return false;
    }




    
}
