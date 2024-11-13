package com.cinema;

import javafx.scene.image.Image;

public class Film {

    private int id;
    private Image image;
    private String title;
    private String genres;
    private String year;
    private String country;
    private String filmLenght;
    private String trailerLink;
    private String filmDescription;
    private String age;
    private String imageName;


    Film(int id, Image image, String imageName, String title, String year, String country, String filmLenght, String trailerLink, String filmDescription) {
        this.id = id;
        this.image = image;
        this.title = title;
        this.year = year;
        this.country = country;
        this.filmLenght = filmLenght;
        this.trailerLink = trailerLink;
        this.filmDescription = filmDescription;
        this.imageName = imageName;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Image getImage() {
        return image;
    }

    public void setImage(Image image) {
        this.image = image;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getGenres() {
        return genres;
    }

    public void setGenres(String genres) {
        this.genres = genres;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getFilmLenght() {
        return filmLenght;
    }

    public void setFilmLenght(String filmLenght) {
        this.filmLenght = filmLenght;
    }

    public String getTrailerLink() {
        return trailerLink;
    }

    public void setTrailerLink(String trailerLink) {
        this.trailerLink = trailerLink;
    }

    public String getFilmDescription() {
        return filmDescription;
    }

    public void setFilmDescription(String filmDescription) {
        this.filmDescription = filmDescription;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public String getImageName() {
        return imageName;
    }

    public void setImageName(String imageName) {
        this.imageName = imageName;
    }
}
