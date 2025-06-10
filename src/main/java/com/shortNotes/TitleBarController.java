package com.shortNotes;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.stage.Stage;

public class TitleBarController
{
    public NotesMainViewController notesMainViewController;

    public void setNotesMainViewController(NotesMainViewController notesMainViewController)
    {
        this.notesMainViewController = notesMainViewController;
    }


    @FXML
    public void onAddNoteClicked(ActionEvent actionEvent)
    {
        notesMainViewController.addNote();
    }

    @FXML
    private void onCloseClicked()
    {
        Platform.exit();
    }

    @FXML
    private void onMinimizeClicked(ActionEvent event)
    {
        Node source = (Node) event.getSource();
        Stage stage = (Stage) source.getScene().getWindow();

        stage.setIconified(true);
    }

}
