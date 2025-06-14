package com.shortNotes;

import java.util.ArrayList;

public class HelperFunctions
{
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

    public static GridPosition calculateGridPosition(int noteIndex, double noteWidth, double gap, double windowWidth)
    {
        int maxColumns = Math.max(1, (int) ((windowWidth + gap) / (noteWidth + gap)));

        int column = noteIndex % maxColumns;
        int row = noteIndex / maxColumns;

        return new GridPosition(column, row);

    }

    public static ArrayList<NoteSave> NoteDataToNoteSaves(ArrayList<NotesMainViewController.NoteData> noteData)
    {
        ArrayList<NoteSave> noteSaves = new ArrayList<>();
        for (NotesMainViewController.NoteData note : noteData)
        {
            noteSaves.add(note.getNoteSave());
        }
        return noteSaves;
    }

    public static void getMiddlePointFromScreen()
    {
        // return the middle point of the screen
        return;
    }

}
