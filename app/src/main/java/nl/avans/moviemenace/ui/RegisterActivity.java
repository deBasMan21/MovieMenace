package nl.avans.moviemenace.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Calendar;
import java.util.Locale;

import nl.avans.moviemenace.R;
import nl.avans.moviemenace.dataLayer.factory.DAOFactory;
import nl.avans.moviemenace.dataLayer.factory.SQLDAOFactory;
import nl.avans.moviemenace.domain.Account;
import nl.avans.moviemenace.domain.validation.Email;
import nl.avans.moviemenace.domain.validation.Password;
import nl.avans.moviemenace.domain.validation.ZipCode;
import nl.avans.moviemenace.logic.AccountManager;

public class RegisterActivity extends AppCompatActivity {
    private Button mConfBn;
    private EditText mBirthEt;
    private EditText mEmailEt;
    private EditText mZipCodeFirstPart;
    private EditText mZipCodeSecondPart;
    private EditText mPassword;
    private EditText mPasswordValidation;
    private EditText mName;
    private EditText mIban;
    private EditText mAddress;
    private Calendar calendar;
    private TextView emailError;
    private TextView zipCodeError;
    private TextView birthdayError;
    private TextView passwordError;
    private TextView passwordValidationError;
    private TextView nameError;
    private TextView ibanError;
    private TextView adressError;
    private DAOFactory factory = new SQLDAOFactory();
    private AccountManager accountManager = new AccountManager(factory);


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        mName = findViewById(R.id.et_register_name);
        mAddress = findViewById(R.id.et_register_address);
        mEmailEt = findViewById(R.id.et_register_email);
        mIban = findViewById(R.id.et_register_iban);
        mZipCodeFirstPart = findViewById(R.id.et_register_post_num);
        mZipCodeSecondPart = findViewById(R.id.et_register_post_char);
        mPassword = findViewById(R.id.et_register_pass);
        mPasswordValidation = findViewById(R.id.et_register_pass_re);
        emailError = findViewById(R.id.tv_register_email_error);
        zipCodeError = findViewById(R.id.tv_register_post_error);
        birthdayError = findViewById(R.id.tv_register_birth_error);
        passwordError = findViewById(R.id.tv_register_password_error);
        passwordValidationError = findViewById(R.id.tv_register_password_confirmation_error);
        nameError = findViewById(R.id.tv_register_name_error);
        adressError = findViewById(R.id.tv_register_address_error);
        ibanError = findViewById(R.id.tv_register_iban_error);


        mBirthEt = findViewById(R.id.et_register_birth);
        calendar = Calendar.getInstance();
        mBirthEt.setText(new SimpleDateFormat("d MMMM yyyy", Locale.getDefault()).format(calendar.getTime()));

        DatePickerDialog.OnDateSetListener date = (view, year, month, dayOfMonth) -> {
            calendar.set(Calendar.YEAR, year);
            calendar.set(Calendar.MONTH, month);
            calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
            mBirthEt.setText(new SimpleDateFormat("d MMMM yyyy", Locale.getDefault()).format(calendar.getTime()));
        };


        mBirthEt.setOnClickListener((View v) -> {
            new DatePickerDialog(RegisterActivity.this,
                    date,
                    calendar.get(Calendar.YEAR),
                    calendar.get(Calendar.MONTH),
                    calendar.get(Calendar.DAY_OF_MONTH))
            .show();
        });


        //Button + validation
        mConfBn = findViewById(R.id.bn_register_conf);
        mConfBn.setOnClickListener((View v) -> {
            String email = mEmailEt.getText().toString();
            String zipCode = mZipCodeFirstPart.getText().toString() + " " + mZipCodeSecondPart.getText().toString();
            LocalDate birthDay = calendar.getTime().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
            String password = mPassword.getText().toString();
            String passwordValidation = mPasswordValidation.getText().toString();
            emailError.setVisibility(View.INVISIBLE);
            zipCodeError.setVisibility(View.INVISIBLE);
            birthdayError.setVisibility(View.INVISIBLE);
            passwordError.setVisibility(View.INVISIBLE);
            passwordValidationError.setVisibility(View.INVISIBLE);
            nameError.setVisibility(View.INVISIBLE);
            adressError.setVisibility(View.INVISIBLE);
            ibanError.setVisibility(View.INVISIBLE);

            //validate input
            boolean correctInput = true;

            //name validation
            if (mName.getText().toString().equals("")) {
                correctInput = false;
                nameError.setVisibility(View.VISIBLE);
            }


            //address validation
            if ( mAddress.getText().toString().equals("")) {
                correctInput = false;
                adressError.setVisibility(View.VISIBLE);
            }

            //Email validation
            if (!Email.checkEmail(email)) {
                correctInput = false;
                emailError.setVisibility(View.VISIBLE);
            }

            //Iban validation
            if (mIban.getText().toString().equals("")) {
                correctInput = false;
                ibanError.setVisibility(View.VISIBLE);
            }

            //zip code validation
            if (ZipCode.checkZipCode(zipCode) || zipCode.equals(" ")) {
                correctInput = false;
                zipCodeError.setVisibility(View.VISIBLE);
            } else {
                zipCode = ZipCode.formatZipCode(zipCode);
            }

            //Birthday validation
            if (birthDay.isAfter(LocalDate.now())) {
                correctInput = false;
                birthdayError.setVisibility(View.VISIBLE);
            }

            //Password validation
            if (!Password.checkPassword(password)) {
                correctInput = false;
                passwordError.setVisibility(View.VISIBLE);
            } else if (!password.equals(passwordValidation)) {
                correctInput = false;
                passwordValidationError.setVisibility(View.VISIBLE);
            }

            //Final decision
            if (correctInput) {
                Account account = new Account(email, mName.getText().toString(), password, mAddress.getText().toString(), zipCode, mIban.getText().toString() , birthDay);
                DatabaseTask task = new DatabaseTask();
                task.execute(account);
                startActivity(new Intent(v.getContext(), MainActivity.class).putExtra(MainActivity.DESTINATION_KEY, "login"));
            }
        });
    }

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return true;
    }

    public class DatabaseTask extends AsyncTask<Account, Void, Void> {

        @Override
        protected Void doInBackground(Account... accounts) {
            accountManager.createAccount(accounts[0]);
            return null;
        }
    }
}