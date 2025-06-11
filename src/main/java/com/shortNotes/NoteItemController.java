package com.shortNotes;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

import java.util.ArrayList;

public class NoteItemController
{
    @FXML
    public TextArea contentLabel;

    private NoteSave currentNoteSave;

    public static ArrayList<NoteSave> noteSaves = new ArrayList<>();

    public void setData(NoteSave noteSave)
    {
        this.currentNoteSave = noteSave;
        contentLabel.setText(currentNoteSave.content());
        noteSaves.add(noteSave);
    }

}


