package com.example.roxana.tripit;

/**
 * Created by Roxana on 5/13/2017.
 */

import android.*;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

import android.*;
import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback, GoogleMap.OnMapLongClickListener {

    private GoogleMap mMap;
    LocationManager locationManager;
    LocationListener locationListener;

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            if (ContextCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, locationListener);
                Location lastKnownLocation = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
                centerMapOnLocation(lastKnownLocation, "Your location");
            }
        }
    }

    public void centerMapOnLocation(Location location, String title) {

        // LatLng userLocation = new LatLng(location.getLatitude(), location.getLongitude());
        LatLng userLocation = new LatLng(44.43861, 26.04949);
        // mMap.clear();
        if (title != "Your location") {
            mMap.addMarker(new MarkerOptions().position(userLocation).title(title));
            Log.d("Marker","addMarker");
        }
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(userLocation, 12));
        // mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(userLocation,10));
        //   mMap.moveCamera(CameraUpdateFactory.newLatLng(userLocation));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }


    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        mMap.setMyLocationEnabled(true);
        mMap.setOnMapLongClickListener(this);
        mMap.getUiSettings().setZoomControlsEnabled(true);
        mMap.getUiSettings().setRotateGesturesEnabled(true);
        mMap.getUiSettings().setScrollGesturesEnabled(true);
        mMap.getUiSettings().setTiltGesturesEnabled(true);

        Intent intent = getIntent();

        if (intent.getIntExtra("placeNumber", 0) == 0) {

            // zoom in on user's location

            locationManager = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);
            locationListener = new LocationListener() {
                @Override
                public void onLocationChanged(Location location) {
                    centerMapOnLocation(location, "Your location");
                }

                @Override
                public void onStatusChanged(String s, int i, Bundle bundle) {

                }

                @Override
                public void onProviderEnabled(String s) {

                }

                @Override
                public void onProviderDisabled(String s) {

                }
            };

            if (Build.VERSION.SDK_INT < 22) {
                locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, locationListener);
            } else {
                if (ContextCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                    locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, locationListener);
                    Location lastKnownLocation = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
                    centerMapOnLocation(lastKnownLocation, "Your location");
                } else {
                    ActivityCompat.requestPermissions(this, new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION}, 1);
                }
            }
        } else {
            Location placeLocation = new Location(LocationManager.GPS_PROVIDER);
            placeLocation.setLatitude(MapsMainActivity.locations.get(intent.getIntExtra("placeNumber", 0)).latitude);
            placeLocation.setLongitude(MapsMainActivity.locations.get(intent.getIntExtra("placeNumber", 0)).longitude);
            centerMapOnLocation(placeLocation, MapsMainActivity.places.get(intent.getIntExtra("placeNumber", 0)));
        }
        // Add a marker in poli and move the camera
        LatLng poli = new LatLng(44.43861, 26.04949);
        //  mMap.addMarker(new MarkerOptions().position(poli).title("Poli"));

        mMap.addMarker(new MarkerOptions()
                .position(poli)
                .title("Poli")
                .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN)));
        int i;
        for(i=0; i<MapsMainActivity.locations.size(); i++){
            mMap.addMarker(new MarkerOptions().position(MapsMainActivity
                    .locations.get(i))
                    .title(MapsMainActivity.places.get(i))
                    .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_BLUE))
            );
        }

        mMap.moveCamera(CameraUpdateFactory.newLatLng(poli));
    }

    @Override
    public void onMapLongClick(LatLng latLng) {

        Geocoder geocoder = new Geocoder(getApplicationContext(), Locale.getDefault());
        String address = "";

        try {
            List<Address> listAddresses = geocoder.getFromLocation(latLng.latitude, latLng.longitude, 1);
            if (listAddresses != null && listAddresses.size() > 0) {
                if (listAddresses.get(0).getThoroughfare() != null) {
                    if (listAddresses.get(0).getSubThoroughfare() != null) {
                        address += listAddresses.get(0).getSubThoroughfare() + " ";
                    }
                    address += listAddresses.get(0).getThoroughfare();
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        if (address == "") {

            //  SimpleDateFormat sdf = new SimpleDateFormat("HH:mm yyyy-MM-dd");

            //    address = sdf.format(new Date());
            address = "This is not a street";
        }

        mMap.addMarker(new MarkerOptions().position(latLng).title(address));

        MapsMainActivity.places.add(address);
        MapsMainActivity.locations.add(latLng);

        MapsMainActivity.arrayAdapter.notifyDataSetChanged();
        Toast.makeText(this, "Location saved", Toast.LENGTH_SHORT).show();
    }

    public void onSearch(View view){
        EditText location_tf = (EditText) findViewById(R.id.TFaddress);
        String location = location_tf.getText().toString();
        List<Address> addressList=null;
        if(!location.equals("")){
            Geocoder geocoder = new Geocoder(this);
            try {
                addressList = geocoder.getFromLocationName(location, 1);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        Address address = addressList.get(0);
        LatLng latLng = new LatLng(address.getLatitude(), address.getLongitude());
        mMap.addMarker(new MarkerOptions().position(latLng).title(location));
        mMap.animateCamera(CameraUpdateFactory.newLatLng(latLng));
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
