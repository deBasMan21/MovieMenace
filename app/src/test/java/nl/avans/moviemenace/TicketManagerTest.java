package nl.avans.moviemenace;

import org.junit.Test;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import nl.avans.moviemenace.dataLayer.factory.DAOFactory;
import nl.avans.moviemenace.dataLayer.factory.SQLDAOFactory;
import nl.avans.moviemenace.domain.Room;
import nl.avans.moviemenace.domain.Viewing;
import nl.avans.moviemenace.logic.TicketManager;

import static org.junit.Assert.*;

public class TicketManagerTest {
    DAOFactory daoFactory = new SQLDAOFactory();
    TicketManager ticketManager = new TicketManager(daoFactory);

    private static final double DELTA = 1e-15;
    private String str = "1986-04-08 12:30";
    private DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

    @Test
    public void validateAgeValidAgeInBetweenZeroAndOneHundredTwentyFive() {
        // Arrange
        int age = 20;

        // Act
        boolean result = ticketManager.validateAge(age);

        // Assert
        assertTrue(result);
    }

    @Test
    public void validateAgeInvalidAgeOlderThanOneHundredTwentyFive() {
        // Arrange
        int age = 126;

        // Act
        boolean result = ticketManager.validateAge(age);

        // Assert
        assertFalse(result);
    }

    @Test
    public void validateAgeInvalidAgeYoungerThanZero() {
        // Arrange
        int age = -1;

        // Act
        boolean result = ticketManager.validateAge(age);

        // Assert
        assertFalse(result);
    }

    @Test
    public void calculatePriceNoDiscountNoThreeDimensional() {
        // Arrange
        int age = 20;
        LocalDateTime dateTime = LocalDateTime.parse(str, formatter);
        Viewing viewing = new Viewing(1, dateTime, 12.50, false, 1, null);

        // Act
        double result = ticketManager.calculatePrice(age, viewing);
//        if (age <= 11) {
//            price -= 2.50;
//        } else if (age >= 65) {
//            price -= 2.00;
//        }

        assertEquals(12.50, result, DELTA);
    }

    @Test
    public void calculatePriceDiscountChildNoThreeDimensional() {
        // Arrange
        int age = 11;
        LocalDateTime dateTime = LocalDateTime.parse(str, formatter);
        Viewing viewing = new Viewing(1, dateTime, 12.50, false, 1, null);

        // Act
        double result = ticketManager.calculatePrice(age, viewing);

        assertEquals(10.00, result, DELTA);
    }

    @Test
    public void calculatePriceDiscountElderNoThreeDimensional() {
        // Arrange
        int age = 65;
        LocalDateTime dateTime = LocalDateTime.parse(str, formatter);
        Viewing viewing = new Viewing(1, dateTime, 12.50, false, 1, null);

        // Act
        double result = ticketManager.calculatePrice(age, viewing);

        assertEquals(10.50, result, DELTA);
    }

    @Test
    public void calculatePriceNoDiscountThreeDimensional() {
        // Arrange
        int age = 20;
        LocalDateTime dateTime = LocalDateTime.parse(str, formatter);
        Viewing viewing = new Viewing(1, dateTime, 15, true, 1, null);

        // Act
        double result = ticketManager.calculatePrice(age, viewing);

        assertEquals(15, result, DELTA);
    }


    @Test
    public void calculatePriceDiscountChildThreeDimensional() {
        // Arrange
        int age = 11;
        LocalDateTime dateTime = LocalDateTime.parse(str, formatter);
        Viewing viewing = new Viewing(1, dateTime, 15, false, 1, null);

        // Act
        double result = ticketManager.calculatePrice(age, viewing);

        assertEquals(12.50, result, DELTA);
    }

    @Test
    public void calculatePriceDiscountElderThreeDimensional() {
        // Arrange
        int age = 65;
        LocalDateTime dateTime = LocalDateTime.parse(str, formatter);
        Viewing viewing = new Viewing(1, dateTime, 15, false, 1, null);

        // Act
        double result = ticketManager.calculatePrice(age, viewing);

        assertEquals(13.00, result, DELTA);
    }

    @Test
    public void getCorrectRow() {
        // Arrange
        int seatNumber = 25;
        LocalDateTime dateTime = LocalDateTime.parse(str, formatter);
        Viewing viewing = new Viewing(1, dateTime, 15, false, 1, new Room(1, 50, true, 5));

        // Act
        int result = ticketManager.getRow(viewing, seatNumber);

        assertEquals(3, result);
    }
}
