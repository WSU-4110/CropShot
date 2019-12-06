package com.example.cropshot;

import org.junit.Test;

import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;

public class MyTileServiceTest {

    @Test
    public void createIntent() {
        //If there is not anything in a context to pass, the intent will be returned as null.
        //So here, we expect a null value to return.
        //With unit mock tests, android contexts are always null, similar to a test case where a null context
        //would be passed in a real test case.
        MyTileService myTileService = mock(MyTileService.class);
        assertNull(myTileService.createIntent(null));
    }

    @Test
    public void structureTile() {
        //When mocking a function, the android.jar that is used to run unit tests does not contain any actual code.
        //As a result, when mocking getQsTile(), there is no tile to get. So, it should fail and return false.
        MyTileService myTileService = mock(MyTileService.class);
        assertFalse(myTileService.structureTile());
    }
}