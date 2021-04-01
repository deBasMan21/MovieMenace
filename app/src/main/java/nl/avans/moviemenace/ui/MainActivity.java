package nl.avans.moviemenace.ui;

import android.app.Application;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.TextView;

import com.google.android.material.navigation.NavigationView;

import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.room.Room;

import java.util.ArrayList;

import nl.avans.moviemenace.R;
import nl.avans.moviemenace.dataLayer.factory.DAOFactory;
import nl.avans.moviemenace.dataLayer.rooms.MovieDB;
import nl.avans.moviemenace.dataLayer.factory.SQLDAOFactory;
import nl.avans.moviemenace.domain.Account;
import nl.avans.moviemenace.domain.Movie;
import nl.avans.moviemenace.logic.MovieEntityManager;
import nl.avans.moviemenace.logic.MovieManager;
import nl.avans.moviemenace.logic.TicketManager;
import nl.avans.moviemenace.ui.account.AccountViewModel;

public class MainActivity extends AppCompatActivity {
    public static final String DESTINATION_KEY = "destination";

    public static DAOFactory factory = new SQLDAOFactory();

    private AppBarConfiguration mAppBarConfiguration;

    public static final String BASE_URL = "https://image.tmdb.org/t/p/w500";

    public static MovieEntityManager mem;

    private AccountViewModel accountViewModel;

    private TextView mNavName;
    private TextView mNavEmail;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mem = new MovieEntityManager(getApplication());
        Room.databaseBuilder(this, MovieDB.class, "movieDB");


        accountViewModel =
                new ViewModelProvider(this).get(AccountViewModel.class);

        accountViewModel.setAccount((Account) getIntent().getSerializableExtra(Account.ACCOUNT_KEY));

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

        TicketManager ticketManager = new TicketManager(factory);

        // navigate to fragments with given destination intent extra, navigate to home by default
        Intent srcIntent = getIntent();
        if (srcIntent.hasExtra(DESTINATION_KEY)) {
            switch (srcIntent.getStringExtra(DESTINATION_KEY)) {
                case "account":
                    navController.navigate(R.id.nav_account);
                    break;
                case "films":
                    navController.navigate(R.id.nav_films);
                    break;
                case "lists":
                    navController.navigate(R.id.nav_lists);
                    break;
                case "login":
                    navController.navigate(R.id.nav_login);
                    break;
                case "tickets":
                    navController.navigate(R.id.nav_tickets);
                    break;
                default:
                    navController.navigate(R.id.nav_home);
                    break;
            }
        }

        // chance text in header to profile name and email
        View navHeader = navigationView.getHeaderView(0);
        mNavName = navHeader.findViewById(R.id.tv_nav_header_name);
        mNavEmail = navHeader.findViewById(R.id.tv_nav_header_email);
        if (accountViewModel.getAccount() != null) {
            mNavName.setText(accountViewModel.getAccount().getName());
            mNavEmail.setText(accountViewModel.getAccount().getEmail());
        } else {
            mNavName.setText("");
            mNavEmail.setText(R.string.login_msg);
            mNavEmail.setOnClickListener((View v) -> {
                navController.navigate(R.id.nav_login);
            });
        }

        new insertMoviesIntoLocalDB().execute();

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

    public class insertMoviesIntoLocalDB extends AsyncTask<Void, Void, Void> {

        @Override
        protected Void doInBackground(Void... voids) {
            MovieManager mm = new MovieManager(factory);
            mem.insertAllMovies(mm.getAllMovies(mem));
            return null;
        }
    }


}