package nl.avans.moviemenace.domain;

import java.time.LocalDateTime;

public class Viewing {
    private int id;
    private LocalDateTime date;
    private double price;
    private boolean threeDimensional;

    public Viewing(int id, LocalDateTime date, double price, boolean threeDimensional) {
        this.id = id;
        this.date = date;
        this.price = price;
        this.threeDimensional = threeDimensional;
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
}
