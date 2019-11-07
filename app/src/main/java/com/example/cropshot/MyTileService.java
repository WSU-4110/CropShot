package com.example.cropshot;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.Icon;
import android.preference.PreferenceManager;
import android.service.quicksettings.Tile;
import android.service.quicksettings.TileService;
import android.util.Log;
import android.widget.Toast;

public class MyTileService extends TileService {
    SharedPreferences mSharedPreferences;

    @Override
    public void onClick() {
        super.onClick();

        Tile tile = getQsTile();
        //Start an activity while collapsing the panel.
        startActivityAndCollapse(new Intent(this, MainActivity.class));
        
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