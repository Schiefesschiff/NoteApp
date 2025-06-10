package com.shortNotes;

record WindowConfig(
        double width,
        double height,
        double x,
        double y,
        boolean maximized
) {}

public class AppConfig
{

    private static final String CONFIG_FILE_NAME = "config.json";

    private WindowConfig mainWindow;
    private WindowConfig noteWindow;

    public AppConfig()
    {
        this.mainWindow = new WindowConfig(300, 500, -1, -1, false);
        this.noteWindow = new WindowConfig(200, 200, -1, -1, false);
    }

    public void LoadAndSetConfig()
    {

    }

}
