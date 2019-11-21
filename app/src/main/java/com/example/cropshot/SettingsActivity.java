package com.example.cropshot;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class SettingsActivity extends AppCompatActivity {

    private Button button;
    private Button about;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
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
    }

    public void openGOBACK(){
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    public void openAbout(){
        AboutBox aboutBox = new AboutBox();
        aboutBox.show(getSupportFragmentManager(),"about box");
    }
}
