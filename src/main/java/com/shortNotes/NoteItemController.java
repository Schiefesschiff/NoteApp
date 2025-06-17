package com.shortNotes;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextArea;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;

/**
 * Controller for a single "Note Item" displayed in the main application view.
 * This class manages the UI representation and basic interactions of a note,
 * such as editing its content, opening it in a larger dedicated window, and deleting it.
 */
public class NoteItemController implements TextChangeListener
{
    @FXML
    public TextArea contentLabel;

    private NotesMainViewController notesMainViewController;
    private NotesMainViewController.NoteData currentNoteSave;


    /**
     * Initializes the controller after its FXML components have been loaded.
     * Sets up listeners for text area focus changes and mouse clicks.
     */
    @FXML
    public void initialize()
    {
        // Listener to save text when the TextArea loses focus.
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

        // Listener for mouse clicks on the TextArea.
        contentLabel.setOnMouseClicked(event ->
        {
            if (event.getClickCount() == 1)
            {

            } else if (event.getClickCount() == 2)
            {
                System.out.println("double clicked");
                OpenWindows();
            }
        });

    }

    /**
     * Sets the data for this note item and establishes a connection to the main notes controller.
     * Also registers this controller as a listener for text changes in the associated NoteSave object.
     * @param noteData The NoteData object containing the note's UI, controller, and data.
     * @param notesMainViewController The main controller responsible for managing all notes.
     */
    public void setData(NotesMainViewController.NoteData noteData, NotesMainViewController notesMainViewController)
    {
        this.currentNoteSave = noteData;
        currentNoteSave.getNoteSave().addTextChangeListener(this);

        this.notesMainViewController = notesMainViewController;
        setTextInTextArea(currentNoteSave.getText());
    }

    /**
     * Updates the text displayed in the TextArea.
     * @param text The new text to display.
     */
    private void setTextInTextArea(String text)
    {
        contentLabel.setText(text);
    }

    /**
     * Updates the content in the underlying NoteSave data model with the text from the TextArea.
     * This method is called when the user finishes editing.
     * @param text The text to save.
     */
    private void setTextInSaveNote(String text)
    {
        currentNoteSave.setText(text);
    }

    /**
     * Opens a new, undecorated window to display the full content of this note.
     * It prevents opening multiple windows for the same note.
     */
    private void OpenWindows()
    {
        // Prevent opening if the note's big window is already open.
        if (currentNoteSave.getNoteSave().isOpen())return;
        currentNoteSave.getNoteSave().setOpen(true);
        try
        {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("BigNoteWindow.fxml"));
            Parent root = fxmlLoader.load();
            BigNoteWindow controller = fxmlLoader.getController();
            Stage newStage = new Stage();
            newStage.setTitle("FXML Fenster");
            Scene newScene = new Scene(root);
            newScene.getStylesheets().add(getClass().getResource("/com/shortNotes/style.css").toExternalForm());
            newScene.getStylesheets().add(getClass().getResource("/com/shortNotes/TextAreaStyle.css").toExternalForm());
            newStage.setScene(newScene);
            newStage.initStyle(StageStyle.UNDECORATED);
            newStage.show();

            // Pass data to the BigNoteWindow controller and store its reference.
            controller.setData(newStage, newScene, currentNoteSave);
            currentNoteSave.setBigNoteWindowController(controller);

            // Attach a custom resizer to the new undecorated window.
            new WindowsResizer(newStage, newScene);

        } catch (IOException e)
        {
            e.printStackTrace();
        }
    }

    /**
     * Called by the main controller when the application is closing.
     * If this note's large window is open, it triggers its closure.
     */
    public void MainViewIsClosing()
    {
        if (!currentNoteSave.getNoteSave().isOpen())return;

        currentNoteSave.getBigNoteWindowController().CloseWindow();
    }

    @FXML
    public void deleteNote(ActionEvent actionEvent)
    {
        currentNoteSave.getNoteSave().removeTextChangeListener(this);
        notesMainViewController.removeNote(currentNoteSave);
    }

    /**
     * Callback method from the TextChangeListener interface.
     * Called when the content of the associated NoteSave object changes.
     * Updates the TextArea to reflect the new text.
     */
    @Override
    public void onTextChanged(String newText)
    {
        setTextInTextArea(newText);
    }
}


