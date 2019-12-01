package com.example.cropshot;

import android.content.Context;
import android.graphics.Bitmap;
import android.service.quicksettings.Tile;
import android.service.quicksettings.TileService;
import android.widget.Toast;
import java.io.ByteArrayOutputStream;
import android.content.Intent;

public class MyTileService extends TileService {

    @Override
    public void onClick() {
        super.onClick();
        System.out.println("Starting myTileService.onClick");
        MainActivity mainActivity = new MainActivity();
        Bitmap bmp = mainActivity.findLatestImage();
        Intent intent = createIntentWithBitmap(this, bmp);
        System.out.println("Starting intent");
        startActivityAndCollapse(intent);
    }

    public Intent createIntentWithBitmap(Context context, Bitmap bmpInput){
        System.out.println("Starting createIntentWithBitmap");
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