package nl.avans.moviemenace.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import android.content.Intent;
import android.content.res.Configuration;
import android.media.Image;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import nl.avans.moviemenace.R;
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
    private List<View> viewList= new ArrayList<>();
    private TicketManager ticketManager;
    private ImageView mChairs;
    private TextView price;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_seats);

        mChairs = findViewById(R.id.iv_choose_seats_hall_preview);
        Configuration configuration = getResources().getConfiguration();
        int currentNightMode = configuration.uiMode & Configuration.UI_MODE_NIGHT_MASK;
        switch (currentNightMode) {
            case Configuration.UI_MODE_NIGHT_YES:
                Picasso.get().load("https://i.ibb.co/7tb8tJ6/Artboard-2.png").into(mChairs);
                break;
            default:
                Picasso.get().load("https://i.ibb.co/ThyWgj3/Artboard-1.png").into(mChairs);
                break;
        }

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

        price = findViewById(R.id.normal_price);
        price.append(" " + viewing.getPrice() + ",-");

        //Create list for spinners
        ArrayList<Integer> availableSeatNumbers = ticketManager.getAvailableSeatNumbers(viewing);


        //Displaying UI elements
        totalPrice = findViewById(R.id.tv_choose_seats_price_value);
        mTicketListLl = findViewById(R.id.ll_choose_seats_ticket_list);
        hall = findViewById(R.id.tv_choose_seats_hall);

        hall.append(" " + viewing.getRoom().getRoomNumber());
        for (int i = 0; i < seatsAmount; i++) {
            View v = View.inflate(this, R.layout.choose_seat_list_item, null);
            Spinner mSeatsSr = v.findViewById(R.id.spinner);
            EditText ageInput = v.findViewById(R.id.editTextNumberSigned);
            ageInput.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {

                }

                @Override
                public void afterTextChanged(Editable s) {
                    if (s.toString().contains("-")) {
                        ageInput.setText("");
                        return;
                    }
                    if (allFilled()) {
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
            //Prepare errormessage
            TextView errorMessage = findViewById(R.id.error_seat_selection);
            errorMessage.setVisibility(View.VISIBLE);


            //gather filled-in information and create tickets
            ArrayList<Integer> selectedSeatNumbers = new ArrayList<>();
            for (View x: viewList) {
                //inflate items
                Spinner seat = x.findViewById(R.id.spinner);
                EditText editText = x.findViewById(R.id.editTextNumberSigned);
                if (!editText.getText().toString().equals("") && !ticketManager.validateAge(Integer.valueOf(editText.getText().toString()))) {
                    errorMessage.setText(R.string.wrong_age);
                    return;
                }
                selectedSeatNumbers.add(Integer.valueOf(seat.getSelectedItem().toString()));
                int seatNumber = availableSeatNumbers.get(seat.getSelectedItemPosition());
                int rowNumber = ticketManager.getRow(viewing, seatNumber);
                Ticket ticket = new Ticket(seatNumber, account.getEmail(), viewing.getId(), "VALID", rowNumber);
                createdTickets.add(ticket);
            }
            //check for errors, else create tickets and redirect user.
            if (ticketManager.hasDoubleSeatNumbers(selectedSeatNumbers)) {
                //check for double seat selection
                errorMessage.setText(R.string.double_seats_selected);
            } else if (!allFilled()) {
                //check for empty age inputs
                errorMessage.setText(R.string.missing_age_input);
            } else {
                new DatabaseTask().execute(createdTickets);
                Intent ticketsIntent = new Intent(v.getContext(), MainActivity.class);
                ticketsIntent.putExtra(Account.ACCOUNT_KEY, account);
                startActivity(ticketsIntent);
            }
        });
    }

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return true;
    }

    public boolean allFilled() {
        boolean filled = true;
        for (View x : viewList) {
            EditText editText = x.findViewById(R.id.editTextNumberSigned);
            if (editText.getText().toString().equals("")) {
                filled = false;
                break;
            }
        }
        return filled;
    }

    public void calculateTotalPrice() {
        double result = 0.0;
        for (View x : viewList) {
            EditText test = x.findViewById(R.id.editTextNumberSigned);
            int age = Integer.valueOf(test.getText().toString());
            result += ticketManager.calculatePrice(age, viewing);
        }
        totalPrice.setText(result + ",-");
    }

    public class DatabaseTask extends AsyncTask< ArrayList<Ticket>, Void, Void> {
        @Override
        protected Void doInBackground(ArrayList<Ticket>... arrayLists) {
            ticketManager.createTickets(arrayLists[0]);
            return null;
        }
    }

}