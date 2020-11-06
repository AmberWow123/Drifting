package com.example.drifting.ui.login;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.drifting.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class ProfileActivity extends AppCompatActivity {

    Button publicpre;
    int flag;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        publicpre =  findViewById(R.id.publicpre);
        flag = 0;
        publicpre.setOnClickListener(new Button.OnClickListener() {
            public void onClick(View v) {
                switch(flag){
                    case 0:
                        publicpre.setActivated(false);
                        flag = 1;
                        break;
                    case 1:
                        publicpre.setActivated(true);
                        flag = 0;
                        break;
                }
            }
        });
    }
}