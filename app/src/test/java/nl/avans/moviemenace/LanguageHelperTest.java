package nl.avans.moviemenace;

import org.junit.Test;
import static org.junit.Assert.*;

import java.util.Locale;

import nl.avans.moviemenace.logic.LanguageHelper;

public class LanguageHelperTest {
    @Test
    public void languageHelperisLanguageSameLanguageTrue() {
        String language = Locale.getDefault().toString();
        assertEquals(true, LanguageHelper.isLanguage(language));
    }

    @Test
    public void languageHelperisLanguageDifferentLanguageFalse() {
        String language = Locale.ENGLISH.toString();
        assertEquals(false, LanguageHelper.isLanguage(language));
    }
}
