package com.cinema;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.FileAlreadyExistsException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.SQLException;
import java.sql.Time;
import java.util.*;

import javafx.collections.ListChangeListener;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.shape.Rectangle;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import org.controlsfx.control.CheckComboBox;

public class FilmAddOrEditController {

    DataBaseHandler dbHandler;

    Film film;

    File loadedImage;

    ImageView choosenImage;

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private CheckComboBox<String> ageBox;

    @FXML
    private AnchorPane anchorPane;

    @FXML
    private Button backBtn;

    @FXML
    private Button commitBtn;

    @FXML
    private TextField countryField;

    @FXML
    private TextArea descriptionArea;

    @FXML
    private CheckComboBox<String> genresBox;

    @FXML
    private Spinner<Integer> hoursSpinner;

    @FXML
    private HBox mainInfoBox;

    @FXML
    private Spinner<Integer> minutesSpinner;

    @FXML
    private Button photoBtn;

    @FXML
    private TextField titleField;

    @FXML
    private TextField trailerField;

    @FXML
    private TextField yearField;

    public void setFilm(Film film) {
        this.film = film;
    }

    @FXML
    void backBtnClick(ActionEvent event) {
        if (film != null) {
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
            primaryStage.setScene(new Scene(root, anchorPane.getLayoutBounds().getWidth(), anchorPane.getLayoutBounds().getHeight()));
            primaryStage.getIcons().add(new Image(getClass().getResourceAsStream("icon.png")));
            backBtn.getScene().getWindow().hide();
            primaryStage.show();
        }
    }

    @FXML
    void commitBtnClick(ActionEvent event) {
        String fileName;
        try {
            fileName = loadedImage.getName();
            try {
                String copyPath = Paths.get(Main.class.getResource("AfishaImages").toURI()).toString();
                try {
                    Files.copy(Path.of(loadedImage.getPath()), Path.of(copyPath + "/" + fileName));
                } catch (FileAlreadyExistsException e) {

                } catch (IOException e) {
                    e.printStackTrace();
                }
            } catch (URISyntaxException e) {
                e.printStackTrace();
            }
        } catch (NullPointerException nullPointerException) {
            fileName = "placeholder.jpg";
        }

        if (titleField.getText() != null &&
                !titleField.getText().trim().isEmpty() &&
                ageBox.getCheckModel().getCheckedItems() != null &&
                !ageBox.getCheckModel().getCheckedItems().isEmpty() &&
                descriptionArea.getText() != null &&
                !descriptionArea.getText().trim().isEmpty() &&
                trailerField.getText() != null &&
                !trailerField.getText().trim().isEmpty() &&
                countryField != null &&
                !countryField.getText().isEmpty() &&
                yearField.getText() != null &&
                !yearField.getText().trim().isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Подтверждение");
            alert.setHeaderText("Подтвердить добавление/изменение?");
            Optional<ButtonType> result = alert.showAndWait();
            if (result.get() == ButtonType.OK) {
                String title = titleField.getText();
                String ageGroup = ageBox.getCheckModel().getCheckedItems().get(0);
                String time = hoursSpinner.getValue() + ":" + minutesSpinner.getValue() + ":00";
                String description = descriptionArea.getText();
                String trailer = trailerField.getText();
                String image = fileName;
                String country = countryField.getText();
                String year = yearField.getText();
                List<String> genres = genresBox.getCheckModel().getCheckedItems();
                if (film != null) {
                    try {
                        dbHandler.updateFilm(film.getId(), title, ageGroup, time, description, trailer, image, country, year, genres);
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                } else {
                    try {
                        dbHandler.addFilm(title, ageGroup, time, description, trailer, image, country, year, genres);
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                }
                if (film != null) {
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
                    primaryStage.setScene(new Scene(root, anchorPane.getLayoutBounds().getWidth(), anchorPane.getLayoutBounds().getHeight()));
                    primaryStage.getIcons().add(new Image(getClass().getResourceAsStream("icon.png")));
                    backBtn.getScene().getWindow().hide();
                    primaryStage.show();
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
    void photoBtnClick(ActionEvent event) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("All Images", "*.jpg; *.png"),
                new FileChooser.ExtensionFilter("JPG", "*.jpg"),
                new FileChooser.ExtensionFilter("PNG", "*.png")
        );
        loadedImage = fileChooser.showOpenDialog(new Stage());
        if (loadedImage != null) {
            Image image = new Image(loadedImage.getPath());
            choosenImage = new ImageView(image);
            choosenImage.setFitWidth(150);
            choosenImage.setFitHeight(200);

            Rectangle clip = new Rectangle(
                    choosenImage.getFitWidth(), choosenImage.getFitHeight()
            );
            clip.setArcWidth(20);
            clip.setArcHeight(20);
            choosenImage.setClip(clip);
            if (mainInfoBox.getChildren().get(0).getClass().getName().equals("javafx.scene.image.ImageView")) {
                mainInfoBox.getChildren().remove(0);
                mainInfoBox.getChildren().add(0, choosenImage);
            } else {
                mainInfoBox.getChildren().add(0, choosenImage);
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

        try {
            genresBox.getItems().addAll(dbHandler.getGenresList());
            ageBox.getItems().addAll(dbHandler.getAgeGroupsList());
        } catch (SQLException e) {
            e.printStackTrace();
        }

        ageBox.getCheckModel().getCheckedItems().addListener(new ListChangeListener<String>() {
            @Override
            public void onChanged(Change<? extends String> change) {
                while (change.next()) {
                    if (change.wasAdded()) {
                        String lastSelected = change.getAddedSubList().get(change.getAddedSize() - 1);
                        List<String> list1 = new ArrayList<>();
                        for (String item : change.getList()) {
                            list1.add(item);
                        }

                        Iterator<String> iterator = list1.iterator();
                        while (iterator.hasNext()) {
                            String string = iterator.next();
                            if (string.equals(lastSelected)) {
                                iterator.remove();
                            }
                        }

                        for (String item : list1) {
                            ageBox.getCheckModel().clearCheck(item);
                        }

                    }
                }
            }
        });

        anchorPane.getStylesheets().add(getClass().getResource("StylesAddOrEdit.css").toExternalForm());

        hoursSpinner.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 5));
        minutesSpinner.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 59));

        yearField.textProperty().addListener((observable, oldValue, newValue) -> {
            try {
                if (newValue.isEmpty()) {
                    return;
                }
                Integer.parseInt(newValue);
            } catch (NumberFormatException e) {
                yearField.setText(oldValue);
            }
            if (newValue.length() > 4) {
                yearField.setText(oldValue);
            }
        });

        titleField.sceneProperty().addListener(((observable, oldValue, newValue) -> {
            if (newValue != null) {
                if (film != null) {

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

                    titleField.setText(film.getTitle());
                    yearField.setText(film.getYear());
                    countryField.setText(film.getCountry());
                    mainInfoBox.getChildren().add(0, afishaImage);
                    trailerField.setText(film.getTrailerLink());
                    descriptionArea.setText(film.getFilmDescription());
                    hoursSpinner.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 5, Time.valueOf(film.getFilmLenght()).getHours()));
                    minutesSpinner.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 59, Time.valueOf(film.getFilmLenght()).getMinutes()));
                    List<String> genres = null;
                    try {
                        genres = dbHandler.getFilmGenres(film.getId());
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                    for (String genre : genres) {
                        genresBox.getCheckModel().check(genre);
                    }
                    ageBox.getCheckModel().check(film.getAge());
                    try {
                        loadedImage = new File(getClass().getResource("AfishaImages/" + film.getImageName()).toURI());
                    } catch (URISyntaxException e) {
                        e.printStackTrace();
                    }
                }
            }
        }));
    }

}
