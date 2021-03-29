package nl.avans.moviemenace.domain;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Account {

    private String email;
    private String name;
    private String password;
    private String address;
    private ZipCode zipCode;
    private String iban;
    private LocalDate dateOfBirth;
    private List<Ticket> tickets;
    private List<MovieList> movieLists;

    public Account(String email, String name, String password, String address, String zipCode, String iban, LocalDate dateOfBirth) {
        this.email = email;
        this.name = name;
        this.password = password;
        this.address = address;
        this.zipCode = new ZipCode(zipCode);
        this.iban = iban;
        this.dateOfBirth = dateOfBirth;
        this.tickets = new ArrayList<>();
        this.movieLists = new ArrayList<>();
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getZipCode() {
        return zipCode.getZipCodeValue();
    }

    public void setZipCode(String zipCode) {
        this.zipCode = new ZipCode(zipCode);
    }

    public String getIban() {
        return iban;
    }

    public void setIban(String iban) {
        this.iban = iban;
    }

    public LocalDate getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(LocalDate dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public List<Ticket> getTickets() {
        return tickets;
    }

    public void setTickets(List<Ticket> tickets) {
        this.tickets = tickets;
    }

    public List<MovieList> getMovieLists() {
        return movieLists;
    }

    public void setMovieLists(List<MovieList> movieLists) {
        this.movieLists = movieLists;
    }
}
