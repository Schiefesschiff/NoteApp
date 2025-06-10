package com.shortNotes;

import javafx.event.EventHandler;
import javafx.scene.Cursor;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

public class WindowsResizer
{
    private final Stage stage;
    private final Scene scene;

    private double xOffset = 0;
    private double yOffset = 0;

    private double initialStageX;
    private double initialStageY;
    private double initialStageWidth;
    private double initialStageHeight;

    private final double RESIZE_MARGIN = 5;

    private ResizeMode currentMode = ResizeMode.NONE;

    private double minWidth = 150;
    private double minHeight = 200;

    private enum ResizeMode
    {
        NONE, NORTH, SOUTH, EAST, WEST, NORTH_EAST, NORTH_WEST, SOUTH_EAST, SOUTH_WEST
    }

    public WindowsResizer(Stage stage, Scene scene)
    {
        this.stage = stage;
        this.scene = scene;
        attachListeners();
    }

    private void attachListeners()
    {
        scene.setOnMousePressed(new EventHandler<MouseEvent>()
        {
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

                    stage.setX(newX);
                    stage.setY(newY);
                    stage.setWidth(newWidth);
                    stage.setHeight(newHeight);
                }
            }
        });

        scene.setOnMouseMoved(new EventHandler<MouseEvent>()
        {
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