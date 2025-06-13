package com.shortNotes;

import java.util.Objects;

public class WindowConfig
{
    public double width;
    public double height;
    public double x;
    public double y;
    public boolean maximized;

    public WindowConfig(
            double width,
            double height,
            double x,
            double y,
            boolean maximized
    )
    {
        this.width = width;
        this.height = height;
        this.x = x;
        this.y = y;
        this.maximized = maximized;
    }



}

