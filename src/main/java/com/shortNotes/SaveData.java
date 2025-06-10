package com.shortNotes;

import java.util.ArrayList;

public class SaveData
{
    public WindowConfig mainWindow;
    public ArrayList<NoteSave> NotesSaves;

    public SaveData()
    {
        NotesSaves = new ArrayList<>();
    }

    public SaveData(WindowConfig mainWindow)
    {
        this.mainWindow = mainWindow;
        NotesSaves = new ArrayList<>();
    }

    public SaveData(WindowConfig mainWindow, ArrayList<NoteSave> notesSaves)
    {
        this.mainWindow = mainWindow;
        this.NotesSaves = notesSaves;
    }

}
