package nl.avans.moviemenace.domain;

public class Room {
    private int roomNumber;
    private int numberOfSeats;
    private boolean threeDimensional;

    public Room(int roomNumber, int numberOfSeats, boolean threeDimensional) {
        this.roomNumber = roomNumber;
        this.numberOfSeats = numberOfSeats;
        this.threeDimensional = threeDimensional;
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
}
