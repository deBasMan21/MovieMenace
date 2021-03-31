package nl.avans.moviemenace.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import nl.avans.moviemenace.R;

public class RegisterActivity extends AppCompatActivity {
    private Button mConfBn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        mConfBn = findViewById(R.id.bn_register_conf);
        mConfBn.setOnClickListener((View v) -> {
            startActivity(new Intent(v.getContext(), MainActivity.class));
        });
    }
}