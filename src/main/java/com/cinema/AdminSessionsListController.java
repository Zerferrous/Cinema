package com.cinema;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.sql.Time;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.ZoneId;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.ResourceBundle;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Bounds;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.paint.Paint;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
import javafx.stage.Stage;

public class AdminSessionsListController {

    DataBaseHandler dbHandler;

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private AnchorPane anchorPane;

    @FXML
    private Button backBtn;

    @FXML
    private DatePicker datePicker;

    @FXML
    private ListView<Session> sessionsList;

    @FXML
    void backBtnClick(ActionEvent event) {
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
        backBtn.getScene().getWindow().hide();
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

        datePicker.sceneProperty().addListener(((observableValue, oldValue, newValue) -> {
            if (newValue != null) {
                try {
                    ObservableList<Session> list = dbHandler.getSessions();
                    FilteredList<Session> filteredList = new FilteredList<>(list, b -> true);
                    datePicker.getEditor().textProperty().addListener((obs, oldV, newV) -> {
                        filteredList.setPredicate(Session -> {

                            String lowerCaseFilter = newV.toLowerCase().replaceAll(" ", "");

                            if (Session.getSessionDate().equals(Date.from(datePicker.getValue().atStartOfDay(ZoneId.systemDefault()).toInstant()))) {
                                return true;
                            }  else return false;

                        });
                    });
                    sessionsList.setItems(filteredList);
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }));

        datePicker.setValue(LocalDate.now());

        sessionsList.setCellFactory(lv -> new ListCell<Session>() {

            final HBox root = new HBox(3);
            final TextFlow sessionInfo = new TextFlow();
            Text title = new Text();
            Text date = new Text();
            Text price = new Text();
            Text hall = new Text();
            Text emptySeats = new Text();
            final Button button = new Button();

            {
                root.setAlignment(Pos.CENTER_LEFT);

                title.setFill(Paint.valueOf("#e4e5ea"));
                title.setStyle("-fx-font-size: " + (Math.cbrt(anchorPane.getLayoutBounds().getHeight() * 15) ) +
                        "; -fx-font-weight: bold;");
                date.setFill(Paint.valueOf("#e4e5ea"));
                date.setStyle("-fx-font-size: " + (Math.cbrt(anchorPane.getLayoutBounds().getHeight() * 3) ) + "; ");
                price.setFill(Paint.valueOf("#e4e5ea"));
                price.setStyle("-fx-font-size: " + (Math.cbrt(anchorPane.getLayoutBounds().getHeight() * 3) ) + "; ");
                hall.setFill(Paint.valueOf("#e4e5ea"));
                hall.setStyle("-fx-font-size: " + (Math.cbrt(anchorPane.getLayoutBounds().getHeight() * 3) ) + "; ");
                emptySeats.setFill(Paint.valueOf("#e4e5ea"));
                emptySeats.setStyle("-fx-font-size: " + (Math.cbrt(anchorPane.getLayoutBounds().getHeight() * 3) ) + "; ");

                anchorPane.layoutBoundsProperty().addListener(new ChangeListener<Bounds>() {
                    @Override
                    public void changed(ObservableValue<? extends Bounds> observableValue, Bounds oldValue, Bounds newValue) {
                        title.setStyle("-fx-font-size: " + (Math.cbrt(newValue.getHeight() * 15) ) +
                                "; -fx-font-weight: bold;");
                        date.setStyle("-fx-font-size: " + (Math.cbrt(anchorPane.getLayoutBounds().getHeight() * 3) ) + "; ");
                        price.setStyle("-fx-font-size: " + (Math.cbrt(anchorPane.getLayoutBounds().getHeight() * 3) ) + "; ");
                        hall.setStyle("-fx-font-size: " + (Math.cbrt(anchorPane.getLayoutBounds().getHeight() * 3) ) + "; ");
                        emptySeats.setStyle("-fx-font-size: " + (Math.cbrt(anchorPane.getLayoutBounds().getHeight() * 3) ) + "; ");
                        sessionInfo.setLineSpacing(anchorPane.getLayoutBounds().getHeight() / 75);
                        root.setMaxWidth(anchorPane.getLayoutBounds().getWidth() - 236);
                    }
                });

                root.setMaxWidth(anchorPane.getLayoutBounds().getWidth() - 236);

                sessionInfo.getChildren().addAll(title, date, price, hall, emptySeats);
                sessionInfo.setLineSpacing(anchorPane.getLayoutBounds().getHeight() / 75);
                HBox.setHgrow(sessionInfo, Priority.ALWAYS);
                button.setMinWidth(100);
                button.setStyle("-fx-background-color:  #6B99C3; " +
                        "-fx-background-radius: 10; " +
                        "-fx-text-fill: #e4e5ea;");

                HBox.setMargin(sessionInfo, new Insets(0, 7, 0, 15));
                HBox.setMargin(button, new Insets(0, 15, 0, 7));
                root.getChildren().addAll(sessionInfo, button);

            }

            @Override
            protected void updateItem(Session session, boolean empty) {

                super.updateItem(session, empty);

                if (session == null || empty) {
                    setGraphic(null);
                    setStyle("-fx-background-color:  #16354D;");
                } else {
                    button.setText("Места");
                    if ((session.getSessionDate().equals(java.sql.Date.valueOf(LocalDate.now()))) && (session.getSessionTime().getHours() - 1 <= LocalTime.now().getHour())) {
                        button.setDisable(true);
                    } else {
                        button.setDisable(false);
                    }
                    button.setOnAction(new EventHandler<ActionEvent>() {
                        @Override
                        public void handle(ActionEvent event) {
                            Parent root = null;
                            FXMLLoader loader = null;
                            try {
                                loader = new FXMLLoader(Main.class.getResource("AdminSeatsInfo.fxml"));
                                root = loader.load();
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                            Stage primaryStage = new Stage();
                            Film film = null;
                            try {
                                film = dbHandler.getFilmById(session.getFilmId());
                            } catch (SQLException e) {
                                e.printStackTrace();
                            }
                            primaryStage.setTitle(film.getTitle() + " - Места");
                            primaryStage.setMinWidth(800);
                            primaryStage.setMinHeight(600);
                            AdminSeatsInfoController adminSeatsInfoController = loader.getController();
                            adminSeatsInfoController.setSession(session);
                            adminSeatsInfoController.setFilm(film);
                            primaryStage.setScene(new Scene(root, anchorPane.getLayoutBounds().getWidth(), anchorPane.getLayoutBounds().getHeight()));
                            primaryStage.getIcons().add(new Image(getClass().getResourceAsStream("icon.png")));
                            datePicker.getScene().getWindow().hide();
                            primaryStage.show();
                        }
                    });
                    String filmTitle = null;
                    try {
                        filmTitle = dbHandler.filmTitleById(session.getFilmId());
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                    title.setText(filmTitle + "\n");
                    DateFormat dateFormat = DateFormat.getDateInstance(DateFormat.DEFAULT, new Locale("ru", "RU"));
                    date.setText("Дата и время сеанса: " + dateFormat.format(session.getSessionDate()) + " " + session.getSessionTime() + "\n");
                    price.setText("Цена билета: " + session.getSessionPrice() + " р." + "\n");
                    hall.setText("№ Зала: " + session.getHallId() + "\n");
                    try {
                        emptySeats.setText("Свободных мест: " + dbHandler.getEmptySeats(session.getSessionId()) + " / " + dbHandler.seatsInHallCount(session.getHallId()) + "\n");
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                    setGraphic(root);
                    setStyle("-fx-background-color:  #16354D;");
                }

            }

        });

    }

}
