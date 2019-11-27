package com.example.cropshot;

import android.content.Intent;
import android.graphics.Bitmap;
import android.view.View;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import static org.junit.Assert.*;

@RunWith(MockitoJUnitRunner.class)
public class PostCropActivityTest {
    @Test
    public void setPostCropImageTest() {
        PostCropActivity activity = mock(PostCropActivity.class);
        assertFalse(activity.setPostCropImage());
    }

    @Test
    public void OnDiscardClickTest() {
        PostCropActivity activity = mock(PostCropActivity.class);
        View v = null;
        assertFalse(activity.onDiscardClick(v));
    }

    @Test
    public void onSaveNewClickTest() {
        PostCropActivity activity = mock(PostCropActivity.class);
        View v = null;
        assertFalse(activity.onSaveNewClick(v));
    }

    @Test
    public void onOverwriteClickTest() {
        PostCropActivity activity = mock(PostCropActivity.class);
        View v = null;
        assertFalse(activity.onOverwriteClick(v));
    }
}
