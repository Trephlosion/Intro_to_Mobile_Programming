package com.example.cityguide;

import android.app.ListActivity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.Objects;


public class MainActivity extends ListActivity {

    ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Objects.requireNonNull(getActionBar()).setDisplayShowHomeEnabled(true);
        getActionBar().setLogo(R.mipmap.ic_launcher);
        getActionBar().setDisplayUseLogoEnabled(true);

        String[] attraction = new String[]{"Art Institute of Chicago", "Magnificent Mile", "Willis Tower", "Chicago Bean", "Water Tower"};
        listView = findViewById(R.id.listView);
        setListAdapter(new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, R.id.travel, attraction));
    }
        protected void onListItemClick(ListView l, View v, int position, long id){

        switch(position){
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

        }






}
