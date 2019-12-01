package com.example.cropshot;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.service.quicksettings.Tile;
import android.service.quicksettings.TileService;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;
import android.content.DialogInterface;
import android.app.AlertDialog;
import android.app.Activity;
import android.view.Window;

import java.io.ByteArrayOutputStream;
import java.io.FileOutputStream;
import java.util.ArrayList;
import android.content.Intent;

public class MyTileService extends TileService {

    @Override
    public void onClick() {
        super.onClick();
        MainActivity mainActivity = new MainActivity();
        Bitmap bmp = mainActivity.findLatestImage();
        System.out.println("Is bitmap null?: " + bmp==null);
        Intent intent = createIntentWithBitmap(bmp, this);
        startActivityAndCollapse(intent);
    }

    public Intent createIntentWithBitmap(Bitmap bmpInput, Context context){
        Intent intent = new Intent(context, MainActivity.class);

        //Convert to byte array
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bmpInput.compress(Bitmap.CompressFormat.PNG, 100, stream);
        byte[] byteArray = stream.toByteArray();
        System.out.println("Byte Array Size: " + byteArray.length);

        intent.putExtra("image",byteArray);
        return intent;
    }

    @Override
    public void onTileAdded() {
        super.onTileAdded();

        //Tile state set to active. Button label set to its functionality.
        Tile tile = getQsTile();
        tile.setState(Tile.STATE_ACTIVE);
        tile.setLabel("Screenshot & Crop");
        tile.updateTile();

        Toast.makeText(getApplicationContext(), "tile added", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onTileRemoved() {
        super.onTileRemoved();
    }

    @Override
    public void onStartListening() {
        super.onStartListening();
    }

    @Override
    public void onStopListening() {
        super.onStopListening();
    }


}