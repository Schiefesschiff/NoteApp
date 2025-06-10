package com.shortNotes;

import javafx.fxml.FXML;
import javafx.scene.control.Label;

import java.util.ArrayList;

public class NoteItemController
{
    @FXML
    public Label noteTitle;
    @FXML
    public Label contentLabel;

    private NoteSave currentNoteSave;

    public static ArrayList<NoteSave> noteSaves = new ArrayList<>();


    public void setData(NoteSave noteSave)
    {
        this.currentNoteSave = noteSave;
        noteTitle.setText(currentNoteSave.title());
        contentLabel.setText(currentNoteSave.content());
        noteSaves.add(noteSave);
    }

}


