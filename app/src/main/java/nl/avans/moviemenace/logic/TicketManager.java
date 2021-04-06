package nl.avans.moviemenace.logic;

import android.os.AsyncTask;
import android.view.View;
import android.widget.Spinner;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.concurrent.ExecutionException;
import nl.avans.moviemenace.dataLayer.factory.DAOFactory;
import nl.avans.moviemenace.dataLayer.IDAO.TicketDAO;
import nl.avans.moviemenace.dataLayer.IDAO.ViewingDAO;
import nl.avans.moviemenace.domain.Ticket;
import nl.avans.moviemenace.domain.Viewing;

public class TicketManager implements Serializable {

    TicketDAO ticketDAO;
    private ArrayList<Ticket> allTickets;
    public static final String TICKETMANAGER_KEY = "ticketManagerKey";

    public TicketManager(DAOFactory factory) {
        this.ticketDAO = factory.createTicketDAO();
    }

    //Last step of payment
    public boolean createTickets(ArrayList<Ticket> tickets) {
        ticketDAO.createTickets(tickets);
        return true;
    }

    // Validate age
    public boolean validateAge(int age) {
        if (age <= 0 || age > 125) {
            return false;
        }
        return true;
    }
    // Calculating price incl. discount (trigger after selecting seats)
    public double calculatePrice(int age, Viewing viewing) {
        double price = viewing.getPrice();

        if (age <= 11) {
            price -= 2.50;
        } else if (age >= 65) {
            price -= 2.00;
        }

        return price;
    }

    public void loadAllTickets() {
       new DatabaseTask().execute();
    }

    //Used for calculating the available seats and the seatNumbers (used in getSeats() && checkAvailableSeats())
    private ArrayList<Ticket> getTakenSeats(int viewID) {
        ArrayList<Ticket> takenSeats = new ArrayList<>();
        for (Ticket ticket: allTickets) {
            if (ticket.getViewID() == viewID) {
                takenSeats.add(ticket);
            }
        }
        return takenSeats;
    }


    // check available seats for the viewing (trigger after selecting a viewing)
    public int checkAvailableSeats(Viewing viewing){
        ArrayList<Ticket> takenSeats = getTakenSeats(viewing.getId());
        int maxSeats = viewing.getRoom().getNumberOfSeats();
        int takenSeatsNumber = takenSeats.size();

        return maxSeats - takenSeatsNumber;
    }

    //returns seat numbers for ticket creation (Needed after pressing pay)
    public ArrayList<Integer> getAvailableSeatNumbers(Viewing viewing){
        int maxSeats = viewing.getRoom().getNumberOfSeats();
        ArrayList<Ticket> takenSeats = getTakenSeats(viewing.getId());

        // Check what chairnumbers are available
        ArrayList<Integer> seatNumbers = new ArrayList<>();
        for (int i = 1; i <= maxSeats; i ++ ) {
            if (allTickets.size() == 0) {
                seatNumbers.add(i);
            } else {
                boolean containsSeat = false;
                for (Ticket x : takenSeats) {
                    if (i == x.getChairNumber()) {
                        containsSeat = true;
                    }
                }
                if (!containsSeat) {
                    seatNumbers.add(i);
                }
            }
        }
        return seatNumbers;
    }

    // returns row number for ticket creation (Needed after pressing pay)
    public int getRow(Viewing viewing, int seatNumber) {
        int rows = viewing.getRoom().getNumberOfRows();
        int seats = viewing.getRoom().getNumberOfSeats();
        int seatsInRow = seats / rows;

        for (int i = 1; i <= rows; i ++) {
            int amountOfSeats = seatsInRow * i;
            if (seatNumber <= amountOfSeats) {
                return i;
            }
        }
        return -1;
    }

    public boolean hasDoubleSeatNumbers(ArrayList<Integer> list) {
        ArrayList<Integer> check = new ArrayList<>();
        for (int x: list) {
            if (check.contains(x)) {
                return true;
            }
            check.add(x);
        }
        return false;
    }

    public ArrayList<Ticket> getAllTicketsForAccount(String email) {
        return ticketDAO.getAllTicketsForAccount(email);
    }
    public ArrayList<Ticket> getUpcomingTicketsForAccount(String email) {
        return ticketDAO.getUpcomingTicketsForAccount(email);
    }
    public ArrayList<Ticket> getExpiredTicketsForAccount(String email) {
        return ticketDAO.getExpiredTicketsForAccount(email);
    }
    public ArrayList<Ticket> getUsedTicketsForAccount(String email) {
        return ticketDAO.getUsedTicketsForAccount(email);
    }

    public void useTicket(Ticket ticket) {
        ticketDAO.useTicket(ticket);
    }

    public class DatabaseTask extends AsyncTask<Void, Void, ArrayList<Ticket>> {

        @Override
        protected ArrayList<Ticket> doInBackground(Void... voids) {
            ArrayList<Ticket> list = new ArrayList<>();
            list = ticketDAO.getAllTickets();
            return list;
        }

        @Override
        protected void onPostExecute(ArrayList<Ticket> tickets) {
            super.onPostExecute(tickets);
            allTickets = tickets;
        }
    }
}
