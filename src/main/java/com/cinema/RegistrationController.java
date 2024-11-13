package com.cinema;

import java.io.IOException;
import java.net.URL;
import java.sql.Date;
import java.sql.SQLException;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import org.controlsfx.control.textfield.CustomPasswordField;

public class RegistrationController {

    DataBaseHandler dbHandler;

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private VBox mainVbox;

    @FXML
    private Button regBtn;

    @FXML
    private Button backBtn;

    @FXML
    private TextField numberField;

    @FXML
    private TextField fioField;

    @FXML
    private DatePicker dateOfBirthPicker;

    @FXML
    private CustomPasswordField passwordField;

    @FXML
    private Button authBtn;

    @FXML
    void regBtnClick(ActionEvent event) {
        if (numberField.getText() != null &&
        !numberField.getText().trim().isEmpty() &&
        fioField.getText() != null &&
        !fioField.getText().trim().isEmpty() &&
        dateOfBirthPicker.getEditor() != null &&
        !dateOfBirthPicker.getEditor().getText().isEmpty()) {
            if (passwordField.getText() != null && !passwordField.getText().trim().isEmpty()) {
                int code = 0;
                try {
                    code = dbHandler.regUser(numberField.getText(), fioField.getText(), Date.valueOf(dateOfBirthPicker.getValue()), passwordField.getText());
                } catch (SQLException e) {
                    e.printStackTrace();
                }
                if (code != 1) {
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Успешно");
                    alert.setHeaderText("Вы успешно зарегистрировались!");
                    alert.showAndWait();
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
                } else {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Ошибка");
                    alert.setHeaderText("Пользователь уже существует");
                    alert.showAndWait();
                }
            } else {
                int code = 0;
                try {
                    code = dbHandler.regUser(numberField.getText(), fioField.getText(), Date.valueOf(dateOfBirthPicker.getValue()));
                } catch (SQLException e) {
                    e.printStackTrace();
                }
                if (code != 1) {
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Успешно");
                    alert.setHeaderText("Вы успешно зарегистрировались!");
                    alert.showAndWait();
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
                } else {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Ошибка");
                    alert.setHeaderText("Пользователь уже существует");
                    alert.showAndWait();
                }
            }
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Ошибка");
            alert.setHeaderText("Заполните пустые поля");
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
    void authBtnClick(ActionEvent event) {
        Parent root = null;
        FXMLLoader loader = null;
        try {
            loader = new FXMLLoader(Main.class.getResource("Authorization.fxml"));
            root = loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Stage primaryStage = new Stage();
        primaryStage.setTitle("Авторизация");
        primaryStage.setResizable(false);
        primaryStage.setScene(new Scene(root, 400, 600));
        primaryStage.getIcons().add(new Image(getClass().getResourceAsStream("icon.png")));
        authBtn.getScene().getWindow().hide();
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

        mainVbox.getStylesheets().add(getClass().getResource("StylesAddOrEdit.css").toExternalForm());

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
