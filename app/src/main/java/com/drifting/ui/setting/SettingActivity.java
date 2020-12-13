package com.drifting.ui.setting;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.drifting.ui.account.LoginActivity;
import com.example.drifting.R;
import com.google.firebase.auth.FirebaseAuth;

import com.drifting.container.BagData;

public class SettingActivity extends AppCompatActivity {

    Button logout_button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);

        logout_button = findViewById(R.id.logout_button);

        Intent intent = new Intent(this, LoginActivity.class);


        logout_button.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAuth fAuth = FirebaseAuth.getInstance();
                    fAuth.signOut();
                    BagData.clear();
                    startActivity(intent);
                    finish();
                    // todo: logout token and end current activity. Make sure that the user cannot go back to the setting page.
                    Toast.makeText(SettingActivity.this, "Logout Succeed", Toast.LENGTH_LONG).show();
            }
        });
    }
}