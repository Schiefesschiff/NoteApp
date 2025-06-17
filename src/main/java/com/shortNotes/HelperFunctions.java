package com.shortNotes;

import java.util.ArrayList;

/**
 * The HelperFunctions class provides a collection of utility methods
 * that assist with common operations related to note management,
 * particularly concerning their layout and data conversion.
 */
public class HelperFunctions
{
    // Represents a position within a grid layout, defined by its column and row indices.
    public static class GridPosition
    {
        public int column;
        public int row;

        public GridPosition(int column, int row)
        {
            this.column = column;
            this.row = row;
        }

    }

    // Calculates the grid position for a given note index, note width, gap, and window width.
    public static GridPosition calculateGridPosition(int noteIndex, double noteWidth, double gap, double windowWidth)
    {
        int maxColumns = Math.max(1, (int) ((windowWidth + gap) / (noteWidth + gap)));

        int column = noteIndex % maxColumns;
        int row = noteIndex / maxColumns;

        return new GridPosition(column, row);

    }

    /**
     * Converts a list of `NoteData` objects (which are typically used internally for active notes)
     * into a list of `NoteSave` objects (which are suitable for persistence, e.g., saving to a file).
     *
     * @param noteData An ArrayList of `NotesMainViewController.NoteData` objects.
     * @return An ArrayList of `NoteSave` objects.
     */
    public static ArrayList<NoteSave> NoteDataToNoteSaves(ArrayList<NotesMainViewController.NoteData> noteData)
    {
        ArrayList<NoteSave> noteSaves = new ArrayList<>();
        for (NotesMainViewController.NoteData note : noteData)
        {
            noteSaves.add(note.getNoteSave());
        }
        return noteSaves;
    }

    // TODO need the position so that new windows open in the center of the screen and not at the top left
    public static void getMiddlePointFromScreen()
    {
        // return the middle point of the screen
        return;
    }

}
