package com.example.medicalcalculator;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.text.DecimalFormat;


public class MainActivity extends AppCompatActivity {

    double conversionRate = 2.2;

    double targetWeight;

    double convertedWeight;

    String Units;

    String results;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        final EditText Target = findViewById(R.id.etPatientWeight);
        final RadioButton lbToKilo = findViewById(R.id.rbLbToKilo);
        final RadioButton kiloToLb = findViewById(R.id.rbKiloToLb);
        final TextView F = findViewById(R.id.tvResults);
        Button convert = findViewById(R.id.btConvert);
        convert.setOnClickListener(view -> {
            targetWeight = Double.parseDouble(Target.getText().toString());
            DecimalFormat tenth = new DecimalFormat("#.#");
            if (kiloToLb.isChecked()){
                if (targetWeight <=225) {
                    Units = " lbs";
                    convertedWeight = targetWeight * conversionRate;
                    results = "Patient weighs " + tenth.format(convertedWeight) + Units;

                } else {
                    Toast.makeText(MainActivity.this,"Kilos must be less than 225kg", Toast.LENGTH_LONG).show();
                }

            }
            if (lbToKilo.isChecked()) {
                if (targetWeight <=225) {
                    Units = " kg";
                    convertedWeight = targetWeight / conversionRate;
                    results = "Patient weighs " + tenth.format(convertedWeight) + Units;

                } else {
                    Toast.makeText(MainActivity.this,"Pounds must be less than 500lbs.", Toast.LENGTH_LONG).show();
                }
            }


            F.setText(results);
        });

    }
}
