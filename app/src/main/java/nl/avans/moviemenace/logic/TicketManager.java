package nl.avans.moviemenace.logic;

import java.time.LocalDate;
import java.time.Period;

import nl.avans.moviemenace.dataLayer.DAOFactory;
import nl.avans.moviemenace.dataLayer.IDAO.TicketDAO;
import nl.avans.moviemenace.domain.Account;
import nl.avans.moviemenace.domain.Ticket;
import nl.avans.moviemenace.domain.Viewing;

public class TicketManager {

    TicketDAO ticketDAO;

    public TicketManager(DAOFactory factory) {
        this.ticketDAO = factory.createTicketDAO();
    }

    public boolean createTicket(Ticket ticket) {
        ticketDAO.createTicket(ticket);
        return true;
    }

    // Calculating price incl. discount
    public double calculatePrice(Account account, Viewing viewing) {
        double price = viewing.getPrice();
        LocalDate dateOfBirth = account.getDateOfBirth();
        LocalDate currentDate = LocalDate.now();
        int age = Period.between(dateOfBirth, currentDate).getYears();

        if (age <= 11) {
            price -= 2.50;
        } else if (age >= 65) {
            price -= 2.00;
        }

        return price;
    }


}
