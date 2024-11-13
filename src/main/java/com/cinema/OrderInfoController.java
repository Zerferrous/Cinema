package com.cinema;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.text.DateFormat;
import java.util.Locale;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
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
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

public class OrderInfoController {

    DataBaseHandler dbHandler;

    Session session;

    Film film;

    ObservableList<Integer> selectedSeats = FXCollections.observableArrayList();

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private AnchorPane anchorPane;

    @FXML
    private Button backBtn;

    @FXML
    private Button confirmOrderBtn;

    @FXML
    private Label dateAndTimeLabel;

    @FXML
    private Label hallLabel;

    @FXML
    private HBox mainInfoBox;

    @FXML
    private Label orderSummLabel;

    @FXML
    private Label seatPriceLabel;

    @FXML
    private Label seatsLabel;

    @FXML
    private Label titleLabel;

    public void setSession(Session session) {
        this.session = session;
    }

    public void setSelectedSeats(ObservableList<Integer> list) {
        this.selectedSeats = list;
    }

    public void setFilm(Film film) {
        this.film = film;
    }

    @FXML
    void backBtnClck(ActionEvent event) {
        Parent root = null;
        FXMLLoader loader = null;
        try {
            loader = new FXMLLoader(Main.class.getResource("SeatsInfo.fxml"));
            root = loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Stage primaryStage = new Stage();
        primaryStage.setTitle(film.getTitle() + " - Места");
        primaryStage.setMinWidth(800);
        primaryStage.setMinHeight(600);
        SeatsInfoController seatsInfoController = loader.getController();
        seatsInfoController.setSession(session);
        seatsInfoController.setFilm(film);
        seatsInfoController.setSelectedSeats(selectedSeats);
        primaryStage.setScene(new Scene(root, anchorPane.getLayoutBounds().getWidth(), anchorPane.getLayoutBounds().getHeight()));
        primaryStage.getIcons().add(new Image(getClass().getResourceAsStream("icon.png")));
        backBtn.getScene().getWindow().hide();
        primaryStage.show();
    }

    @FXML
    void confirmOrderBtnClick(ActionEvent event) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Подтверждение");
        alert.setHeaderText("Подтвердить заказ?");
        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK) {
            try {
                dbHandler.addOrder(session.getSessionId(), UserInfo.USER_NUMBER, session.getHallId(), selectedSeats);
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
                primaryStage.setScene(new Scene(root, anchorPane.getLayoutBounds().getWidth(), anchorPane.getLayoutBounds().getHeight()));
                primaryStage.getIcons().add(new Image(getClass().getResourceAsStream("icon.png")));
                confirmOrderBtn.getScene().getWindow().hide();
                primaryStage.show();
            } catch (SQLException e) {
                e.printStackTrace();
            }
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

        anchorPane.sceneProperty().addListener(((observableValue, oldValue, newValue) -> {
            if (newValue != null) {
                titleLabel.setText(film.getTitle());
                DateFormat dateFormat = DateFormat.getDateInstance(DateFormat.DEFAULT, new Locale("ru", "RU"));
                dateAndTimeLabel.setText("Дата и время сеанса: " + dateFormat.format(session.getSessionDate()) + " " + session.getSessionTime() + "\n");
                hallLabel.setText("Зал №" + session.getHallId());
                if (selectedSeats.size() == 1) {
                    seatsLabel.setText("Место: " + selectedSeats.toString().replace("[", "").replace("]", ""));
                } else {
                    seatsLabel.setText("Места: " + selectedSeats.toString().replace("[", "").replace("]", ""));
                }
                seatPriceLabel.setWrapText(true);
                seatPriceLabel.setText("Цена за одно место: " + session.getSessionPrice() + " р.");
                orderSummLabel.setText("К оплате: " + String.format("%1$,.2f", Double.valueOf(session.getSessionPrice() * selectedSeats.size())) + " р.");
            }
        }));
    }

}
