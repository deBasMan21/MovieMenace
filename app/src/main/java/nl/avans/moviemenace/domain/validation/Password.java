package nl.avans.moviemenace.domain.validation;

import java.io.Serializable;

public class Password implements Serializable {

    public static boolean checkPassword(String password) {
        if (password.length() >= 8) {
            return true;
        }
        return false;
    }
}
