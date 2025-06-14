package com.shortNotes;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.TextArea;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

public class BigNoteWindow
{
    @FXML
    public TextArea bigContentTextArea;

    private Stage stage;
    private Scene scene;
    private NotesMainViewController.NoteData currentNoteSave;


    @FXML
    private void handleCloseButton(ActionEvent event)
    {
        CloseWindow();
    }

    public void CloseWindow()
    {
        SaveData();
        currentNoteSave.setBigNoteWindowController(null);
        stage.close();
    }

    public void setData(Stage stage, Scene scene, NotesMainViewController.NoteData noteData)
    {
        this.stage = stage;
        this.scene = scene;

        this.currentNoteSave = noteData;
        bigContentTextArea.setText(currentNoteSave.getText());

        stage.setX(currentNoteSave.getNoteSave().getWindowConfig().x);
        stage.setY(currentNoteSave.getNoteSave().getWindowConfig().y);

        stage.setMaximized(currentNoteSave.getNoteSave().getWindowConfig().maximized);

        stage.setWidth(currentNoteSave.getNoteSave().getWindowConfig().width);
        stage.setHeight(currentNoteSave.getNoteSave().getWindowConfig().height);

    }

    private void SaveData()
    {
        WindowConfig windowConfig = new WindowConfig(stage.getWidth(), stage.getHeight(), stage.getX(), stage.getY(), stage.isMaximized());

        currentNoteSave.setNoteSave(windowConfig, bigContentTextArea.getText(), false);
    }
}