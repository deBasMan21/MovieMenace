package nl.avans.moviemenace.dataLayer.SQL;


import java.time.LocalDate;

import nl.avans.moviemenace.dataLayer.DatabaseConnection;
import nl.avans.moviemenace.dataLayer.IDAO.AccountDAO;
import nl.avans.moviemenace.domain.Account;

public class SQLAccountDAO extends DatabaseConnection implements AccountDAO  {
    @Override
    public Account getAccount(String email) {
        Account account = null;
        try{
            String SQL = "SELECT * FROM Account WHERE Email = " + email;
            executeSQLSelectStatement(SQL);

            account = new Account(rs.getString("Email"), rs.getString("Name"), rs.getString("Password"), rs.getString("Adress"), rs.getString("Zipcode"), rs.getString("IBAN"), LocalDate.parse(rs.getString("DateOfBirth")));
        } catch(Exception e){
            e.printStackTrace();
        }
        return account;
    }

    @Override
    public void updateAccount(String email, Account updatedAccount) {
        try{
            String SQL = "UPDATE Account (Email, Name, Adress, Zipcode) SET VALUES("
                    + updatedAccount.getEmail() + ", " + updatedAccount.getName() + ", "
                    + updatedAccount.getAddress() + ", " + updatedAccount.getZipCode() + ") WHERE Email = " + email;

            executeSQLSelectStatement(SQL);
        } catch(Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public void createAccount(Account newAccount) {
        try{
            String SQL = "INSERT INTO Account (Email, Name, Password, Adress, Zipcode, IBAN, DateOfBirth) VALUES("
                    + newAccount.getEmail() + ", " + newAccount.getName() + ", " + newAccount.getPassword() + ", "
                    + newAccount.getAddress() + ", " + newAccount.getZipCode() + ", " + newAccount.getIban() + ", "
                    + newAccount.getDateOfBirth().toString() + ")";

            executeSQLSelectStatement(SQL);
        } catch(Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public String getPassword(String email) {
        String password = "";
        try{
            String SQL = "SELECT * FROM Account WHERE Email = " + email;
            executeSQLSelectStatement(SQL);

            password = rs.getString("Password");
        } catch(Exception e){
            e.printStackTrace();
        }
        return password;
    }
}
