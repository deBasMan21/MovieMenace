package nl.avans.moviemenace;

import org.junit.Test;

import nl.avans.moviemenace.domain.validation.Email;

import static org.junit.Assert.*;

public class EmailTest {

    @Test
    public void emailIncludesAtTrue() {
        String email = "user@domain.com";
        assertEquals(true, Email.checkEmail(email));
    }

    @Test
    public void emailExcludesAtFalse() {
        String email = "user_domain.com";
        assertEquals(false, Email.checkEmail(email));
    }

    @Test
    public void emailIncludesDotTrue() {
        String email = "user@domain.com";
        assertEquals(true, Email.checkEmail(email));
    }

    @Test
    public void emailExcludesDotFalse() {
        String email = "user@domain_com";
        assertEquals(false, Email.checkEmail(email));
    }

    @Test
    public void emailIncludesAtDotFalse() {
        String email = "user@domain.com";
        assertEquals(true, Email.checkEmail(email));
    }

    @Test
    public void emailExcludesAtDotFalse() {
        String email = "user_domain_com";
        assertEquals(false, Email.checkEmail(email));
    }

    @Test
    public void emailIncludesMailboxTrue() {
        String email = "user@domain.com";
        assertEquals(true, Email.checkEmail(email));
    }

    @Test
    public void emailExcludesMailboxFalse() {
        String email = "@domain.com";
        assertEquals(false, Email.checkEmail(email));
    }

    @Test
    public void email0SubdomainsFalse() {
        String email = "user@.com";
        assertEquals(false, Email.checkEmail(email));
    }

    @Test
    public void email1SubdomainsTrue() {
        String email = "user@domain.com";
        assertEquals(true, Email.checkEmail(email));
    }

    @Test
    public void email2SubdomainsTrue() {
        String email = "user@subdomain.domain.com";
        assertEquals(true, Email.checkEmail(email));
    }

    @Test
    public void email3SubdomainsFalse() {
        String email = "user@subdomain.subdomain.domain.com";
        assertEquals(false, Email.checkEmail(email));
    }

    @Test
    public void emailExcludesTldFalse() {
        String email = "user@domain.";
        assertEquals(false, Email.checkEmail(email));
    }
}
