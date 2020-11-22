package com.example.drifting;

import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

import com.example.drifting.ui.login.ViewBottleActivity;
import com.example.drifting.ui.login.WriteMessageActivity;

import java.util.Dictionary;
import java.util.Hashtable;
import java.util.Random;
import java.util.Vector;

import backend.util.database.Bottle_back;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link HomeFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class HomeFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    public static Bottle currBottle;

    protected View mView;
    final public int BOTTLE_MAX = 7;

    public HomeFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Home.
     */
    // TODO: Rename and change types and number of parameters
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

    static boolean[] availableLocation =  {false,false,false,false,false,false,false};
    static Vector<Bottle> bottleList = new Vector<Bottle>();





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

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Log.d("gnereed id", "id is "+R.id.generate_button);
        final Button generate_button = getView().findViewById(R.id.generate_button);
        final Button writeMessageButton = getView().findViewById(R.id.compose_button);


        ImageView[] bottles = new ImageView[7];
        for (int i = 0; i < bottleAry.length; i++){
            bottles[i] = getView().findViewById(bottleAry[i]);
            bottles[i].setVisibility(View.GONE);
        }

        // **** codes below must change in correspondence to Bottle's constructor ****
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
        // **** **** **** **** **** **** **** **** **** **** **** **** **** ****

        generate_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (bottleList.size() < BOTTLE_MAX) {
                    int randomNum;
                    Random rand = new Random();
                    randomNum = rand.nextInt(10);
                    Bottle bottle = new Bottle(randomNum+"", bottleList.size());
                    bottle.setVisible();
                    bottleList.add(bottle);
                    Log.d(" Bottle content is :", " " + randomNum);
                    Log.d(" BottleList size is :", " " + bottleList.size());
                    Log.d(" vector contains ", bottleList.toString());
                }
            }
        });

        writeMessageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), WriteMessageActivity.class);
                startActivity(intent);
            }
        });

    }


    public class Bottle{

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