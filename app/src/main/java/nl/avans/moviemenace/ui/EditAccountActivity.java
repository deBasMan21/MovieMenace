package nl.avans.moviemenace.ui;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
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
    private String zipCode = "";

    private Button mConfBn;
    private View v;

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
            this.v = v;
            String email = mEmailEt.getText().toString();
            zipCode = mPostNumEt.getText().toString() + " " + mPostCharEt.getText().toString();
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
            }
        });
    }

    public void startIntent(){
        Intent accountIntent = new Intent(v.getContext(), MainActivity.class);
        accountIntent.putExtra(MainActivity.DESTINATION_KEY, "account");
        accountIntent.putExtra(Account.ACCOUNT_KEY, MainActivity.account);
        startActivity(accountIntent);
    }

    public void errorMessage(){
        new AlertDialog.Builder(this).setTitle(R.string.warning).setMessage(R.string.error_email).setPositiveButton("Oke", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        }).show();
    }

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return true;
    }

    public class DatabaseTask extends AsyncTask<Void, Void, Boolean> {
        @Override
        protected Boolean doInBackground(Void... voids) {
            Account acc = new Account(mEmailEt.getText().toString(), mNameEt.getText().toString(), MainActivity.account.getPassword(), mAddressEt.getText().toString(), zipCode, MainActivity.account.getIban(), MainActivity.account.getDateOfBirth());
            return accountManager.updateAccount(prevEmail, acc);
        }

        @Override
        protected void onPostExecute(Boolean aBoolean) {
            super.onPostExecute(aBoolean);
            if(aBoolean){
                MainActivity.account.setName(mNameEt.getText().toString());
                MainActivity.account.setEmail(mEmailEt.getText().toString());
                MainActivity.account.setAddress(mAddressEt.getText().toString());
                MainActivity.account.setZipCode(zipCode);
                startIntent();
            } else{
                errorMessage();
            }
        }
    }
}