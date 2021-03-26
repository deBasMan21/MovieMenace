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

    }

    @Override
    public void createAccount(Account newAccount) {

    }

    @Override
    public String getPassword(String email) {
        return null;
    }
}
