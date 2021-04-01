package nl.avans.moviemenace.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
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

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import nl.avans.moviemenace.R;
import nl.avans.moviemenace.domain.Movie;
import nl.avans.moviemenace.domain.Viewing;

public class FilmDetailActivity extends AppCompatActivity {
    private Movie movie;
    private Button mFilmToListBn;
    private ImageView mPoster;
    private TextView mDuration;
    private TextView mDescription;
    private TextView mAge;
    public static final String MOVIE_KEY = "MovieKey";
    private Button mPurchaseTicketBn;
    private ViewingViewModel viewingViewModel;
    private List<Viewing> viewingList;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_film_detail);

        Intent intent = getIntent();
        if (intent.hasExtra(MOVIE_KEY)) {
            movie = (Movie) intent.getSerializableExtra(MOVIE_KEY);
        }

        final String MOVIE_ID = movie.getId() + "";

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

        viewingViewModel = new ViewModelProvider(this).get(ViewingViewModel.class);
        viewingViewModel.setMovieId(movie.getId());

        viewingViewModel.getViewingList().observe(this, new Observer<List<Viewing>>() {
            @Override
            public void onChanged(List<Viewing> viewings) {
                viewingList = viewings;
            }
        });


        mFilmToListBn = findViewById(R.id.bn_film_detail_list);

        mFilmToListBn.setOnClickListener((View v) -> {
            startActivity(new Intent(v.getContext(), FilmToListActivity.class));
        });

        mPurchaseTicketBn = findViewById(R.id.bn_film_detail_ticket);
        mPurchaseTicketBn.setOnClickListener((View v) -> {
            Intent purchaseTicketIntent = new Intent(v.getContext(), PurchaseTicketActivity.class);
            purchaseTicketIntent.putExtra(MOVIE_KEY, movie);
            purchaseTicketIntent.putExtra("test", (Serializable) viewingList);
            startActivity(purchaseTicketIntent);
        });

    }
}