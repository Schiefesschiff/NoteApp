module com.shortNotes.notizen {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires org.kordamp.ikonli.javafx;
    requires org.kordamp.bootstrapfx.core;

    opens com.shortNotes to javafx.fxml;
    exports com.shortNotes;
}