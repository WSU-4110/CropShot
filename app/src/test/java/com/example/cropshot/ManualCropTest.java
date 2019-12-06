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

        ManualCrop MCTEST = mock(ManualCrop.class);
        Uri imageuri = mock(Uri.class);

        assertFalse(MCTEST.startCrop(imageuri));
    }

    @Test
    public void openGOBACK() {

        ManualCrop MCTEST = mock(ManualCrop.class);

        assertFalse(MCTEST.openGOBACK());
    }

    @Test
    public void onSaveNewClick() {

        View v = mock(View.class);
        ManualCrop MCTEST = mock(ManualCrop.class);

        assertFalse(MCTEST.onSaveNewClick(v));


    }
}