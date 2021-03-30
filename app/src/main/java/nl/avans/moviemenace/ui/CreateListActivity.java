package nl.avans.moviemenace.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import nl.avans.moviemenace.R;

public class CreateListActivity extends AppCompatActivity {
    private Button mConfBn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_list);

        mConfBn = findViewById(R.id.bn_create_list_conf);
        mConfBn.setOnClickListener((View v) -> {
            startActivity(new Intent(v.getContext(), FilmDetailActivity.class));
        });
    }
}