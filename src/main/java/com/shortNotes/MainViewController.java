package com.shortNotes;

import javafx.fxml.FXML;

public class MainViewController
{
    @FXML
    private TitleBarController titleBarController;
    @FXML
    private ScrollBarController scrollBarController;
    private NotesMainViewController notesMainViewController;

    @FXML
    public void initialize()
    {
        if (scrollBarController != null)
        {
            notesMainViewController = scrollBarController.getNotesMainViewController();
            titleBarController.setNotesMainViewController(notesMainViewController);
        }
    }
}
