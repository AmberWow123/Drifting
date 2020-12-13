package com.example.drifting.ui.login;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.WindowManager;

import com.example.drifting.HomeFragment;
import com.example.drifting.NavBar;
import com.example.drifting.R;
import com.google.firebase.auth.FirebaseAuth;

import backend.util.container.BagData;


public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        FirebaseAuth mAuth = FirebaseAuth.getInstance();

        if(mAuth.getCurrentUser() != null){
            Intent intent = new Intent(this, NavBar.class);
            startActivity(intent);
            finish();
            return;
        }

        BagData.clear();



        // set window to full screen for this loading page
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        // start new intent to the login page
        Intent intent = new Intent(this, LoginActivity.class);

        new CountDownTimer(2000, 2000) {
            public void onFinish() {
                // When timer is finished
                // Execute your code here
                startActivity(intent);
                finish();
            }
            public void onTick(long millisUntilFinished) {
                // millisUntilFinished    The amount of time until finished.
                // *** LEAVE BLANK HERE ****
            }
        }.start();
    }
}