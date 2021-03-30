package nl.avans.moviemenace.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;

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

        });

        mListFilmsRv = findViewById(R.id.rv_list_films);
        mListFilmsRv.setAdapter(new ListFilmAdapter());
        mListFilmsRv.setLayoutManager(new GridLayoutManager(this, 3, GridLayoutManager.VERTICAL, false));
    }
}