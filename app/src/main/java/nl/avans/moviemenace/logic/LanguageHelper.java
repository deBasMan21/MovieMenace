package nl.avans.moviemenace.logic;

import java.util.Locale;

public class LanguageHelper {
    private static String language = Locale.getDefault().toString();

    public static void setLanguage(String newLanguage){
        language = newLanguage;
    }

    public static String getLanguage(){
        return language;
    }

    public static boolean isLanguage(String compareLanguage){
        return language.equals(compareLanguage);
    }
}
