package nl.avans.moviemenace.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;

import java.util.ArrayList;
import java.util.List;

import nl.avans.moviemenace.R;
import nl.avans.moviemenace.dataLayer.factory.DAOFactory;
import nl.avans.moviemenace.dataLayer.factory.SQLDAOFactory;
import nl.avans.moviemenace.domain.Account;
import nl.avans.moviemenace.domain.Movie;
import nl.avans.moviemenace.domain.MovieList;
import nl.avans.moviemenace.logic.MovieEntityManager;
import nl.avans.moviemenace.logic.MovieManager;
import nl.avans.moviemenace.ui.lists.ListsFragment;

public class ListToFilmActivity extends AppCompatActivity {
    private RecyclerView mListToFilmsRv;
    private ListToFilmsAdapter mListToFilmsAdapter;

    private DAOFactory daoFactory = new SQLDAOFactory();
    private MovieManager movieManager = new MovieManager(daoFactory);
    private List<Movie> movies = new ArrayList<>();
    private MovieList movieList;
    private Account account;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_to_film);

        Intent intent = getIntent();
        if (intent.getSerializableExtra(ListsFragment.LIST_KEY) != null) {
            movieList = (MovieList) intent.getSerializableExtra(ListsFragment.LIST_KEY);
        }
        if (intent.getSerializableExtra(Account.ACCOUNT_KEY) != null) {
            account = (Account) intent.getSerializableExtra(Account.ACCOUNT_KEY);
        }

        mListToFilmsRv = findViewById(R.id.rv_list_to_films);
        mListToFilmsAdapter = new ListToFilmsAdapter(movies, movieList, account);
        mListToFilmsRv.setAdapter(mListToFilmsAdapter);
        mListToFilmsRv.setLayoutManager(new GridLayoutManager(this, 3, GridLayoutManager.VERTICAL, false));

        new DatabaseTask().execute();
    }

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return true;
    }

    public void setMovies(List<Movie> movies) {
        this.movies.clear();
        List<Movie> moviesInList = movieList.getMovies();
        for (Movie movieInList : moviesInList) {
            for (int i = 0; i < movies.size(); i++) {
                if (movieInList.getId() == movies.get(i).getId()) {
                    movies.remove(i);
                }
            }
        }
        this.movies.addAll(movies);
        mListToFilmsAdapter.notifyDataSetChanged();
    }

    public class DatabaseTask extends AsyncTask<Void, Void, List<Movie>> {

        @Override
        protected List<Movie> doInBackground(Void... voids) {
            List<Movie> movieList;
            MovieEntityManager mem = MainActivity.mem;
            movieList = movieManager.getAllMovies(mem);
            return movieList;
        }

        @Override
        protected void onPostExecute(List<Movie> movies) {
            super.onPostExecute(movies);
            setMovies(movies);
        }
    }
}