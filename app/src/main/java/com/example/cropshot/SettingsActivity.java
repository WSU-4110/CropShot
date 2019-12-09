package com.example.cropshot;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.appcompat.app.AppCompatDialogFragment;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.provider.Settings;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.Toast;

import com.google.android.gms.vision.text.TextRecognizer;
import com.google.firebase.ml.vision.FirebaseVision;
import com.google.firebase.ml.vision.text.FirebaseVisionTextRecognizer;

import java.io.File;

public class SettingsActivity extends AppCompatActivity {

    private Button button;
    private Button about;
    private Switch darkswitch;
    private Switch MLswitch;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        if(AppCompatDelegate.getDefaultNightMode()==AppCompatDelegate.MODE_NIGHT_YES){
                setTheme(R.style.darktheme);
        }
        else setTheme(R.style.AppTheme);

        super.onCreate(savedInstanceState);

       // setTheme(Shared.Data.style);

        setContentView(R.layout.activity_settings);
        button = (Button) findViewById(R.id.back);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openGOBACK();
            }
        });

        about = (Button) findViewById(R.id.About);
        about.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openAbout();
            }
        });

        darkswitch = findViewById(R.id.darkswitch);
        if(AppCompatDelegate.getDefaultNightMode()==AppCompatDelegate.MODE_NIGHT_YES){
                darkswitch.setChecked(true);
        }
        darkswitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    SettingsSingleton.getInstance().setDarkMode(true);
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
                    //Shared.Data.style = R.style.darktheme;
                    restartApp();
                }
                else{
                    SettingsSingleton.getInstance().setDarkMode(false);
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
                    //Shared.Data.style = R.style.AppTheme;
                    restartApp();
                }
            }
        });

        MLswitch  = findViewById(R.id.MLswitch);

        MLswitch.setChecked(SettingsSingleton.getInstance().getUseML());

        MLswitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(!isChecked) {
                    SettingsSingleton.getInstance().setUseML(false);
                }

                else
                {
                    final TextRecognizer textRecognizer = new TextRecognizer.Builder(getApplicationContext()).build();
                    if(textRecognizer.isOperational())
                        SettingsSingleton.getInstance().setUseML(true);
                    else
                    {

                        AlertDialog.Builder builder = new AlertDialog.Builder(SettingsActivity.this);
                        builder.setCancelable(true);
                        builder.setTitle("Firebase detection isn't working!");
                        builder.setMessage("Don't worry, you can still crop your images! If you want to set this option, perhaps try" +
                                " redownloading the application, otherwise enjoy CropShot!");
                        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                            }
                        });
                        AlertDialog dialog = builder.create();
                        dialog.show();
                        MLswitch.setChecked(SettingsSingleton.getInstance().getUseML());
                    }
                }

            }
        });

    }

    public void openGOBACK(){
        finish();
    }

    public void openAbout(){
        AboutBox aboutBox = new AboutBox();
        aboutBox.show(getSupportFragmentManager(),"about box");
    }

    public void restartApp(){
        Intent i = new Intent(getApplicationContext(),SettingsActivity.class);
        startActivity(i);
        finish();
    }
}
