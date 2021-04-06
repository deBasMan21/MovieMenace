package nl.avans.moviemenace.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.time.LocalDate;
import java.time.ZoneId;

import nl.avans.moviemenace.R;
import nl.avans.moviemenace.dataLayer.factory.DAOFactory;
import nl.avans.moviemenace.dataLayer.factory.SQLDAOFactory;
import nl.avans.moviemenace.domain.Account;
import nl.avans.moviemenace.domain.validation.Email;
import nl.avans.moviemenace.domain.validation.Password;
import nl.avans.moviemenace.domain.validation.ZipCode;
import nl.avans.moviemenace.logic.AccountManager;

public class EditAccountActivity extends AppCompatActivity {
    private EditText mNameEt;
    private EditText mEmailEt;
    private EditText mAddressEt;
    private EditText mPostNumEt;
    private EditText mPostCharEt;
    private TextView emailError;
    private TextView zipCodeError;
    private TextView nameError;
    private TextView adressError;
    private DAOFactory factory = new SQLDAOFactory();
    private AccountManager accountManager = new AccountManager(factory);
    private String prevEmail = MainActivity.account.getEmail();

    private Button mConfBn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_account);

        mNameEt = findViewById(R.id.et_edit_account_name);
        mNameEt.setText(MainActivity.account.getName());
        mEmailEt = findViewById(R.id.et_edit_account_email);
        mEmailEt.setText(MainActivity.account.getEmail());
        mAddressEt = findViewById(R.id.et_edit_account_address);
        mAddressEt.setText(MainActivity.account.getAddress());
        mPostNumEt = findViewById(R.id.et_edit_account_post_num);
        mPostNumEt.setText(MainActivity.account.getZipCode().substring(0, 4));
        mPostCharEt = findViewById(R.id.et_edit_account_post_char);
        mPostCharEt.setText(MainActivity.account.getZipCode().substring(5));
        emailError = findViewById(R.id.tv_edit_email_error);
        zipCodeError = findViewById(R.id.tv_edit_post_error);
        nameError = findViewById(R.id.tv_edit_name_error);
        adressError = findViewById(R.id.tv_edit_address_error);

        mConfBn = findViewById(R.id.bn_edit_account_conf);

        mConfBn.setOnClickListener((View v) -> {
            String email = mEmailEt.getText().toString();
            String zipCode = mPostNumEt.getText().toString() + " " + mPostCharEt.getText().toString();
            emailError.setVisibility(View.INVISIBLE);
            zipCodeError.setVisibility(View.INVISIBLE);
            nameError.setVisibility(View.INVISIBLE);
            adressError.setVisibility(View.INVISIBLE);

            //validate input
            boolean correctInput = true;

            //name validation
            if (mNameEt.getText().toString().equals("")) {
                correctInput = false;
                nameError.setVisibility(View.VISIBLE);
            }

            //address validation
            if (mAddressEt.getText().toString().equals("")) {
                correctInput = false;
                adressError.setVisibility(View.VISIBLE);
            }

            //Email validation
            if (!Email.checkEmail(email)) {
                correctInput = false;
                emailError.setVisibility(View.VISIBLE);
            }

            //zip code validation
            if (!ZipCode.checkZipCode(zipCode)) {
                correctInput = false;
                zipCodeError.setVisibility(View.VISIBLE);
            } else {
                zipCode = ZipCode.formatZipCode(zipCode);
            }


            //Final decision
            if (correctInput) {
                new DatabaseTask().execute();
                MainActivity.account.setName(mNameEt.getText().toString());
                MainActivity.account.setEmail(mEmailEt.getText().toString());
                MainActivity.account.setAddress(mAddressEt.getText().toString());
                MainActivity.account.setZipCode(zipCode);

                startActivity(new Intent(v.getContext(), MainActivity.class).putExtra(MainActivity.DESTINATION_KEY, "account"));
            }
        });
    }

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return true;
    }

    public class DatabaseTask extends AsyncTask<Void, Void, Void> {
        @Override
        protected Void doInBackground(Void... voids) {
            accountManager.updateAccount(prevEmail, MainActivity.account);
            return null;
        }
    }
}