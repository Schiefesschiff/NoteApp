package com.shortNotes;

import java.util.Objects;

/**
 * A simple data class to store the configuration (dimensions, position, and maximized state)
 * of a JavaFX window. This class is designed to be easily serialized and deserialized
 * to persist window state between application sessions.
 */
public class WindowConfig
{
    public double width;
    public double height;
    public double x;
    public double y;
    public boolean maximized;

    /**
     * Constructor for creating a WindowConfig instance with all properties.
     * @param width The width of the window.
     * @param height The height of the window.
     * @param x The X-coordinate of the window.
     * @param y The Y-coordinate of the window.
     * @param maximized The maximized state of the window.
     */
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

