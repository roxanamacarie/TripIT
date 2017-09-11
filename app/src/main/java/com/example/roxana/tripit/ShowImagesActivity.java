package com.example.roxana.tripit;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Roxana on 5/13/2017.
 */

public class ShowImagesActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;
    private DatabaseReference mDatabase;
    private ProgressDialog progressDialog;

    private List<Upload> uploads;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_images);


        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));


        progressDialog = new ProgressDialog(this);

        uploads = new ArrayList<>();

        progressDialog.setMessage("Please wait...");
        progressDialog.show();
        mDatabase = FirebaseDatabase.getInstance().getReference(Constants.DATABASE_PATH_UPLOADS);

        mDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                progressDialog.dismiss();

                for (DataSnapshot postSnapshot : snapshot.getChildren()) {
                    Upload upload = postSnapshot.getValue(Upload.class);
                    uploads.add(upload);
                }
                adapter = new MyAdapter(getApplicationContext(), uploads);

                recyclerView.setAdapter(adapter);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                progressDialog.dismiss();
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

