package nl.avans.moviemenace.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import nl.avans.moviemenace.R;

public class ListToFilmActivity extends AppCompatActivity {
    private RecyclerView mListToFilmsRv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_to_film);

        mListToFilmsRv = findViewById(R.id.rv_list_to_films);
        mListToFilmsRv.setAdapter(new ListToFilmsAdapter());
        mListToFilmsRv.setLayoutManager(new GridLayoutManager(this, 3, GridLayoutManager.VERTICAL, false));
    }
}