package com.shortNotes;

import javafx.fxml.FXML;
import javafx.stage.Stage;

/**
 * The MainViewController acts as the central controller for the shortNotes application's main window.
 * It coordinates interactions between the UI components (TitleBar, ScrollBar, NotesMainView),
 * manages the application's lifecycle events like startup and shutdown, and handles saving/loading application state.
 */
public class MainViewController
{
    @FXML
    private TitleBarController titleBarController;
    @FXML
    private ScrollBarController scrollBarController;
    private NotesMainViewController notesMainViewController;

    private Stage stage;
    private SaveManager saveManager;

    /**
     * Initializes the controller after its FXML elements have been loaded.
     * This method is automatically called by JavaFX.
     * It connects the NotesMainViewController to other UI components that need to interact with it.
     */
    @FXML
    public void initialize()
    {
        if (scrollBarController != null)
        {
            notesMainViewController = scrollBarController.getNotesMainViewController();
            titleBarController.setNotesMainViewController(notesMainViewController);
        }
    }

    // Sets the primary stage (window) for this controller.
    public void setStage(Stage stage)
    {
        this.stage = stage;
    }

    /**
     * Starts the main application logic once the stage is ready.
     * This method handles loading saved data (window state and notes) and setting up
     * the application's initial display.
     */
    public void start()
    {
        stage.setOnCloseRequest(event ->
        {
            onAppClose();
        });

        saveManager = new SaveManager();
        var saveData = saveManager.LoadData();
        for (var noteSave : saveData.NotesSaves)
        {
            noteSave.init();
        }

        stage.setMinWidth(100);
        stage.setMinHeight(100);

        stage.setX(saveData.mainWindow.x);
        stage.setY(saveData.mainWindow.y);

        stage.setMaximized(saveData.mainWindow.maximized);

        stage.setWidth(saveData.mainWindow.width);
        stage.setHeight(saveData.mainWindow.height);

        for (var noteSave : saveData.NotesSaves)
        {
            notesMainViewController.addNote(noteSave);
        }
    }

    /**
     * This method is called when the application is about to close (e.g., user clicks the close button).
     * It ensures all current notes and the main window's state are saved before termination.
     */
    public void onAppClose()
    {
        notesMainViewController.MainViewIsClosing();
        var noteData = notesMainViewController.getNoteData();
        var noteSaves = HelperFunctions.NoteDataToNoteSaves(noteData);
        var WinDa = new WindowConfig(stage.getWidth(), stage.getHeight(), stage.getX(), stage.getY(), stage.isMaximized());
        var savedata = new SaveData(WinDa, noteSaves);
        saveManager.SaveData(savedata);


    }

}
