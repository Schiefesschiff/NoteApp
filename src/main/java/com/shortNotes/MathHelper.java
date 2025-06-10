package com.shortNotes;

public class MathHelper
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
}
