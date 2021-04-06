package nl.avans.moviemenace.dataLayer.SQL;

import java.io.Serializable;
import java.util.ArrayList;

import nl.avans.moviemenace.dataLayer.DatabaseConnection;
import nl.avans.moviemenace.dataLayer.IDAO.TicketDAO;
import nl.avans.moviemenace.domain.Ticket;

public class SQLTicketDAO extends DatabaseConnection implements TicketDAO, Serializable {
    @Override
    public void createTickets(ArrayList<Ticket> tickets) {
        openConnection();
        try{
            StringBuilder builder = new StringBuilder();
            builder.append("INSERT INTO Tickets (ChairNumber, Email, ViewID, Status, RowNumber) VALUES");
            for (int i = 0; i < tickets.size(); i ++) {
                Ticket ticket = tickets.get(i);
                if (i == 0) {
                    builder.append(" (").append(ticket.getChairNumber() + ", '" + ticket.getEmail() + "'," + ticket.getViewID() + ", '" + ticket.getStatus() + "'," + ticket.getRowNumber() + ")");
                } else {
                    builder.append(", (").append(ticket.getChairNumber() + ", '" + ticket.getEmail() + "'," + ticket.getViewID() + ", '" + ticket.getStatus() + "'," + ticket.getRowNumber() + ")");
                }
            }
            //this string contains the query to add a ticket to the db
            String SQL = builder.toString();
            //this executes the query
            executeSQLStatement(SQL);
        } catch (Exception e){
            //Prints out any errors that may occur
            e.printStackTrace();
        } finally {
            closeConnection();
        }
    }

    @Override
    public Ticket getTicket(int chairnumber, String email, int viewID) {
        openConnection();
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
        } finally {
            closeConnection();
        }
        //returns the ticket
        return ticket;
    }

    @Override
    public ArrayList<Ticket> getAllTickets() {
        openConnection();
        ArrayList<Ticket> list = null;

        try {
            // Retrieve ticket count for a viewing
            String SQL = "SELECT * FROM Tickets";
            executeSQLSelectStatement(SQL);

            list = new ArrayList<>();

            while(rs.next()) {
                list.add(new Ticket(rs.getInt("ChairNumber"), rs.getString("Email"), rs.getInt("ViewID"), rs.getString("Status"), rs.getInt("RowNumber")));
            }
        } catch (Exception e) {
            //Prints out any errors that may occur
            e.printStackTrace();
        } finally {
            closeConnection();
        }

        return list;
    }

    @Override
    public ArrayList<Ticket> getAllTicketsForAccount(String email) {
        openConnection();
        //creates an arraylist to store the tickets from an account
        ArrayList<Ticket> tickets = new ArrayList<>();
        try{
            //this string contains the query to select all the tickets for an account
            String SQL = "SELECT * FROM Tickets WHERE Email = '" + email + "'";
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
        } finally {
            closeConnection();
        }
        //this returns the list
        return tickets;
    }

    @Override
    public ArrayList<Ticket> getUpcomingTicketsForAccount(String email) {
        openConnection();
        //creates an arraylist with tickets to store all upcoming tickets for an account
        ArrayList<Ticket> tickets = new ArrayList<>();
        try{
            //this string contains the sql query to select the upcoming tickets for an account
            String SQL = "SELECT * FROM TICKETS WHERE Email = '" + email + "' AND Status = 'VALID'";
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
        } finally {
            closeConnection();
        }
        //this returns the list of tickets
        return tickets;
    }

    @Override
    public ArrayList<Ticket> getExpiredTicketsForAccount(String email) {
        openConnection();
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
        }finally {
            closeConnection();
        }
        //returns the list of tickets
        return tickets;
    }

    @Override
    public ArrayList<Ticket> getUsedTicketsForAccount(String email) {
        openConnection();
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
        } finally {
            closeConnection();
        }
        //returns the list of tickets
        return tickets;
    }

    @Override
    public void updateTickets(){
        openConnection();
        try{
            String SQL = "SELECT * FROM Tickets AS T INNER JOIN Viewing AS V ON V.ViewID = T.ViewID WHERE T.Status = 'VALID' AND V.Date < GETDATE()";
            executeSQLSelectStatement(SQL);
            ArrayList<Ticket> tickets = new ArrayList<>();
            while(rs.next()){
                Ticket ticket = new Ticket(rs.getInt("ChairNumber"), rs.getString("Email"), rs.getInt("ViewID"), rs.getString("Status"), rs.getInt("RowNumber"));
                tickets.add(ticket);
            }

            for (Ticket ticket : tickets) {
                String updateSQL = "UPDATE Tickets SET Status = 'EXPIRED' WHERE Email = '" + ticket.getEmail() +"' AND ChairNumber = " + ticket.getChairNumber() + " AND ViewID = "  + ticket.getViewID();
                executeSQLStatement(updateSQL);
            }
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public void useTicket(Ticket ticket) {
        openConnection();
        try{
            String SQL = "UPDATE Tickets SET Status = 'USED' WHERE Email = '" + ticket.getEmail() + "' AND ChairNumber = " + ticket.getChairNumber() + "AND ViewID = " + ticket.getViewID();
            executeSQLStatement(SQL);
        } catch (Exception e){
            e.printStackTrace();
        }finally {
            closeConnection();
        }
    }
}
