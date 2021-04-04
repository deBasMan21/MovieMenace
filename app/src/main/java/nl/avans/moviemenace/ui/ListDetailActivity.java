package nl.avans.moviemenace.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.ListFragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.SearchView;
import android.widget.TextView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import nl.avans.moviemenace.R;
import nl.avans.moviemenace.domain.Account;
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
        mAddFb.setOnClickListener((View v) -> startActivity(new Intent(v.getContext(),
                ListToFilmActivity.class)));

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
    public boolean onSupportNavigateUp() {
        finish();
        return true;
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