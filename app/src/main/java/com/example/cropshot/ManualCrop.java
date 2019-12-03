package com.example.cropshot;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;

public class ManualCrop extends AppCompatActivity {
    public static final int IMAGE_GALLERY_REQUEST = 20;

    ImageButton browser,btReset;
    ImageView imageView;
    Uri uri;
    private Button button;

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
        //btReset = findViewById(R.id.bt_reset);
        imageView = findViewById(R.id.image_view);

        btReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                imageView.setImageBitmap(null);
            }
        });

        Intent intent = getIntent();
        Bundle extras = intent.getExtras();
        if(extras != null)
        {
            Uri myUri = Uri.parse(extras.getString("imageUri"));
            startCrop(myUri);
        }

    }

    public void onGalleryClick(View v) {
        Load loadObject = new Load();
        loadObject.accessGallery(this, IMAGE_GALLERY_REQUEST);

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
}
