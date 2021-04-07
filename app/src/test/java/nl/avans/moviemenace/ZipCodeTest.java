package nl.avans.moviemenace;

import org.junit.Test;

import nl.avans.moviemenace.domain.validation.ZipCode;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class ZipCodeTest {

    @Test
    public void checkZipCorrect( )  {
        // Arrange
        String zipCode = "1234 AB";

        // Act
        boolean result = ZipCode.checkZipCode(zipCode);

        // Assert
        assertTrue(result);
    }

    @Test
    public void checkZipCodeMissingLetters( )  {
        // Arrange
        String zipCode = "1234";

        // Act
        boolean result = ZipCode.checkZipCode(zipCode);

        // Assert
        assertFalse(result);
    }

    @Test
    public void checkZipCodeMissingNumbers( )  {
        // Arrange
        String zipCode = "AB";

        // Act
        boolean result = ZipCode.checkZipCode(zipCode);

        // Assert
        assertFalse(result);
    }

    @Test
    public void checkZipCodeMissingOneNumber( )  {
        // Arrange
        String zipCode = "123 AB";

        // Act
        boolean result = ZipCode.checkZipCode(zipCode);

        // Assert
        assertFalse(result);
    }

    @Test
    public void checkZipCodeMissingOneLetter( )  {
        // Arrange
        String zipCode = "1234 A";

        // Act
        boolean result = ZipCode.checkZipCode(zipCode);

        // Assert
        assertFalse(result);
    }

    @Test
    public void checkZipCodeOneNumberTooMuch( )  {
        // Arrange
        String zipCode = "12345 AB";

        // Act
        boolean result = ZipCode.checkZipCode(zipCode);

        // Assert
        assertFalse(result);
    }

    @Test
    public void checkZipCodeOneLetterTooMuch( )  {
        // Arrange
        String zipCode = "1234 ABC";

        // Act
        boolean result = ZipCode.checkZipCode(zipCode);

        // Assert
        assertFalse(result);
    }
}
