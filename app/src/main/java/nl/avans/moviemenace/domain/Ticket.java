package nl.avans.moviemenace.domain;

import java.io.Serializable;

public class Ticket implements Serializable {
    public static final String TICKET_KEY = "TicketKey";

    private int chairNumber;
    private String email;
    private int viewID;
    private String status;
    private int rowNumber;

    public Ticket(int chairNumber, String email, int viewID, String status, int rowNumber) {
        this.chairNumber = chairNumber;
        this.email = email;
        this.viewID = viewID;
        this.status = status;
        this.rowNumber = rowNumber;
    }

    public int getChairNumber() {
        return chairNumber;
    }

    public void setChairNumber(int chairNumber) {
        this.chairNumber = chairNumber;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getViewID() {
        return viewID;
    }

    public void setViewID(int viewID) {
        this.viewID = viewID;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public int getRowNumber() {
        return rowNumber;
    }

    public void setRowNumber(int rowNumber) {
        this.rowNumber = rowNumber;
    }
}
