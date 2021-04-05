package nl.avans.moviemenace.domain.validation;

import java.io.Serializable;

public class Email implements Serializable {
    private String email;

    public Email(String email) {
        setEmail(email);
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        if (checkEmail(email)) {
            this.email = email;
        }
    }

    public static boolean checkEmail(String mailAddress) {
        String[] parts = mailAddress.split("@");

        // contains no @ or dot
        if (!mailAddress.contains("@") || !mailAddress.contains(".")) {
            return false;
        }
        // no mailbox part
        if (parts[0].length() < 1) {
            return false;
        }
        // subdomain-tld delimiter
        if (parts[1].split("\\.").length > 3) {
            return false;
        }
        // no subdomain part
        if (parts[1].split("\\.")[0].length() < 1) {
            return false;
        }
        // no tld part
        if (parts[1].split("\\.", -1)[1].length() < 1) {
            return false;
        }

        return true;
    }
}
