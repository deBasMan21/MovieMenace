package nl.avans.moviemenace.domain.validation;

import java.io.Serializable;

public class ZipCode implements Serializable {

    public static boolean checkZipCode(String zipCode) {
        try {
            String letters = zipCode.trim().substring(4).trim().toUpperCase();
            int numbersLength = Integer.valueOf(zipCode.trim().substring(0, 4));
            int lettersLength = zipCode.trim().substring(4).length();
            char firstLetter = letters.charAt(0);
            char secondLetter = letters.charAt(1);
            if (numbersLength > 999 && numbersLength <= 9999 && lettersLength == 2 && (firstLetter >= 'A' && firstLetter <= 'Z') && (secondLetter >= 'A' && secondLetter <= 'Z')) {
                return true;
            }
        } catch (Exception e) {
            return false;
        }
        return false;
    }

    public static String formatZipCode(String zipCode) {

        String numbers = zipCode.trim().substring(0, 4).trim();
        String letters = zipCode.trim().substring(4).trim().toUpperCase();


        return numbers + " " + letters;


    }


}
