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
import androidx.appcompat.app.AppCompatActivity;

import com.example.drifting.ui.login.ViewBottleActivity;
import com.example.drifting.ui.login.WriteMessageActivity;

import java.util.Random;

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
        return inflater.inflate(R.layout.fragment_home, container, false);
//        View V = inflater.inflate(R.layout.fragment_home, container, false);
//        Button writeMessageButton = (Button) V.findViewById(R.id.write_message_button);
//        return V;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Log.d("gnereed id", "id is "+R.id.generate_button);
        final Button generate_button = getView().findViewById(R.id.generate_button);

        final Button writeMessageButton = getView().findViewById(R.id.write_message_button);

        ImageView[] bottles = new ImageView[7];
        for (int i = 0; i < bottleAry.length; i++){
            bottles[i] = getView().findViewById(bottleAry[i]);
            bottles[i].setVisibility(View.GONE);
        }


        generate_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bottle bottle1 = new Bottle("123");
                bottle1.setVisible();

                Bottle bottle2 = new Bottle("123");
                bottle2.setVisible();

                Bottle bottle3 = new Bottle("123");
                bottle3.setVisible();
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

        String message;
        ImageView bottleLocation;
        int imageSrc;
        int index;
        AnimationDrawable bottleAnimation;

        // construct with a message
        public Bottle(String msg){
            message = msg;
            bottleLocation =  getView().findViewById(getRandomBottleLocation());
            imageSrc = getRandomBottleImg();
            bottleLocation.setBackgroundResource(imageSrc);

            bottleAnimation = (AnimationDrawable) bottleLocation.getBackground();
            bottleAnimation.start();

            bottleLocation.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    startActivity(new Intent(getActivity(), ViewBottleActivity.class));
                    availableLocation[index] = false;
                    bottleAnimation.stop();
                    bottleLocation.setVisibility(View.GONE);
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
            index = rand.nextInt(bottleAry.length);
            while (availableLocation[index]){
                index = rand.nextInt(bottleAry.length);
            }

            location = bottleAry[index];
            availableLocation[index] = true;

            // for debug
            //Log.d("index", "index = "+index);
            //Log.d("bottleloc", "id = "+bottleAry[index]);
            //Log.d("avail", "avial = "+availableLocation[index]);

            return location;
        }

        public void setVisible(){
            bottleLocation.setVisibility(View.VISIBLE);
        }

    }
}