package com.cinema;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.event.EventType;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.HPos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Circle;
import javafx.scene.text.Text;
import javafx.scene.text.TextBoundsType;
import javafx.stage.Stage;
import org.controlsfx.control.SearchableComboBox;

public class AdminSeatsInfoController {

    DataBaseHandler dbHandler;
    Session session;
    Film film;

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private AnchorPane anchorPane;

    @FXML
    private Button backBtn;

    @FXML
    private Button orderBtn;

    @FXML
    private SearchableComboBox<String> userNumberBox;

    @FXML
    private GridPane seatsPane;

    ObservableList<Integer> selectedSeats = FXCollections.observableArrayList();

    public void setSession(Session session) {
        this.session = session;
    }

    public void setFilm(Film film) {
        this.film = film;
    }

    public void setSelectedSeats(ObservableList<Integer> list) {
        this.selectedSeats = list;
    }

    public void setUserNumber(String userNumber) {
        userNumberBox.setValue(userNumber);
    }

    @FXML
    void backBtnClck(ActionEvent event) {
        Parent root = null;
        FXMLLoader loader = null;
        try {
            loader = new FXMLLoader(Main.class.getResource("AdminSessionsList.fxml"));
            root = loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Stage primaryStage = new Stage();
        primaryStage.setTitle("Админ - Сеансы");
        primaryStage.setMinWidth(800);
        primaryStage.setMinHeight(600);
        primaryStage.setScene(new Scene(root, anchorPane.getLayoutBounds().getWidth(), anchorPane.getLayoutBounds().getHeight()));
        primaryStage.getIcons().add(new Image(getClass().getResourceAsStream("icon.png")));
        backBtn.getScene().getWindow().hide();
        primaryStage.show();
    }

    @FXML
    void orderBtnClick(ActionEvent event) {
        Parent root = null;
        FXMLLoader loader = null;
        try {
            loader = new FXMLLoader(Main.class.getResource("AdminOrderInfo.fxml"));
            root = loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Stage primaryStage = new Stage();
        primaryStage.setTitle("Админ - Корзина");
        primaryStage.setMinWidth(800);
        primaryStage.setMinHeight(600);
        AdminOrderInfoController adminOrderInfoController = loader.getController();
        adminOrderInfoController.setSession(session);
        adminOrderInfoController.setSelectedSeats(selectedSeats);
        adminOrderInfoController.setFilm(film);
        adminOrderInfoController.setUserNumber(userNumberBox.getValue());
        primaryStage.setScene(new Scene(root, anchorPane.getLayoutBounds().getWidth(), anchorPane.getLayoutBounds().getHeight()));
        primaryStage.getIcons().add(new Image(getClass().getResourceAsStream("icon.png")));
        orderBtn.getScene().getWindow().hide();
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

        anchorPane.getStylesheets().add(getClass().getResource("StylesAddOrEdit.css").toExternalForm());

        try {
            userNumberBox.setItems(dbHandler.getUsersNumber());
        } catch (SQLException e) {
            e.printStackTrace();
        }

        seatsPane.sceneProperty().addListener((observableValue, oldValue, newValue) -> {
            if (newValue != null) {
                if (selectedSeats.isEmpty() || userNumberBox.getValue() == null || userNumberBox.getValue().isEmpty()) {
                    orderBtn.setDisable(true);
                }
                selectedSeats.addListener(new ListChangeListener<Integer>() {
                    @Override
                    public void onChanged(Change<? extends Integer> change) {
                        if (selectedSeats.isEmpty() || userNumberBox.getValue() == null || userNumberBox.getValue().isEmpty()) {
                            orderBtn.setDisable(true);
                        } else {
                            orderBtn.setDisable(false);
                        }
                    }
                });

                userNumberBox.valueProperty().addListener(new ChangeListener<String>() {
                    @Override
                    public void changed(ObservableValue<? extends String> observableValue, String s, String t1) {
                        if (userNumberBox.getValue() == null || userNumberBox.getValue().isEmpty() || selectedSeats.isEmpty()) {
                            orderBtn.setDisable(true);
                        } else {
                            orderBtn.setDisable(false);
                        }
                    }
                });

                int seatId = 1;
                for (int i = 0; i < 5; i++) {
                    for (int j = 0; j < 10; j++) {
                        Circle circle = new Circle();
                        circle.setRadius(20);
                        String seatStatus = "Свободно";
                        try {
                            seatStatus = dbHandler.getSeatStatus(session.getSessionId(), seatId);
                        } catch (SQLException e) {
                            e.printStackTrace();
                        }
                        if (seatStatus.equals("Занят")) {
                            circle.setFill(Paint.valueOf("crimson"));
                        } else {
                            circle.setFill(Paint.valueOf("green"));
                        }
                        Text text = new Text(String.valueOf(seatId));
                        text.setBoundsType(TextBoundsType.VISUAL);
                        StackPane stack = new StackPane();
                        stack.getChildren().addAll(circle, text);
                        GridPane.setHalignment(stack, HPos.CENTER);

                        int finalSeatId = seatId;
                        if (selectedSeats.contains(Integer.valueOf(finalSeatId))) {
                            circle.setFill(Paint.valueOf("yellow"));
                        }
                        stack.setOnMouseClicked(new EventHandler<MouseEvent>() {
                            @Override
                            public void handle(MouseEvent mouseEvent) {
                                if (!circle.getFill().equals(Paint.valueOf("yellow")) && !circle.getFill().equals(Paint.valueOf("crimson"))) {
                                    circle.setFill(Paint.valueOf("yellow"));
                                    selectedSeats.add(Integer.valueOf(finalSeatId));
                                } else if (circle.getFill().equals(Paint.valueOf("yellow"))) {
                                    circle.setFill(Paint.valueOf("green"));
                                    selectedSeats.remove(Integer.valueOf(finalSeatId));
                                }
                            }
                        });
                        seatsPane.add(stack, j, i);
                        seatId++;
                    }
                }
            }
        });

    }

}
