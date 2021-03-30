package nl.avans.moviemenace.domain;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Viewing {
    private int id;
    private LocalDateTime date;
    private double price;
    private boolean threeDimensional;
    private int movie;
    private Room room;


    public Viewing(int id, LocalDateTime date, double price, boolean threeDimensional,
                   int movie, Room room) {
        this.id = id;
        this.date = date;
        this.price = price;
        this.threeDimensional = threeDimensional;
        this.movie = movie;
        this.room = room;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public boolean isThreeDimensional() {
        return threeDimensional;
    }

    public void setThreeDimensional(boolean threeDimensional) {
        this.threeDimensional = threeDimensional;
    }

    public int getMovie() {
        return this.movie;
    }

    public void setMovie(int movie) {
        this.movie = movie;
    }

    public Room getRoom() {
        return room;
    }

    public void setRoom(Room room) {
        this.room = room;
    }
}
