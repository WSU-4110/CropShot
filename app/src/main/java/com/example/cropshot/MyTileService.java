package com.example.cropshot;

import android.service.quicksettings.Tile;
import android.service.quicksettings.TileService;
import android.widget.Toast;
import android.content.DialogInterface;
import android.app.AlertDialog;

public class MyTileService extends TileService {

    @Override
    public void onClick() {
        super.onClick();

        System.out.println("Outputting dialog");
        createDialog();

    }
    public boolean createDialog(){
        AlertDialog.Builder builder = new AlertDialog.Builder(getApplicationContext());

        builder.setTitle("Confirm");
        builder.setMessage("Are you sure you want to screenshot?");

        builder.setPositiveButton("YES", new DialogInterface.OnClickListener() {

            public void onClick(DialogInterface dialog, int which) {

                // Do nothing, but close the dialog
                dialog.dismiss();
            }
        });

        builder.setNegativeButton("NO", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {

                // Do nothing
                dialog.dismiss();
            }
        });

        AlertDialog alertDialog = builder.create();
        showDialog(alertDialog);
        //Checking to make sure that builder was properly set up
        if (builder != null)
            return true;
        else
            return false;
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
        //If tile is not properly set up, return false
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