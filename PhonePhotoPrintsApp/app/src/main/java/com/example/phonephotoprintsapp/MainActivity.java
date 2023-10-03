package com.example.phonephotoprintsapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;

import java.text.DecimalFormat;

public class MainActivity extends AppCompatActivity {


    double Rate8x10 = 0.79;

    double Rate5x7 = 0.49;
    double Rate4x6 = 0.19;

    int numDays;

    double finalcost;

    String results;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        final EditText Day = findViewById(R.id.etday);
        final RadioButton fourbysix = findViewById(R.id.rb4x6);
        final RadioButton fivebyseven = findViewById(R.id.rb5x7);
        final RadioButton eightbyten = findViewById(R.id.rb8x10);
        final TextView F = findViewById(R.id.tvFinalCost);
        Button compute = findViewById(R.id.btCompute);
        compute.setOnClickListener(view -> {

                    numDays = Integer.parseInt(Day.getText().toString());
                    DecimalFormat currency = new DecimalFormat("#.##");
                    if (fourbysix.isChecked()) {
                        if (numDays <= 100000) {
                            finalcost = numDays * Rate4x6;
                            results = "It costs $" + currency.format(finalcost) + " to rent " + numDays + " prints.";

                        }

                    }
                    if (fivebyseven.isChecked()) {
                        if (numDays <= 100000) {
                            finalcost = numDays * Rate5x7;
                            results = "It costs $" + currency.format(finalcost) + " to rent " +
                                    " " + numDays + " prints.";

                        }
                        if (eightbyten.isChecked()) {
                            if (numDays <= 100000) {
                                finalcost = numDays * Rate8x10;
                                results = "It costs $" + currency.format(finalcost) + " to rent  " +
                                        " " + numDays + " prints.";

                            }
                        }
                    }



                F.setText(results);


            });
    }
}
