package nl.avans.moviemenace.dataLayer.SQL;

import android.accounts.Account;

import nl.avans.moviemenace.dataLayer.IDAO.AccountDAO;

public class SQLAccountDAO implements AccountDAO {
    @Override
    public Account getAccount(String email) {
        return null;
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
