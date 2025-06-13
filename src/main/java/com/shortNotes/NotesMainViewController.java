package com.shortNotes;

import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;

import java.io.IOException;
import java.util.ArrayList;

//interface NoteDeleteCallback {
//    void onNoteDeleteRequested(NotesMainViewController.NoteData noteFile);
//}

public class NotesMainViewController
{
    private int NoteCounter;

    @FXML
    private GridPane notesMainView;

    private double gap = 10;
    private double actualNoteWidth = 100; // Fallback

    private static final ArrayList<NoteData> noteSaves = new ArrayList<>();


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
            StackPane noteBox = loader.load();
            NoteItemController noteItemController = loader.getController();

            notesMainView.add(noteBox, 0, 0);
            NoteCounter++;

            var noteData = new NoteData(noteBox, noteItemController, note);
            noteSaves.add(noteData);
            noteItemController.setData(noteData, this);

            Platform.runLater(() ->
            {
                setAllNotePositions();
            });


        } catch (IOException e)
        {
            e.printStackTrace();
        }
    }

    public void addNote()
    {
        var temp = new NoteSave(SaveManager.NOTE_DEFAULT_WINDOW, "New Note", false);

        addNote(temp);
    }

    public void removeNote(NoteData noteData)
    {
        Node noteView = noteData.box;
        if (noteView != null)
        {
            notesMainView.getChildren().remove(noteView);
        }
        noteSaves.remove(noteData);
        setAllNotePositions();
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

        HelperFunctions.GridPosition cc = HelperFunctions.calculateGridPosition(noteIndex, actualNoteWidth, gap, notesMainView.getScene().getWidth());
        GridPane.setColumnIndex(note, cc.column);
        GridPane.setRowIndex(note, cc.row);
    }

    @FXML
    protected void onNewNoteButtonClick()
    {
        addNote();
    }

    public ArrayList<NoteData> getNoteData()
    {
        return noteSaves;
    }


    public static class NoteData
    {
        final StackPane box;
        final NoteItemController controller;
        private NoteSave noteSave;

        NoteData(StackPane box, NoteItemController controller, NoteSave NoteSave)
        {
            this.box = box;
            this.controller = controller;
            this.noteSave = NoteSave;
        }

        public NoteSave getNoteSave()
        {
            return noteSave;
        }

        public void setNoteSave(WindowConfig windowConfig,String content, boolean isOpen)
        {
            this.noteSave.setWindowConfig(windowConfig);
            this.noteSave.setContent(content);
            this.noteSave.setOpen(isOpen);
        }

        public void setText(String text)
        {
            noteSave.setContent(text);
        }

        public String getText()
        {
            return noteSave.getContent();
        }
    }

}