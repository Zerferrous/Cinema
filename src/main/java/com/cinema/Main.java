package com.cinema;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.IOException;
import java.time.LocalDate;
import java.util.Date;

public class Main extends Application {
    @Override
    public void start(Stage stage) throws IOException {

        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("ClientMain.fxml"));
        stage.setTitle("Гость - Афиша");
        stage.setMinWidth(800);
        stage.setMinHeight(600);
        Scene scene = new Scene(fxmlLoader.load(), 800, 600);
        stage.getIcons().add(new Image(getClass().getResourceAsStream("icon.png")));
        stage.setScene(scene);
        stage.show();

    }
}