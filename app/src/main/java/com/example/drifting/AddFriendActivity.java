package com.example.drifting;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.drifting.ui.login.ViewBottleActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

public class AddFriendActivity extends AppCompatActivity {

    private static  String name = null;
    private static  String age = null;
    private static  String email = null;
    private static  String gender = null;
    private static  String country = null;


    //contact
    private DatabaseReference ContactsRef;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        //contact
        ContactsRef = FirebaseDatabase.getInstance().getReference().child("Contacts");


        FirebaseAuth fAuth = FirebaseAuth.getInstance();
        String current_user = fAuth.getUid();


        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_friend);

        String fromUser = HomeFragment.currBottle.fromUser;
        String fromUserID = HomeFragment.currBottle.userID;

        TextView username_view = findViewById(R.id.username_view);
        username_view.setText(fromUser);


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
        ContactsRef.child(current_user).child(receiverUserID)
                .child("Contacts").setValue("Saved")
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task)
                    {
                        if (task.isSuccessful())
                        {
                            ContactsRef.child(receiverUserID).child(current_user)
                                    .child("Contacts").setValue("Saved");
                        }
                    }
                });
    }

}