package com.example.physicscalculator;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.text.DecimalFormat;


public class MainActivity extends AppCompatActivity {

    double mass;

    double acceleration;

    double force;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        final EditText M = (EditText)findViewById(R.id.tfMass);
        final EditText A = (EditText)findViewById(R.id.tfAccel);
        Button calculate = (Button)findViewById(R.id.btCalculate);
        calculate.setOnClickListener(new View.OnClickListener() {

            final TextView F = ((TextView)findViewById(R.id.tvResults));
        @Override
        public void onClick(View view) {
            mass = Double.parseDouble(M.getText().toString());
            acceleration = Double.parseDouble(A.getText().toString());
            force = mass * acceleration;
            DecimalFormat form = new DecimalFormat("0.000");
            F.setText(form.format(force) + " Newtons");

            /*String finalAns = Double.valueOf(force).toString();
            F.setText(finalAns);*/
        }
    });
    }
}