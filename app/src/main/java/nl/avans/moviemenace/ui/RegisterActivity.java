package nl.avans.moviemenace.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;

import java.text.SimpleDateFormat;
import java.time.Year;
import java.util.Calendar;
import java.util.Locale;

import nl.avans.moviemenace.R;

public class RegisterActivity extends AppCompatActivity {
    private Button mConfBn;
    private EditText mBirthEt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        mConfBn = findViewById(R.id.bn_register_conf);
        mConfBn.setOnClickListener((View v) -> {

            startActivity(new Intent(v.getContext(), MainActivity.class).putExtra(MainActivity.DESTINATION_KEY, "login"));
        });

        mBirthEt = findViewById(R.id.et_register_birth);
        Calendar calendar = Calendar.getInstance();
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
    }

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return true;
    }
}