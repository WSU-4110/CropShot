package com.example.cropshot;

import android.content.Context;
import android.content.ContextWrapper;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;

import androidx.annotation.NonNull;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Random;

public class Save {






    public void saveAsNew2(Bitmap finalBitmap) {
        File mydir = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES),"saved_images");
        mydir.mkdirs();
        FileOutputStream fostream = null;
        try {
            fostream = new FileOutputStream(createFile());
            finalBitmap.compress(Bitmap.CompressFormat.JPEG,100,fostream);

        }
        catch (Exception e) {
            e.printStackTrace();
        }
        finally {
            try {
                if (fostream != null)
                    fostream.close();
            }
            catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /*@NonNull
    private File createFile() {
        File directory = this.context.getDir("saved_images",Context.MODE_PRIVATE);
        if (!directory.exists() && !directory.mkdirs())
            Log.e("Saver","can't make directory");
        Random generator = new Random();
        int n = 10000;
        n = generator.nextInt(n);
        String fname = "ImageCropshotters-" + n + ".jpg";
        return new File("saved_images",fname);
    }*/

    public static File mainDirectory(Context context) {
        File mainDir = new File(context.getExternalFilesDir())
    }

    public void saveAsNew(Bitmap finalBitmap)
    {
        String root = Environment.getExternalStorageDirectory().toString();
        File myDir = new File(root + "/saved_images");
        myDir.mkdirs();
        Random generator = new Random();
        int n = 10000;
        n = generator.nextInt(n);
        String fname = "ImageCropShotter-" + n + ".jpg";
        File file = new File(myDir, fname);
        Log.i("LOAD", root + fname);
        try {
            FileOutputStream out = new FileOutputStream(file);
            finalBitmap.compress(Bitmap.CompressFormat.JPEG, 90, out);
            out.flush();
            out.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void Overwrite(Bitmap finalBitmap)
    {

    }

    public void SaveAs(String name, Bitmap finalBitmap)
    {

    }
}
