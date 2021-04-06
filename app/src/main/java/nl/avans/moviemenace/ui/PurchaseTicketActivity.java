package nl.avans.moviemenace.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

import java.lang.reflect.Array;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ExecutionException;

import nl.avans.moviemenace.R;
import nl.avans.moviemenace.dataLayer.factory.DAOFactory;
import nl.avans.moviemenace.dataLayer.factory.SQLDAOFactory;
import nl.avans.moviemenace.domain.Account;
import nl.avans.moviemenace.domain.Movie;
import nl.avans.moviemenace.domain.Viewing;
import nl.avans.moviemenace.logic.TicketManager;
import nl.avans.moviemenace.ui.home.HomeViewModel;

public class PurchaseTicketActivity extends AppCompatActivity {
    Spinner mDateSr;
    Spinner mTimesSr;
    Spinner mSeatsSr;

    Button mConfBn;
    private Movie movie;
    private int movieId;
    private ViewingViewModel viewingViewModel;
    private List<Viewing> viewingList;
    private TicketManager ticketManager;
    private Context context;
    private LocalDate selectedDate;
    private LocalTime selectedTime;
    private int selectedSeats;
    private Viewing selectedViewing;
    private Account account;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_purchase_ticket);
        this.context = this;
        this.account = MainActivity.account;
        if (account == null) {
            startActivity(new Intent(this, MainActivity.class).putExtra(MainActivity.DESTINATION_KEY, "login"));
        }

        Intent intent = getIntent();
        if (intent.hasExtra(FilmDetailActivity.MOVIE_KEY)) {
            movie = (Movie) intent.getSerializableExtra(FilmDetailActivity.MOVIE_KEY);
            this.movieId = movie.getId();
        }
        if (intent.hasExtra("test")) {
            viewingList  = (List<Viewing>) intent.getSerializableExtra("test");
        }
        if (intent.hasExtra(TicketManager.TICKETMANAGER_KEY)) {
            ticketManager = (TicketManager) intent.getSerializableExtra(TicketManager.TICKETMANAGER_KEY);
        }

        //sorted list to prevent double dates with multiple viewing times
        List<Viewing> sortedViewingList = new ArrayList<>();
        for (Viewing unsortedViewing: viewingList) {
            LocalDate unsortedDate = unsortedViewing.getDate().toLocalDate();
            boolean exists = false;
            for (Viewing sortedViewing: sortedViewingList) {
                LocalDate sortedDate = sortedViewing.getDate().toLocalDate();
                if (unsortedDate.compareTo(sortedDate) == 0) {
                    exists = true;
                    break;
                }
            }
            if (!exists) {
                sortedViewingList.add(unsortedViewing);
            }
        }

        //make date array for showing in spinner
        String[] dates = new String[sortedViewingList.size()];
        for (int i = 0; i < sortedViewingList.size(); i ++) {
            Viewing x = sortedViewingList.get(i);
            LocalDateTime dateTime = x.getDate();
            LocalDate date = dateTime.toLocalDate();
            String dateString = date.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
            dates[i] = dateString;
        }



        //Datespinner
        mDateSr = findViewById(R.id.sr_purchase_ticket_dates);
        ArrayAdapter<String> datesAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, dates);
        datesAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mDateSr.setAdapter(datesAdapter);

        //Listener on date selection
        mDateSr.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            //the selected index of the spinner == the index of the sorted list and can be different from the original viewingList
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                //Revert selected date (from sortingList) into a viewing, must be retrieved from the original list
                int selectedIndex = mDateSr.getSelectedItemPosition();

                for (Viewing k: viewingList) {
                    if (sortedViewingList.get(selectedIndex) == k) {
                        selectedViewing = k;
                    }
                }
                // Change/save selectedDate each time it changes, for passing the right date to create a ticket
                selectedDate = selectedViewing.getDate().toLocalDate();

                //Calculate how many seats there are available
                int availableSeats = ticketManager.checkAvailableSeats(selectedViewing);

                //Generate amount of spinner items for selecting seats
                Integer[] seats = new Integer[availableSeats];
                for (int i = 0; i < seats.length; i++) {
                    seats[i] = i + 1;
                }
                //Create seats spinner
                mSeatsSr = findViewById(R.id.sr_purchase_ticket_seats);
                ArrayAdapter<Integer> seatsAdapter = new ArrayAdapter<>(context, android.R.layout.simple_spinner_dropdown_item, seats);
                seatsAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                mSeatsSr.setAdapter(seatsAdapter);
                mSeatsSr.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                        selectedSeats = seats[mSeatsSr.getSelectedItemPosition()];
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {

                    }
                });

                //Check if the selected viewing has multiple times
                ArrayList<String> times = new ArrayList<>();
                LocalDate selectedDate = selectedViewing.getDate().toLocalDate();
                for (Viewing x: viewingList) {
                    if (x.getDate().toLocalDate().compareTo(selectedDate) == 0) {
                        LocalTime time = x.getDate().toLocalTime();
                        String timeString = time.format(DateTimeFormatter.ofPattern("HH:mm"));
                        times.add(timeString);
                    }
                }

                // Create times spinner
                mTimesSr = findViewById(R.id.sr_purchase_ticket_times);
                ArrayAdapter<String> timesAdapter = new ArrayAdapter<>(context, android.R.layout.simple_spinner_dropdown_item, times);
                timesAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                mTimesSr.setAdapter(timesAdapter);

                //Listener on time selection
                mTimesSr.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    // Change/save selectedTime each time it changes, for passing the right date to create a ticket
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                        selectedTime = LocalTime.parse(times.get(mTimesSr.getSelectedItemPosition()));
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {

                    }
                });
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });







        mConfBn = findViewById(R.id.bn_purchase_ticket_conf);
        mConfBn.setOnClickListener((View v) -> {
            Intent newIntent = new Intent(v.getContext(), ChooseSeatsActivity.class);
            newIntent.putExtra(ChooseSeatsActivity.SEATS_AMOUNT_KEY, selectedSeats);
            newIntent.putExtra(Account.ACCOUNT_KEY, account);
            newIntent.putExtra(Viewing.VIEWING_KEY, selectedViewing);
            newIntent.putExtra(TicketManager.TICKETMANAGER_KEY, ticketManager);
            startActivity(newIntent);
        });
    }

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return true;
    }

}