package com.example.cropshot;

import android.content.Context;
import android.content.ContextWrapper;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;

import androidx.annotation.NonNull;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Random;

public class Save {









    public static File mainDirectory(Context context) {
        File mainDir = new File ("/sdcard/savepics");
        File mainDirec = new File(context.getExternalFilesDir(Environment.DIRECTORY_PICTURES), "screenshotsaver");
        if (!mainDir.exists()) {
            if (mainDir.mkdirs()) Log.e("Create Directory", "Save Directory Created: " + mainDir);
        }
        return mainDir;
    }

    public static File saver(Bitmap bm, File saveFilePath) {
        File dir = new File(saveFilePath.getAbsolutePath());
        if (!dir.exists()) dir.mkdirs();
        Random generator = new Random();
        int n = 10000;
        n = generator.nextInt(n);
        String fileName = "ImageCropShotter-" + n + ".jpg";
        File file = new File(saveFilePath.getAbsolutePath(), fileName);
        try {
            FileOutputStream fout = new FileOutputStream(file);
            bm.compress(Bitmap.CompressFormat.JPEG, 85, fout);
            fout.flush();
            fout.close();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return file;

    }



    public void Overwrite(Bitmap finalBitmap, Uri oglocation)
    {
        File dir = new File(oglocation.getPath());
        try {
            FileOutputStream fout = new FileOutputStream(dir);
            finalBitmap.compress(Bitmap.CompressFormat.JPEG, 85, fout);
            fout.flush();
            fout.close();
        }
        catch (Exception e) {
            e.printStackTrace();
        }

    }


}
