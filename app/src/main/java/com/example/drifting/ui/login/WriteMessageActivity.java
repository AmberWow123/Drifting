 package com.example.drifting.ui.login;

import android.Manifest;
import android.annotation.SuppressLint;
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
import android.widget.EditText;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.Switch;
import android.os.Looper;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;
import com.example.drifting.HomeFragment;
import com.example.drifting.NavBar;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import com.example.drifting.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;

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

    FusedLocationProviderClient fusedLocationProviderClient;
    private LocationRequest locationRequest;
    private LocationCallback locationCallback;

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
        sendBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {

                //set bottle id with user id and timestamp
                @SuppressLint("SimpleDateFormat") String timeStamp = new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());
                String input_text = TextMessage.getText().toString().trim();

                // anonymous case
                if(whether_anonymous[0] > 0) {
                    //create a new bottle object
//                    String userID = "NOTAVAILABLE";
//                    //generate a random number
//                    int upperbound = 10;
//                    Random rand = new Random();
//                    int int_random = rand.nextInt(upperbound);
//                    String random_int = Integer.toString(int_random);
//                    String bottleID = (userID + timeStamp + random_int).trim();
                    String userID = fAuth.getUid();
                    String bottleID = (userID + timeStamp).trim();
                    String city = locationText.getText().toString();
                    Bottle_back this_bottle = new Bottle_back(input_text, bottleID, userID, true, city);
                    SetDatabase set = new SetDatabase();
                    set.addNewBottle(this_bottle);
                }
                //not anonymous
                else{
                    String userID = fAuth.getUid();
                    String bottleID = (userID + timeStamp).trim();
                    String city = locationText.getText().toString();
                    Bottle_back this_bottle = new Bottle_back(input_text, bottleID, userID, false, city);
                    SetDatabase set = new SetDatabase();
                    set.addNewBottle(this_bottle);
                }

                //return to the home page
                Toast.makeText(WriteMessageActivity.this, "Yay you just throw a bottle! :D", Toast.LENGTH_SHORT).show();
                openHomepageActivity();
            }
        });

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
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);


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

        // request permissions
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