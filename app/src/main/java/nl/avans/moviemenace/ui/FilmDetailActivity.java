package nl.avans.moviemenace.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import nl.avans.moviemenace.R;

public class FilmDetailActivity extends AppCompatActivity {
    private Button mFilmToListBn;
    private Button mPurchaseTicketBn;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_film_detail);

        mFilmToListBn = findViewById(R.id.bn_film_detail_list);
        mFilmToListBn.setOnClickListener((View v) -> {
            startActivity(new Intent(v.getContext(), FilmToListActivity.class));
        });

        mPurchaseTicketBn = findViewById(R.id.bn_film_detail_ticket);
        mPurchaseTicketBn.setOnClickListener((View v) -> {
            startActivity(new Intent(v.getContext(), PurchaseTicketActivity.class));
        });

    }
}