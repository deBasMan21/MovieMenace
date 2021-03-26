package nl.avans.moviemenace.dataLayer.SQL;

import java.util.ArrayList;

import nl.avans.moviemenace.dataLayer.IDAO.TicketDAO;
import nl.avans.moviemenace.domain.Ticket;

public class SQLTicketDAO implements TicketDAO {
    @Override
    public void createTicket(Ticket ticket) {

    }

    @Override
    public Ticket getTicket(int chairnumber, String email, int viewID) {
        return null;
    }

    @Override
    public ArrayList<Ticket> getAllTicketsForAccount(String email) {
        return null;
    }

    @Override
    public ArrayList<Ticket> getUpcomingTicketsForAccount(String email) {
        return null;
    }

    @Override
    public ArrayList<Ticket> getExpiredTicketsForAccount(String email) {
        return null;
    }

    @Override
    public ArrayList<Ticket> getUsedTicketsForAccount(String email) {
        return null;
    }
}
