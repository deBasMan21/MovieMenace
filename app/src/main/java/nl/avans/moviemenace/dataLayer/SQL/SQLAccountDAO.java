package nl.avans.moviemenace.dataLayer.SQL;


import java.time.LocalDate;

import nl.avans.moviemenace.dataLayer.DatabaseConnection;
import nl.avans.moviemenace.dataLayer.IDAO.AccountDAO;
import nl.avans.moviemenace.domain.Account;

public class SQLAccountDAO extends DatabaseConnection implements AccountDAO  {
    @Override
    public Account getAccount(String email) {
        // An account is created to fill in with the data from the db
        Account account = null;
        try{
            //This string contains the SQL query that will be used
            String SQL = "SELECT * FROM Account WHERE Email = '" + email + "'";
            executeSQLSelectStatement(SQL);
            //The account that was created is filled in with the data from the db selected by the query
            account = new Account(rs.getString("Email"), rs.getString("Name"), rs.getString("Password"), rs.getString("Adress"), rs.getString("Zipcode"), rs.getString("IBAN"), LocalDate.parse(rs.getString("DateOfBirth")));
        } catch(Exception e){
            //Prints out any errors that may occur
            e.printStackTrace();
        }
        //Returns the account
        return account;
    }

    @Override
    public void updateAccount(String email, Account updatedAccount) {
        try{
            //This string contains the update query filled in with the data from the updatedaccount
            String SQL = "UPDATE Account (Email, Name, Adress, Zipcode) SET VALUES('"
                    + updatedAccount.getEmail() + "', '" + updatedAccount.getName() + "', '"
                    + updatedAccount.getAddress() + "', '" + updatedAccount.getZipCode() + "') WHERE Email = '" + email + "'";

            //This executes the query
            executeSQLStatement(SQL);
        } catch(Exception e){
            //Prints out any errors that may occur
            e.printStackTrace();
        }
    }

    @Override
    public void createAccount(Account newAccount) {
        try{
            //This string contains the create query filled in with the data from the newaccount to create a new account in the db
            String SQL = "INSERT INTO Account (Email, Name, Password, Adress, Zipcode, IBAN, DateOfBirth) VALUES('"
                    + newAccount.getEmail() + "', '" + newAccount.getName() + "', '" + newAccount.getPassword() + "', '"
                    + newAccount.getAddress() + "', '" + newAccount.getZipCode() + "', " + newAccount.getIban() + ", '"
                    + newAccount.getDateOfBirth().toString() + "')";
            //This executes the query
            executeSQLStatement(SQL);
        } catch(Exception e){
            //Prints out any errors that may occur
            e.printStackTrace();
        }
    }

    @Override
    public String getPassword(String email) {
        //This string is the string that will be returned with the password in it
        String password = "";
        try{
            //This string contains the select query for the password from an account with a specific email
            String SQL = "SELECT * FROM Account WHERE Email = '" + email + "'";
            //Executes the select query
            executeSQLSelectStatement(SQL);
            //Fills the string created before with the data thats selected with the query
            password = rs.getString("Password");
        } catch(Exception e){
            //Prints out any errors that may occur
            e.printStackTrace();
        }
        //Returns the password that was selected
        return password;
    }
}
