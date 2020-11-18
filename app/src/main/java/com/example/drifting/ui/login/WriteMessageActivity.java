package com.example.drifting.ui.login;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.media.ThumbnailUtils;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
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

import com.example.drifting.R;

import java.util.List;
import java.util.Locale;

public class WriteMessageActivity extends AppCompatActivity {

    private static final int MY_PERMISSION_REQUEST_LOCATION = 1;
    TextView locationText;

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_write_message);
        locationText = findViewById(R.id.get_location_text);

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

        // switch button
        switch_anon = findViewById(R.id.switch_button);
        text_view_anon = findViewById(R.id.text_is_anon);

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

    private void pickImageFromGallery() {
        // intent to pick image
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType("image/*");
        startActivityForResult(intent, IMAGE_PICK_CODE);
    }

    private void pickVideoFromGallery() {
        // intent to pick video
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType("video/*");
        startActivityForResult(Intent.createChooser(intent,"Select Video"), VIDEO_PICK_CODE);
    }

    // handle result of runtime permission
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
            // adding image
            case PERMISSION_CODE_IMAGE: {
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    // permission was granted
                    pickImageFromGallery();
                }
                else {
                    // permission was denied
                    Toast.makeText(this, "Permission denied...!", Toast.LENGTH_SHORT).show();
                }
            }

            // adding video
            case PERMISSION_CODE_VIDEO: {
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    // permission was granted
                    pickVideoFromGallery();
                }
                else {
                    // permission was denied
                    Toast.makeText(this, "Permission denied...!", Toast.LENGTH_SHORT).show();
                }
            }
        }
    }

    // handle result of picked image
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && requestCode == IMAGE_PICK_CODE) {
            // set image to image view
            added_image_view.setImageURI(data.getData());
        }
        if (resultCode == RESULT_OK && requestCode == VIDEO_PICK_CODE) {
            // set video preview to image view

            Uri selectedImageUri = data.getData();

            // MEDIA GALLERY
            String selectedImagePath = getPath(selectedImageUri);
            if (selectedImagePath != null) {
                Bitmap thumbnail = ThumbnailUtils.createVideoThumbnail(selectedImagePath, MediaStore.Images.Thumbnails.MINI_KIND);
                added_image_view.setImageBitmap(thumbnail);
            }
        }
    }

    // get the path of video on the drive
    String getPath(Uri uri) {
        String[] projection = { MediaStore.Video.Media.DATA };
        Cursor cursor = getContentResolver().query(uri, projection, null, null, null);
        if (cursor != null) {
            // HERE YOU WILL GET A NULLPOINTER IF CURSOR IS NULL
            // THIS CAN BE, IF YOU USED OI FILE MANAGER FOR PICKING THE MEDIA
            int column_index = cursor
                    .getColumnIndexOrThrow(MediaStore.Video.Media.DATA);
            cursor.moveToFirst();
            return cursor.getString(column_index);
        } else
            return null;
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