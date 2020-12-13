package com.drifting.ui.account;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.WindowManager;

import com.drifting.ui.NavBar;
import com.example.drifting.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import com.drifting.container.BagData;
import com.drifting.container.DrifterData;
import com.drifting.database.SetDatabase;


public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        FirebaseAuth mAuth = FirebaseAuth.getInstance();

        if(mAuth.getCurrentUser() != null){
            BagData.clear();
            DatabaseReference UserRef = FirebaseDatabase.getInstance().getReference().child("user").child(mAuth.getCurrentUser().getUid());
            SetDatabase sd = new SetDatabase();
            sd.get_sent_bottles(BagData.sentBottle);
            sd.get_picked_bottles(BagData.pickedBottle);
            UserRef.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    String name = snapshot.child("user_name").getValue() != null ? snapshot.child("user_name").getValue().toString() : "unspecified";
                    DrifterData.username = name;
                    // Toast.makeText(LoginActivity.this, "Welcome, " + mAuth.getCurrentUser().getUid(), Toast.LENGTH_LONG).show();
                    UserRef.removeEventListener(this);
                }
                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });

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