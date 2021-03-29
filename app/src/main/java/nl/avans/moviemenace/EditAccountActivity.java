package nl.avans.moviemenace;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.EditText;

public class EditAccountActivity extends AppCompatActivity {
    private EditText mNameEt;
    private EditText mEmailEt;
    private EditText mPlaceEt;
    private EditText mAddressEt;
    private EditText mPostNumEt;
    private EditText mPostCharEt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_account);
    }
}