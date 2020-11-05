package com.example.drifting.ui.login;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.drifting.R;
import java.util.Random;

public class Homepage extends AppCompatActivity {

    // define bottle positions
    public static int[] bottleAry = {R.id.imageView1, R.id.imageView2, R.id.imageView3, R.id.imageView4,
            R.id.imageView5, R.id.imageView6, R.id.imageView7};
    // define bottle images
    public static int[] imgAry = {R.drawable.bottle1, R.drawable.bottle2, R.drawable.bottle3,
            R.drawable.bottle4};
    static boolean[] availableLocation =  {false,false,false,false,false,false,false};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_homepage);

        ImageView[] bottles = new ImageView[7];
        for (int i = 0; i < bottleAry.length; i++){
            bottles[i] = findViewById(bottleAry[i]);
            bottles[i].setVisibility(View.GONE);
        }


        Bottle bottle1 = new Bottle("123");
        bottle1.setVisible();

        Bottle bottle2 = new Bottle("123");
        bottle2.setVisible();

        Bottle bottle3 = new Bottle("123");
        bottle3.setVisible();


        //bottle1.setOnClickListener(new View.OnClickListener() {
         //   @Override
          //  public void onClick(View v) {
           //     bottle1.setImageResource(getRandomBottleImg());
           // }
        //});

    }

    public class Bottle{

        String message;
        ImageView bottleLocation;
        int imageSrc;

        // construct with a message
        public Bottle(String msg){
            message = msg;
            bottleLocation =  findViewById(getRandomBottleLocation());
            imageSrc = getRandomBottleImg();
            bottleLocation.setImageResource(imageSrc);

            bottleLocation.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    bottleLocation.setImageResource(getRandomBottleImg());
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
            int index;

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