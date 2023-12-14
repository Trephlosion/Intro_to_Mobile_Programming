package com.example.drawingtest;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.SeekBar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import androidx.appcompat.app.AppCompatActivity;

public class OptionsNSettings extends AppCompatActivity {

    final int defaultnum = 3;

    private RadioGroup radioGroupTheme;
    private EditText Participants;
    private Button btnCredits;
    private SeekBar seekBarVolume;
    private SeekBar seekBarSoundEffect;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        // Set the theme based on saved preferences or default to AppThemeLight
        int theme = getSharedPreferences("prefs", MODE_PRIVATE).getInt("theme", 0);
        setTheme(R.style.Theme_Drawingtest);


        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_options_nsettings);

        // default
        Participants.setText(defaultnum);

        // Find views
        RadioGroup radioGroupTheme = findViewById(R.id.radioGroupTheme);
        EditText Participants = findViewById(R.id.editTextParticipants);
        Button btnCredits = findViewById(R.id.btnCredits);
        SeekBar seekBarVolume = findViewById(R.id.seekBarVolume);
        SeekBar seekBarSoundEffect = findViewById(R.id.seekBarSoundEffect);




        seekBarVolume.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                // Handle volume change
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                // Do nothing
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                // Do nothing
            }
        });

        seekBarSoundEffect.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                // Handle sound effect change
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                // Do nothing
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                // Do nothing
            }


            // Handle UI events and implement your logic here
            // For example, set onClickListener for the credits button, handle seekbar changes, etc.

        });

        radioGroupTheme.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (checkedId == R.id.radioLight) {
                    setThemeAndSavePreference(R.style.Theme_Drawingtest);
                } else if (checkedId == R.id.radioDark) {
                    setThemeAndSavePreference(R.style.Theme_Drawingtest);
                }
            }
        });
    }
//remove this


    public void onApplyButtonClick(View view) {
        // Get the value from the EditText
        String participantsStr = Participants.getText().toString();

        try {
            // Parse the value to an integer
            int participants = Integer.parseInt(participantsStr);

            // Check if the value is within the allowed range
            if (participants < 3 || participants > 10) {
                // Display a toast notification
                Toast.makeText(this, "Participants must be between 3 and 10", Toast.LENGTH_SHORT).show();
            } else {
                // The value is within the allowed range, you can use it as needed
            }
        } catch (NumberFormatException e) {
            // Handle the case where the input cannot be parsed to an integer
            Toast.makeText(this, "Invalid input. Please enter a number.", Toast.LENGTH_SHORT).show();
        }
    }

    private void setThemeAndSavePreference(int theme) {
        setTheme(theme);
        AppCompatDelegate.setDefaultNightMode(
                theme == R.style.Theme_Drawingtest ? AppCompatDelegate.MODE_NIGHT_YES :
                        AppCompatDelegate.MODE_NIGHT_NO);

        // Save the selected theme to preferences
        getSharedPreferences("prefs", MODE_PRIVATE).edit().putInt("theme", theme).apply();

        // Recreate the activity to apply the theme
        recreate();
    }

}
