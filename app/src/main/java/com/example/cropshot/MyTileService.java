package com.example.cropshot;

import android.content.Context;

import android.service.quicksettings.Tile;
import android.service.quicksettings.TileService;
import android.view.View;
import android.widget.Toast;
import android.content.Intent;

import static android.content.Intent.FLAG_ACTIVITY_NEW_TASK;

public class MyTileService extends TileService {

    @Override
    public void onClick() {
        super.onClick();
        System.out.println("Starting myTileService.onClick");
        Intent intent = createIntent(this);
        System.out.println("Starting intent");
        startActivityAndCollapse(intent);
    }

    public Intent createIntent(Context context){
        System.out.println("Starting createIntent");
        Intent intent = new Intent(context, MainActivity.class);
        intent.addFlags(FLAG_ACTIVITY_NEW_TASK);
        Integer code = 10;
        intent.putExtra("tileServiceCode", code);
        return intent;
    }

    private void takeScreenshot(View v) {
        View screenView = v.getRootView();


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