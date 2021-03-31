package nl.avans.moviemenace.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

import nl.avans.moviemenace.R;

public class PurchaseTicketActivity extends AppCompatActivity {
    Spinner mDateSr;
    Spinner mTimesSr;
    Spinner mSeatsSr;

    Button mConfBn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_purchase_ticket);

        String[] dummyDates = new String[] {
                "Maandag 1 januari", "Dinsdag 2 februari"
        };
        String[] dummyTimes = new String[] {
                "14:00 - 15:30", "16:00 - 17:30"
        };

        Integer[] dummySeats = new Integer[50];
        for (int i = 0; i < dummySeats.length; i++) {
            dummySeats[i] = i + 1;
        }

        mDateSr = findViewById(R.id.sr_purchase_ticket_dates);
        ArrayAdapter<String> datesAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, dummyDates);
        datesAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mDateSr.setAdapter(datesAdapter);

        mTimesSr = findViewById(R.id.sr_purchase_ticket_times);
        ArrayAdapter<String> timesAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, dummyTimes);
        timesAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mTimesSr.setAdapter(timesAdapter);

        mSeatsSr = findViewById(R.id.sr_purchase_ticket_seats);
        ArrayAdapter<Integer> seatsAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, dummySeats);
        seatsAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mSeatsSr.setAdapter(seatsAdapter);

        mConfBn = findViewById(R.id.bn_purchase_ticket_conf);
        mConfBn.setOnClickListener((View v) -> {
            startActivity(new Intent(v.getContext(), MainActivity.class).putExtra("destination", "tickets"));
        });
    }
}