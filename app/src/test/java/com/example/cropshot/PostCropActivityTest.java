package com.example.cropshot;


import android.graphics.Bitmap;
import android.net.Uri;
import android.view.View;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;
import static org.junit.Assert.*;

import static org.mockito.Mockito.mock;

@RunWith(MockitoJUnitRunner.class)
public class PostCropActivityTest {
    @Test
    public void setPostCropImageTest() {
        PostCropActivity activity = mock(PostCropActivity.class);
        assertFalse(activity.setPostCropImage());
    }

    @Test
    public void onSaveNewClickTest() {
        PostCropActivity activity = mock(PostCropActivity.class);
        View v = mock(View.class);
        assertFalse(activity.onSaveNewClick(v));
    }

    @Test
    public void onDiscardClickTest() {
        PostCropActivity activity = mock(PostCropActivity.class);
        View v = mock(View.class);
        assertFalse(activity.onDiscardClick(v));
    }


}
