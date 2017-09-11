package com.example.roxana.tripit;

import android.app.Activity;
import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.Toast;

public class MainActivity extends Activity {

    public static ImageAdapter imageAdapter;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Get the view from gridview_main.xml
        setContentView(R.layout.activity_main);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Click action
                Log.i("err","erore");
                Intent intent = new Intent(MainActivity.this, NewCityActivity.class);
                startActivity(intent);
            }
        });
        // Locate GridView in listview_main.xml
        GridView gridview = (GridView) findViewById(R.id.gridview);

        // Set the ImageAdapter into GridView Adapter
        imageAdapter = new ImageAdapter(this);
        gridview.setAdapter(imageAdapter);

        // Capture GridView item click
        gridview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View v,
                                    int position, long id) {

                // Launch ViewImage.java using intent
                Intent i = new Intent(MainActivity.this, MenuActivity.class);

                // Start ViewImage.java
                startActivity(i);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_grid_view, menu);
        return true;
    }
}
