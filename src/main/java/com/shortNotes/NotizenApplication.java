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
        scene.getStylesheets().add(getClass().getResource("/com/shortNotes/TextAreaStyle.css").toExternalForm());
        stage.setTitle("Notizen");
        stage.setScene(scene);
        stage.initStyle(StageStyle.UNDECORATED);
        stage.show();

        new WindowsResizer(stage, scene);
        MainViewController mainViewController = fxmlLoader.getController();
        mainViewController.setStage(stage);
        mainViewController.start();

    }

    public static void main(String[] args)
    {
        launch();
    }
}