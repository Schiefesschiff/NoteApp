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

public class NoteItemController implements TextChangeListener
{
    @FXML
    public TextArea contentLabel;

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
                System.out.println("double clicked");
                OpenWindows();
            }
        });

    }

    public void setData(NotesMainViewController.NoteData noteData, NotesMainViewController notesMainViewController)
    {
        this.currentNoteSave = noteData;
        currentNoteSave.getNoteSave().addTextChangeListener(this);

        this.notesMainViewController = notesMainViewController;
        setTextInTextArea(currentNoteSave.getText());
    }

    private void setTextInTextArea(String text)
    {
        contentLabel.setText(text);
    }

    private void setTextInSaveNote(String text)
    {
        currentNoteSave.setText(text);
    }

    private void OpenWindows()
    {
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

            controller.setData(newStage, newScene, currentNoteSave);
            currentNoteSave.setBigNoteWindowController(controller);

            new WindowsResizer(newStage, newScene);

        } catch (IOException e)
        {
            e.printStackTrace();
        }
    }

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

    @Override
    public void onTextChanged(String newText)
    {
        setTextInTextArea(newText);
    }
}


