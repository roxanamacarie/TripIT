package com.example.roxana.tripit;

/**
 * Created by Roxana on 5/13/2017.
 */

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;
import com.google.android.gms.maps.model.LatLng;
import java.util.ArrayList;

public class MapsMainActivity extends AppCompatActivity {

    static ArrayList<String> places = new ArrayList<>();
    static ArrayList<LatLng> locations = new ArrayList<>();
    static ArrayAdapter arrayAdapter;

    public  void modifyName(View view) {
        EditText index = (EditText) findViewById(R.id.index);
        Integer i = Integer.parseInt(index.getText().toString());

        EditText denumire = (EditText) findViewById(R.id.nume);
        String name = denumire.getText().toString();

        if (i < places.size() && i>0) {
            places.set(i, name);
            arrayAdapter.notifyDataSetChanged();
            Toast.makeText(getApplicationContext(), "Name changed", Toast.LENGTH_LONG).show();
        }
        else{
            Toast.makeText(getApplicationContext(), "Element not found", Toast.LENGTH_LONG).show();
        }
    }

    public  void delete(View view) {
        EditText index = (EditText) findViewById(R.id.index);
        Integer i = Integer.parseInt(index.getText().toString());

        if (i < places.size() && i>0) {
            places.remove(places.get(i));
            locations.remove(locations.get(i));
            arrayAdapter.notifyDataSetChanged();
            Toast.makeText(getApplicationContext(), "Location deleted", Toast.LENGTH_LONG).show();
        }
        else{
            Toast.makeText(getApplicationContext(), "Element not found", Toast.LENGTH_LONG).show();
        }
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mapsmain);

        ListView listView = (ListView) findViewById(R.id.listView);

        places.add("Add a new place...");
        locations.add(new LatLng(44.43861, 26.04949));

        arrayAdapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, places);
        listView.setAdapter(arrayAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(getApplicationContext(), MapsActivity.class);
                intent.putExtra("placeNumber", i);
                startActivity(intent);
            }


        });
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_settings)
            return true;
        if (id == R.id.memories) {
            Intent intent = new Intent(getApplicationContext(), MemoriesActivity.class);
            //intent.putExtra("wish",inputString);
            startActivity(intent);
            return true;
        }
        if (id == R.id.maps) {
            Intent intent = new Intent(getApplicationContext(), MapsMainActivity.class);
            //intent.putExtra("wish",inputString);
            startActivity(intent);
            return true;
        }
        if (id == R.id.docs) {
            Intent intent = new Intent(getApplicationContext(), DocsActivity.class);
            //intent.putExtra("wish",inputString);
            startActivity(intent);
            return true;
        }
        if (id == R.id.budget) {
            Intent intent = new Intent(getApplicationContext(), BudgetActivity.class);
            //intent.putExtra("wish",inputString);
            startActivity(intent);
            return true;
        }
        if (id == R.id.meteo) {
            Intent intent = new Intent(getApplicationContext(), MeteoActivity.class);
            startActivity(intent);
            return true;
        }

        if (id == R.id.exit) {
            finish();
            moveTaskToBack(true);
            return true;
        }


        return super.onOptionsItemSelected(item);
    }
}


