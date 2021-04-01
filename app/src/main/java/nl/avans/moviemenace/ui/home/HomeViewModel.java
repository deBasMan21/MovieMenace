package nl.avans.moviemenace.ui.home;

import android.app.Application;
import android.os.AsyncTask;
import android.provider.ContactsContract;
import android.view.View;
import android.widget.ProgressBar;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import java.util.ArrayList;
import java.util.List;

import nl.avans.moviemenace.dataLayer.factory.DAOFactory;
import nl.avans.moviemenace.dataLayer.factory.SQLDAOFactory;
import nl.avans.moviemenace.domain.Movie;
import nl.avans.moviemenace.logic.MovieEntityManager;
import nl.avans.moviemenace.logic.MovieManager;
import nl.avans.moviemenace.ui.MainActivity;

public class HomeViewModel extends ViewModel {

    private MutableLiveData<List<Movie>> movies;
    private DAOFactory factory= new SQLDAOFactory();
    private MovieManager movieManager = new MovieManager(factory);

    public LiveData<List<Movie>> getMovies(ProgressBar loadingIndicator) {
        loadingIndicator.setVisibility(View.VISIBLE);
        if (movies == null) {
            movies = new MutableLiveData<List<Movie>>();
            new DatabaseTask().execute();
        }
        return movies;
    }

    public class DatabaseTask extends AsyncTask<Void, Void, List<Movie>> {

        @Override
        protected List<Movie> doInBackground(Void... voids) {
            List<Movie> test = new ArrayList<>();
            MovieEntityManager mem = MainActivity.mem;
            test = movieManager.getTop10Movies(mem);
            return test;
        }


        @Override
        protected void onPostExecute(List<Movie> movieList) {
            super.onPostExecute(movieList);
            movies.setValue(movieList);
        }
    }
}