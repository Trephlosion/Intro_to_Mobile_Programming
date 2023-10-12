package com.example.vacationselector;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    String groupChoice;

//    String advent = "Adventure";
//
//    String relax = "Relaxation";
//    String result;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setLogo(R.mipmap.ic_launcher);
        getSupportActionBar().setDisplayUseLogoEnabled(true);

        final Spinner interest = (Spinner) findViewById(R.id.spInterests);
        final RadioButton History = findViewById(R.id.rbHistory);
        final RadioButton Beach = findViewById(R.id.rbBeach);
        Button travel = (Button) findViewById(R.id.btDestination);
//        TextView c = findViewById(R.id.tvChoice);

        travel.setOnClickListener(new View.OnClickListener() {
            char choice;
            @Override
            public void onClick(View view) {

                groupChoice = interest.getSelectedItem().toString();

/*
                if (Beach.isChecked()) {
                    if (interest.getSelectedItem().toString() == "Adventure") {
                        choice = 0;
//                startActivity(new Intent(MainActivity.this, Tamarindo.class));
                    } else if (interest.getSelectedItem().toString() == "Relaxation") {
                        choice = 1;
//                startActivity(new Intent(MainActivity.this, Maldives.class));
                    }
                }

                if (History.isChecked()) {
                    if (interest.getSelectedItem().toString() == "Adventure") {
                        choice = 2;
//                startActivity(new Intent(MainActivity.this, Petra.class));
                    } else if (interest.getSelectedItem().toString() == "Relaxation") {
                        choice = 3;
//                        startActivity(new Intent(MainActivity.this, Bath.class));
                    }
*/
                if (Beach.isChecked() && groupChoice.equals("Adventure")) {
                    choice = 't';

                }

                if (Beach.isChecked() && groupChoice.equals("Relaxation")) {
                    choice = 'm';

                }

                if (History.isChecked() && groupChoice.equals("Adventure")) {
                    choice = 'p';

                }

                if (History.isChecked() && groupChoice.equals("Relaxation")) {
                    choice = 'b';

                }
//                result = "choice = " + choice;
//                c.setText(result);



                switch (choice) {
                    case 't':
                        startActivity(new Intent(MainActivity.this, Tamarindo.class));
                        break;
                    case 'm':
                        startActivity(new Intent(MainActivity.this, Maldives.class));
                        break;
                    case 'p':
                        startActivity(new Intent(MainActivity.this, Petra.class));
                        break;
                    case 'b':
                        startActivity(new Intent(MainActivity.this, Bath.class));
                        break;
                    default:
                        Toast.makeText(MainActivity.this, "Something went wrong, restart app.",
                                Toast.LENGTH_LONG).show();
                        break;


                }

            }
    });

}
}
