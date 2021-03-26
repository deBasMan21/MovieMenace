package nl.avans.moviemenace.domain;

public class Room {
    private int roomNumber;
    private int numberOfSeats;
    private boolean threeDimensional;
    private int numberOfRows;
    private Cinema cinema;

    public Room(int roomNumber, int numberOfSeats, boolean threeDimensional, int numberOfRows) {
        this.roomNumber = roomNumber;
        this.numberOfSeats = numberOfSeats;
        this.threeDimensional = threeDimensional;
        this.numberOfRows = numberOfRows;
    }

    public int getRoomNumber() {
        return roomNumber;
    }

    public void setRoomNumber(int roomNumber) {
        this.roomNumber = roomNumber;
    }

    public int getNumberOfSeats() {
        return numberOfSeats;
    }

    public void setNumberOfSeats(int numberOfSeats) {
        this.numberOfSeats = numberOfSeats;
    }

    public boolean isThreeDimensional() {
        return threeDimensional;
    }

    public void setThreeDimensional(boolean threeDimensional) {
        this.threeDimensional = threeDimensional;
    }

    public Cinema getCinema() {
        return cinema;
    }

    public void setCinema(Cinema cinema) {
        this.cinema = cinema;
    }

    public int getNumberOfRows() {
        return numberOfRows;
    }

    public void setNumberOfRows(int numberOfRows) {
        this.numberOfRows = numberOfRows;
    }
}
