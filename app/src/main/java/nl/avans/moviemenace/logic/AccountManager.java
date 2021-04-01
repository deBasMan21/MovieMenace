package nl.avans.moviemenace.logic;

import nl.avans.moviemenace.dataLayer.factory.DAOFactory;
import nl.avans.moviemenace.dataLayer.IDAO.AccountDAO;
import nl.avans.moviemenace.domain.Account;

public class AccountManager {

    private AccountDAO accountDAO;

    public AccountManager(DAOFactory factory) {
        this.accountDAO = factory.createAccountDAO();
    }

    public Account getAccount(String email) {
        return accountDAO.getAccount(email);
    }

    public void updateAccount(String email, Account updatedAccount) {
        accountDAO.updateAccount(email, updatedAccount);
    }

    public void createAccount(Account newAccount) {
        accountDAO.createAccount(newAccount);
    }
    public String getPassword(String email) {
        return accountDAO.getPassword(email);
    }

    public Account loginWithAccount(String email, String password){
        Account account = null;
        if(password.equals(getPassword(email))){
            account = getAccount(email);
        }
        return account;
    }
}
