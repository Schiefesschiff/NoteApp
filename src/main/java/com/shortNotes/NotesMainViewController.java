package com.shortNotes;

import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;

import java.io.IOException;
import java.util.ArrayList;


/**
 * The main controller for displaying and managing a collection of notes within a grid layout.
 * It handles adding/removing notes, dynamically arranging them based on window size,
 * and coordinating with individual note controllers.
 */
public class NotesMainViewController
{
    // The main layout container for all notes, arranged in a grid.
    @FXML
    private GridPane notesMainView;

    private double gap = 10;
    private double actualNoteWidth = 100; // Fallback

    // Static list to hold all note data.
    private static final ArrayList<NoteData> noteSaves = new ArrayList<>();

    /**
     * Initializes the controller after FXML loading.
     * Sets up a listener to re-arrange notes when the main window's width changes.
     */
    public void initialize()
    {
        // Defer UI-related tasks to the JavaFX Application Thread.
        Platform.runLater(() ->
        {
            notesMainView.getScene().widthProperty().addListener((obs, oldWidth, newWidth) ->
            {
                // When width changes, re-calculate and set positions for all notes.
                setAllNotePositions();
                //  System.out.println("width: " + newWidth);
            });
        });
    }

    /**
     * Adds a new note to the display based on an existing `NoteSave` object.
     * This is typically used for loading notes from saved data.
     * @param note The `NoteSave` object containing the note's data.
     */
    public void addNote(NoteSave note)
    {
        try
        {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("NoteItem.fxml"));
            StackPane noteBox = loader.load();
            NoteItemController noteItemController = loader.getController();

            notesMainView.add(noteBox, 0, 0);

            var noteData = new NoteData(noteBox, noteItemController, note);
            noteSaves.add(noteData);
            noteItemController.setData(noteData, this);

            Platform.runLater(() ->
            {
                setAllNotePositions();
            });


        } catch (IOException e)
        {
            e.printStackTrace();
        }
    }

    /**
     * Creates and adds a new default empty note to the display.
     * This is used when the user clicks a "New Note" button.
     */
    public void addNote()
    {
        var temp = new NoteSave(SaveManager.NOTE_DEFAULT_WINDOW, "double click me", false);

        addNote(temp);
    }

    /**
     * Removes a specific note from the display and the internal data structure.
     * @param noteData The `NoteData` object of the note to be removed.
     */
    public void removeNote(NoteData noteData)
    {
        Node noteView = noteData.box;
        if (noteView != null)
        {
            notesMainView.getChildren().remove(noteView);
        }
        noteSaves.remove(noteData);
        setAllNotePositions();
    }

    /**
     * Recalculates and sets the grid positions for all visible notes.
     * This is called when notes are added/removed or the window size changes.
     */
    private void setAllNotePositions()
    {
        int count = notesMainView.getChildren().size();
        ObservableList<Node> alleNotes = notesMainView.getChildren();

        for (Node note : alleNotes)
        {
            int index = notesMainView.getChildren().indexOf(note);
            setNotePosition(index, note);
        }
    }

    /**
     * Calculates and applies the grid column and row for a single note.
     * @param noteIndex The index of the note in the collection.
     * @param note The UI `Node` representing the note.
     */
    private void setNotePosition(int noteIndex, Node note)
    {
        if (!notesMainView.getChildren().isEmpty())
        {
            Node firstNote = notesMainView.getChildren().get(0);
            actualNoteWidth = firstNote.getBoundsInParent().getWidth();
        }

        // Use `HelperFunctions` to calculate the grid column and row based on the note's index
        // and the available window width.
        HelperFunctions.GridPosition cc = HelperFunctions.calculateGridPosition(noteIndex, actualNoteWidth, gap, notesMainView.getScene().getWidth());

        // Apply the calculated column and row to the note's UI element within the GridPane.
        GridPane.setColumnIndex(note, cc.column);
        GridPane.setRowIndex(note, cc.row);
    }

    /**
     * FXML handler for a button click to add a new note.
     */
    @FXML
    private void onNewNoteButtonClick()
    {
        addNote();
    }


    /**
     * Returns the internal list of `NoteData` objects.
     * This is used by `MainViewController` for saving the application state.
     * @return An ArrayList of `NoteData` objects.
     */
    public ArrayList<NoteData> getNoteData()
    {
        return noteSaves;
    }

    /**
     * Called by `MainViewController` when the main application window is closing.
     * It instructs all currently managed `NoteItemController`s to close their large note windows.
     */
    public void MainViewIsClosing()
    {
        for (NoteData noteData : noteSaves)
        {
            noteData.noteItemController.MainViewIsClosing();
        }
    }


    /**
     * Inner static class to bundle a note's UI (`StackPane`), its controller (`NoteItemController`),
     * and its data (`NoteSave`). This simplifies managing note instances.
     */
    public static class NoteData
    {
        final StackPane box;
        final NoteItemController noteItemController;
        private BigNoteWindow bigNoteWindowController;
        private final NoteSave noteSave;

        NoteData(StackPane box, NoteItemController controller, NoteSave NoteSave)
        {
            this.box = box;
            this.noteItemController = controller;
            this.noteSave = NoteSave;
        }

        // --- Getters and Setters for NoteData properties ---

        public NoteSave getNoteSave()
        {
            return noteSave;
        }

        /**
         * Convenience method to update all fields of the encapsulated `NoteSave` object.
         */
        public void setNoteSave(WindowConfig windowConfig, String content, boolean isOpen)
        {
            this.noteSave.setWindowConfig(windowConfig);
            this.noteSave.setContent(content);
            this.noteSave.setOpen(isOpen);
        }

        /**
         * Convenience method to set the text content of the encapsulated `NoteSave` object.
         */
        public void setText(String text)
        {
            noteSave.setContent(text);
        }

        /**
         * Convenience method to get the text content from the encapsulated `NoteSave` object.
         */
        public String getText()
        {
            return noteSave.getContent();
        }

        public BigNoteWindow getBigNoteWindowController()
        {
            return bigNoteWindowController;
        }

        public void setBigNoteWindowController(BigNoteWindow bigNoteWindowController)
        {
            this.bigNoteWindowController = bigNoteWindowController;
        }
    }

}