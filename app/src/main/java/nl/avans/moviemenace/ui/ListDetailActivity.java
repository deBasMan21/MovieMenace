package nl.avans.moviemenace.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import nl.avans.moviemenace.R;

public class ListDetailActivity extends AppCompatActivity {
    private RecyclerView mListFilmsRv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_detail);

        mListFilmsRv = findViewById(R.id.rv_list_films);
        mListFilmsRv.setAdapter(new ListFilmAdapter());
        mListFilmsRv.setLayoutManager(new GridLayoutManager(this, 3, GridLayoutManager.VERTICAL, false));
    }
}