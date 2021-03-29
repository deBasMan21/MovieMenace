package nl.avans.moviemenace.dataLayer.SQL;

import java.util.ArrayList;

import nl.avans.moviemenace.dataLayer.DatabaseConnection;
import nl.avans.moviemenace.dataLayer.IDAO.TicketDAO;
import nl.avans.moviemenace.domain.Ticket;

public class SQLTicketDAO extends DatabaseConnection implements TicketDAO {
    @Override
    public void createTicket(Ticket ticket) {
        try{
            //this string contains the query to add a ticket to the db
            String SQL = "INSERT INTO Ticket (ChairNumber, Email, ViewID, Status, RowNumber) VALUES ("
                    + ticket.getChairNumber() + ", '" + ticket.getEmail() + "', " + ticket.getViewID() + ", '"
                    + ticket.getStatus() + "', " + ticket.getRowNumber() + ")";
            //this executes the query
            executeSQLStatement(SQL);
        } catch (Exception e){
            //Prints out any errors that may occur
            e.printStackTrace();
        }
    }

    @Override
    public Ticket getTicket(int chairnumber, String email, int viewID) {
        //creates a ticket object to store the data
        Ticket ticket = null;
        try{
            //this string contains the sql query to select the ticket
            String SQL = "SELECT * FROM Ticket WHERE ChairNumber = " + chairnumber + " AND Email = '" + email + "' AND ViewID = " + viewID;
            //this executes the sql query
            executeSQLSelectStatement(SQL);
            //adds the data to the object created above
            ticket = new Ticket(rs.getInt("ChairNumber"), rs.getString("Email"), rs.getInt("ViewID"), rs.getString("Status"), rs.getInt("RowNumber"));
        } catch (Exception e){
            //Prints out any errors that may occur
            e.printStackTrace();
        }
        //returns the ticket
        return ticket;
    }

    @Override
    public ArrayList<Ticket> getAllTicketsForAccount(String email) {
        //creates an arraylist to store the tickets from an account
        ArrayList<Ticket> tickets = new ArrayList<>();
        try{
            //this string contains the query to select all the tickets for an account
            String SQL = "SELECT * FROM Ticket WHERE Email = '" + email + "'";
            //this executes the query
            executeSQLSelectStatement(SQL);
            //this creates a loop. within it the ticket objects are created and put into the list
            while(rs.next()){
                Ticket ticket = new Ticket(rs.getInt("ChairNumber"), rs.getString("Email"), rs.getInt("ViewID"), rs.getString("Status"), rs.getInt("RowNumber"));
                tickets.add(ticket);
            }
        } catch (Exception e){
            //Prints out any errors that may occur
            e.printStackTrace();
        }
        //this returns the list
        return tickets;
    }

    @Override
    public ArrayList<Ticket> getUpcomingTicketsForAccount(String email) {
        //creates an arraylist with tickets to store all upcoming tickets for an account
        ArrayList<Ticket> tickets = new ArrayList<>();
        try{
            //this string contains the sql query to select the upcoming tickets for an account
            String SQL = "SELECT * FROM TICKETS WHERE Email = '" + email + "' AND Status = 'UPCOMING'";
            //this executes the query
            executeSQLSelectStatement(SQL);
            //this creates a loop. within it the tickets are created and added to the list
            while(rs.next()){
                Ticket ticket = new Ticket(rs.getInt("ChairNumber"), rs.getString("Email"), rs.getInt("ViewID"), rs.getString("Status"), rs.getInt("RowNumber"));
                tickets.add(ticket);
            }
        } catch (Exception e){
            //Prints out any errors that may occur
            e.printStackTrace();
        }
        //this returns the list of tickets
        return tickets;
    }

    @Override
    public ArrayList<Ticket> getExpiredTicketsForAccount(String email) {
        //creates an arraylist to contain all expired tickets for an account
        ArrayList<Ticket> tickets = new ArrayList<>();
        try{
            // this string contains the sql query to select alle expired tickets for an account
            String SQL = "SELECT * FROM TICKETS WHERE Email = '" + email + "' AND Status = 'EXPIRED'";
            //this executes the query
            executeSQLSelectStatement(SQL);
            //this creates a loop. within it the tickets are created and added to the list
            while(rs.next()){
                Ticket ticket = new Ticket(rs.getInt("ChairNumber"), rs.getString("Email"), rs.getInt("ViewID"), rs.getString("Status"), rs.getInt("RowNumber"));
                tickets.add(ticket);
            }
        } catch (Exception e){
            //Prints out any errors that may occur
            e.printStackTrace();
        }
        //returns the list of tickets
        return tickets;
    }

    @Override
    public ArrayList<Ticket> getUsedTicketsForAccount(String email) {
        //creates an arraylist to contain all the used tickets for an account
        ArrayList<Ticket> tickets = new ArrayList<>();
        try{
            //this string contains the sql query to select alle used tickets for an account
            String SQL = "SELECT * FROM TICKETS WHERE Email = '" + email + "' AND Status = 'USED'";
            //this executes the query
            executeSQLSelectStatement(SQL);
            //this creates a loop. within it the tickets are created and added to the list
            while(rs.next()){
                Ticket ticket = new Ticket(rs.getInt("ChairNumber"), rs.getString("Email"), rs.getInt("ViewID"), rs.getString("Status"), rs.getInt("RowNumber"));
                tickets.add(ticket);
            }
        } catch (Exception e){
            //Prints out any errors that may occur
            e.printStackTrace();
        }
        //returns the list of tickets
        return tickets;
    }
}
