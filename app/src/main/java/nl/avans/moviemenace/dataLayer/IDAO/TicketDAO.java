package nl.avans.moviemenace.dataLayer.IDAO;

import java.util.ArrayList;

import nl.avans.moviemenace.domain.Ticket;

public interface TicketDAO {
    void createTickets(ArrayList<Ticket> tickets);
    Ticket getTicket(int chairnumber, String email, int viewID);
    ArrayList<Ticket> getAllTickets();
    ArrayList<Ticket> getAllTicketsForAccount(String email);
    ArrayList<Ticket> getUpcomingTicketsForAccount(String email);
    ArrayList<Ticket> getExpiredTicketsForAccount(String email);
    ArrayList<Ticket> getUsedTicketsForAccount(String email);
    void updateTickets();
}
