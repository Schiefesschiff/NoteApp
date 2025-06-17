package com.shortNotes;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.TextArea;
import javafx.stage.Stage;

public class BigNoteWindow
{
    @FXML
    public TextArea bigContentTextArea;

    private Stage stage;
    private Scene scene;
    private NotesMainViewController.NoteData currentNoteSave;

    // is executed when the Close button is pressed
    @FXML
    private void handleCloseButton(ActionEvent event)
    {
        CloseWindow();
    }


    public void CloseWindow()
    {
        //transfers the data to the NoteSave memory Object
        SaveData();
        //deletes this class so that the garbage collector can delete it permanently
        currentNoteSave.setBigNoteWindowController(null);
        // closes the window
        stage.close();
    }

    // function to transfer and save all important data for creating the class
    // cannot be executed in the Constructor because the window can only pass important data later
    public void setData(Stage stage, Scene scene, NotesMainViewController.NoteData noteData)
    {
        this.stage = stage;
        this.scene = scene;

        this.currentNoteSave = noteData;
        bigContentTextArea.setText(currentNoteSave.getText());

        // set Window Position, if Maximized and Size
        stage.setX(currentNoteSave.getNoteSave().getWindowConfig().x);
        stage.setY(currentNoteSave.getNoteSave().getWindowConfig().y);

        stage.setMaximized(currentNoteSave.getNoteSave().getWindowConfig().maximized);

        stage.setWidth(currentNoteSave.getNoteSave().getWindowConfig().width);
        stage.setHeight(currentNoteSave.getNoteSave().getWindowConfig().height);

    }

    // Saves the text, size and position of the note in the memory object
    private void SaveData()
    {
        WindowConfig windowConfig = new WindowConfig(stage.getWidth(), stage.getHeight(), stage.getX(), stage.getY(), stage.isMaximized());

        currentNoteSave.setNoteSave(windowConfig, bigContentTextArea.getText(), false);
    }
}