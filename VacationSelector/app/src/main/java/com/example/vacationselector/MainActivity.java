package com.example.vacationselector;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    int choice;
    String groupChoice;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setLogo(R.mipmap.ic_launcher);
        getSupportActionBar().setDisplayUseLogoEnabled(true);

        final Spinner interest = findViewById(R.id.spInterests);
        final RadioButton History = findViewById(R.id.rbHistory);
        final RadioButton Beach = findViewById(R.id.rbBeach);
        Button travel = findViewById(R.id.btDestination);

        groupChoice = interest.getSelectedItem().toString();
        travel.setOnClickListener(view -> {

            if (Beach.isChecked() && groupChoice.equals("Adventure")) {
                choice = 0;
            } else if (Beach.isChecked() && groupChoice.equals("Relaxation")) {
                choice = 1;
            } else if (History.isChecked() && groupChoice.equals("Adventure")) {
                choice = 2;
            } else if (History.isChecked() && groupChoice.equals("Relaxation")) {
                choice = 3;
            } else {
                Toast.makeText(MainActivity.this, "Something went wrong, restart app.",
                        Toast.LENGTH_LONG).show();
            }

            switch (choice) {
                case 0:
                    startActivity(new Intent(MainActivity.this, Tamarindo.class));
                    break;
                case 1:
                    startActivity(new Intent(MainActivity.this, Maldives.class));
                    break;
                case 2:
                    startActivity(new Intent(MainActivity.this, Petra.class));
                    break;
                case 3:
                    startActivity(new Intent(MainActivity.this, Bath.class));
                    break;

            }

        });


    }
}
