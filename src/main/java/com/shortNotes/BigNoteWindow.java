package com.shortNotes;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.TextArea;
import javafx.stage.Stage;

public class BigNoteWindow
{
    @FXML
    public TextArea bigContentTextArea;

    @FXML
    private void handleCloseButton(ActionEvent event) {
        // Holen Sie die Stage, zu der der Button gehört
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.close(); // Schließt das Fenster
    }
}
