package nl.avans.moviemenace.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.NumberPicker;
import android.widget.Spinner;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;
import java.util.zip.Inflater;

import nl.avans.moviemenace.R;
import nl.avans.moviemenace.dataLayer.factory.DAOFactory;
import nl.avans.moviemenace.dataLayer.factory.SQLDAOFactory;
import nl.avans.moviemenace.domain.Account;
import nl.avans.moviemenace.domain.Ticket;
import nl.avans.moviemenace.domain.Viewing;
import nl.avans.moviemenace.logic.TicketManager;

public class ChooseSeatsActivity extends AppCompatActivity {
    public static final String SEATS_AMOUNT_KEY = "SeatsAmountKey";
    private int seatsAmount;
    private Account account;
    private LinearLayout mTicketListLl;
    private TextView totalPrice;
    private TextView hall;
    private Button mConfBn;
    private Viewing viewing;
    private List<View> viewList= new ArrayList<>();;
    private TicketManager ticketManager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_seats);

        Intent intent = getIntent();
        if (intent.hasExtra(ChooseSeatsActivity.SEATS_AMOUNT_KEY)) {
            seatsAmount = intent.getIntExtra(ChooseSeatsActivity.SEATS_AMOUNT_KEY, 0);
        }
        if (intent.hasExtra(Account.ACCOUNT_KEY)) {
            account = (Account) intent.getSerializableExtra(Account.ACCOUNT_KEY);
        }

        if (intent.hasExtra(Viewing.VIEWING_KEY)) {
            viewing = (Viewing) intent.getSerializableExtra(Viewing.VIEWING_KEY);
        }

        if (intent.hasExtra(TicketManager.TICKETMANAGER_KEY)) {
            ticketManager = (TicketManager) intent.getSerializableExtra(TicketManager.TICKETMANAGER_KEY);
        }

        List<Integer> ageList = new ArrayList<>();
        List<Integer> seatNumberList = new ArrayList<>();


        //Create list for spinners
        ArrayList<Integer> availableSeatNumbers = ticketManager.getAvailableSeatNumbers(viewing);


        //Displaying UI elements
        totalPrice = findViewById(R.id.tv_choose_seats_price_value);
        mTicketListLl = findViewById(R.id.ll_choose_seats_ticket_list);
        hall = findViewById(R.id.tv_choose_seats_hall);
        hall.append(viewing.getRoom().getRoomNumber() + "");
        for (int i = 0; i < seatsAmount; i++) {
            View v = View.inflate(this, R.layout.choose_seat_list_item, null);
            Spinner mSeatsSr = v.findViewById(R.id.spinner);
            EditText ree = v.findViewById(R.id.editTextNumberSigned);
            ree.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {

                }

                @Override
                public void afterTextChanged(Editable s) {
                    if (checkIfAllfilled()) {
                        calculateTotalPrice();
                    }
                }
            });
            ArrayAdapter<Integer> seatsAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, availableSeatNumbers);
            seatsAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            mSeatsSr.setAdapter(seatsAdapter);
            viewList.add(v);
            mTicketListLl.addView(v);
        }

        //Finishing ticket creation
        mConfBn = findViewById(R.id.bn_choose_seats_conf);
        ArrayList<Ticket> createdTickets = new ArrayList<>();
        mConfBn.setOnClickListener((View v) -> {
            for (View x: viewList) {
                Spinner seat = x.findViewById(R.id.spinner);
                int seatNumber = availableSeatNumbers.get(seat.getSelectedItemPosition());
                int rowNumber = ticketManager.getRow(viewing, seatNumber);
                Ticket ticket = new Ticket(seatNumber, account.getEmail(), viewing.getId(), "VALID", rowNumber);
                createdTickets.add(ticket);
            }
            new DatabaseTask().execute(createdTickets);
            Intent ticketsIntent = new Intent(v.getContext(), MainActivity.class);
            ticketsIntent.putExtra(MainActivity.DESTINATION_KEY, "tickets");
            ticketsIntent.putExtra(Account.ACCOUNT_KEY, account);
            startActivity(ticketsIntent);
        });
    }

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return true;
    }

    public boolean checkIfAllfilled() {
        boolean allFilled = true;
        for (View x : viewList) {
            EditText test = x.findViewById(R.id.editTextNumberSigned);
            String ehudh = test.getText().toString();
            if (test.getText().toString().equals("")) {
                allFilled = false;
                break;
            }
        }
        return allFilled;
    }

    public void calculateTotalPrice() {
        int result = 0;
        for (View x : viewList) {
            EditText test = x.findViewById(R.id.editTextNumberSigned);
            int age = Integer.valueOf(test.getText().toString());
            result += ticketManager.calculatePrice(age, viewing);
        }
        totalPrice.setText(result + "");
    }

    public class DatabaseTask extends AsyncTask< ArrayList<Ticket>, Void, Void> {
        @Override
        protected Void doInBackground(ArrayList<Ticket>... arrayLists) {
            ticketManager.createTickets(arrayLists[0]);
            return null;
        }
    }

}