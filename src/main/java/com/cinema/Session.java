package com.cinema;

import java.util.Date;

public class Session {

    private int sessionId;
    private int filmId;
    private int hallId;
    private Date sessionDate;
    private Date sessionTime;
    private double sessionPrice;

    Session(int sessionId, int filmId, int hallId, Date sessionDate, Date sessionTime, double sessionPrice) {
        this.sessionId = sessionId;
        this.filmId = filmId;
        this.hallId = hallId;
        this.sessionDate = sessionDate;
        this.sessionTime = sessionTime;
        this.sessionPrice = sessionPrice;
    }

    public int getSessionId() {
        return sessionId;
    }

    public void setSessionId(int sessionId) {
        this.sessionId = sessionId;
    }

    public int getFilmId() {
        return filmId;
    }

    public void setFilmId(int filmId) {
        this.filmId = filmId;
    }

    public int getHallId() {
        return hallId;
    }

    public void setHallId(int hallId) {
        this.hallId = hallId;
    }

    public Date getSessionDate() {
        return sessionDate;
    }

    public void setSessionDate(Date sessionDate) {
        this.sessionDate = sessionDate;
    }

    public double getSessionPrice() {
        return sessionPrice;
    }

    public void setSessionPrice(double sessionPrice) {
        this.sessionPrice = sessionPrice;
    }

    public Date getSessionTime() {
        return sessionTime;
    }

    public void setSessionTime(Date sessionTime) {
        this.sessionTime = sessionTime;
    }
}
