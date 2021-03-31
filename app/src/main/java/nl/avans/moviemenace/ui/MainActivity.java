package nl.avans.moviemenace.ui;

import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.view.Menu;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.navigation.NavigationView;

import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.room.Room;

import java.time.LocalDate;
import java.util.ArrayList;

import nl.avans.moviemenace.R;
import nl.avans.moviemenace.dataLayer.DAOFactory;
import nl.avans.moviemenace.dataLayer.DatabaseConnection;
import nl.avans.moviemenace.dataLayer.IDAO.MovieDAO;
import nl.avans.moviemenace.dataLayer.Rooms.MovieDB;
import nl.avans.moviemenace.dataLayer.Rooms.RoomDAO.RoomMovieDAO;
import nl.avans.moviemenace.dataLayer.SQLDAOFactory;
import nl.avans.moviemenace.domain.Account;
import nl.avans.moviemenace.domain.Cinema;
import nl.avans.moviemenace.domain.Movie;
import nl.avans.moviemenace.logic.MovieManager;
import nl.avans.moviemenace.logic.TicketManager;

public class MainActivity extends AppCompatActivity {
    private final boolean LOGINTEST = true;



    private AppBarConfiguration mAppBarConfiguration;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Room.databaseBuilder(this, MovieDB.class, "movieDB");

        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_home, R.id.nav_films, R.id.nav_account, R.id.nav_lists, R.id.nav_tickets, R.id.nav_login)
                .setDrawerLayout(drawer)
                .build();

        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);

        MovieManager movieManager = new MovieManager();
        movieManager.loadTrendingMoviesPerWeek();

        DAOFactory factory = new SQLDAOFactory();
        TicketManager ticketManager = new TicketManager(factory);

        new dbtest().execute();

        MovieDB movieDB = MovieDB.getDatabase(getApplication());
        RoomMovieDAO movieDAO = movieDB.getMovieDAO();
        movieDAO.getAllMovies();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }

    public class dbtest extends AsyncTask<Void, Void, Void> {

        @Override
        protected Void doInBackground(Void... voids) {

            return null;
        }
    }

}