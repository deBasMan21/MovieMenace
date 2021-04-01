package nl.avans.moviemenace.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import nl.avans.moviemenace.R;

public class EditAccountActivity extends AppCompatActivity {
    private EditText mNameEt;
    private EditText mEmailEt;
    private EditText mPlaceEt;
    private EditText mAddressEt;
    private EditText mPostNumEt;
    private EditText mPostCharEt;

    private Button mConfBn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_account);

        mNameEt = findViewById(R.id.et_edit_account_name);
        mEmailEt = findViewById(R.id.et_edit_account_email);
        mPlaceEt = findViewById(R.id.et_edit_account_place);
        mAddressEt = findViewById(R.id.et_edit_account_address);
        mPostNumEt = findViewById(R.id.et_edit_account_post_num);
        mPostCharEt = findViewById(R.id.et_edit_account_post_char);

        mConfBn = findViewById(R.id.bn_edit_account_conf);

        mConfBn.setOnClickListener((View v) -> {
            finish();
        });
    }

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return true;
    }
}