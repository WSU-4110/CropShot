package com.example.cropshot;

import android.content.Context;
import android.content.ContextWrapper;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;

import androidx.annotation.NonNull;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URI;
import java.util.Random;

public class Save {









    public static File mainDirectory(Context context) {
        File mainDir = new File ("/sdcard/Pictures");
        File mainDirec = new File(context.getExternalFilesDir(Environment.DIRECTORY_PICTURES), "screenshotsaver");
        if (!mainDir.exists()) {
            if (mainDir.mkdirs()) Log.e("Create Directory", "Save Directory Created: " + mainDir);
        }
        return mainDir;
    }

    public static File saver(Bitmap bm, File saveFilePath, Context context) {
        if (saveFilePath == null) {
            File returner = null;
            return returner;
        }
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

            // Required to scan and allow the image to appear within the gallery
            mediaScanFile(context, file);

            fout.flush();
            fout.close();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return file;

    }

    private static void mediaScanFile (Context context, File file)
    {
        MediaScannerConnection.scanFile(
                context.getApplicationContext(),
                new String[]{file.getAbsolutePath()},
                null,
                new MediaScannerConnection.OnScanCompletedListener() {
                    @Override
                    public void onScanCompleted(String path, Uri uri) {
                        Log.v("grokkingandroid",
                                "file " + path + " was scanned seccessfully: " + uri);
                    }
                });

    }



    public boolean overwrite(Context context, Bitmap finalBitmap, Uri oglocation)
    {

        String path = null;
        String[] proj = { MediaStore.MediaColumns.DATA };
        Cursor cursor = context.getContentResolver().query(oglocation, proj, null, null, null);
        if (cursor.moveToFirst()) {
            int column_index = cursor.getColumnIndexOrThrow(MediaStore.MediaColumns.DATA);
            path = cursor.getString(column_index);
        }
        cursor.close();
        File dir = new File(path);
        try {
            FileOutputStream fout = new FileOutputStream(dir);
            finalBitmap.compress(Bitmap.CompressFormat.JPEG, 85, fout);
            mediaScanFile(context, dir);
            fout.flush();
            fout.close();
            return true;
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }


}
