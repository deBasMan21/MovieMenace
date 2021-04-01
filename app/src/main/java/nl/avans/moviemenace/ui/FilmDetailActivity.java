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
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.material.navigation.NavigationView;
import com.squareup.picasso.Picasso;

import nl.avans.moviemenace.R;
import nl.avans.moviemenace.domain.Account;
import nl.avans.moviemenace.domain.Movie;

public class FilmDetailActivity extends AppCompatActivity {
    private Movie movie;
    private Button mFilmToListBn;
    private ImageView mPoster;
    private TextView mDuration;
    private TextView mDescription;
    private TextView mAge;
    public static String MOVIE_KEY = "MovieKey";
    private Button mPurchaseTicketBn;
    private Account account;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_film_detail);

        Intent intent = getIntent();
        if (intent.hasExtra(MOVIE_KEY)) {
            movie = (Movie) intent.getSerializableExtra(MOVIE_KEY);
        }
        if (intent.hasExtra("account_temp")) {
            account = (Account) intent.getSerializableExtra("account_temp");
        }

        mDuration = findViewById(R.id.tv_film_detail_duration_value);
        mDuration.setText(movie.getDuration()+ "");
        mDescription = findViewById(R.id.tv_film_detail_desc);
        mDescription.setText(movie.getOverview());
        mPoster = findViewById(R.id.iv_film_detail_poster);
        Picasso.get().load(MainActivity.BASE_URL + movie.getUrl()).into(mPoster);
        mAge = findViewById(R.id.tv_film_detail_agerating_value);
        if (movie.isAdult()) {
            mAge.setText("Adult");
        } else {
            mAge.setText("Children");
        }


        mFilmToListBn = findViewById(R.id.bn_film_detail_list);

        mFilmToListBn.setOnClickListener((View v) -> {
            startActivity(new Intent(v.getContext(), FilmToListActivity.class));
        });

        mPurchaseTicketBn = findViewById(R.id.bn_film_detail_ticket);
        mPurchaseTicketBn.setOnClickListener((View v) -> {
            if (account != null) {
                startActivity(new Intent(v.getContext(), PurchaseTicketActivity.class).putExtra("account_temp", account));
            }
        });

    }

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return true;
    }
}