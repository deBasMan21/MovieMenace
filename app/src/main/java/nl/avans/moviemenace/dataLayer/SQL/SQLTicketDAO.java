package nl.avans.moviemenace.dataLayer.SQL;

import java.util.ArrayList;

import nl.avans.moviemenace.dataLayer.DatabaseConnection;
import nl.avans.moviemenace.dataLayer.IDAO.TicketDAO;
import nl.avans.moviemenace.domain.Ticket;

public class SQLTicketDAO extends DatabaseConnection implements TicketDAO {
    @Override
    public void createTicket(Ticket ticket) {
        try{
            String SQL = "INSERT INTO Ticket (ChairNumber, Email, ViewID, Status, RowNumber) VALUES ("
                    + ticket.getChairNumber() + ", '" + ticket.getEmail() + "', " + ticket.getViewID() + ", '"
                    + ticket.getStatus() + "', " + ticket.getRowNumber() + ")";
            executeSQLStatement(SQL);
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public Ticket getTicket(int chairnumber, String email, int viewID) {
        Ticket ticket = null;
        try{
            String SQL = "SELECT * FROM Ticket WHERE ChairNumber = " + chairnumber + " AND Email = '" + email + "' AND ViewID = " + viewID;
            executeSQLSelectStatement(SQL);

            ticket = new Ticket(rs.getInt("ChairNumber"), rs.getString("Email"), rs.getInt("ViewID"), rs.getString("Status"), rs.getInt("RowNumber"));
        } catch (Exception e){
            e.printStackTrace();
        }
        return ticket;
    }

    @Override
    public ArrayList<Ticket> getAllTicketsForAccount(String email) {
        ArrayList<Ticket> tickets = new ArrayList<>();
        try{
            String SQL = "SELECT * FROM Ticket WHERE Email = '" + email + "'";
            executeSQLSelectStatement(SQL);
            while(rs.next()){
                Ticket ticket = new Ticket(rs.getInt("ChairNumber"), rs.getString("Email"), rs.getInt("ViewID"), rs.getString("Status"), rs.getInt("RowNumber"));
                tickets.add(ticket);
            }
        } catch (Exception e){
            e.printStackTrace();
        }
        return tickets;
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
