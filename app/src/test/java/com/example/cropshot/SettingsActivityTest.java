package com.example.cropshot;

import android.content.Intent;

import org.junit.Test;

import static org.junit.Assert.*;

public class SettingsActivityTest extends SettingsActivity{

    @Test
    public void onPostCreate() {
    }

    @Test
    public void openGOBACK() {

        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    @Test
    public void openAbout() {

        AboutBox aboutBox = new AboutBox();
        aboutBox.show(getSupportFragmentManager(),"about box");
    }

    @Test
    public void restartApp() {
    }
}