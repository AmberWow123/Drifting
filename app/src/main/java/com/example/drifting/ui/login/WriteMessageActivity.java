package com.example.drifting.ui.login;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Looper;
import android.widget.TextView;
import android.widget.Toast;

import com.example.drifting.R;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;

import java.util.List;
import java.util.Locale;

public class WriteMessageActivity extends AppCompatActivity {

    private static final int MY_PERMISSION_REQUEST_LOCATION = 1;
    TextView locationText;

    FusedLocationProviderClient fusedLocationProviderClient;
    private LocationRequest locationRequest;
    private LocationCallback locationCallback;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_write_message);
        locationText = findViewById(R.id.get_location_text);

        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);


        if (ContextCompat.checkSelfPermission(WriteMessageActivity.this,
                Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

            if (ActivityCompat.shouldShowRequestPermissionRationale(
                    WriteMessageActivity.this, Manifest.permission.ACCESS_FINE_LOCATION)) {
                ActivityCompat.requestPermissions(WriteMessageActivity.this,
                        new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                        MY_PERMISSION_REQUEST_LOCATION);
            } else {
                ActivityCompat.requestPermissions(WriteMessageActivity.this,
                        new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                        MY_PERMISSION_REQUEST_LOCATION);
            }

        } else {

            fusedLocationProviderClient.getLastLocation()
                    .addOnSuccessListener(this, new OnSuccessListener<Location>() {
                        @SuppressLint("MissingPermission")
                        @Override
                        public void onSuccess(Location location) {
                            // Got last known location. In some rare situations this can be null.
                            if (location != null) {
                                locationText.setText(hereLocation(location.getLatitude(), location.getLongitude()));
                            } else {
                                Toast.makeText(WriteMessageActivity.this, "Not found!", Toast.LENGTH_SHORT).show();

                                locationRequest = LocationRequest.create();
                                locationRequest.setPriority(LocationRequest.PRIORITY_LOW_POWER);
                                locationRequest.setInterval(2 * 1000);
                                locationCallback = new LocationCallback() {
                                    @Override
                                    public void onLocationResult(LocationResult locationResult) {
                                        if (locationResult == null) {
                                            return;
                                        }
                                        for (Location mlocation : locationResult.getLocations()) {
                                            if (mlocation != null) {
                                                locationText.setText(hereLocation(mlocation.getLatitude(), mlocation.getLongitude()));
                                                fusedLocationProviderClient.removeLocationUpdates(locationCallback);
                                            }
                                        }
                                    }
                                };

                                fusedLocationProviderClient.requestLocationUpdates(locationRequest, locationCallback, Looper.getMainLooper());

                            }
                        }
                    });

         //   LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
         //   Location location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
         //   try{
         //       locationText.setText(hereLocation(location.getLatitude(), location.getLongitude()));
         //   }
         //   catch (Exception e){
         //       e.printStackTrace();
         //       Toast.makeText(WriteMessageActivity.this, "Not found!", Toast.LENGTH_SHORT).show();
         //   }
        }

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode){
            case MY_PERMISSION_REQUEST_LOCATION: {
                if (grantResults.length >0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                    if(ContextCompat.checkSelfPermission(WriteMessageActivity.this,
                            Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED){

                        fusedLocationProviderClient.getLastLocation()
                                .addOnSuccessListener(this, new OnSuccessListener<Location>() {
                                    @Override
                                    public void onSuccess(Location location) {
                                        // Got last known location. In some rare situations this can be null.
                                        if (location != null) {
                                            locationText.setText(hereLocation(location.getLatitude(), location.getLongitude()));
                                        }
                                        else{
                                            Toast.makeText(WriteMessageActivity.this, "Not found!", Toast.LENGTH_SHORT).show();
                                        }
                                    }
                                });


                    //    LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
                    //    Location location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
                    //    try{
                    //        locationText.setText(hereLocation(location.getLatitude(), location.getLongitude()));
                    //    }
                    //    catch (Exception e){
                    //        e.printStackTrace();
                    //        Toast.makeText(WriteMessageActivity.this, "Not found!", Toast.LENGTH_SHORT).show();
                    //    }
                    }
                }
                else {
                    Toast.makeText(WriteMessageActivity.this, "No permission granted", Toast.LENGTH_SHORT).show();
                }
            }
        }
    }

    // get closest city name
    public String hereLocation(double lat, double lon){
        String curCity = "";

        Geocoder geocoder = new Geocoder(WriteMessageActivity.this, Locale.getDefault());
        List<Address> addressList;
        try{
            addressList = geocoder.getFromLocation(lat,lon,1);
            if (addressList.size()>0) {
                curCity = addressList.get(0).getLocality();
            }
        }
        catch (Exception e){
            e.printStackTrace();;
        }
        return  curCity;
    }
}