package com.shortNotes;

import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;

import java.io.IOException;
import java.util.ArrayList;

public class NotesMainViewController
{
    private int NoteCounter;

    @FXML
    private GridPane notesMainView;


    private double gap = 10;
    private double actualNoteWidth = 100; // Fallback

    public void initialize()
    {
        Platform.runLater(() ->
        {
            notesMainView.getScene().widthProperty().addListener((obs, oldWidth, newWidth) ->
            {
                setAllNotePositions();
              //  System.out.println("width: " + newWidth);
            });
        });
    }

    public void addNote(NoteSave note)
    {
        try
        {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("NoteItem.fxml"));
            VBox noteBox = loader.load();
            NoteItemController noteItemController = loader.getController();
            noteItemController.setData(note);

            notesMainView.add(noteBox, 0, 0);
            NoteCounter++;
            setAllNotePositions();

        } catch (IOException e)
        {
            e.printStackTrace();
        }
    }

    public void addNote()
    {
        var temp = new NoteSave(SaveManager.NOTE_DEFAULT_WINDOW, "New Note", "Number " + (NoteCounter+1));

        addNote(temp);
    }

    public void removeNote()
    {

    }

    private void setAllNotePositions()
    {
        int count = notesMainView.getChildren().size();
        ObservableList<Node> alleNotes = notesMainView.getChildren();

        for (Node note : alleNotes)
        {
            int index = notesMainView.getChildren().indexOf(note);
            setNotePosition(index, note);
        }
    }

    private void setNotePosition(int noteIndex, Node note)
    {
        if (!notesMainView.getChildren().isEmpty())
        {
            Node firstNote = notesMainView.getChildren().get(0);
            actualNoteWidth = firstNote.getBoundsInParent().getWidth();
        }

        MathHelper.GridPosition cc = MathHelper.calculateGridPosition(noteIndex, actualNoteWidth, gap, notesMainView.getScene().getWidth());
        GridPane.setColumnIndex(note, cc.column);
        GridPane.setRowIndex(note, cc.row);
    }

    @FXML
    protected void onNewNoteButtonClick()
    {
        addNote();
    }


}