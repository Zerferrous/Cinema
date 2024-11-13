package com.cinema;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
import javafx.scene.web.WebView;
import javafx.stage.Stage;

public class FilmInfoController {

    private Film film;

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Label ageLabel;

    @FXML
    private AnchorPane anchorPane;

    @FXML
    private Button backBtn;

    @FXML
    private Label countryLabel;

    @FXML
    private Text descriptionText;

    @FXML
    private TextFlow descriptionTextFlow;

    @FXML
    private Label genresLabel;

    @FXML
    private HBox mainInfoBox;

    @FXML
    private Label lenLabel;

    @FXML
    private Button sessionsBtn;

    @FXML
    private Label titleLabel;

    @FXML
    private WebView trailerWebView;

    @FXML
    private Label yearLabel;

    @FXML
    void backBtnClck(ActionEvent event) {
        Parent root = null;
        try {
            root = FXMLLoader.load(getClass().getResource("ClientMain.fxml"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        Stage primaryStage = new Stage();
        if (UserInfo.USER_NUMBER == null) {
            primaryStage.setTitle("Гость - Афиша");
        } else {
            primaryStage.setTitle(UserInfo.USER_NUMBER + " - Афиша");
        }
        primaryStage.setMinWidth(800);
        primaryStage.setMinHeight(600);
        primaryStage.setScene(new Scene(root, anchorPane.getLayoutBounds().getWidth(), anchorPane.getLayoutBounds().getHeight()));
        trailerWebView.getEngine().reload();
        primaryStage.getIcons().add(new Image(getClass().getResourceAsStream("icon.png")));
        backBtn.getScene().getWindow().hide();
        primaryStage.show();
    }

    @FXML
    void sessionsBtnClick(ActionEvent event) {
        Parent root = null;
        FXMLLoader loader = null;
        try {
            loader = new FXMLLoader(Main.class.getResource("SessionsInfo.fxml"));
            root = loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Stage primaryStage = new Stage();
        primaryStage.setTitle(film.getTitle() + " - Сеансы");
        primaryStage.setMinWidth(800);
        primaryStage.setMinHeight(600);
        SessionsInfoController sessionsInfoController = loader.getController();
        sessionsInfoController.setFilm(film);
        primaryStage.setScene(new Scene(root, anchorPane.getLayoutBounds().getWidth(), anchorPane.getLayoutBounds().getHeight()));
        primaryStage.getIcons().add(new Image(getClass().getResourceAsStream("icon.png")));
        sessionsBtn.getScene().getWindow().hide();
        primaryStage.show();
    }

    public void setFilm(Film film) {
        this.film = film;
    }

    @FXML
    void initialize() {

        titleLabel.sceneProperty().addListener(((observable, oldValue, newValue) -> {
            if (newValue != null) {
                titleLabel.setText(film.getTitle());
                genresLabel.setText("Жанры: " + film    .getGenres());
                yearLabel.setText("Год: " + film.getYear());
                countryLabel.setText("Страна: " + film.getCountry());

                ImageView afishaImage = new ImageView();
                afishaImage.setImage(film.getImage());
                afishaImage.setFitWidth(150);
                afishaImage.setFitHeight(200);

                Rectangle clip = new Rectangle(
                        afishaImage.getFitWidth(), afishaImage.getFitHeight()
                );
                clip.setArcWidth(20);
                clip.setArcHeight(20);
                afishaImage.setClip(clip);

                mainInfoBox.getChildren().add(0, afishaImage);
                trailerWebView.getEngine().load("https://www.youtube.com/embed/" + film.getTrailerLink());
                descriptionText.setText(film.getFilmDescription());
                lenLabel.setText("Продолжительность: " + film.getFilmLenght());
                ageLabel.setText("Возрастное ограничение: " + film.getAge());
            }
        }));

    }

}
