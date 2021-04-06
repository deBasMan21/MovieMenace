package nl.avans.moviemenace.logic;

public class LanguageHelper {
    private static String language = "us_EN";

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
