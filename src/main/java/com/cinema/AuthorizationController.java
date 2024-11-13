package com.cinema;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import org.controlsfx.control.textfield.CustomPasswordField;

public class AuthorizationController {

    DataBaseHandler dbHandler;

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Button loginBtn;

    @FXML
    private Button backBtn;

    @FXML
    private TextField numberField;

    @FXML
    private CustomPasswordField passwordField;

    @FXML
    private Button regBtn;

    @FXML
    void loginBtnClick(ActionEvent event) {

        String number = numberField.getText();
        String password = passwordField.getText();
        int code = 1;
        try {
           code = dbHandler.loginUser(number, password);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        if (code == 0) {
            if (UserInfo.USER_ROLE == 2) {
                Parent root = null;
                try {
                    root = FXMLLoader.load(getClass().getResource("ClientMain.fxml"));
                } catch (IOException e) {
                    e.printStackTrace();
                }
                Stage primaryStage = new Stage();
                primaryStage.setTitle(UserInfo.USER_NUMBER + " - Афиша");
                primaryStage.setMinWidth(800);
                primaryStage.setMinHeight(600);
                primaryStage.setScene(new Scene(root, 800, 600));
                primaryStage.getIcons().add(new Image(getClass().getResourceAsStream("icon.png")));
                backBtn.getScene().getWindow().hide();
                primaryStage.show();
            } else {
                Parent root = null;
                try {
                    root = FXMLLoader.load(getClass().getResource("AdminMain.fxml"));
                } catch (IOException e) {
                    e.printStackTrace();
                }
                Stage primaryStage = new Stage();
                primaryStage.setTitle(UserInfo.USER_NUMBER + " - Админ - Главное меню");
                primaryStage.setMinWidth(800);
                primaryStage.setMinHeight(600);
                primaryStage.setScene(new Scene(root, 800, 600));
                primaryStage.getIcons().add(new Image(getClass().getResourceAsStream("icon.png")));
                backBtn.getScene().getWindow().hide();
                primaryStage.show();
            }
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Ошибка");
            alert.setHeaderText("Неправильный логин И|ИЛИ пароль");
            alert.setContentText("Проверьте правильность ввода данных");
            alert.showAndWait();
        }
    }

    @FXML
    void backBtnClick(ActionEvent event) {
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
        backBtn.getScene().getWindow().hide();
        primaryStage.show();
    }


    @FXML
    void regBtnClick(ActionEvent event) {
        Parent root = null;
        FXMLLoader loader = null;
        try {
            loader = new FXMLLoader(Main.class.getResource("Registration.fxml"));
            root = loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Stage primaryStage = new Stage();
        primaryStage.setTitle("Регистрация");
        primaryStage.setResizable(false);
        primaryStage.setScene(new Scene(root, 400, 600));
        primaryStage.getIcons().add(new Image(getClass().getResourceAsStream("icon.png")));
        regBtn.getScene().getWindow().hide();
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

        numberField.textProperty().addListener((observable, oldValue, newValue) -> {
            try {
                if (newValue.isEmpty()) {
                    return;
                }
                Long.parseLong(newValue);
            } catch (NumberFormatException e) {
                numberField.setText(oldValue);
            }
            if (newValue.length() > 11) {
                numberField.setText(oldValue);
            }
        });

    }

}
