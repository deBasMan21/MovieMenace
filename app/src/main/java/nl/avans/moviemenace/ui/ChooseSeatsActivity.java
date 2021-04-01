package nl.avans.moviemenace.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.Spinner;

import java.util.zip.Inflater;

import nl.avans.moviemenace.R;

public class ChooseSeatsActivity extends AppCompatActivity {
    private int seatsAmount;
    private LinearLayout mTicketListLl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_seats);

        Intent intent = getIntent();
        if (intent.hasExtra("amount")) {
            seatsAmount = intent.getIntExtra("amount", 1);
        }

        mTicketListLl = findViewById(R.id.ll_choose_seats_ticket_list);
        for (int i = 0; i < seatsAmount; i++) {
            View v = View.inflate(this, R.layout.choose_seat_list_item, null);
            Integer[] dummySeats = {1, 2, 3, 4, 7, 8, 10, 11, 12, 23, 24, 25, 26, 27, 34, 35, 36};

            Spinner mSeatsSr = v.findViewById(R.id.spinner);
            ArrayAdapter<Integer> seatsAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, dummySeats);
            seatsAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            mSeatsSr.setAdapter(seatsAdapter);

            mTicketListLl.addView(v);
        }
    }
}