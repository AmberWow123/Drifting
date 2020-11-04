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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_homepage);

        final ImageView myimg = findViewById(R.id.imageView7);


        myimg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myimg.setImageResource(getRandomBottleImg());
                Log.v(getRandomBottleImg()+"", getRandomBottleImg()+"");
            }
        });

    }

    public int getRandomBottleImg(){
        int bottle;
        Random rand = new Random();
        int[] bottleAry = {R.drawable.bottle1, R.drawable.bottle2, R.drawable.bottle3,
                R.drawable.bottle4};

        bottle = bottleAry[rand.nextInt(bottleAry.length)];
        return bottle;
    }

    public int getRandomBottleLocation(){
        int location;
        Random rand = new Random();
        int[] bottleAry = {};

        location = bottleAry[rand.nextInt(bottleAry.length)];
        return location;
    }
}