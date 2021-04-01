package nl.avans.moviemenace.domain.validation;

import java.io.Serializable;

public class Password implements Serializable {

    private String Password;

    public Password(String password) {
        setPassword(password);
    }

    public String getPassword() {
        return Password;
    }

    public void setPassword(String password) {
        if (checkPassword(password)) {
            Password = password;
        }
    }

    public boolean checkPassword(String password) {
        if (password.matches("^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=])(?=\\S+$).{8,}$")) {
            return true;
        }
        return false;
    }
}
