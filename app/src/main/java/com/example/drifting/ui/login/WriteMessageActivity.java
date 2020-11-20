package com.example.drifting.ui.login;
<<<<<<< HEAD
=======

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
>>>>>>> parent of d514bbc... Merge branch 'master' of https://github.com/AmberWow123/CSE110_Drifting

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
<<<<<<< HEAD
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

=======
import android.widget.TextView;
import android.widget.Toast;

>>>>>>> parent of d514bbc... Merge branch 'master' of https://github.com/AmberWow123/CSE110_Drifting
import com.example.drifting.R;

import java.util.List;
import java.util.Locale;

public class WriteMessageActivity extends AppCompatActivity {

    private static final int MY_PERMISSION_REQUEST_LOCATION = 1;
    TextView locationText;

<<<<<<< HEAD
    Switch switch_anon;
    TextView text_view_anon;

    private static String MY_PREFS = "switch_prefs";
    private static String ANON_STATUS = "anon_on";
    private static String SWITCH_STATUS = "switch_status";

    boolean switch_status;
    boolean anon_status;

    SharedPreferences myPreferences;
    SharedPreferences.Editor myEditor;

    // for adding image
    ImageView added_image_view;
    Button added_image_button;

    // for adding video
    Button added_video_button;

    private static final int IMAGE_PICK_CODE = 1000;
    private static final int VIDEO_PICK_CODE = 2000;
    private static final int PERMISSION_CODE_IMAGE = 1001;
    private static final int PERMISSION_CODE_VIDEO = 2001;

=======
>>>>>>> parent of d514bbc... Merge branch 'master' of https://github.com/AmberWow123/CSE110_Drifting
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_write_message);
        locationText = findViewById(R.id.get_location_text);
<<<<<<< HEAD

        // adding image
        added_image_view = findViewById(R.id.image_view_added);
        added_image_button = findViewById(R.id.button_Add_Image);

        //adding video
        added_video_button = findViewById(R.id.button_Add_Video_Link);

        added_image_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // check runtime permission
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    if (checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_DENIED) {
                        // permission not granted, request it.
                        String[] permissions = {Manifest.permission.READ_EXTERNAL_STORAGE};
                        // show popup for runtime permission
                        requestPermissions(permissions, PERMISSION_CODE_IMAGE);
                    } else {
                        // permission already granted
                        pickImageFromGallery();
                    }
                }
                else {
                    // system os is less than marshmallow
                    pickImageFromGallery();
                }
            }
        });

        //clicks on add video button
        added_video_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // check runtime permission
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    if (checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_DENIED) {
                        // permission not granted, request it.
                        String[] permissions = {Manifest.permission.READ_EXTERNAL_STORAGE};
                        // show popup for runtime permission
                        requestPermissions(permissions, PERMISSION_CODE_VIDEO);
                    } else {
                        // permission already granted
                        pickVideoFromGallery();
                    }
                }
                else {
                    // system os is less than marshmallow
                    pickVideoFromGallery();
                }
            }
        });
=======
>>>>>>> parent of d514bbc... Merge branch 'master' of https://github.com/AmberWow123/CSE110_Drifting


<<<<<<< HEAD
        myPreferences = getSharedPreferences(MY_PREFS, MODE_PRIVATE);
        myEditor = getSharedPreferences(MY_PREFS, MODE_PRIVATE).edit();

        switch_status = myPreferences.getBoolean(SWITCH_STATUS, false);
        anon_status = myPreferences.getBoolean(ANON_STATUS, false);

        switch_anon.setChecked(switch_status);

        if(anon_status) {
            text_view_anon.setText("ON!!");
        } else {
            text_view_anon.setText("OFF!");
        }

        switch_anon.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean b) {
                if (buttonView.isChecked()) {
                    text_view_anon.setText("ON!!");

                    myEditor.putBoolean(SWITCH_STATUS, true);
                    myEditor.putBoolean(ANON_STATUS, true);
                    myEditor.apply();
                    switch_anon.setChecked(true);
                } else {
                    text_view_anon.setText("OFF!");

                    myEditor.putBoolean(SWITCH_STATUS, false);
                    myEditor.putBoolean(ANON_STATUS, false);
                    myEditor.apply();
                    switch_anon.setChecked(false);
                }
            }
        });

=======
>>>>>>> parent of d514bbc... Merge branch 'master' of https://github.com/AmberWow123/CSE110_Drifting
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