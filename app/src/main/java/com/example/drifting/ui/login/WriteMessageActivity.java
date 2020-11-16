package com.example.drifting.ui.login;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.drifting.HomeFragment;
import com.example.drifting.NavBar;
import com.example.drifting.R;

import java.util.List;
import java.util.Locale;

import backend.util.database.Bottle_back;
import backend.util.database.EnumD;
import backend.util.database.SetDatabase;
import backend.util.database.UserProfile;

public class WriteMessageActivity extends AppCompatActivity {

    private static final int MY_PERMISSION_REQUEST_LOCATION = 1;
    public TextView locationText;
    EditText TextMessage;
    Button sendBtn;

    //function to return to home
    public void openHomepageActivity() {
        Intent intent = new Intent(this, NavBar.class);
        startActivity(intent);
        finish();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_write_message);

        // set the text and button
        //locationText = findViewById(R.id.get_location_text);
        sendBtn = findViewById(R.id.button_send_button);
        TextMessage = findViewById(R.id.text_InputMessage);

        sendBtn.setOnClickListener((new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                //TODO: set bottle index
                int bottle_index = 1;
                String input_text = TextMessage.getText().toString().trim();
                //create a new bottle object
                Bottle_back this_bottle = new Bottle_back(input_text, bottle_index);
                SetDatabase set = new SetDatabase();
                set.addNewBottle(this_bottle);

                //return to the previous page
                openHomepageActivity();

            }
        }));




//        if (ContextCompat.checkSelfPermission(WriteMessageActivity.this,
//                Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED){
//
//            if (ActivityCompat.shouldShowRequestPermissionRationale(
//                    WriteMessageActivity.this, Manifest.permission.ACCESS_COARSE_LOCATION)){
//                ActivityCompat.requestPermissions(WriteMessageActivity.this,
//                        new String[]{Manifest.permission.ACCESS_COARSE_LOCATION},
//                        MY_PERMISSION_REQUEST_LOCATION);
//            }
//            else {
//                ActivityCompat.requestPermissions(WriteMessageActivity.this,
//                        new String[]{Manifest.permission.ACCESS_COARSE_LOCATION},
//                        MY_PERMISSION_REQUEST_LOCATION);
//            }
//
//        }
//
//        else{
//            LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
//            Location location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
//            try{
//                locationText.setText(hereLocation(location.getLatitude(), location.getLongitude()));
//            }
//            catch (Exception e){
//                e.printStackTrace();
//                Toast.makeText(WriteMessageActivity.this, "Not found!", Toast.LENGTH_SHORT).show();
//            }
//        }

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode){
            case MY_PERMISSION_REQUEST_LOCATION: {
                if (grantResults.length >0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                    if(ContextCompat.checkSelfPermission(WriteMessageActivity.this,
                            Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED){

                        LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
                        Location location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
                        try{
                            locationText.setText(hereLocation(location.getLatitude(), location.getLongitude()));
                        }
                        catch (Exception e){
                            e.printStackTrace();
                            Toast.makeText(WriteMessageActivity.this, "Not found!", Toast.LENGTH_SHORT).show();
                        }
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