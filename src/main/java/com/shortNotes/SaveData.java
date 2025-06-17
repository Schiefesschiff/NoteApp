package com.shortNotes;

import java.util.ArrayList;

/**
 * A simple data class used to encapsulate all the application's
 * savable state. This includes the main window's configuration
 * and a list of all saved notes. It is designed to be easily
 * serialized and deserialized (to JSON).
 */
public class SaveData
{
    // Stores the width, height, position, and maximized state of the main application window.
    public WindowConfig mainWindow;

    // A list of all individual note data to be saved.
    public ArrayList<NoteSave> NotesSaves;

    /**
     * Default constructor, initializes `NotesSaves` as an empty list.
     */
    public SaveData()
    {
        NotesSaves = new ArrayList<>();
    }

    /**
     * Constructor for creating SaveData with only main window configuration,
     * notes list remains empty.
     * @param mainWindow The configuration of the main application window.
     */
    public SaveData(WindowConfig mainWindow)
    {
        this.mainWindow = mainWindow;
        NotesSaves = new ArrayList<>();
    }

    /**
     * Full constructor for creating SaveData with both main window configuration
     * and a list of notes.
     * @param mainWindow The configuration of the main application window.
     * @param notesSaves The list of NoteSave objects.
     */
    public SaveData(WindowConfig mainWindow, ArrayList<NoteSave> notesSaves)
    {
        this.mainWindow = mainWindow;
        this.NotesSaves = notesSaves;
    }

}
