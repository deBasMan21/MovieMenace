package nl.avans.moviemenace.logic;

import java.time.LocalDate;
import java.time.Period;
import java.util.List;
import java.util.Map;

import nl.avans.moviemenace.dataLayer.DAOFactory;
import nl.avans.moviemenace.dataLayer.IDAO.TicketDAO;
import nl.avans.moviemenace.dataLayer.IDAO.ViewingDAO;
import nl.avans.moviemenace.domain.Account;
import nl.avans.moviemenace.domain.Ticket;
import nl.avans.moviemenace.domain.Viewing;

public class TicketManager {

    TicketDAO ticketDAO;
    ViewingDAO viewingDAO;

    public TicketManager(DAOFactory factory) {
        this.ticketDAO = factory.createTicketDAO();
        this.viewingDAO = factory.createViewingDAO();
    }

    //Last step of payment
    public boolean createTicket(Ticket ticket) {
        ticketDAO.createTicket(ticket);
        return true;
    }

    // Calculating price incl. discount (trigger after selecting seats)
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

    //Used for calculating the available seats and the seatNumbers (used in getSeats() && checkAvailableSeats())
    private int getTakenSeats(int viewID) {
        return viewingDAO.getTakenSeats(viewID);
    }

    // check available seats for the viewing (trigger after selecting a viewing)
    public int checkAvailableSeats(Viewing viewing) {
        int maxSeats = viewing.getRoom().getNumberOfSeats();
        int takenSeats = getTakenSeats(viewing.getId());

        return maxSeats - takenSeats;
    }

    //returns seat numbers for ticket creation (Needed after pressing pay)
    public int[] getSeats(Viewing viewing, int selectedSeats) {
        int takenSeats = getTakenSeats(viewing.getId());
        int[] seatNumbers = new int[selectedSeats];

        for (int i = 0; i < selectedSeats; i ++) {
            seatNumbers[i] = takenSeats + 1;;
            takenSeats += 1;
        }

        return seatNumbers;
    }

    // returns row number for ticket creation (Needed after pressing pay)
    public int getRow(Viewing viewing, int seatNumber) {
        int rows = viewing.getRoom().getNumberOfRows();
        int seats = viewing.getRoom().getNumberOfSeats();
        int seatsInRow = seats / rows;

        for (int i = 0; i < rows; i ++) {
            int amountOfSeats = seatsInRow * (i + 1);
            if (seatNumber <= amountOfSeats) {
                return i;
            }
        }

        return -1;
    }
}
