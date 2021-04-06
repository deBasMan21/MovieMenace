package nl.avans.moviemenace.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;

import java.util.ArrayList;
import java.util.List;

import nl.avans.moviemenace.R;
import nl.avans.moviemenace.dataLayer.DatabaseConnection;
import nl.avans.moviemenace.dataLayer.factory.DAOFactory;
import nl.avans.moviemenace.dataLayer.factory.SQLDAOFactory;
import nl.avans.moviemenace.domain.Account;
import nl.avans.moviemenace.domain.Movie;
import nl.avans.moviemenace.domain.MovieList;
import nl.avans.moviemenace.logic.MovieListManager;

public class FilmToListActivity extends AppCompatActivity {
    private DAOFactory factory = new SQLDAOFactory();
    private MovieListManager movieListManager = new MovieListManager(factory);

    private RecyclerView mListsRv;
    private FilmToListAdapter mFilmToListAdapter;

    private List<MovieList> movieLists = new ArrayList<>();
    private Movie movie;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_film_to_list);

        Intent originalIntent = getIntent();

        if (originalIntent.getSerializableExtra(FilmDetailActivity.MOVIE_KEY) != null) {
            movie = (Movie) originalIntent.getSerializableExtra(FilmDetailActivity.MOVIE_KEY);
        }

        mListsRv = findViewById(R.id.rv_film_to_lists);
        mFilmToListAdapter = new FilmToListAdapter(movieLists, movie);
        mListsRv.setAdapter(mFilmToListAdapter);
        mListsRv.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));

        new DatabaseTask().execute();
    }

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return true;
    }

    public void setMovieLists(List<MovieList> movieLists) {
        this.movieLists.addAll(movieLists);
        mFilmToListAdapter.notifyDataSetChanged();
    }

    public class DatabaseTask extends AsyncTask<Void, Void, List<MovieList>> {

        @Override
        protected List<MovieList> doInBackground(Void... voids) {
            DatabaseConnection db = new DatabaseConnection();
            if (!db.connectionIsOpen()) {
                db.openConnection();
            }
            Account account = MainActivity.account;
            List<MovieList> movieLists;
            List<MovieList> returnMovieLists = new ArrayList<>();
            if (account == null) {
                movieLists = movieListManager.getMovieListsForAccount("");
            } else {
                movieLists = movieListManager.getMovieListsForAccount(account.getEmail());
            }
            returnMovieLists.addAll(movieLists);
            for (MovieList movieList : movieLists) {
                if (movieListManager.listHasMovie(movieList.getId(), movie.getId())) {
                    returnMovieLists.remove(movieList);
                }
            }
            db.closeConnection();
            return returnMovieLists;
        }

        @Override
        protected void onPostExecute(List<MovieList> movieLists) {
            super.onPostExecute(movieLists);
            setMovieLists(movieLists);
        }
    }
}