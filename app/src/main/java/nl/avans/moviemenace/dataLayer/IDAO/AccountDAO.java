package nl.avans.moviemenace.dataLayer.IDAO;

import android.accounts.Account;

public interface AccountDAO {
    Account getAccount(String email);
    void updateAccount(String email, Account updatedAccount);
    void createAccount(Account newAccount);
    String getPassword(String email);
}
