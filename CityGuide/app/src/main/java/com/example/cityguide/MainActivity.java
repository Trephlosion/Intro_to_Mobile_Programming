package com.example.cityguide;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import java.util.Objects;


public class MainActivity extends AppCompatActivity {

    ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setLogo(R.mipmap.ic_launcher);
        getSupportActionBar().setDisplayUseLogoEnabled(true);

        String[] attraction = new String[]{"Art Institute of Chicago", "Magnificent Mile", "Willis Tower", "Chicago Bean", "Water Tower"};
        listView = findViewById(R.id.listView);
        ArrayAdapter<String> adapter = (new ArrayAdapter<>(this,
                android.R.layout.simple_list_item_1,
                android.R.id.text1,
                attraction));
        listView.setAdapter(adapter);
        listView.setOnItemClickListener((parent, view, position, id) -> {
            switch (position) {
                case 0:
                    startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("http://artic.edu")));
                    break;
                case 1:
                    startActivity(new Intent(Intent.ACTION_VIEW,
                            Uri.parse("http://themagnificentmile.com")));
                    break;
                case 2:
                    startActivity(new Intent(MainActivity.this, Willis.class));
                    break;
                case 3:
                    startActivity(new Intent(MainActivity.this, Pier.class));
                    break;
                case 4:
                    startActivity(new Intent(MainActivity.this, Water.class));
                    break;
            }
        });


    }
}
