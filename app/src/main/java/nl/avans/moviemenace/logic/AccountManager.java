package nl.avans.moviemenace.logic;

import nl.avans.moviemenace.dataLayer.DAOFactory;
import nl.avans.moviemenace.dataLayer.IDAO.AccountDAO;
import nl.avans.moviemenace.dataLayer.SQL.SQLAccountDAO;
import nl.avans.moviemenace.dataLayer.SQLDAOFactory;

public class AccountManager {

    private AccountDAO accountDAO;

    public AccountManager(DAOFactory factory) {
        this.accountDAO = factory.createAccountDAO();
    }
}
