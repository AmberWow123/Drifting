package com.drifting.ui.account;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.WindowManager;

<<<<<<< Updated upstream:app/src/main/java/com/example/drifting/ui/login/MainActivity.java
import com.example.drifting.R;
=======
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
>>>>>>> Stashed changes:app/src/main/java/com/drifting/ui/account/MainActivity.java


public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

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