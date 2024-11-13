package com.cinema;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.time.format.DateTimeFormatter;
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

public class AdminCreateSessionController {

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
    private DatePicker datePicker;

    @FXML
    private ComboBox<String> filmBox;

    @FXML
    private Spinner<Integer> hallNumberSpinner;

    @FXML
    private Spinner<Integer> hoursSpinner;

    @FXML
    private HBox mainInfoBox;

    @FXML
    private Spinner<Integer> minutesSpinner;

    @FXML
    private TextField ticketPriceField;

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
        if (filmBox.getValue() != null &&
                !filmBox.getValue().isEmpty() &&
                ticketPriceField.getText() != null &&
                !ticketPriceField.getText().isEmpty() &&
                datePicker.getEditor().getText() != null &&
                !datePicker.getEditor().getText().isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Подтверждение");
            alert.setHeaderText("Действительно добавить сеанс?");
            Optional<ButtonType> result = alert.showAndWait();
            if (result.get() == ButtonType.OK) {
                try {
                    int filmId = dbHandler.getFilmIdByTitle(filmBox.getValue());
                    String sessionDateTime = datePicker.getValue().toString() + " " + hoursSpinner.getValue() + ":" + minutesSpinner.getValue() + ":" + "00";
                    double sessionPrice = Double.valueOf(ticketPriceField.getText());
                    int hallId = hallNumberSpinner.getValue();
                    dbHandler.addSession(filmId, sessionDateTime, sessionPrice, hallId);
                } catch (SQLException e) {
                    e.printStackTrace();
                }
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

        hallNumberSpinner.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 10, 1));
        hoursSpinner.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 23, 0));
        minutesSpinner.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 59, 0));

        try {
            filmBox.setItems(dbHandler.getFilmsTitles());
        } catch (SQLException e) {
            e.printStackTrace();
        }

            ticketPriceField.textProperty().addListener((observable, oldValue, newValue) -> {
                try {
                    if (newValue.isEmpty()) {
                        return;
                    }

                    Double.parseDouble(newValue);
                } catch (NumberFormatException e) {
                    ticketPriceField.setText(oldValue);
                }
            });


    }

}
