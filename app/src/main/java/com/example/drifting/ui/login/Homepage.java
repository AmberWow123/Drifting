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

    int[] bottleAry = {R.id.imageView1, R.id.imageView2, R.id.imageView3, R.id.imageView4,
            R.id.imageView5, R.id.imageView6, R.id.imageView7};

    int[] imgAry = {R.drawable.bottle1, R.drawable.bottle2, R.drawable.bottle3,
            R.drawable.bottle4};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_homepage);

        ImageView[] bottles = new ImageView[7];
        for (int i = 0; i < bottleAry.length; i++){
            bottles[i] = findViewById(bottleAry[i]);
            bottles[i].setVisibility(View.GONE);
        }

        ImageView bottle1 = findViewById(getRandomBottleLocation());
        bottle1.setImageResource(getRandomBottleImg());
        bottle1.setVisibility(View.VISIBLE);


        bottle1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bottle1.setImageResource(getRandomBottleImg());
                Log.v(getRandomBottleImg()+"", getRandomBottleImg()+"");
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

        location = bottleAry[rand.nextInt(bottleAry.length)];
        return location;
    }
}