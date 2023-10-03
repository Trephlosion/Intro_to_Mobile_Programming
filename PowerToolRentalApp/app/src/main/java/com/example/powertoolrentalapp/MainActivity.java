package com.example.powertoolrentalapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import java.text.DecimalFormat;

public class MainActivity extends AppCompatActivity {

    double tillerRate = 68.99;

    double pWashRate = 55.99;

    int numDays;

    double finalcost;

    String results;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        final EditText Day = findViewById(R.id.etday);
        final RadioButton PWash = findViewById(R.id.rbpWash);
        final RadioButton Tiller = findViewById(R.id.rbtiller);
        final TextView F = findViewById(R.id.tvFinalCost);
        Button compute = findViewById(R.id.btCompute);
        compute.setOnClickListener(view -> {

            numDays = Integer.parseInt(Day.getText().toString());
            DecimalFormat currency = new DecimalFormat("#.##");
            if (PWash.isChecked()){
                if (numDays <=7) {
                    finalcost = numDays * pWashRate;
                    results = "It costs $" + currency.format(finalcost) + " to rent the " +
                            "power washer for " + numDays + " days.";

                } else {
                    Toast.makeText(MainActivity.this,"Do not enter more than 7 days.", Toast.LENGTH_LONG).show();
                }

            }
            if (Tiller.isChecked()) {
                if (numDays <= 7) {
                    finalcost = numDays * tillerRate;
                    results = "It costs $" + currency.format(finalcost) + " to rent the tiller " +
                            "for" +
                            " " + numDays + " days.";

                } else {
                    Toast.makeText(MainActivity.this, "Do not enter more than 7 days.", Toast.LENGTH_LONG).show();
                }


                F.setText(results);

            }
        });

    }
}
