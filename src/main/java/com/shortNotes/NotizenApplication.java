package com.shortNotes;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;

public class NotizenApplication extends Application
{
    @Override
    public void start(Stage stage) throws IOException
    {
        FXMLLoader fxmlLoader = new FXMLLoader(NotizenApplication.class.getResource("MainView.fxml"));
        Parent root = fxmlLoader.load();

        Scene scene = new Scene(root, 320, 240);
        scene.getStylesheets().add(getClass().getResource("/com/shortNotes/style.css").toExternalForm());
        stage.setTitle("Notizen");
        stage.setScene(scene);
        stage.initStyle(StageStyle.UNDECORATED);
        stage.setMinWidth(150);
        stage.setMinHeight(200);

        new WindowsResizer(stage, scene);

        stage.show();
    }

    public static void main(String[] args)
    {
        launch();
    }
}