package com.example.cropshot;

import android.net.Uri;
import android.view.View;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;

import java.util.List;

import static org.junit.Assert.*;

@RunWith(MockitoJUnitRunner.class)
public class ManualCropTest {

    @Test
    public void startCrop() {

        ManualCrop MCTEST = new ManualCrop();
        Uri imageuri = null;

        assertTrue(MCTEST.startCrop(imageuri));
    }

    @Test
    public void openGOBACK() {

        ManualCrop MCTEST = new ManualCrop();

        assertTrue(MCTEST.openGOBACK());
    }

    @Test
    public void onSaveNewClick() {

        View v = null;
        ManualCrop MCTEST = new ManualCrop();

        assertTrue(MCTEST.onSaveNewClick(v));


    }
}