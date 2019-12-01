package com.example.cropshot;

public class SettingsSingleton {

    private static SettingsSingleton INSTANCE = null;

    private SettingsSingleton() {};
    private boolean isDarkMode = false;
    private boolean useML = true;

    // If the class doesn't already exist, create a new instance of it
    // otherwise, return the currently existing instance.
    public static SettingsSingleton getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new SettingsSingleton();
        }
        return(INSTANCE);
    }

    public void setDarkMode(boolean set)
    {
        isDarkMode = set;
    }

    public boolean getDarkMode()
    {
        return isDarkMode;
    }

    public void setUseML(boolean set)
    {
        useML = set;
    }

    public boolean getUseML()
    {
        return useML;
    }

}
