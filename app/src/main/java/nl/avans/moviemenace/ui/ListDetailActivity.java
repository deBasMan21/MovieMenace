package nl.avans.moviemenace.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.SearchView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import nl.avans.moviemenace.R;

public class ListDetailActivity extends AppCompatActivity {
    private RecyclerView mListFilmsRv;

    private FloatingActionButton mAddFb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_detail);

        mAddFb = findViewById(R.id.fb_list_detail_add);
        mAddFb.setOnClickListener((View v) -> {
            startActivity(new Intent(v.getContext(), ListToFilmActivity.class));
        });

        mListFilmsRv = findViewById(R.id.rv_list_films);
        mListFilmsRv.setAdapter(new ListFilmAdapter());
        mListFilmsRv.setLayoutManager(new GridLayoutManager(this, 3, GridLayoutManager.VERTICAL, false));
    }

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return true;
    }

    // TODO Add a filter to custom lists when adapter gets list of movies in code.
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