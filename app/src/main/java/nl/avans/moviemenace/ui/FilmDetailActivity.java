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

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
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
import nl.avans.moviemenace.logic.LanguageHelper;
import nl.avans.moviemenace.logic.TicketManager;
import nl.avans.moviemenace.ui.login.LoginFragment;

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

        if (LanguageHelper.isLanguage("nl_NL") && movie.getTranslations() != null) {
            mDuration.setText(movie.getDuration() + "");
            mDescription.setText(movie.getTranslations().get("Dutch").getDescription());
            mTitle.setText(movie.getTranslations().get("Dutch").getTitle());

            if (movie.getTitle().equals("Avans Endgame")) {
                Picasso.get().load("https://i.ibb.co/qNKQXP1/Microsoft-Teams-image.jpg").into(mPoster);
            } else {
                Picasso.get().load(MainActivity.BASE_URL + movie.getTranslations().get("Dutch").getUrl()).into(mPoster);
            }
        } else {
            mDuration.setText(movie.getDuration() + "");
            mDescription.setText(movie.getOverview());
            mTitle.setText(movie.getTitle());
            if (movie.getTitle().equals("Avans Endgame")) {
                Picasso.get().load("https://i.ibb.co/qNKQXP1/Microsoft-Teams-image.jpg").into(mPoster);
            } else {
                Picasso.get().load(MainActivity.BASE_URL + movie.getUrl()).into(mPoster);
            }
        }

        if (movie.isAdult()) {
            mAge.setText(R.string.adult);
        } else {
            mAge.setText(R.string.children);
        }

        mPoster.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                Dialog builder = new Dialog(FilmDetailActivity.this);
                ImageView imageView = new ImageView(FilmDetailActivity.this);
                if (movie.getTitle().equals("Avans Endgame")) {
                    Picasso.get().load("https://i.ibb.co/qNKQXP1/Microsoft-Teams-image.jpg").resize(900, 1200).into(imageView);
                } else {
                    Picasso.get().load(MainActivity.BASE_URL + movie.getTranslations().get("Dutch").getUrl()).resize(900, 1200).into(imageView);
                }
                builder.addContentView(imageView, new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
                builder.show();
                return false;
            }
        });

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
            if (MainActivity.account != null) {
                startActivity(new Intent(v.getContext(), FilmToListActivity.class)
                .putExtra(MOVIE_KEY, movie));
            } else {
                startActivity(new Intent(v.getContext(), MainActivity.class)
                        .putExtra(MainActivity.DESTINATION_KEY, "login"));
            }
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