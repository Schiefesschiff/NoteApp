module com.shortNotes {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires org.kordamp.ikonli.javafx;
    requires org.kordamp.bootstrapfx.core;
    requires com.google.gson;
    requires org.fxmisc.richtext;

    exports com.shortNotes;
    opens com.shortNotes to javafx.fxml, com.google.gson;
}