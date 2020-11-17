package com.example.drifting.ui.login;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.annotation.SuppressLint;
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
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.example.drifting.HomeFragment;
import com.example.drifting.NavBar;
import com.example.drifting.R;
import com.google.firebase.auth.FirebaseAuth;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Random;

import backend.util.database.Bottle_back;
import backend.util.database.EnumD;
import backend.util.database.SetDatabase;
import backend.util.database.UserProfile;

public class WriteMessageActivity extends AppCompatActivity {

    private static final int MY_PERMISSION_REQUEST_LOCATION = 1;
    public TextView locationText;
    EditText TextMessage;
    Button sendBtn;

    //function to return to home after sending the bottle
    public void openHomepageActivity() {
        Intent intent = new Intent(this, NavBar.class);
        startActivity(intent);
        finish();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_write_message);

        //set the text and button
        locationText = findViewById(R.id.get_location_text);
        sendBtn = findViewById(R.id.button_send_button);
        TextMessage = findViewById(R.id.text_InputMessage);
        @SuppressLint("UseSwitchCompatOrMaterialCode") Switch AnonymousBtn = findViewById(R.id.switch_button);

        //get current userID
        FirebaseAuth fAuth;
        fAuth = FirebaseAuth.getInstance();
        final int[] whether_anonymous = {0};

        //check if the user switches to anonymous
        AnonymousBtn.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    whether_anonymous[0]++;
                }
                else{
                    whether_anonymous[0]--;
                }
            }
        });

        // throw the bottle when "send" is clicked
        sendBtn.setOnClickListener((new View.OnClickListener(){
            @Override
            public void onClick(View v) {

                //set bottle id with user id and timestamp
                @SuppressLint("SimpleDateFormat") String timeStamp = new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());
                String input_text = TextMessage.getText().toString().trim();

                // anonymous case
                if(whether_anonymous[0] > 0) {
                    //create a new bottle object
                    String userID = "NOTAVAILABLE";
                    //generate a random number
                    int upperbound = 10;
                    Random rand = new Random();
                    int int_random = rand.nextInt(upperbound);
                    String random_int = Integer.toString(int_random);
                    String bottleID = (userID + timeStamp + random_int).trim();
                    Bottle_back this_bottle = new Bottle_back(input_text, bottleID, userID);
                    SetDatabase set = new SetDatabase();
                    set.addNewBottle(this_bottle);
                }
                //not anonymous
                else{
                    String userID = fAuth.getUid();
                    String bottleID = (userID + timeStamp).trim();
                    Bottle_back this_bottle = new Bottle_back(input_text, bottleID, userID);
                    SetDatabase set = new SetDatabase();
                    set.addNewBottle(this_bottle);
                }

                //return to the home page
                Toast.makeText(WriteMessageActivity.this, "Yay you just throw a bottle! :D", Toast.LENGTH_SHORT).show();
                openHomepageActivity();
            }
        }));

        // request permissions
        if (ContextCompat.checkSelfPermission(WriteMessageActivity.this,
                Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED){

            if (ActivityCompat.shouldShowRequestPermissionRationale(
                    WriteMessageActivity.this, Manifest.permission.ACCESS_COARSE_LOCATION)){
                ActivityCompat.requestPermissions(WriteMessageActivity.this,
                        new String[]{Manifest.permission.ACCESS_COARSE_LOCATION},
                        MY_PERMISSION_REQUEST_LOCATION);
            }
            else {
                ActivityCompat.requestPermissions(WriteMessageActivity.this,
                        new String[]{Manifest.permission.ACCESS_COARSE_LOCATION},
                        MY_PERMISSION_REQUEST_LOCATION);
            }
        }

        //set locations
        else{
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