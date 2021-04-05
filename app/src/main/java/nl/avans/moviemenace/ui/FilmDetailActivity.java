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
import java.util.Locale;

import nl.avans.moviemenace.R;
import nl.avans.moviemenace.dataLayer.factory.DAOFactory;
import nl.avans.moviemenace.dataLayer.factory.SQLDAOFactory;
import nl.avans.moviemenace.domain.Movie;
import nl.avans.moviemenace.domain.Viewing;
import nl.avans.moviemenace.logic.TicketManager;

public class FilmDetailActivity extends AppCompatActivity {
    private Movie movie;
    private Button mFilmToListBn;
    private ImageView mPoster;
    private TextView mDuration;
    private TextView mDescription;
    private TextView mAge;
    private TextView mTitle;
    public static final String MOVIE_KEY = "MovieKey";
    private Button mPurchaseTicketBn;
    private ViewingViewModel viewingViewModel;
    private List<Viewing> viewingList;
    private DAOFactory daoFactory = new SQLDAOFactory();
    private TicketManager ticketManager = new TicketManager(daoFactory);

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_film_detail);


        Intent intent = getIntent();
        if (intent.hasExtra(MOVIE_KEY)) {
            movie = (Movie) intent.getSerializableExtra(MOVIE_KEY);
        }

        final String MOVIE_ID = movie.getId() + "";
        //preload tickets
        ticketManager.loadAllTickets();

        mDuration = findViewById(R.id.tv_film_detail_duration_value);
        mDescription = findViewById(R.id.tv_film_detail_desc);
        mTitle = findViewById(R.id.tv_detail_title);
        mPoster = findViewById(R.id.iv_film_detail_poster);
        mAge = findViewById(R.id.tv_film_detail_agerating_value);
        if(Locale.getDefault().equals(Locale.US)){
            mDuration.setText(movie.getDuration()+ "");
            mDescription.setText(movie.getOverview());
            mTitle.setText(movie.getTitle());
            Picasso.get().load(MainActivity.BASE_URL + movie.getUrl()).into(mPoster);
        } else {
            mDuration.setText(movie.getDuration()+ "");
            mDescription.setText(movie.getTranslations().get("Dutch").getDescription());
            mTitle.setText(movie.getTranslations().get("Dutch").getTitle());
            Picasso.get().load(MainActivity.BASE_URL + movie.getTranslations().get("Dutch").getUrl()).into(mPoster);
        }

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
            purchaseTicketIntent.putExtra(TicketManager.TICKETMANAGER_KEY, ticketManager);
            startActivity(purchaseTicketIntent);
        });
    }

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return true;
    }
}