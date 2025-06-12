package com.shortNotes;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;

import java.util.ArrayList;

public class NoteItemController
{
    @FXML
    public TextArea contentLabel;
    @FXML
    public Button deleteButton;

    private NotesMainViewController notesMainViewController;
    private NotesMainViewController.NoteData currentNoteSave;

    @FXML
    public void initialize()
    {
        contentLabel.focusedProperty().addListener(new ChangeListener<Boolean>()
        {
            @Override
            public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue)
            {
                if (oldValue && !newValue)
                {
                    setTextInSaveNote(contentLabel.getText());
                }
            }
        });

        contentLabel.setOnMouseClicked(event ->
        {
            if (event.getClickCount() == 1)
            {

            } else if (event.getClickCount() == 2)
            {

            }
        });

    }


    public void setData(NotesMainViewController.NoteData noteData, NotesMainViewController notesMainViewController)
    {
        this.currentNoteSave = noteData;
        this.notesMainViewController = notesMainViewController;
        contentLabel.setText(currentNoteSave.getText());
    }

    private void setTextInSaveNote(String text)
    {
        currentNoteSave.setText(text);
    }

    @FXML
    public void deleteNote(ActionEvent actionEvent)
    {
        notesMainViewController.removeNote(currentNoteSave);
    }
}


