package com.example.drifting;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.drawable.AnimationDrawable;
import android.location.Location;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;

import com.example.drifting.ui.login.ViewBottleActivity;
import com.example.drifting.ui.login.WriteMessageActivity;

import com.google.android.gms.location.*;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Random;
import java.util.Vector;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import backend.util.bottleProvider.BottleProvider;
import backend.util.database.Bottle_back;


public class HomeFragment extends Fragment {


    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";


    private String mParam1;
    private String mParam2;
    private int nextBottleIndex = 0;
    public static Bottle currBottle;

    private BottleProvider provider;

    protected View mView;
    final public int BOTTLE_MAX = 7;

    public HomeFragment() {
        // Required empty public constructor
    }

    public static HomeFragment newInstance(String param1, String param2) {
        HomeFragment fragment = new HomeFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);

        return fragment;
    }


    // define bottle positions
    public static int[] bottleAry = {R.id.imageView1, R.id.imageView2, R.id.imageView3, R.id.imageView4,
            R.id.imageView5, R.id.imageView6, R.id.imageView7};
    // define bottle images
    public static int[] imgAry = {R.drawable.animated_bottle1, R.drawable.animated_bottle2, R.drawable.animated_bottle3,
            R.drawable.animated_bottle4};

    static boolean[] availableLocation = {false, false, false, false, false, false, false};
    static Vector<Bottle> bottleList = new Vector<>();
    static Bottle_back[] nextBottles = new Bottle_back[7];


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_home, container, false);

        mView = rootView;
        return rootView;
    }


    /**
     *      onViewCreated() is the class where changes to the view are defined. DO NOT make
     *  changes to any View Elements (Button, TextView, etc.) outside of onViewCreated() due
     *  to the life cycle of Fragments.
     *
     *      E.g. If you want to set the text of a TextView, do it here. The line of code
     *  "findViewById()" only works if it is written in this method.
     */
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        provider = new BottleProvider(Executors.newFixedThreadPool(5), getContext(), getActivity());


        LocationRequest mLocationRequest = LocationRequest.create();
        mLocationRequest.setInterval(60000);
        mLocationRequest.setFastestInterval(5000);
        mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        LocationCallback mLocationCallback = new LocationCallback() {
            @Override
            public void onLocationResult(LocationResult locationResult) {
                if (locationResult == null) {
                    Log.e("LOC", "Unable to obtain current location");
                    provider.serveNextBottles();
                    return;
                }
                for (Location location : locationResult.getLocations()) {
                    if (location != null) {
                        Log.e("LOC", "Successfully obtained current location");
                        provider.serveNextBottles();
                    }
                }
            }
        };

        if (ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.ACCESS_COARSE_LOCATION,
                    Manifest.permission.ACCESS_FINE_LOCATION}, 6666);
        }

        LocationServices.getFusedLocationProviderClient(getContext()).requestLocationUpdates(mLocationRequest, mLocationCallback, null);



        super.onViewCreated(view, savedInstanceState);
        Log.d("gnereed id", "id is "+R.id.generate_button);
        final Button generate_button = getView().findViewById(R.id.generate_button);
        final Button writeMessageButton = getView().findViewById(R.id.compose_button);


        /**
         *  We have a static Vector, a list of bottles, stored in bottleList.
         */
        ImageView[] bottles = new ImageView[7];
        for (int i = 0; i < bottleAry.length; i++){
            bottles[i] = getView().findViewById(bottleAry[i]);
            bottles[i].setVisibility(View.GONE);
        }

        // **** codes below must change in correspondence to Bottle's constructor ****
        /**
         * The lines below tell Android to create every single bottle stored in the bottleList.
         * The listener for each bottle must set up individually. Therefore any changes to the
         * Bottle Class must be duplicated here.
         */
        Log.e(" mView : ", mView.toString());
        for (int i = 0; i < bottleList.size(); i ++) {
            Bottle myBottle = bottleList.get(i);
            ImageView bottleView = mView.findViewById(myBottle.locationID);

            bottleView.setBackgroundResource(myBottle.imageSrc);
            bottleView.setVisibility(View.VISIBLE);
            myBottle.bottleAnimation = (AnimationDrawable) bottleView.getBackground();
            myBottle.bottleAnimation.start();

            bottleView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    currBottle = myBottle;
                    startActivity(new Intent(getActivity(), ViewBottleActivity.class));
                    availableLocation[myBottle.avail_index] = false;
                    myBottle.bottleAnimation.stop();
                    bottleView.setVisibility(View.GONE);
                    bottleList.remove(myBottle);
                    Log.d(" BottleList size is :" , " " + bottleList.size());
                    Log.d(" vector contains ", bottleList.toString());
                }
            });

        }

        Log.e("POP", "WAITING FOR POPULATION");
        (new Handler()).postDelayed(new Runnable() {
            @Override
            public void run() {
                provider.populateNextBottles(nextBottles);
                Log.e("POP", "POPULATE DONE");
            }
        }, 5000);
        // **** **** **** **** **** **** **** **** **** **** **** **** **** ****

        /**
         *  the generate button is for debugging purpose.
         */
        generate_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(!provider.locationLess) {
                    if (bottleList.size() < BOTTLE_MAX) {
                        if(nextBottleIndex == 0 && nextBottles[nextBottleIndex] == null){
                            provider.populateNextBottles(nextBottles);
                        }
                        if(nextBottles[nextBottleIndex] == null){
                            for(int i = 0; i < 7; i++){
                                nextBottles[i] = null;
                            }
                            provider.serveNextBottles();
                            nextBottleIndex = 0;
                            Toast.makeText(getContext(), "There are no more bottle in the sea. Please wait a few moments.", Toast.LENGTH_SHORT).show();
                            return;
                        }

                        Bottle bottle_get = new Bottle(nextBottles[nextBottleIndex++], bottleList.size());
                        bottle_get.comment = "filler comment";
                        bottle_get.setVisible();
                        bottleList.add(bottle_get);
                        Log.d(" Bottle content is :", " " + bottle_get.message);
                        Log.d(" BottleList size is :", " " + bottleList.size());
                        Log.d(" vector contains ", bottleList.toString());

                        if(nextBottleIndex == 7){
                            nextBottleIndex = 0;
                            provider.populateNextBottles(nextBottles);
                        }
                    }


                    provider.serveNextBottles();
                }
                else {
                    if (bottleList.size() < BOTTLE_MAX) {
                        //get database reference
                        DatabaseReference reference = FirebaseDatabase.getInstance().getReference().child("bottle");
                        //get current userID
                        FirebaseAuth fAuth;
                        fAuth = FirebaseAuth.getInstance();


                        reference.orderByChild("isViewed").equalTo(false).addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot snapshot) {
                                for (DataSnapshot snapshot1 : snapshot.getChildren()) {
                                    Bottle_back this_bottle = snapshot1.getValue(Bottle_back.class);
                                    //String bottleID = this_bottle.getBottleID();
                                    String userID = fAuth.getUid();

                                    //check if the bottle is viewed
                                    if(this_bottle.getIsViewed()) {
                                        Log.d("isViewed","A viewed bottle was returned");
                                        continue;
                                    }

                                    //check if the bottle is from the same user
                                    if(this_bottle.getUserID().equals(userID)){
                                        continue;
                                    }

                                    else {

                                        Bottle bottle_get = new Bottle(this_bottle, bottleList.size());
                                        bottle_get.comment = "filler comment";
                                        bottle_get.setVisible();
                                        bottleList.add(bottle_get);
                                        Log.d(" Bottle content is :", " " + bottle_get.message);
                                        Log.d(" BottleList size is :", " " + bottleList.size());
                                        Log.d(" vector contains ", bottleList.toString());
                                        reference.removeEventListener(this);
                                        break;
                                    }
                                }
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError error) {

                            }
                        });
                        Log.d(" BottleList size is gg:", " " + bottleList.size());
                    }
                }
            }
        });

        /**
         * write a new message.
         */
        writeMessageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), WriteMessageActivity.class);
                startActivity(intent);
            }
        });

    }


    public class Bottle{

        /**
         *  there sre more properties to be added
         *  Note:
         *      LocationID is not the user location. please refer to city
         *      fromUser is the thrower's name (this should be changed to user ID)
         */
        public Bottle self;
        public String message;
        public ImageView bottleView;
        public int imageSrc;
        public int avail_index;
        public String fromUser;
        public int bottle_index;
        public int locationID;
        public String city;
        public AnimationDrawable bottleAnimation;
        public String comment;


        /**
         * This constructor is not for deployment. Please refer to the other constructor
         */
        // This a constructor  FOR TESTING AND DEBUG
        // construct with a message and bottle index
        public Bottle(String msg, int bottle_index){
            // setting for debug
            fromUser = "Gary";
            city = "San Francisco";
            //

            self = this;
            message = msg;
            this.bottle_index = bottle_index;
            locationID = getRandomBottleLocation();
            bottleView =  getView().findViewById(locationID);
            imageSrc = getRandomBottleImg();
            bottleView.setBackgroundResource(imageSrc);


            bottleAnimation = (AnimationDrawable) bottleView.getBackground();
            bottleAnimation.start();

            bottleView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    currBottle = self;
                    startActivity(new Intent(getActivity(), ViewBottleActivity.class));
                    availableLocation[avail_index] = false;
                    bottleAnimation.stop();
                    bottleView.setVisibility(View.GONE);
                    bottleList.remove(self);

                    Log.d(" BottleList size is :" , " " + bottleList.size());
                    Log.d(" vector contains ", bottleList.toString());
                }
            });

        }

        /**
         * This is the constructor that should be implemented with backend. It takes a
         * bottle_back object and creates a bottle for the frontend. For bottle_index just
         * pass in bottleList.size(). 
         */
        // ACTUAL CONSTRUCTOR: construct with a bottle_back and bottle index
        public Bottle(Bottle_back bottleBack, int bottle_index){
            self = this;
            message = bottleBack.message;
            city = bottleBack.city;
            this.bottle_index = bottle_index;
            locationID = getRandomBottleLocation();
            bottleView =  getView().findViewById(locationID);
            imageSrc = getRandomBottleImg();
            bottleView.setBackgroundResource(imageSrc);


            bottleAnimation = (AnimationDrawable) bottleView.getBackground();
            bottleAnimation.start();

            bottleView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    startActivity(new Intent(getActivity(), ViewBottleActivity.class));
                    availableLocation[avail_index] = false;
                    bottleAnimation.stop();
                    bottleView.setVisibility(View.GONE);
                    bottleList.remove(self);

                    Log.d(" BottleList size is :" , " " + bottleList.size());
                    Log.d(" vector contains ", bottleList.toString());
                }
            });
        }

        public int getRandomBottleImg(){
            int bottle;
            Random rand = new Random();

            bottle = imgAry[rand.nextInt(imgAry.length)];
            return bottle;
        }

        public int getRandomBottleLocation(){
            int location;

            Random rand = new Random();
            avail_index = rand.nextInt(bottleAry.length);
            while (availableLocation[avail_index] && bottleList.size() <BOTTLE_MAX){
                avail_index = rand.nextInt(bottleAry.length);
            }

            location = bottleAry[avail_index];
            availableLocation[avail_index] = true;

            return location;
        }

        public void setVisible(){
            bottleView.setVisibility(View.VISIBLE);
        }

    }
}