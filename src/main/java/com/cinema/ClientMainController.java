package com.cinema;

import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.*;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.*;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
import javafx.stage.Stage;
import org.controlsfx.control.CheckComboBox;


public class ClientMainController {

    DataBaseHandler dbHandler;

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private AnchorPane anchorPane;

    @FXML
    private ListView<Film> filmsList;

    @FXML
    private FontAwesomeIconView filterIcon;

    @FXML
    private Button loginLogoutBtn;

    @FXML
    private TextField searchField;

    ContextMenu filterMenu;

    @FXML
    void filterIconClick(MouseEvent event) {
        Bounds bounds = filterIcon.getBoundsInLocal();
        Bounds screenBounds = filterIcon.localToScreen(bounds);
        int x = (int) screenBounds.getMinX();
        int y = (int) screenBounds.getMinY();
        int width = (int) screenBounds.getWidth();
        int height = (int) screenBounds.getHeight();
        filterMenu.show(anchorPane, x, y);
    }

    @FXML
    void loginLogoutBtnClick(ActionEvent event) {
        if (UserInfo.USER_NUMBER == null) {
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
            loginLogoutBtn.getScene().getWindow().hide();
            primaryStage.show();
        } else {
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
                loginLogoutBtn.getScene().getWindow().hide();
                primaryStage.show();
            }
        }
    }

    void switchToFilmScene(Film film) {

        Parent root = null;
        FXMLLoader loader = null;
        try {
            loader = new FXMLLoader(Main.class.getResource("FilmInfo.fxml"));
            root = loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Stage primaryStage = new Stage();
        primaryStage.setTitle(film.getTitle());
        primaryStage.setMinWidth(800);
        primaryStage.setMinHeight(800);
        FilmInfoController filmInfoController = loader.getController();
        filmInfoController.setFilm(film);
        primaryStage.setScene(new Scene(root, anchorPane.getLayoutBounds().getWidth(), anchorPane.getLayoutBounds().getHeight()));
        primaryStage.getIcons().add(new Image(getClass().getResourceAsStream("icon.png")));
        loginLogoutBtn.getScene().getWindow().hide();
        primaryStage.show();
    }

    @FXML
    void initialize() {

        dbHandler = new DataBaseHandler();

        try {
            dbHandler.getDbConnection();
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }

        ObservableList<Film> list = null;
        try {
            list = dbHandler.getFilms();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        if (UserInfo.USER_NUMBER == null) {
            loginLogoutBtn.setText("Войти");
        } else {
            loginLogoutBtn.setText("Выйти");
        }

        FilteredList<Film> filteredList = new FilteredList<>(list, b -> true);

        anchorPane.getStylesheets().add(getClass().getResource("Styles.css").toExternalForm());
        CheckComboBox<String> genresBox = new CheckComboBox<>();
        genresBox.setTitle("Жанр(ы)");
        CheckComboBox<String> countriesBox = new CheckComboBox<>();
        countriesBox.setPrefWidth(146);
        countriesBox.getCheckModel().getCheckedItems().addListener(new ListChangeListener<String>() {
            @Override
            public void onChanged(Change<? extends String> change) {
                while (change.next()) {
                    if (change.wasAdded()) {
                        String lastSelected = change.getAddedSubList().get(change.getAddedSize() - 1);
                        List<String> list1 = new ArrayList<>();
                        for (String item: change.getList()) {
                            list1.add(item);
                        }

                        Iterator<String> iterator = list1.iterator();
                        while (iterator.hasNext()) {
                            String string = iterator.next();
                            if (string.equals(lastSelected)) {
                                iterator.remove();
                            }
                        }

                        for (String item: list1) {
                           countriesBox.getCheckModel().clearCheck(item);
                        }

                    }
                }
            }
        });
        countriesBox.setTitle("Страна");
        CheckComboBox<String> yearBox = new CheckComboBox<>();
        yearBox.setPrefWidth(146);
        yearBox.setTitle("Год");
        yearBox.getCheckModel().getCheckedItems().addListener(new ListChangeListener<String>() {
            @Override
            public void onChanged(Change<? extends String> change) {
                while (change.next()) {
                    if (change.wasAdded()) {
                        String lastSelected = change.getAddedSubList().get(change.getAddedSize() - 1);
                        List<String> list1 = new ArrayList<>();
                        for (String item: change.getList()) {
                            list1.add(item);
                        }

                        Iterator<String> iterator = list1.iterator();
                        while (iterator.hasNext()) {
                            String string = iterator.next();
                            if (string.equals(lastSelected)) {
                                iterator.remove();
                            }
                        }

                        for (String item: list1) {
                            yearBox.getCheckModel().clearCheck(item);
                        }

                    }
                }
            }
        });

        CustomMenuItem genresItem = new CustomMenuItem(genresBox);
        CustomMenuItem countriesItem = new CustomMenuItem(countriesBox);
        CustomMenuItem yearItem = new CustomMenuItem(yearBox);
        try {
            genresBox.getItems().addAll(dbHandler.getGenresList());
            countriesBox.getItems().addAll(dbHandler.getCountries());
            yearBox.getItems().addAll(dbHandler.getYears());
        } catch (SQLException e) {
            e.printStackTrace();
        }
        genresBox.getCheckModel().getCheckedItems().addListener((ListChangeListener<? super String>) observable -> {
            filteredList.setPredicate(Film -> {
                List<String> selectedGenres = genresBox.getCheckModel().getCheckedItems();
                List<String> filmGenres = null;
                String filmYear = null;
                String filmCountry = null;
                try {
                    filmGenres = dbHandler.getFilmGenres(Film.getId());
                    filmYear = Film.getYear();
                    filmCountry = Film.getCountry();
                } catch (SQLException e) {
                    e.printStackTrace();
                }

                String lowerCaseFilter = searchField.getText().toLowerCase().replaceAll(" ", "");

                if (filmGenres.containsAll(selectedGenres) && (countriesBox.getCheckModel().getCheckedItems().isEmpty() || countriesBox.getCheckModel().getCheckedItems().contains(filmCountry)) && (yearBox.getCheckModel().getCheckedItems().isEmpty() || yearBox.getCheckModel().getCheckedItems().contains(filmYear))) {
                    if (!searchField.getText().isEmpty() && searchField.getText() != null) {
                        if (Film.getTitle().toLowerCase().replaceAll(" ", "").indexOf(lowerCaseFilter) != -1) {
                            return true;
                        } else if (Film.getGenres().toLowerCase().replaceAll(" ", "").indexOf(lowerCaseFilter) != -1) {
                            return true;
                        } else if (Film.getYear().toLowerCase().replaceAll(" ", "").indexOf(lowerCaseFilter) != -1) {
                            return true;
                        } else if (Film.getCountry().toLowerCase().replaceAll(" ", "").indexOf(lowerCaseFilter) != -1) {
                            return true;
                        } else return false;
                    } else return true;
                }
                return false;
            });

        });

        yearBox.getCheckModel().getCheckedItems().addListener((ListChangeListener<? super String>) observable -> {
            filteredList.setPredicate(Film -> {
                List<String> selectedGenres = genresBox.getCheckModel().getCheckedItems();
                List<String> filmGenres = null;
                String filmYear = null;
                String filmCountry = null;
                try {
                    filmGenres = dbHandler.getFilmGenres(Film.getId());
                    filmYear = Film.getYear();
                    filmCountry = Film.getCountry();
                } catch (SQLException e) {
                    e.printStackTrace();
                }

                String lowerCaseFilter = searchField.getText().toLowerCase().replaceAll(" ", "");

                if (filmGenres.containsAll(selectedGenres) && (countriesBox.getCheckModel().getCheckedItems().isEmpty() || countriesBox.getCheckModel().getCheckedItems().contains(filmCountry)) && (yearBox.getCheckModel().getCheckedItems().isEmpty() || yearBox.getCheckModel().getCheckedItems().contains(filmYear))) {
                    if (!searchField.getText().isEmpty() && searchField.getText() != null) {
                        if (Film.getTitle().toLowerCase().replaceAll(" ", "").indexOf(lowerCaseFilter) != -1) {
                            return true;
                        } else if (Film.getGenres().toLowerCase().replaceAll(" ", "").indexOf(lowerCaseFilter) != -1) {
                            return true;
                        } else if (Film.getYear().toLowerCase().replaceAll(" ", "").indexOf(lowerCaseFilter) != -1) {
                            return true;
                        } else if (Film.getCountry().toLowerCase().replaceAll(" ", "").indexOf(lowerCaseFilter) != -1) {
                            return true;
                        } else return false;
                    } else return true;
                }
                return false;
            });

        });

        countriesBox.getCheckModel().getCheckedItems().addListener((ListChangeListener<? super String>) observable -> {
            filteredList.setPredicate(Film -> {
                List<String> selectedGenres = genresBox.getCheckModel().getCheckedItems();
                List<String> filmGenres = null;
                String filmYear = null;
                String filmCountry = null;
                try {
                    filmGenres = dbHandler.getFilmGenres(Film.getId());
                    filmYear = Film.getYear();
                    filmCountry = Film.getCountry();
                } catch (SQLException e) {
                    e.printStackTrace();
                }

                String lowerCaseFilter = searchField.getText().toLowerCase().replaceAll(" ", "");

                if (filmGenres.containsAll(selectedGenres) && (countriesBox.getCheckModel().getCheckedItems().isEmpty() || countriesBox.getCheckModel().getCheckedItems().contains(filmCountry)) && (yearBox.getCheckModel().getCheckedItems().isEmpty() || yearBox.getCheckModel().getCheckedItems().contains(filmYear))) {
                    if (!searchField.getText().isEmpty() && searchField.getText() != null) {
                        if (Film.getTitle().toLowerCase().replaceAll(" ", "").indexOf(lowerCaseFilter) != -1) {
                            return true;
                        } else if (Film.getGenres().toLowerCase().replaceAll(" ", "").indexOf(lowerCaseFilter) != -1) {
                            return true;
                        } else if (Film.getYear().toLowerCase().replaceAll(" ", "").indexOf(lowerCaseFilter) != -1) {
                            return true;
                        } else if (Film.getCountry().toLowerCase().replaceAll(" ", "").indexOf(lowerCaseFilter) != -1) {
                            return true;
                        } else return false;
                    } else return true;
                }
                return false;
            });

        });

        searchField.textProperty().addListener((observableValue, oldValue, newValue) -> {
            filteredList.setPredicate(Film -> {
                List<String> selectedGenres = genresBox.getCheckModel().getCheckedItems();
                List<String> filmGenres = null;
                String filmYear = null;
                String filmCountry = null;
                try {
                    filmGenres = dbHandler.getFilmGenres(Film.getId());
                    filmYear = Film.getYear();
                    filmCountry = Film.getCountry();
                } catch (SQLException e) {
                    e.printStackTrace();
                }

                String lowerCaseFilter = newValue.toLowerCase().replaceAll(" ", "");

                if (filmGenres.containsAll(selectedGenres) && (countriesBox.getCheckModel().getCheckedItems().isEmpty() || countriesBox.getCheckModel().getCheckedItems().contains(filmCountry)) && (yearBox.getCheckModel().getCheckedItems().isEmpty() || yearBox.getCheckModel().getCheckedItems().contains(filmYear))) {
                    if (!newValue.isEmpty() && newValue != null) {
                        if (Film.getTitle().toLowerCase().replaceAll(" ", "").indexOf(lowerCaseFilter) != -1) {
                            return true;
                        } else if (Film.getGenres().toLowerCase().replaceAll(" ", "").indexOf(lowerCaseFilter) != -1) {
                            return true;
                        } else if (Film.getYear().toLowerCase().replaceAll(" ", "").indexOf(lowerCaseFilter) != -1) {
                            return true;
                        } else if (Film.getCountry().toLowerCase().replaceAll(" ", "").indexOf(lowerCaseFilter) != -1) {
                            return true;
                        } else return false;
                    } else return true;
                }
                return false;
            });

        });


        filmsList.setItems(filteredList);

        filmsList.setCellFactory(lv -> new ListCell<Film>() {

            final HBox root = new HBox(3);
            final ImageView afishaImage = new ImageView();
            final TextFlow filmInfo = new TextFlow();
            Text title = new Text();
            Text genres = new Text();
            Text year = new Text();
            Text country = new Text();
            final Button button = new Button();

            {
                root.setPrefHeight(200);
                root.setAlignment(Pos.CENTER_LEFT);
                afishaImage.setFitWidth(150);
                afishaImage.setFitHeight(200);

                Rectangle clip = new Rectangle(
                        afishaImage.getFitWidth(), afishaImage.getFitHeight()
                );
                clip.setArcWidth(20);
                clip.setArcHeight(20);
                afishaImage.setClip(clip);

                title.setFill(Paint.valueOf("#e4e5ea"));
                title.setStyle("-fx-font-size: " + (Math.cbrt(anchorPane.getLayoutBounds().getHeight() * 15)) +
                        "; -fx-font-weight: bold;");
                genres.setFill(Paint.valueOf("#e4e5ea"));
                genres.setStyle("-fx-font-size: " + (Math.cbrt(anchorPane.getLayoutBounds().getHeight() * 3)) + "; ");
                year.setFill(Paint.valueOf("#e4e5ea"));
                year.setStyle("-fx-font-size: " + (Math.cbrt(anchorPane.getLayoutBounds().getHeight() * 3)) + "; ");
                country.setFill(Paint.valueOf("#e4e5ea"));
                country.setStyle("-fx-font-size: " + (Math.cbrt(anchorPane.getLayoutBounds().getHeight() * 3)) + "; ");

                anchorPane.layoutBoundsProperty().addListener(new ChangeListener<Bounds>() {
                    @Override
                    public void changed(ObservableValue<? extends Bounds> observableValue, Bounds oldValue, Bounds newValue) {
                        title.setStyle("-fx-font-size: " + (Math.cbrt(newValue.getHeight() * 15)) +
                                "; -fx-font-weight: bold;");
                        genres.setStyle("-fx-font-size: " + (Math.cbrt(anchorPane.getLayoutBounds().getHeight() * 3)) + "; ");
                        year.setStyle("-fx-font-size: " + (Math.cbrt(anchorPane.getLayoutBounds().getHeight() * 3)) + "; ");
                        country.setStyle("-fx-font-size: " + (Math.cbrt(anchorPane.getLayoutBounds().getHeight() * 3)) + "; ");
                        filmInfo.setLineSpacing(anchorPane.getLayoutBounds().getHeight() / 75);
                        root.setMaxWidth(anchorPane.getLayoutBounds().getWidth() - 236);
                    }
                });

                root.setMaxWidth(anchorPane.getLayoutBounds().getWidth() - 236);

                filmInfo.getChildren().addAll(title, genres, year, country);
                filmInfo.setLineSpacing(anchorPane.getLayoutBounds().getHeight() / 75);
                HBox.setHgrow(filmInfo, Priority.ALWAYS);
                button.setMinWidth(100);
                button.setStyle("-fx-background-color:  #6B99C3; " +
                        "-fx-background-radius: 10; " +
                        "-fx-text-fill: #e4e5ea;");

                HBox.setMargin(afishaImage, new Insets(0, 7, 0, 15));
                HBox.setMargin(filmInfo, new Insets(0, 7, 0, 7));
                HBox.setMargin(button, new Insets(0, 15, 0, 7));
                root.getChildren().addAll(afishaImage, filmInfo, button);

            }

            @Override
            protected void updateItem(Film filmItem, boolean empty) {

                super.updateItem(filmItem, empty);

                if (filmItem == null || empty) {
                    setGraphic(null);
                    setStyle("-fx-background-color:  #16354D;");
                } else {
                    button.setText("О фильме");
                    button.setOnAction(new EventHandler<ActionEvent>() {
                        @Override
                        public void handle(ActionEvent event) {
                            switchToFilmScene(filmItem);
                        }
                    });
                    afishaImage.setImage(filmItem.getImage());
                    title.setText(filmItem.getTitle() + "\n");
                    genres.setText(filmItem.getGenres() + "\n");
                    year.setText(filmItem.getYear() + "\n");
                    country.setText(filmItem.getCountry() + "\n");
                    setGraphic(root);
                    setStyle("-fx-background-color:  #16354D;");
                }

            }

        });

        filterMenu = new ContextMenu();
        filterMenu.getItems().addAll(genresItem, countriesItem, yearItem);

    }

}

