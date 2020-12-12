package com.example.drifting;

import android.os.Bundle;
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
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Calendar;
import java.util.Date;

import backend.util.database.Chat;
import backend.util.database.SetDatabase;

public class AddFriendActivity extends AppCompatActivity {

    private static String [] info = new String[5];
    private static TextView [] text_render = new TextView[5];

    //contact
    private DatabaseReference ContactsRef;
    //chats
    private DatabaseReference ChatRef;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        FirebaseAuth fAuth = FirebaseAuth.getInstance();
        String current_user = fAuth.getUid();

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_friend);

        String fromUser;
        String fromUserID;

        fromUser = getIntent().getStringExtra("FriendName");
        fromUserID = getIntent().getStringExtra("FriendID");


        SetDatabase sd = new SetDatabase();
        text_render[0] = findViewById(R.id.username_view);
        text_render[1] = findViewById(R.id.email_text_view);
        text_render[2] = findViewById(R.id.gender_text_view);
        text_render[3] = findViewById(R.id.age_text_view);
        text_render[4] = findViewById(R.id.country_text_view);

        sd.add_friend_info_text(info, text_render, fromUserID);

        ImageView profileImage = findViewById(R.id.profile_image);
        sd.add_friend_avatar(profileImage, fromUserID);

        Button addFriendButton = findViewById(R.id.add_friend_button);

        addFriendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Add_friend(current_user, fromUserID);
                Toast.makeText(AddFriendActivity.this, "Yay you just add a friend!! :D", Toast.LENGTH_SHORT).show();

                finish();

            }
        });
    }


    private void Add_friend(String current_user, String receiverUserID)
    {
        Date currentTime = Calendar.getInstance().getTime();

        DatabaseReference ContactsRef = FirebaseDatabase.getInstance().getReference().child("Contacts");
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

        Chat new_chat = new Chat (current_user, receiverUserID, "You are friends now! Let's start chatting.");
        SetDatabase db = new SetDatabase();
        db.addNewChat(new_chat);

    }

}