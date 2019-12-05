package com.example.cropshot;

import android.content.Context;

import android.service.quicksettings.Tile;
import android.service.quicksettings.TileService;
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
        if (context == null){
            Intent intent2 = null;
            return intent2;
        }
        
        System.out.println("Starting createIntent");
        Intent intent = new Intent(context, MainActivity.class);
        intent.addFlags(FLAG_ACTIVITY_NEW_TASK);
        Integer code = 10;
        intent.putExtra("tileServiceCode", code);
        return intent;
    }

    @Override
    public void onTileAdded() {
        super.onTileAdded();
        MyTileService myTileService = new MyTileService();
        myTileService.structureTile();
    }

    public boolean structureTile(){
        //Tile state set to active. Button label set to its functionality.
        Tile tile = getQsTile();
        tile.setState(Tile.STATE_ACTIVE);
        tile.setLabel("Screenshot & Crop");
        tile.updateTile();

        Toast.makeText(getApplicationContext(), "tile added", Toast.LENGTH_SHORT).show();
        //If tile is not properly set up, return false. If getQsTile() does not implement properly,
        //tile.setState(Tile.STATE_ACTIVE) would fail, causing function below to fail.
        if (tile.getState() != Tile.STATE_ACTIVE){
            return false;}
        else{
            return true;}
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