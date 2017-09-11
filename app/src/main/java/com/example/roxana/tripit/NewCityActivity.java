package com.example.roxana.tripit;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.widget.ImageView;

/**
 * Created by Roxana on 5/13/2017.
 */

public class NewCityActivity extends Activity {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Get the view from singleitemview.xml
        setContentView(R.layout.activity_new_city);

        //
        MainActivity.imageAdapter.addCity(R.drawable.paris);
        MainActivity.imageAdapter.notifyDataSetChanged();
    }
}
