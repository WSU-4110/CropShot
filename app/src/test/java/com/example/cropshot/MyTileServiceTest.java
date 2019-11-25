package com.example.cropshot;

import org.junit.Test;

import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;

public class MyTileServiceTest {
    @Test
    public void structureTile() {
        MyTileService myTileService = mock(MyTileService.class);
        assertFalse(myTileService.structureTile());
    }

    @Test
    public void createDialog() {
        MyTileService myTileService = mock(MyTileService.class);
        assertFalse(myTileService.createDialog());
    }

}