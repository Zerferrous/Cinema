package com.cinema;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.image.Image;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class DataBaseHandler {

    private Connection dbConnection;

    public Connection getDbConnection() throws ClassNotFoundException, SQLException {
        String connectionUrl = "jdbc:mysql://127.0.0.1:3306/javafxTest";
        Class.forName("com.mysql.cj.jdbc.Driver");
        dbConnection = DriverManager.getConnection(connectionUrl, "root", "LolaLoveq2");

        return dbConnection;
    }

    public ObservableList<Film> getFilms() throws SQLException{
        ObservableList<Film> list = FXCollections.observableArrayList();
        String query = "SELECT * FROM films";
        PreparedStatement preparedStatement = dbConnection.prepareStatement(query);
        ResultSet rs = preparedStatement.executeQuery();
        while (rs.next()) {
            Film film = new Film(
                    rs.getInt("FilmId"),
                    new Image(Main.class.getResourceAsStream("AfishaImages/" + rs.getString("FilmImage"))),
                    rs.getString("FilmImage"),
                    rs.getString("FilmTitle"),
                    rs.getString("FilmYear"),
                    rs.getString("FilmCountry"),
                    rs.getTime("FilmLenght").toString(),
                    rs.getString("FilmTrailer"),
                    rs.getString("FilmDescription")
            );
            film.setGenres(getFilmGenres(film.getId()).stream().collect(Collectors.joining("; ")));
            film.setAge(getFilmAge(film.getId()));
            list.add(film);
        }
        return list;
    }

    public List<String> getFilmGenres(int filmId) throws SQLException {
        List<String> list = new ArrayList<>();
        String query = "SELECT GenreTitle FROM genres, films_has_genres WHERE films_FilmId = ? AND GenreId = genres_GenreId";
        PreparedStatement preparedStatement = dbConnection.prepareStatement(query);
        preparedStatement.setInt(1, filmId);
        ResultSet rs = preparedStatement.executeQuery();
        while (rs.next()) {
            list.add(rs.getString("GenreTitle"));
        }
        return list;
    }

    public String getFilmAge(int filmId) throws SQLException {
        String age = "";
        String query = "SELECT AgeGroupTile FROM films, age_groups WHERE FilmId = ? AND age_groups.AgeGroupId = films.AgeGroupId";
        PreparedStatement preparedStatement = dbConnection.prepareStatement(query);
        preparedStatement.setInt(1, filmId);
        ResultSet rs = preparedStatement.executeQuery();
        while (rs.next()) {
            age = rs.getString("AgeGroupTile");
        }
        return age;
    }

    public ObservableList<Session> getFilmSessions(Film film) throws SQLException {
        ObservableList<Session> list = FXCollections.observableArrayList();
        String query = "SELECT * FROM sessions WHERE FilmId = ?";
        PreparedStatement preparedStatement = dbConnection.prepareStatement(query);
        preparedStatement.setInt(1, film.getId());
        ResultSet rs = preparedStatement.executeQuery();
        while (rs.next()) {
            Session session = new Session(
                    rs.getInt("SessionId"),
                    film.getId(),
                    rs.getInt("HallId"),
                    rs.getDate("SessionDatetime"),
                    rs.getTime("SessionDateTime"),
                    rs.getDouble("SessionPrice")
            );
            list.add(session);
        }

        return list;
    }

    public int seatsInHallCount(int hallId) throws SQLException{
        int count = -1;
        String query = "SELECT COUNT(SeatId) count FROM halls_seats WHERE HallId = ?";
        PreparedStatement preparedStatement = dbConnection.prepareStatement(query);
        preparedStatement.setInt(1, hallId);
        ResultSet rs = preparedStatement.executeQuery();
        while (rs.next()) {
            count = rs.getInt("count");
        }
        return count;
    }

    public int getEmptySeats(int sessionId) throws SQLException{
        int count = -1;
        String query = "SELECT COUNT(SeatId) count FROM cinema.sessions_has_halls_seats WHERE SessionId = ? AND SeatStatus = 'Свободно'";
        PreparedStatement preparedStatement = dbConnection.prepareStatement(query);
        preparedStatement.setInt(1, sessionId);
        ResultSet rs = preparedStatement.executeQuery();
        while (rs.next()) {
            count = rs.getInt("count");
        }

        return count;
    }

    public String getSeatStatus(int sessionId, int seatId) throws SQLException {
        String status = "";
        String query = "SELECT SeatStatus FROM sessions_has_halls_seats WHERE SessionId = ? AND SeatId = ?";
        PreparedStatement preparedStatement = dbConnection.prepareStatement(query);
        preparedStatement.setInt(1, sessionId);
        preparedStatement.setInt(2, seatId);
        ResultSet rs = preparedStatement.executeQuery();
        while (rs.next()) {
            status = rs.getString("SeatStatus");
        }

        return status;
    }

    public List<String> getGenresList() throws SQLException {
        List<String> list = new ArrayList<>();
        String query = "SELECT GenreTitle FROM genres";
        PreparedStatement preparedStatement = dbConnection.prepareStatement(query);
        ResultSet rs = preparedStatement.executeQuery();
        while (rs.next()) {
            list.add(rs.getString("GenreTitle"));
        }

        return list;
    }

    public List<String> getAgeGroupsList() throws SQLException {
        List<String> list = new ArrayList<>();
        String query = "SELECT AgeGroupTile FROM age_groups";
        PreparedStatement preparedStatement = dbConnection.prepareStatement(query);
        ResultSet rs = preparedStatement.executeQuery();
        while (rs.next()) {
            list.add(rs.getString("AgeGroupTile"));
        }

        return list;
    }

    public List<String> getCountries() throws SQLException {
        List<String> list = new ArrayList<>();
        String query = "SELECT DISTINCT FilmCountry FROM films";
        PreparedStatement preparedStatement = dbConnection.prepareStatement(query);
        ResultSet rs = preparedStatement.executeQuery();
        while (rs.next()) {
            list.add(rs.getString("FilmCountry"));
        }

        return list;
    }

    public List<String> getYears() throws SQLException {
        List<String> list = new ArrayList<>();
        String query = "SELECT DISTINCT FilmYear FROM films ORDER BY FilmYear DESC";
        PreparedStatement preparedStatement = dbConnection.prepareStatement(query);
        ResultSet rs = preparedStatement.executeQuery();
        while (rs.next()) {
            list.add(rs.getString("FilmYear"));
        }

        return list;
    }

    public int getAgeGroupIdByTitle(String title) throws SQLException {
        int id = 0;
        String query = "SELECT AgeGroupId FROM age_groups WHERE AgeGroupTile = ?";
        PreparedStatement preparedStatement = dbConnection.prepareStatement(query);
        preparedStatement.setString(1, title);
        ResultSet rs = preparedStatement.executeQuery();
        while (rs.next()) {
            id = rs.getInt("AgeGroupId");
        }
        return id;
    }

    public List<Integer> getGenresIds(List<String> genres) throws SQLException {
        List<Integer> list = new ArrayList<>();
        String query = "SELECT GenreId FROM genres WHERE GenreTitle = ?";
        PreparedStatement preparedStatement = dbConnection.prepareStatement(query);
        for (String genre: genres) {
            preparedStatement.setString(1, genre);
            ResultSet rs = preparedStatement.executeQuery();
            while (rs.next()) {
                list.add(rs.getInt("GenreId"));
            }
        }
        return list;
    }

    public void addFilmGenres(int filmId, List<String> genres) throws SQLException {
        String query = "INSERT INTO films_has_genres(films_FilmId, genres_GenreId) VALUES(?, ?)";
        PreparedStatement preparedStatement = dbConnection.prepareStatement(query);
        for (int genreId: getGenresIds(genres)) {
            preparedStatement.setInt(1, filmId);
            preparedStatement.setInt(2, genreId);
            preparedStatement.executeUpdate();
        }
    }

    public int getFilmIdByTitle(String title) throws SQLException {
        int id = 0;
        String query = "SELECT FilmId FROM films WHERE FilmTitle = ?";
        PreparedStatement preparedStatement = dbConnection.prepareStatement(query);
        preparedStatement.setString(1, title);
        ResultSet rs = preparedStatement.executeQuery();
        while (rs.next()) {
            id = rs.getInt("FilmId");
        }
        return id;
    }

    public void updateFilmGenres(int filmId, List<String> genres) throws SQLException {
        deleteFilmGenres(filmId);
        addFilmGenres(filmId, genres);
    }

    public void deleteFilmGenres(int filmId) throws SQLException {
        String query = "DELETE FROM films_has_genres WHERE films_FilmId = ?";
        PreparedStatement preparedStatement= dbConnection.prepareStatement(query);
        preparedStatement.setInt(1, filmId);
        preparedStatement.executeUpdate();
    }

    public void updateFilm(int filmId, String title, String ageGroup, String time, String description, String trailer, String image, String country, String year, List<String> genres) throws SQLException {
        String query = "UPDATE films SET FilmTitle = ?, AgeGroupId = ?, FilmLenght = ?, FilmDescription = ?, FilmTrailer = ?, FilmImage = ?, FilmCountry = ?, FilmYear = ? WHERE FilmId = ?";
        PreparedStatement preparedStatement = dbConnection.prepareStatement(query);
        preparedStatement.setString(1, title);
        preparedStatement.setInt(2, getAgeGroupIdByTitle(ageGroup));
        preparedStatement.setTime(3, Time.valueOf(time));
        preparedStatement.setString(4, description);
        preparedStatement.setString(5, trailer);
        preparedStatement.setString(6, image);
        preparedStatement.setString(7, country);
        preparedStatement.setString(8, year);
        preparedStatement.setInt(9, filmId);
        preparedStatement.executeUpdate();
        preparedStatement.close();
        updateFilmGenres(getFilmIdByTitle(title), genres);
    }

    public void addFilm(String title, String ageGroup, String time, String description, String trailer, String image, String country, String year, List<String> genres) throws SQLException {
        String query = "INSERT INTO films(FilmTitle, AgeGroupId, FilmLenght, FilmDescription, FilmTrailer, FilmImage, FilmCountry, FilmYear) VALUES(?, ?, ?, ?, ?, ?, ?, ?)";
        PreparedStatement preparedStatement = dbConnection.prepareStatement(query);
        preparedStatement.setString(1, title);
        preparedStatement.setInt(2, getAgeGroupIdByTitle(ageGroup));
        preparedStatement.setTime(3, Time.valueOf(time));
        preparedStatement.setString(4, description);
        preparedStatement.setString(5, trailer);
        preparedStatement.setString(6, image);
        preparedStatement.setString(7, country);
        preparedStatement.setString(8, year);
        preparedStatement.executeUpdate();
        preparedStatement.close();
        addFilmGenres(getFilmIdByTitle(title), genres);
    }

    public void deleteFilm(int filmId) throws SQLException {
        String query = "DELETE FROM films WHERE FilmId = ?";
        PreparedStatement preparedStatement = dbConnection.prepareStatement(query);
        preparedStatement.setInt(1, filmId);
        deleteFilmGenres(filmId);
        preparedStatement.executeUpdate();
    }

    public ObservableList<Session> getSessions() throws SQLException {
        ObservableList<Session> list = FXCollections.observableArrayList();
        String query = "SELECT * FROM sessions";
        PreparedStatement preparedStatement = dbConnection.prepareStatement(query);
        ResultSet rs = preparedStatement.executeQuery();
        while (rs.next()) {
            Session session = new Session(
                    rs.getInt("SessionId"),
                    rs.getInt("FilmId"),
                    rs.getInt("HallId"),
                    rs.getDate("SessionDatetime"),
                    rs.getTime("SessionDateTime"),
                    rs.getDouble("SessionPrice")
            );
            list.add(session);
        }
        return list;
    }

    public String filmTitleById(int filmId) throws SQLException {
        String title = null;
        String query = "SELECT FilmTitle FROM films WHERE FilmId = ?";
        PreparedStatement preparedStatement = dbConnection.prepareStatement(query);
        preparedStatement.setInt(1, filmId);
        ResultSet rs = preparedStatement.executeQuery();
        while (rs.next()) {
            title = rs.getString("FilmTitle");
        }
        return title;
    }

    public Film getFilmById(int filmId) throws SQLException {
        Film film = null;
        String query = "SELECT * FROM films WHERE FilmId = ?";
        PreparedStatement preparedStatement = dbConnection.prepareStatement(query);
        preparedStatement.setInt(1, filmId);
        ResultSet rs = preparedStatement.executeQuery();
        while (rs.next()) {
            film = new Film(
                    rs.getInt("FilmId"),
                    new Image(Main.class.getResourceAsStream("AfishaImages/" + rs.getString("FilmImage"))),
                    rs.getString("FilmImage"),
                    rs.getString("FilmTitle"),
                    rs.getString("FilmYear"),
                    rs.getString("FilmCountry"),
                    rs.getTime("FilmLenght").toString(),
                    rs.getString("FilmTrailer"),
                    rs.getString("FilmDescription")
            );
            film.setGenres(getFilmGenres(film.getId()).stream().collect(Collectors.joining("; ")));
            film.setAge(getFilmAge(film.getId()));
        }
        return film;
    }

    public int getActualSessionsCount() throws SQLException{
        int count = 0;
        String query = "SELECT COUNT(*) count FROM sessions WHERE DATE(SessionDateTime) >= DATE(now())";
        PreparedStatement preparedStatement = dbConnection.prepareStatement(query);
        ResultSet rs = preparedStatement.executeQuery();
        while (rs.next()) {
            count = rs.getInt("count");
        }
        return count;
    }

    public int getTodaySessionsCount() throws SQLException{
        int count = 0;
        String query = "SELECT COUNT(*) count FROM sessions WHERE DATE(SessionDateTime) = DATE(now())";
        PreparedStatement preparedStatement = dbConnection.prepareStatement(query);
        ResultSet rs = preparedStatement.executeQuery();
        while (rs.next()) {
            count = rs.getInt("count");
        }
        return count;
    }

    public ObservableList<String> getFilmsTitles() throws SQLException {
        ObservableList<String> list = FXCollections.observableArrayList();
        String query = "SELECT FilmTitle FROM films";
        PreparedStatement preparedStatement = dbConnection.prepareStatement(query);
        ResultSet rs = preparedStatement.executeQuery();
        while (rs.next()) {
            list.add(rs.getString("FilmTitle"));
        }
        return list;
    }

    public void addSession(int filmId, String sessionDateTime,double sessionPrice,int hallId) throws SQLException {
        String query = "INSERT INTO sessions(FilmId, SessionDateTime, SessionPrice, HallId) VALUES(?, ?, ?, ?)";
        PreparedStatement preparedStatement = dbConnection.prepareStatement(query);
        preparedStatement.setInt(1, filmId);
        preparedStatement.setString(2, sessionDateTime);
        preparedStatement.setDouble(3, sessionPrice);
        preparedStatement.setInt(4, hallId);
        preparedStatement.executeUpdate();
        preparedStatement.close();
    }

    public void deleteNonActualSessions() throws SQLException {
        String query = "CALL deleteNonActualSessions()";
        PreparedStatement preparedStatement = dbConnection.prepareStatement(query);
        preparedStatement.executeQuery();
        preparedStatement.close();
    }

    public void addUser(String number, String fio, Date dateOfBirth) throws SQLException {
        String query = "INSERT INTO users(UserNumber, UserFio, UserDateOfBirth) VALUES(?, ?, ?)";
        PreparedStatement preparedStatement = dbConnection.prepareStatement(query);
        preparedStatement.setString(1, number);
        preparedStatement.setString(2, fio);
        preparedStatement.setDate(3, dateOfBirth);
        preparedStatement.executeUpdate();
        preparedStatement.close();
    }

    public ObservableList<String> getUsersNumber() throws SQLException {
        ObservableList<String> list = FXCollections.observableArrayList();
        String query = "SELECT UserNumber FROM users";
        PreparedStatement preparedStatement = dbConnection.prepareStatement(query);
        ResultSet rs = preparedStatement.executeQuery();
        while (rs.next()) {
            list.add(rs.getString("UserNumber"));
        }
        return list;
    }

    public int loginUser(String number, String password) throws SQLException{
        String query = "SELECT * FROM users WHERE UserNumber = ?";
        PreparedStatement preparedStatement = dbConnection.prepareStatement(query);
        preparedStatement.setString(1, number);
        ResultSet rs = preparedStatement.executeQuery();
        while (rs.next()) {
            if (rs.getString("UserNumber") == null) {
                return 1;
            } else if (rs.getString("UserPassword") == null) {
                UserInfo.USER_NUMBER = number;
                UserInfo.USER_FIO = rs.getString("UserFio");
                UserInfo.USER_ROLE = rs.getInt("RoleId");
                return 0;
            } else if(rs.getString("UserPassword") != null && rs.getString("UserPassword").equals(password)) {
                UserInfo.USER_NUMBER = number;
                UserInfo.USER_FIO = rs.getString("UserFio");
                UserInfo.USER_ROLE = rs.getInt("RoleId");
                return 0;
            } else return 1;
        }
        return 1;
    }

    public int ticketsCount() throws SQLException {
        int count = 0;
        String query = "SELECT COUNT(*) count FROM tickets";
        PreparedStatement preparedStatement = dbConnection.prepareStatement(query);
        ResultSet rs = preparedStatement.executeQuery();
        while (rs.next()) {
            count = rs.getInt("count");
        }
        return count;
    }

    public void addOrder(int sessionId, String userNumber, int hallId, List<Integer> selectedSeats) throws SQLException {
        String query = "INSERT INTO tickets(TicketId, SessionId, UserNumber, HallId, SeatId) VALUES(?, ?, ?, ?, ?)";
        PreparedStatement preparedStatement = dbConnection.prepareStatement(query);
        preparedStatement.setInt(1, ticketsCount() + 1);
        preparedStatement.setInt(2, sessionId);
        preparedStatement.setString(3, userNumber);
        preparedStatement.setInt(4, hallId);
        for (int seatId: selectedSeats) {
            preparedStatement.setInt(5, seatId);
            preparedStatement.executeUpdate();
        }
        preparedStatement.close();
    }

    public int checkUser(String number) throws SQLException {
        int count = 0;
        String query = "SELECT COUNT(*) count FROM users WHERE UserNumber = ?";
        PreparedStatement preparedStatement = dbConnection.prepareStatement(query);
        preparedStatement.setString(1, number);
        ResultSet rs = preparedStatement.executeQuery();
        while (rs.next()) {
            count = rs.getInt("count");
        }
        return count;
    }

    public int regUser(String number, String fio, Date dateOfBirth, String password) throws SQLException {
       int code = 0;
       if (checkUser(number) != 0) {
           return 1;
       }
       String query = "INSERT INTO users(UserNumber, UserFio, UserDateOfBirth, UserPassword) VALUES(?, ?, ?, ?)";
       PreparedStatement preparedStatement = dbConnection.prepareStatement(query);
       preparedStatement.setString(1, number);
       preparedStatement.setString(2, fio);
       preparedStatement.setDate(3, dateOfBirth);
       preparedStatement.setString(4, password);
       preparedStatement.executeUpdate();
       return 0;
    }

    public int regUser(String number, String fio, Date dateOfBirth) throws SQLException {
        int code = 0;
        if (checkUser(number) != 0) {
            return 1;
        }
        String query = "INSERT INTO users(UserNumber, UserFio, UserDateOfBirth) VALUES(?, ?, ?)";
        PreparedStatement preparedStatement = dbConnection.prepareStatement(query);
        preparedStatement.setString(1, number);
        preparedStatement.setString(2, fio);
        preparedStatement.setDate(3, dateOfBirth);
        preparedStatement.executeUpdate();
        return 0;
    }
}
