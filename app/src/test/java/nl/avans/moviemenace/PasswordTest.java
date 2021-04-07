package nl.avans.moviemenace;

import org.junit.Test;

import nl.avans.moviemenace.domain.validation.Password;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class PasswordTest {

    @Test
    public void checkPasswordCorrectEightCharacters() {
        // Arrange
        String pass = "12345678";

        // Act
        boolean result = Password.checkPassword(pass);

        // Assert
        assertTrue(result);
    }

    @Test
    public void checkPassWordCorrectMoreThanEightCharacters() {
        // Arrange
        String pass = "123456789";

        // Act
        boolean result = Password.checkPassword(pass);

        // Assert
        assertTrue(result);
    }

    @Test
    public void checkPassWordWrongLessThanEightCharacters() {
        // Arrange
        String pass = "1234567";

        // Act
        boolean result = Password.checkPassword(pass);

        // Assert
        assertFalse(result);
    }
}
