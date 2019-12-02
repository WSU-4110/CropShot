package com.example.cropshot;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.appcompat.app.AppCompatDialogFragment;

import android.content.Intent;
import android.os.Bundle;
import android.provider.Settings;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.Switch;

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
                    SettingsSingleton.getInstance().setUseML(true);
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
