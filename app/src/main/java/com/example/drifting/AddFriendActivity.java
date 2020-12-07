package com.example.drifting;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.Calendar;
import java.util.Date;

import backend.util.database.Chat;
import backend.util.database.SetDatabase;

public class AddFriendActivity extends AppCompatActivity {

    private static  String name = null;
    private static  String age = null;
    private static  String email = null;
    private static  String gender = null;
    private static  String country = null;


    //contact
    private DatabaseReference ContactsRef;
    //chats
    private DatabaseReference ChatRef;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        //contact
        ContactsRef = FirebaseDatabase.getInstance().getReference().child("Contacts");
        //chat
        ChatRef = FirebaseDatabase.getInstance().getReference().child("Chats");


        FirebaseAuth fAuth = FirebaseAuth.getInstance();
        String current_user = fAuth.getUid();


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
                Toast.makeText(AddFriendActivity.this, "Failed to retrieve user's data :(", Toast.LENGTH_SHORT).show();
            }
        });

        //retrieve and set the sender's avatar
        DatabaseReference avatarRef = FirebaseDatabase.getInstance().getReference("avatars/");
        avatarRef = avatarRef.child(fromUserID);
        ImageView profileImage;
        profileImage = findViewById(R.id.profile_image);
        avatarRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot ss : snapshot.getChildren()) {
                    String url = ss.getValue(String.class);
                    Picasso.get().load(url).into(profileImage);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(AddFriendActivity.this, "Failed to retrieve user's avatar :(", Toast.LENGTH_SHORT).show();
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
                Add_friend(current_user, fromUserID);
                Toast.makeText(AddFriendActivity.this, "Yay you just add a friend!! :D", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(AddFriendActivity.this, NavBar.class));
                finish();

            }
        });
    }


    private void Add_friend(String current_user, String receiverUserID)
    {


        Date currentTime = Calendar.getInstance().getTime();

        ContactsRef.child(current_user).child("friend_list")
                .child(receiverUserID).setValue(currentTime)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task)
                    {
                        if (task.isSuccessful())
                        {
                            ContactsRef.child(receiverUserID).child("friend_list")
                                    .child(current_user).setValue(currentTime);
                        }
                    }
                });

        //add chat
//        HashMap<String, Object> hashMap = new HashMap<>();
//        hashMap.put("sender", current_user);
//        hashMap.put("receiver", receiverUserID);
//        hashMap.put("message", "You are friends now! Let's start chatting.");
//        ChatRef.push().setValue(hashMap);
        Chat new_chat = new Chat (current_user, receiverUserID, "You are friends now! Let's start chatting.");
        SetDatabase db = new SetDatabase();
        db.addNewChat(new_chat);

    }

}