package nl.avans.moviemenace.ui;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.ListFragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.SearchView;
import android.widget.TextView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

import nl.avans.moviemenace.R;
import nl.avans.moviemenace.domain.Account;
import nl.avans.moviemenace.domain.Movie;
import nl.avans.moviemenace.domain.MovieList;
import nl.avans.moviemenace.ui.account.AccountFragment;
import nl.avans.moviemenace.ui.lists.ListsFragment;

public class ListDetailActivity extends AppCompatActivity {
    private TextView mListTitle;
    private TextView mListDesc;
    private RecyclerView mListFilmsRv;
    private ListFilmAdapter mListFilmAdapter;
    private FloatingActionButton mAddFb;

    private MovieList movieList;
    private Account account;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_detail);

        mListTitle = findViewById(R.id.tv_list_detail_title);
        mListDesc = findViewById(R.id.tv_list_detail_desc);

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
        mListFilmAdapter = new ListFilmAdapter(movieList.getMovies(), account);
        mListFilmsRv.setAdapter(mListFilmAdapter);
        mListFilmsRv.setLayoutManager(new GridLayoutManager(this, 3, GridLayoutManager.VERTICAL,
                false));
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

    // TODO Add a filter to custom lists when adapter gets list of movies in code. - Stefan
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);

        MenuItem searchItem = menu.findItem(R.id.action_search);
        SearchView searchView = (SearchView) searchItem.getActionView();

        searchItem.setVisible(true);
        return true;
    }
}