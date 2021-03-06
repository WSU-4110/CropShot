package com.example.cropshot;

import android.content.Context;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;

import java.io.File;
import java.io.FileOutputStream;
import java.util.Random;

public class Save {


    public static File mainDirectory(Context context) {
        File mainDir = new File ("/sdcard/Pictures");
        //checks to see if it exists and creates the directory if it does not exist
        if (!mainDir.exists()) {
            if (mainDir.mkdirs()) Log.e("Create Directory", "Save Directory Created: " + mainDir);
        }
        return mainDir;
    }

    public static File saver(Bitmap bm, File saveFilePath, Context context) {
        File dir = new File(saveFilePath.getAbsolutePath());
        if (!dir.exists()) dir.mkdirs();
        Random generator = new Random();
        int n = 10000;
        n = generator.nextInt(n);
        String fileName = "ImageCropShotter-" + n + ".jpg";
        File file = new File(saveFilePath.getAbsolutePath(), fileName);
        try {
            //the following code places the bitmap into the file location we want
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


    //required to refresh the gallery and show new images
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



    public void overwrite(Context context, Bitmap finalBitmap, Uri oglocation)
    {

        String path = null;
        String[] proj = { MediaStore.MediaColumns.DATA };
        //receives file path from the uri that we wish to overwrite at
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
            //refreshes gallery
            mediaScanFile(context, dir);
            fout.flush();
            fout.close();
        }
        catch (Exception e) {
            e.printStackTrace();
        }

    }



}
