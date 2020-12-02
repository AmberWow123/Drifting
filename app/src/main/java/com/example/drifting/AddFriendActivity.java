package com.example.drifting;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class AddFriendActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_friend);

        String fromUser = HomeFragment.currBottle.fromUser;
        String fromUserID = HomeFragment.currBottle.userID;

        TextView username_view = findViewById(R.id.username_view);
        username_view.setText(fromUser);

        Button addFriendButton = findViewById(R.id.add_friend_button);
        addFriendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // TODO: ADD FRIEND

            }
        });
    }
}