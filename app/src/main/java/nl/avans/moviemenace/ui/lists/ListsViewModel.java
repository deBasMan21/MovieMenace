package nl.avans.moviemenace.ui.lists;

import android.os.AsyncTask;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.ArrayList;
import java.util.List;

import nl.avans.moviemenace.dataLayer.DatabaseConnection;
import nl.avans.moviemenace.dataLayer.factory.DAOFactory;
import nl.avans.moviemenace.dataLayer.factory.SQLDAOFactory;
import nl.avans.moviemenace.domain.Account;
import nl.avans.moviemenace.domain.Movie;
import nl.avans.moviemenace.domain.MovieList;
import nl.avans.moviemenace.logic.MovieListManager;

public class ListsViewModel extends ViewModel {
    private MutableLiveData<List<MovieList>> movieLists;
    private DAOFactory factory = new SQLDAOFactory();
    private MovieListManager movieListManager = new MovieListManager(factory);

    private Account account;

    public void setAccount(Account account) {
        this.account = account;
    }

    public LiveData<List<MovieList>> getMovieLists() {
        if (movieLists == null || movieLists.getValue().size() == 0) {
            movieLists = new MutableLiveData<List<MovieList>>();
            new DatabaseTask().execute(account);
        }
        return movieLists;
    }

    public class DatabaseTask extends AsyncTask<Account, Void, List<MovieList>> {

        @Override
        protected List<MovieList> doInBackground(Account... accounts) {
            DatabaseConnection db = new DatabaseConnection();
            if (!db.connectionIsOpen()) {
                db.openConnection();
            }
            Account account = accounts[0];
            List<MovieList> movieLists;
            if (account == null) {
                movieLists = movieListManager.getMovieListsForAccount("");
            } else {
                movieLists = movieListManager.getMovieListsForAccount(account.getEmail());
            }
            db.closeConnection();
            return movieLists;
        }

        @Override
        protected void onPostExecute(List<MovieList> newMovieLists) {
            super.onPostExecute(newMovieLists);
            movieLists.setValue(newMovieLists);
        }
    }
}
