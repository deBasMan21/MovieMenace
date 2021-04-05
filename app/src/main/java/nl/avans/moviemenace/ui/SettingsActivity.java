package nl.avans.moviemenace.ui;

import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.preference.PreferenceFragmentCompat;

import java.util.Locale;

import nl.avans.moviemenace.R;

public class SettingsActivity extends AppCompatActivity {
    private RadioButton mRbLanguageEn;
    private RadioButton mRbLanguageNl;
    private RadioButton mRbColorDark;
    private RadioButton mRbColorLight;

    private RadioGroup mRgLanguage;
    private RadioGroup mRgColor;

    private Button mLogoutButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.settings_activity);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
        mRbColorDark = findViewById(R.id.rb_color_dark);
        mRbColorLight = findViewById(R.id.rb_color_light);
        mRbLanguageEn = findViewById(R.id.rb_language_en);
        mRbLanguageNl = findViewById(R.id.rb_language_nl);

        mRgLanguage = findViewById(R.id.rg_language);
        mRgColor = findViewById(R.id.rg_color_mode);

        mLogoutButton = findViewById(R.id.bn_account_logout);

        if(Locale.getDefault().equals(Locale.US)){
            mRgLanguage.check(R.id.rb_language_en);
        } else {
            mRgLanguage.check(R.id.rb_language_nl);
        }

        Configuration configuration = getResources().getConfiguration();
        int currentNightMode = configuration.uiMode & Configuration.UI_MODE_NIGHT_MASK;
        switch (currentNightMode) {
            case Configuration.UI_MODE_NIGHT_YES:
                mRgColor.check(R.id.rb_color_dark);
                break;
            default:
                mRgColor.check(R.id.rb_color_light);
                break;
        }

        mLogoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MainActivity.account = null;
            }
        });

        mRbLanguageEn.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                Locale.setDefault(Locale.US);
            }
        });
        mRbLanguageNl.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                Locale.setDefault(Locale.forLanguageTag("nl_NL"));
            }
        });
    }

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return true;
    }
}