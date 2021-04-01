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

import nl.avans.moviemenace.R;
import nl.avans.moviemenace.dataLayer.factory.DAOFactory;
import nl.avans.moviemenace.dataLayer.factory.SQLDAOFactory;
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
    private DAOFactory factory = new SQLDAOFactory();
    private TicketManager ticketManager = new TicketManager(factory);
    private Context context;
    private LocalDate selectedDate;
    private LocalTime selectedTime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_purchase_ticket);
        this.context = this;

        Intent intent = getIntent();
        if (intent.hasExtra(FilmDetailActivity.MOVIE_KEY)) {
            movie = (Movie) intent.getSerializableExtra(FilmDetailActivity.MOVIE_KEY);
            this.movieId = movie.getId();
        }
        if (intent.hasExtra("test")) {
            viewingList  = (List<Viewing>) intent.getSerializableExtra("test");
        }

        //sorted list to prevent double dates with multiple viewing times
        List<Viewing> sortedList = new ArrayList<>();
        for (Viewing viewing: viewingList) {
            LocalDate unsortedDate = viewing.getDate().toLocalDate();
            boolean exists = false;
            for (Viewing sortedViewing: sortedList) {
                LocalDate sortedDate = sortedViewing.getDate().toLocalDate();
                if (unsortedDate.compareTo(sortedDate) == 0) {
                    exists = true;
                    break;
                }
            }
            if (!exists) {
                sortedList.add(viewing);
            }
        }

        //make date array
        String[] dates = new String[sortedList.size()];
        for (int i = 0; i < sortedList.size(); i ++) {
            Viewing x = sortedList.get(i);
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
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Viewing selectedViewing = null;

                for (Viewing k: viewingList) {
                    if (sortedList.get(mDateSr.getSelectedItemPosition()) == k) {
                        selectedViewing = k;
                    }
                }
                selectedDate = selectedViewing.getDate().toLocalDate();
                //Calculate how many seats there are available
                int availableSeats = ticketManager.checkAvailableSeats(selectedViewing);
                Integer[] seats = new Integer[availableSeats];
                for (int i = 0; i < seats.length; i++) {
                    seats[i] = i + 1;
                }
                mSeatsSr = findViewById(R.id.sr_purchase_ticket_seats);
                ArrayAdapter<Integer> seatsAdapter = new ArrayAdapter<>(context, android.R.layout.simple_spinner_dropdown_item, seats);
                seatsAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                mSeatsSr.setAdapter(seatsAdapter);

                //Check different times
                ArrayList<String> times = new ArrayList<>();
                LocalDate selectedDate = sortedList.get(mDateSr.getSelectedItemPosition()).getDate().toLocalDate();
                for (int i = 0; i < viewingList.size(); i ++) {
                    Viewing x = viewingList.get(i);
                    if (x.getDate().toLocalDate().compareTo(selectedDate) == 0) {
                        LocalTime time = x.getDate().toLocalTime();
                        String timeString = time.format(DateTimeFormatter.ofPattern("HH:mm"));
                        times.add(timeString);
                    }
                }
                mTimesSr = findViewById(R.id.sr_purchase_ticket_times);
                ArrayAdapter<String> timesAdapter = new ArrayAdapter<>(context, android.R.layout.simple_spinner_dropdown_item, times);
                timesAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                mTimesSr.setAdapter(timesAdapter);

                //Listener on time selection
                mTimesSr.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
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
            startActivity(new Intent(v.getContext(), MainActivity.class).putExtra("destination", "tickets"));
        });
    }

}