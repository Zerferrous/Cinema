package com.cinema;

import java.io.IOException;
import java.net.URL;
import java.sql.Date;
import java.sql.SQLException;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

public class AdminUserAddController {

    DataBaseHandler dbHandler;

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private AnchorPane anchorPane;

    @FXML
    private Button cancelBtn;

    @FXML
    private Button commitBtn;

    @FXML
    private DatePicker dateOfBirthPicker;

    @FXML
    private HBox mainInfoBox;

    @FXML
    private TextField nameField;

    @FXML
    private TextField numberField;

    @FXML
    private TextField patrinymicField;

    @FXML
    private TextField surnameField;

    @FXML
    void cancelBtnClick(ActionEvent event) {
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
        primaryStage.setScene(new Scene(root, anchorPane.getLayoutBounds().getWidth(), anchorPane.getLayoutBounds().getHeight()));
        primaryStage.getIcons().add(new Image(getClass().getResourceAsStream("icon.png")));
        cancelBtn.getScene().getWindow().hide();
        primaryStage.show();
    }

    @FXML
    void commitBtnClick(ActionEvent event) {
        if (numberField.getText() != null &&
        !numberField.getText().trim().isEmpty() &&
        surnameField.getText() != null &&
        !surnameField.getText().trim().isEmpty() &&
        nameField.getText() != null &&
        !nameField.getText().trim().isEmpty() &&
        dateOfBirthPicker.getEditor().getText() != null &&
        !dateOfBirthPicker.getEditor().getText().isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Подтверждение");
            alert.setHeaderText("Действительно добавить пользователя?");
            Optional<ButtonType> result = alert.showAndWait();
            if (result.get() == ButtonType.OK) {
                try {
                    String userNumber = numberField.getText();
                    String fio = surnameField.getText() + " " + nameField.getText() + " " + patrinymicField.getText();
                    Date dateOfBirth = Date.valueOf(dateOfBirthPicker.getValue());
                    dbHandler.addUser(userNumber, fio, dateOfBirth);
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
                    primaryStage.setScene(new Scene(root, anchorPane.getLayoutBounds().getWidth(), anchorPane.getLayoutBounds().getHeight()));
                    primaryStage.getIcons().add(new Image(getClass().getResourceAsStream("icon.png")));
                    cancelBtn.getScene().getWindow().hide();
                    primaryStage.show();
                } catch (SQLException e) {
                    e.printStackTrace();
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
    void initialize() {

        dbHandler = new DataBaseHandler();

        try {
            dbHandler.getDbConnection();
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }

        anchorPane.getStylesheets().add(getClass().getResource("StylesAddOrEdit.css").toExternalForm());
    }

}
