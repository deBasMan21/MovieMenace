package nl.avans.moviemenace.domain.validation;

public class Password {

    private String Password;

    public Password(String password) {

    }

    public String getPassword() {
        return Password;
    }

    public void setPassword(String password) {
        Password = password;
    }

    public boolean checkPassword(String password) {
        if (password.matches(" ^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[!@#&()–[{}]:;',?/*~$^+=<>]).{8,20}$\n")) {
            return true;
        }
        return false;
    }
}
