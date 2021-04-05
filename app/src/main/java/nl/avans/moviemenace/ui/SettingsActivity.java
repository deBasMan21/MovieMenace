package nl.avans.moviemenace.ui;

import android.content.res.Configuration;
import android.os.Bundle;
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
    }

    public static class SettingsFragment extends PreferenceFragmentCompat {
        @Override
        public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
        }
    }
}