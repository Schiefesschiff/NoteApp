package com.shortNotes;

import javafx.fxml.FXML;
import javafx.stage.Stage;

public class MainViewController
{
    @FXML
    private TitleBarController titleBarController;
    @FXML
    private ScrollBarController scrollBarController;
    private NotesMainViewController notesMainViewController;

    private Stage stage;
    private SaveManager saveManager;

    @FXML
    public void initialize()
    {
        if (scrollBarController != null)
        {
            notesMainViewController = scrollBarController.getNotesMainViewController();
            titleBarController.setNotesMainViewController(notesMainViewController);
        }
    }

    public void setStage(Stage stage)
    {
        this.stage = stage;
    }

    public void start()
    {
        stage.setOnCloseRequest(event ->
        {
            onAppClose();
        });

        saveManager = new SaveManager();
        var saveData = saveManager.LoadData();

        stage.setMinWidth(100);
        stage.setMinHeight(100);

        stage.setWidth(saveData.mainWindow.width());
        stage.setHeight(saveData.mainWindow.height());

        for (var noteSave : saveData.NotesSaves)
        {
            notesMainViewController.addNote(noteSave);
        }
    }

    public void onAppClose()
    {
        var noteData = notesMainViewController.getNoteData();
        var noteSaves = HelperFunctions.NoteDataToNoteSaves(noteData);

        var savedata = new SaveData(new WindowConfig(stage.getWidth(), stage.getHeight(), -1, -1, false),noteSaves );
        saveManager.SaveData(savedata);
    }

}
