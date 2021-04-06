package nl.avans.moviemenace.ui;

import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import java.util.Locale;

import nl.avans.moviemenace.R;
import nl.avans.moviemenace.domain.Account;
import nl.avans.moviemenace.logic.LanguageHelper;
import nl.avans.moviemenace.ui.home.HomeFragment;

public class SettingsActivity extends AppCompatActivity {
    private RadioButton mRbLanguageEn;
    private RadioButton mRbLanguageNl;
    private RadioButton mRbColorDark;
    private RadioButton mRbColorLight;

    private RadioGroup mRgLanguage;
    private RadioGroup mRgColor;

    private Button mLogoutButton;
    private Button mBackButton;

    private Context context;
    private Resources recourses;

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
        mBackButton = findViewById(R.id.bn_account_confirm);

        if(LanguageHelper.isLanguage("us_EN")){
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

        mBackButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
            }
        });

        mRbLanguageEn.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    LanguageHelper.setLanguage("us_EN");
                    Locale locale = new Locale("en");
                    Locale.setDefault(locale);
                    Configuration config = new Configuration();
                    config.locale = locale;
                    getBaseContext().getResources().updateConfiguration(config, getBaseContext().getResources().getDisplayMetrics());
                }

            }
        });
        mRbLanguageNl.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    LanguageHelper.setLanguage("nl_NL");
                    Locale locale = new Locale("nl");
                    Locale.setDefault(locale);
                    Configuration config = new Configuration();
                    config.locale = locale;
                    getBaseContext().getResources().updateConfiguration(config, getBaseContext().getResources().getDisplayMetrics());
                }

            }
        });

        mRbColorLight.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
                }
            }
        });

        mRbColorDark.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
                }
            }
        });
    }
}