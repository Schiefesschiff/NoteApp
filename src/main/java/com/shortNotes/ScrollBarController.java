package com.shortNotes;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.ScrollPane;
import javafx.scene.input.ScrollEvent;
import javafx.scene.layout.GridPane;

public class ScrollBarController
{
    @FXML
    private ScrollPane scrollBar;
    @FXML
    private NotesMainViewController notesMainViewController;


    public NotesMainViewController getNotesMainViewController()
    {
        return notesMainViewController;
    }

    public void initialize()
    {
        Platform.runLater(() ->
        {
            scrollBar.addEventFilter(ScrollEvent.SCROLL, event ->
            {
                double deltaY = event.getDeltaY(); // Standard mouse wheel movement
                double contentHeight = scrollBar.getContent().getBoundsInLocal().getHeight();
                double scrollSpeed = 11;

                double delta = deltaY * scrollSpeed / contentHeight;
                scrollBar.setVvalue(scrollBar.getVvalue() - delta);
                event.consume(); // Prevents JavaFX from scrolling additionally
            });
        });
    }
}
