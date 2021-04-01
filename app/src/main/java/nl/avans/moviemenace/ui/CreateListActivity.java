package nl.avans.moviemenace.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import nl.avans.moviemenace.R;
import nl.avans.moviemenace.dataLayer.factory.DAOFactory;
import nl.avans.moviemenace.dataLayer.factory.SQLDAOFactory;
import nl.avans.moviemenace.domain.Account;
import nl.avans.moviemenace.domain.MovieList;
import nl.avans.moviemenace.logic.MovieListManager;

public class CreateListActivity extends AppCompatActivity {
    private Button mConfBn;

    private EditText mListNameInput;
    private EditText mListDescInput;

    private DAOFactory factory = new SQLDAOFactory();
    private MovieListManager movieListManager = new MovieListManager(factory);

    private Account account;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_list);

        mListNameInput = findViewById(R.id.et_create_list_name);
        mListDescInput = findViewById(R.id.et_create_list_desc);

        Intent orginalIntent = getIntent();
        if (orginalIntent.getSerializableExtra("loggedInAccount") != null) {
            account = (Account) orginalIntent.getSerializableExtra("loggedInAccount");
        }

        mConfBn = findViewById(R.id.bn_create_list_conf);
        mConfBn.setOnClickListener((View v) -> {
            if (mListNameInput.getText().toString().isEmpty() || mListDescInput.getText().toString().isEmpty()) {
                new AlertDialog.Builder(this)
                        .setTitle(R.string.create_list_empty_alert_title)
                        .setMessage(R.string.create_list_empty_alert_desc)
                        .setNegativeButton("Confirm", null)
                        .show();
            } else {
                String[] information = {mListNameInput.getText().toString(),
                        mListDescInput.getText().toString()};
                new DatabaseTask().execute(information);
                startActivity(new Intent(v.getContext(), MainActivity.class)
                        .putExtra(MainActivity.DESTINATION_KEY, "lists")
                        .putExtra(Account.ACCOUNT_KEY, account));
            }
        });
    }

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return true;
    }

    public class DatabaseTask extends AsyncTask<String[], Void, Void> {

        @Override
        protected Void doInBackground(String[]... strings) {
            String name = strings[0][0];
            String desc = strings[0][1];
            String email = account.getEmail();
            movieListManager.createList(name, desc, email);
            return null;
        }
    }
}