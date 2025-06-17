package com.shortNotes;

import javafx.event.EventHandler;
import javafx.scene.Cursor;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;


/**
 * This class enables resizing and dragging of a JavaFX window.
 * It attaches mouse listeners to the scene to detect interactions
 * at the window's edges for resizing or anywhere else for dragging.
 */
public class WindowsResizer
{
    private final Stage stage;
    private final Scene scene;

    // Variables to store initial mouse and stage positions/dimensions for calculations during drag/resize.
    private double xOffset = 0;
    private double yOffset = 0;

    private double initialStageX;
    private double initialStageY;
    private double initialStageWidth;
    private double initialStageHeight;

    // The pixel width around the window edges that triggers a resize.
    private final double RESIZE_MARGIN = 5;

    private ResizeMode currentMode = ResizeMode.NONE;

    private double minWidth = 150;
    private double minHeight = 200;

    /**
     * Defines the different modes of interaction: no action, or resizing from various directions.
     */
    private enum ResizeMode
    {
        NONE, NORTH, SOUTH, EAST, WEST, NORTH_EAST, NORTH_WEST, SOUTH_EAST, SOUTH_WEST
    }

    /**
     * Constructor: Initializes the resizer with the target stage and scene, then sets up event listeners.
     *
     * @param stage The JavaFX Stage to apply resize/drag functionality to.
     * @param scene The JavaFX Scene that captures mouse events for the stage.
     */
    public WindowsResizer(Stage stage, Scene scene)
    {
        this.stage = stage;
        this.scene = scene;
        attachListeners();
    }

    /**
     * Attaches all necessary mouse event handlers to the scene.
     * This includes handling mouse press, drag, move, and release events.
     */
    private void attachListeners()
    {
        scene.setOnMousePressed(new EventHandler<MouseEvent>()
        {
            // When the mouse is pressed:
            @Override
            public void handle(MouseEvent event)
            {
                // Set xOffset and yOffset for dragging the window
                xOffset = event.getSceneX();
                yOffset = event.getSceneY();

                // Store initial stage and mouse positions for resizing
                initialStageX = stage.getX();
                initialStageY = stage.getY();
                initialStageWidth = stage.getWidth();
                initialStageHeight = stage.getHeight();

                currentMode = getResizeMode(event.getSceneX(), event.getSceneY());
            }
        });

        // When the mouse is dragged:
        scene.setOnMouseDragged(new EventHandler<MouseEvent>()
        {
            @Override
            public void handle(MouseEvent event)
            {
                if (currentMode == ResizeMode.NONE)
                {
                    // No resize mode, then drag mode (move the window)
                    stage.setX(event.getScreenX() - xOffset);
                    stage.setY(event.getScreenY() - yOffset);
                } else
                {
                    // If in a resize mode, calculate and apply new dimensions and position
                    // based on the initial stage state, mouse movement, and the resize mode.
                    // It also ensures the window doesn't shrink below `minWidth` or `minHeight`.
                    double newWidth = initialStageWidth;
                    double newHeight = initialStageHeight;
                    double newX = initialStageX;
                    double newY = initialStageY;

                    double fixedX = initialStageX + initialStageWidth;
                    double fixedY = initialStageY + initialStageHeight;

                    switch (currentMode)
                    {
                        case NORTH:
                            newY = Math.min(event.getScreenY(), fixedY - minHeight);
                            newHeight = fixedY - newY;
                            newX = initialStageX;
                            newWidth = initialStageWidth;
                            break;
                        case SOUTH:
                            newHeight = Math.max(minHeight, event.getScreenY() - initialStageY);
                            newY = initialStageY;
                            newX = initialStageX;
                            newWidth = initialStageWidth;
                            break;
                        case EAST:
                            newWidth = Math.max(minWidth, event.getScreenX() - initialStageX);
                            newX = initialStageX;
                            newY = initialStageY;
                            newHeight = initialStageHeight;
                            break;
                        case WEST:
                            newX = Math.min(event.getScreenX(), fixedX - minWidth);
                            newWidth = fixedX - newX;
                            newY = initialStageY;
                            newHeight = initialStageHeight;
                            break;
                        case NORTH_EAST:
                            newY = Math.min(event.getScreenY(), fixedY - minHeight);
                            newHeight = fixedY - newY;
                            newWidth = Math.max(minWidth, event.getScreenX() - initialStageX);
                            newX = initialStageX;
                            break;
                        case NORTH_WEST:
                            newY = Math.min(event.getScreenY(), fixedY - minHeight);
                            newHeight = fixedY - newY;
                            newX = Math.min(event.getScreenX(), fixedX - minWidth);
                            newWidth = fixedX - newX;
                            break;
                        case SOUTH_EAST:
                            newWidth = Math.max(minWidth, event.getScreenX() - initialStageX);
                            newX = initialStageX;
                            newHeight = Math.max(minHeight, event.getScreenY() - initialStageY);
                            newY = initialStageY;
                            break;
                        case SOUTH_WEST:
                            newX = Math.min(event.getScreenX(), fixedX - minWidth);
                            newWidth = fixedX - newX;
                            newHeight = Math.max(minHeight, event.getScreenY() - initialStageY);
                            newY = initialStageY;
                            break;
                    }

                    // Apply the calculated changes to the stage.
                    stage.setX(newX);
                    stage.setY(newY);
                    stage.setWidth(newWidth);
                    stage.setHeight(newHeight);
                }
            }
        });

        // When the mouse moves (without being pressed):
        scene.setOnMouseMoved(new EventHandler<MouseEvent>()
        {
            // Change the cursor to indicate if resizing is possible from the current mouse position.
            @Override
            public void handle(MouseEvent event)
            {
                ResizeMode mode = getResizeMode(event.getSceneX(), event.getSceneY());
                switch (mode)
                {
                    case NORTH:
                    case SOUTH:
                        scene.setCursor(Cursor.S_RESIZE);
                        break;
                    case EAST:
                    case WEST:
                        scene.setCursor(Cursor.E_RESIZE);
                        break;
                    case NORTH_WEST:
                    case SOUTH_EAST:
                        scene.setCursor(Cursor.NW_RESIZE);
                        break;
                    case NORTH_EAST:
                    case SOUTH_WEST:
                        scene.setCursor(Cursor.SW_RESIZE);
                        break;
                    default:
                        scene.setCursor(Cursor.DEFAULT);
                        break;
                }
            }
        });

        // When the mouse button is released:
        scene.setOnMouseReleased(new EventHandler<MouseEvent>()
        {
            @Override
            public void handle(MouseEvent event)
            {
                currentMode = ResizeMode.NONE;
                scene.setCursor(Cursor.DEFAULT);
            }
        });
    }

    /**
     * Determines the current resize mode based on the mouse's `x` and `y` coordinates within the scene.
     * It checks if the mouse is within the `RESIZE_MARGIN` from any of the window's edges.
     * @param x The mouse's X-coordinate within the scene.
     * @param y The mouse's Y-coordinate within the scene.
     * @return The `ResizeMode` corresponding to the detected edge(s), or `NONE` if no edge is hit.
     */
    private ResizeMode getResizeMode(double x, double y)
    {
        boolean north = y < RESIZE_MARGIN;
        boolean south = y > scene.getHeight() - RESIZE_MARGIN;
        boolean east = x > scene.getWidth() - RESIZE_MARGIN;
        boolean west = x < RESIZE_MARGIN;

        if (north && east) return ResizeMode.NORTH_EAST;
        if (north && west) return ResizeMode.NORTH_WEST;
        if (south && east) return ResizeMode.SOUTH_EAST;
        if (south && west) return ResizeMode.SOUTH_WEST;
        if (north) return ResizeMode.NORTH;
        if (south) return ResizeMode.SOUTH;
        if (east) return ResizeMode.EAST;
        if (west) return ResizeMode.WEST;

        return ResizeMode.NONE;
    }
}