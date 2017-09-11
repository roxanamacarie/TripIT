package com.example.roxana.tripit;


import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;


public class MeteoActivity extends Activity {

    EditText cityName;
    TextView resultTextView;

    public void findWeather(View view) {

        Log.i("cityName", cityName.getText().toString());

        InputMethodManager mgr = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        mgr.hideSoftInputFromWindow(cityName.getWindowToken(), 0);

        try {
            String encodedCityName = URLEncoder.encode(cityName.getText().toString(), "UTF-8");

            DownloadTask task = new DownloadTask();
            task.execute(String.format("http://api.openweathermap.org/data/2.5/weather?q=%s&appid=bfcc7f464c4de3b7eade25edb1b37ec1", cityName.getText().toString()));

        } catch (UnsupportedEncodingException e) {

            e.printStackTrace();

            Toast.makeText(getApplicationContext(), "Could not find weather", Toast.LENGTH_LONG);

        }



    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_meteo);



        cityName = (EditText) findViewById(R.id.cityName);
        resultTextView = (TextView) findViewById(R.id.resultTextView);

    }


    public class DownloadTask extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... urls) {

            String result = "";
            URL url;
            HttpURLConnection urlConnection = null;

            try {
                url = new URL(urls[0]);

                urlConnection = (HttpURLConnection) url.openConnection();

                InputStream in = urlConnection.getInputStream();

                InputStreamReader reader = new InputStreamReader(in);

                int data = reader.read();

                while (data != -1) {

                    char current = (char) data;

                    result += current;

                    data = reader.read();

                }

                return result;

            } catch (Exception e) {

                Toast.makeText(getApplicationContext(), "Could not find weather", Toast.LENGTH_LONG).show();

            }

            return null;
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);

            try {

                String message = "";

                JSONObject jsonObject = new JSONObject(result);

                String weatherInfo = jsonObject.getString("weather");
                JSONObject mainObject = jsonObject.getJSONObject("main");
                Double temp = mainObject.getDouble("temp");

                message +="Temperature:";
                Double tempCelsius = temp - 272.15;
                message += Integer.toString(tempCelsius.intValue()) + "\n";
                message += "Weather: ";

                Log.i("Weather content", weatherInfo);

                JSONArray arr = new JSONArray(weatherInfo);

                for (int i = 0; i < arr.length(); i++) {

                    JSONObject jsonPart = arr.getJSONObject(i);

                    String main = "";
                    String description = "";

                    main = jsonPart.getString("main");
                    description = jsonPart.getString("description");

                    if (main != "" && description != "") {

                        message += main + ", " + description + "\r\n";

                    }

                }

                if (message != "") {

                    resultTextView.setText(message);

                } else {

                    Toast.makeText(getApplicationContext(), "Could not find weather", Toast.LENGTH_LONG);

                }


            } catch (JSONException e) {

                Toast.makeText(getApplicationContext(), "Could not find weather", Toast.LENGTH_LONG);

            }



        }
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
