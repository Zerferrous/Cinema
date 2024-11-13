package com.cinema;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class AdminMainController {

    DataBaseHandler dbHandler;

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Label actualSessionsLabel;

    @FXML
    private AnchorPane anchorPane;

    @FXML
    private Button deleteNonActualSessionsBtn;

    @FXML
    private Button filmAddBtn;

    @FXML
    private Button filmsInfoBtn;

    @FXML
    private Button logoutBtn;

    @FXML
    private Button sessionAddBtn;

    @FXML
    private Button sessionsInfoBtn;

    @FXML
    private Label todaySessionsLabel;

    @FXML
    private Button userAddBtn;

    @FXML
    void deleteNonActualSessionsBtnClick(ActionEvent event) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Подтверждение");
        alert.setHeaderText("Вы действительно хотите удалить все неактуальные сеансы?");
        alert.setContentText("Будут удалены все сеансы, до текущего дня");
        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK) {
            try {
                dbHandler.deleteNonActualSessions();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    @FXML
    void filmAddBtnClick(ActionEvent event) {
        Parent root = null;
        FXMLLoader loader = null;
        try {
            loader = new FXMLLoader(Main.class.getResource("FilmAddOrEdit.fxml"));
            root = loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Stage primaryStage = new Stage();
        primaryStage.setTitle("Админ - Добавление фильма");
        primaryStage.setMinWidth(800);
        primaryStage.setMinHeight(600);
        primaryStage.setResizable(false);
        FilmAddOrEditController filmAddOrEditController = loader.getController();
        primaryStage.setScene(new Scene(root, 800, 600));
        primaryStage.getIcons().add(new Image(getClass().getResourceAsStream("icon.png")));
        filmAddBtn.getScene().getWindow().hide();
        primaryStage.show();
    }

    @FXML
    void filmsInfoBtnClick(ActionEvent event) {
        Parent root = null;
        try {
            root = FXMLLoader.load(getClass().getResource("AdminFilmsList.fxml"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        Stage primaryStage = new Stage();
        primaryStage.setTitle("Админ - Информация о фильмах");
        primaryStage.setMinWidth(800);
        primaryStage.setMinHeight(600);
        primaryStage.setScene(new Scene(root, anchorPane.getLayoutBounds().getWidth(), anchorPane.getLayoutBounds().getHeight()));
        primaryStage.getIcons().add(new Image(getClass().getResourceAsStream("icon.png")));
        filmsInfoBtn.getScene().getWindow().hide();
        primaryStage.show();
    }

    @FXML
    void logoutBtnClck(ActionEvent event) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Подтверждение");
        alert.setHeaderText("Вы действительно хотите выйти из аккаунта?");
        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK) {
            UserInfo.USER_NUMBER = null;
            UserInfo.USER_FIO = null;
            UserInfo.USER_ROLE = 0;
            Parent root = null;
            try {
                root = FXMLLoader.load(getClass().getResource("ClientMain.fxml"));
            } catch (IOException e) {
                e.printStackTrace();
            }
            Stage primaryStage = new Stage();
            primaryStage.setTitle("Гость - Афиша");
            primaryStage.setMinWidth(800);
            primaryStage.setMinHeight(600);
            primaryStage.setScene(new Scene(root, 800, 600));
            primaryStage.getIcons().add(new Image(getClass().getResourceAsStream("icon.png")));
            logoutBtn.getScene().getWindow().hide();
            primaryStage.show();
        }
    }

    @FXML
    void sessionAddBtnClick(ActionEvent event) {
        Parent root = null;
        FXMLLoader loader = null;
        try {
            loader = new FXMLLoader(Main.class.getResource("AdminCreateSession.fxml"));
            root = loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Stage primaryStage = new Stage();
        primaryStage.setTitle("Админ - Добавление сеанса");
        primaryStage.setMinWidth(800);
        primaryStage.setMinHeight(600);
        primaryStage.setResizable(false);
        AdminCreateSessionController adminCreateSessionController = loader.getController();
        primaryStage.setScene(new Scene(root, 800, 600));
        primaryStage.getIcons().add(new Image(getClass().getResourceAsStream("icon.png")));
        filmAddBtn.getScene().getWindow().hide();
        primaryStage.show();
    }

    @FXML
    void sessionsInfoBtnClick(ActionEvent event) {
        Parent root = null;
        FXMLLoader loader = null;
        try {
            loader = new FXMLLoader(Main.class.getResource("AdminSessionsList.fxml"));
            root = loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Stage primaryStage = new Stage();
        primaryStage.setTitle("Админ - Информация о сеансах");
        primaryStage.setMinWidth(800);
        primaryStage.setMinHeight(600);
        primaryStage.setScene(new Scene(root, anchorPane.getLayoutBounds().getWidth(), anchorPane.getLayoutBounds().getHeight()));
        primaryStage.getIcons().add(new Image(getClass().getResourceAsStream("icon.png")));
        sessionsInfoBtn.getScene().getWindow().hide();
        primaryStage.show();
    }

    @FXML
    void userAddBtnClick(ActionEvent event) {
        Parent root = null;
        FXMLLoader loader = null;
        try {
            loader = new FXMLLoader(Main.class.getResource("AdminUserAdd.fxml"));
            root = loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Stage primaryStage = new Stage();
        primaryStage.setTitle("Админ - Добавление пользователя");
        primaryStage.setMinWidth(800);
        primaryStage.setMinHeight(600);
        primaryStage.setResizable(false);
        primaryStage.setScene(new Scene(root, 800, 600));
        primaryStage.getIcons().add(new Image(getClass().getResourceAsStream("icon.png")));
        filmAddBtn.getScene().getWindow().hide();
        primaryStage.show();
    }

    @FXML
    void initialize() {
        dbHandler = new DataBaseHandler();

        try {
            dbHandler.getDbConnection();
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }

        try {
            actualSessionsLabel.setText("Актуальных сеансов: " + dbHandler.getActualSessionsCount());
            todaySessionsLabel.setText("Сеансов сегодня: " + dbHandler.getTodaySessionsCount());
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}
