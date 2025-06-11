package com.shortNotes;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

public class TitleBarController
{
    public NotesMainViewController notesMainViewController;
    private MainViewController mainViewController;

    @FXML
    private Button closeButton;

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
    private void onCloseClicked(ActionEvent event)
    {
        Stage stage = (Stage) closeButton.getScene().getWindow();

        stage.fireEvent(new WindowEvent(stage, WindowEvent.WINDOW_CLOSE_REQUEST));
        event.consume();
    }

    @FXML
    private void onMinimizeClicked(ActionEvent event)
    {
        Node source = (Node) event.getSource();
        Stage stage = (Stage) source.getScene().getWindow();

        stage.setIconified(true);

    }

}
