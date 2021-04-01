package nl.avans.moviemenace.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import nl.avans.moviemenace.R;

public class FilmToListActivity extends AppCompatActivity {
    private RecyclerView mListsRv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_film_to_list);

        mListsRv = findViewById(R.id.rv_film_to_lists);
        mListsRv.setAdapter(new FilmToListAdapter());
        mListsRv.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
    }

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return true;
    }
}