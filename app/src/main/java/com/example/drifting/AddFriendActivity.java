package com.example.drifting;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class AddFriendActivity extends AppCompatActivity {

    private static  String name = null;
    private static  String age = null;
    private static  String email = null;
    private static  String gender = null;
    private static  String country = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_friend);

        String fromUser = HomeFragment.currBottle.fromUser;
        String fromUserID = HomeFragment.currBottle.userID;

        //retrieve user data from database
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference().child("user");
        DatabaseReference this_user_data = reference.child(fromUserID);

        this_user_data.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                name = (snapshot.child("user_name").getValue() != null) ? snapshot.child("user_name").getValue().toString() : "unspecified";
                gender = (snapshot.child("user_gender").getValue() != null) ? snapshot.child("user_gender").getValue().toString() : "unspecified";
                country = (snapshot.child("user_country").getValue() != null) ? snapshot.child("user_country").getValue().toString() : "unspecified";
                age = (snapshot.child("user_age").getValue() != null) ? snapshot.child("user_age").getValue().toString() : "unspecified";
                email = (snapshot.child("user_email").getValue() != null) ? snapshot.child("user_email").getValue().toString() : "unspecified";

                Log.d("", "??????????????????????????????user info:" + name + gender + country + age + email);

                //set bottle's user's name, email, gender, age and country
                TextView username_view = findViewById(R.id.username_view);
                username_view.setText(name);

                TextView email_view = findViewById(R.id.email_text_view);
                email_view.setText(email);

                TextView gender_view = findViewById(R.id.gender_text_view);
                gender_view.setText(gender);

                TextView age_view = findViewById(R.id.age_text_view);
                age_view.setText(age);

                TextView country_view = findViewById(R.id.country_text_view);
                country_view.setText(country);

            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(AddFriendActivity.this, "Failed to retrieve user data :(", Toast.LENGTH_SHORT).show();
            }
        });

        Log.d("", "user info:" + name + gender + country + age + email);


        Button addFriendButton = findViewById(R.id.add_friend_button);
        /*
        if fromUserID is already in user's friend list  //TODO: NEED USER FRIEND LIST FROM DATABASE
            addFriendButton.setVisibility(View_GONE);
         */
        addFriendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // TODO: ADD FRIEND

            }
        });
    }
}