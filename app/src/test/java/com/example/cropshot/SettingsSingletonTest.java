package com.example.cropshot;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;

@RunWith(MockitoJUnitRunner.class)
public class SettingsSingletonTest {
    @Test
    public void getUseMLTest() {
        assertTrue(SettingsSingleton.getInstance().getUseML());
        SettingsSingleton.getInstance().setUseML(false);
        assertFalse(SettingsSingleton.getInstance().getUseML());

    }

    @Test
    public void getDarkModeTest() {
        assertFalse(SettingsSingleton.getInstance().getDarkMode());
        SettingsSingleton.getInstance().setDarkMode(true);
        assertTrue(SettingsSingleton.getInstance().getDarkMode());
    }
}
