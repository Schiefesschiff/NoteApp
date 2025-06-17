package com.shortNotes;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;


/**
 * Controller for the custom title bar of the application's main window.
 * This class handles actions initiated from the title bar, such as
 * adding new notes, closing the window, and minimizing the window.
 */
public class TitleBarController
{
    // Reference to the NotesMainViewController, allowing this controller to trigger note-related actions.
    public NotesMainViewController notesMainViewController;
    // Reference to the MainViewController (though currently unused in this snippet).
    private MainViewController mainViewController;

    // The FXML injected close button from the UI.
    @FXML
    private Button closeButton;

    /**
     * Sets the NotesMainViewController instance. This is essential for the title bar
     * to interact with the main note management logic (e.g., adding a note).
     * @param notesMainViewController The NotesMainViewController instance.
     */
    public void setNotesMainViewController(NotesMainViewController notesMainViewController)
    {
        this.notesMainViewController = notesMainViewController;
    }

    /**
     * Handles the action when the "Add Note" button in the title bar is clicked.
     * It delegates the task of creating a new note to the `NotesMainViewController`.
     * @param actionEvent The event triggered by the button click.
     */
    @FXML
    public void onAddNoteClicked(ActionEvent actionEvent)
    {
        notesMainViewController.addNote();
    }

    /**
     * Handles the action when the "Close" button in the title bar is clicked.
     * Instead of directly closing, it fires a `WINDOW_CLOSE_REQUEST` event on the stage.
     * This allows the main application to gracefully handle shutdown procedures (like saving data)
     * before the window actually closes.
     * @param event The event triggered by the button click.
     */
    @FXML
    private void onCloseClicked(ActionEvent event)
    {
        Stage stage = (Stage) closeButton.getScene().getWindow();

        stage.fireEvent(new WindowEvent(stage, WindowEvent.WINDOW_CLOSE_REQUEST));
        event.consume();
    }

    /**
     * Handles the action when the "Minimize" button in the title bar is clicked.
     * It iconifies (minimizes) the application window.
     * @param event The event triggered by the button click.
     */
    @FXML
    private void onMinimizeClicked(ActionEvent event)
    {
        Node source = (Node) event.getSource();
        Stage stage = (Stage) source.getScene().getWindow();

        stage.setIconified(true);

    }

}
