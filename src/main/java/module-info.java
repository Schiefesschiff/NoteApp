module com.shortNotes.notizen {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires org.kordamp.ikonli.javafx;
    requires org.kordamp.bootstrapfx.core;
    requires com.google.gson;

    opens com.shortNotes to javafx.fxml, com.google.gson;
    exports com.shortNotes;
}