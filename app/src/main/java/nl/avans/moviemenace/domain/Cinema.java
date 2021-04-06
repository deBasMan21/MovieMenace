package nl.avans.moviemenace.domain;

import java.io.Serializable;

public class Cinema implements Serializable {
    private String name;
    private String place;
    private int numberOfRooms;

    public Cinema(String name, String place, int numberOfRooms) {
        this.name = name;
        this.place = place;
        this.numberOfRooms = numberOfRooms;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPlace() {
        return place;
    }

    public void setPlace(String place) {
        this.place = place;
    }

    public int getNumberOfRooms() {
        return numberOfRooms;
    }

    public void setNumberOfRooms(int numberOfRooms) {
        this.numberOfRooms = numberOfRooms;
    }
}
