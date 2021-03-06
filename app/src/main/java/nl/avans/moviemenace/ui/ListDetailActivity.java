package nl.avans.moviemenace.ui;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.ListFragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.SearchView;
import android.widget.TextView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

import nl.avans.moviemenace.R;
import nl.avans.moviemenace.dataLayer.factory.DAOFactory;
import nl.avans.moviemenace.dataLayer.factory.SQLDAOFactory;
import nl.avans.moviemenace.domain.Account;
import nl.avans.moviemenace.domain.Movie;
import nl.avans.moviemenace.domain.MovieList;
import nl.avans.moviemenace.logic.MovieListManager;
import nl.avans.moviemenace.ui.account.AccountFragment;
import nl.avans.moviemenace.ui.lists.ListsFragment;

public class ListDetailActivity extends AppCompatActivity {
    DAOFactory daoFactory = new SQLDAOFactory();
    MovieListManager movieListManager = new MovieListManager(daoFactory);

    private TextView mListTitle;
    private TextView mListDesc;
    private RecyclerView mListFilmsRv;
    private ListFilmAdapter mListFilmAdapter;
    private FloatingActionButton mAddFb;
    private ProgressBar mProgressBar;

    private MovieList movieList;
    private List<Movie> movies = new ArrayList<>();
    private Account account;

    private TextView mListMsgTv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_detail);

        mListTitle = findViewById(R.id.tv_list_detail_title);
        mListDesc = findViewById(R.id.tv_list_detail_desc);
        mProgressBar = findViewById(R.id.pb_list_detail);

        mListMsgTv = findViewById(R.id.tv_list_msg);

        mAddFb = findViewById(R.id.fb_list_detail_add);
        mAddFb.setOnClickListener((View v) -> {
            Intent intent = new Intent(this, ListToFilmActivity.class);
            intent.putExtra(ListsFragment.LIST_KEY, movieList);
            intent.putExtra(Account.ACCOUNT_KEY, account);
            startActivityForResult(intent, 1);
        });

        Intent intent = getIntent();
        if (intent.getSerializableExtra(ListsFragment.LIST_KEY) != null) {
            movieList = (MovieList) intent.getSerializableExtra(ListsFragment.LIST_KEY);
            mListTitle.setText(movieList.getName());
            mListDesc.setText(movieList.getDescription());
        }
        if (intent.getSerializableExtra(Account.ACCOUNT_KEY) != null) {
            account = (Account) intent.getSerializableExtra(Account.ACCOUNT_KEY);
        }
        mListFilmsRv = findViewById(R.id.rv_list_films);
        mListFilmAdapter = new ListFilmAdapter(movies, account, movieList);
        mListFilmsRv.setAdapter(mListFilmAdapter);
        mListFilmsRv.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));

        new DatabaseTask().execute();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1) {
            if (resultCode == RESULT_OK) {
                MovieList newMovieList = (MovieList) data.getSerializableExtra(ListsFragment.LIST_KEY);
                movieList.setMovies(newMovieList.getMovies());
            }
        }
    }

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return true;
    }

    @Override
    protected void onPostResume() {
        super.onPostResume();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        MenuItem settings = menu.findItem(R.id.action_settings);
        settings.setVisible(false);

        MenuItem searchItem = menu.findItem(R.id.action_search);
        SearchView searchView = (SearchView) searchItem.getActionView();
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                mListFilmAdapter.getFilter().filter(newText);
                return false;
            }
        });

        searchItem.setVisible(true);
        return true;
    }

    private void setMovies(List<Movie> movies) {
        this.movieList.setMovies(movies);
        this.movies.addAll(movieList.getMovies());
        mListFilmAdapter.setMoviesFull(movies);
        mListFilmAdapter.notifyDataSetChanged();
        mProgressBar.setVisibility(View.INVISIBLE);
        if (movies.size() == 0) {
            mListMsgTv.setVisibility(View.VISIBLE);
        } else {
            mListMsgTv.setVisibility(View.INVISIBLE);
        }
    }

    public class DatabaseTask extends AsyncTask<Void, Void, List<Movie>> {

        @Override
        protected List<Movie> doInBackground(Void... voids) {

            List<Movie> movies;
            movies = movieListManager.getMoviesForList(movieList.getId());
            for (Movie movie : movies) {
                movie.setTranslations(movieListManager.getTranslationsForMovie(movie.getId()));
            }
            return movies;
        }

        @Override
        protected void onPostExecute(List<Movie> movies) {
            super.onPostExecute(movies);
            setMovies(movies);
        }
    }
}