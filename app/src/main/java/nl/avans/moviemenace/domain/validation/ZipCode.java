package nl.avans.moviemenace.domain.validation;

public class ZipCode {
    private String zipCodeValue;

    public ZipCode(String zipCode) {
        setZipCodeValue(zipCode);
    }

    public void setZipCodeValue(String zipCode) {
        String formattedZipCode = formatZipCode(zipCode);
        if (formattedZipCode == null) {
            zipCodeValue = null;
        } else {
            this.zipCodeValue = formattedZipCode;
        }
    }

    public String getZipCodeValue() {
        return zipCodeValue;
    }

    public boolean checkZipCode(String zipCode) {
        String letters = zipCode.trim().substring(4).trim().toUpperCase();
        int numbersLength = Integer.valueOf(zipCode.trim().substring(0, 4));
        int lettersLength = zipCode.trim().substring(4).length();
        char firstLetter = letters.charAt(0);
        char secondLetter = letters.charAt(1);
        if (numbersLength > 999 && numbersLength <= 9999 && lettersLength == 2 && (firstLetter >= 'A' && firstLetter <= 'Z') && (secondLetter >= 'A' && secondLetter <= 'Z')) {
            return true;
        }
        return false;
    }

    private String formatZipCode(String zipCode) {

        String letters = zipCode.trim().substring(4).trim().toUpperCase();
        int numbersLength = Integer.valueOf(zipCode.trim().substring(0, 4));
        int lettersLength = zipCode.trim().substring(4).length();
        char firstLetter = letters.charAt(0);
        char secondLetter = letters.charAt(1);

        if (numbersLength > 999 && numbersLength <= 9999 && lettersLength == 2 && (firstLetter >= 'A' && firstLetter <= 'Z') && (secondLetter >= 'A' && secondLetter <= 'Z')) {
            return zipCode.trim().substring(0, 4) + " " + letters;
        }

        return null;
    }


}
