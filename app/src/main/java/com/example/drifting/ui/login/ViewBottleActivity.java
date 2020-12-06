package com.example.drifting.ui.login;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.drifting.HomeFragment;
import com.example.drifting.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

import java.util.HashMap;
import java.util.Map;

import backend.util.database.Bottle_back;

public class ViewBottleActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_bottle);

        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);

        int width=dm.widthPixels;
        int height = dm.heightPixels;

        // set canvas width and height.
        getWindow().setLayout((int)(width*1), (int)(height*0.75));

        String msg = "";
        String fromUser = "";
        String city = "";
        String comment = "";
        String bottleID = "";
        String fromUserID = "";
        String pictureURL = null;
        String videoURL = null;
        FirebaseAuth fAuth = FirebaseAuth.getInstance();
        String current_user = fAuth.getUid();

        if (HomeFragment.currBottle != null) {
             msg = HomeFragment.currBottle.message;
             fromUser = HomeFragment.currBottle.fromUser;
             city = HomeFragment.currBottle.city;
             comment = HomeFragment.currBottle.comment;
             bottleID = HomeFragment.currBottle.bottleID;
             fromUserID = HomeFragment.currBottle.userID;
             pictureURL = HomeFragment.currBottle.pictureDownloadURL;
             if(pictureURL!=null) Log.d("feafiawn",pictureURL);
             videoURL = HomeFragment.currBottle.videoDownloadURL;
        }

                    //check if the bottle is viewed
                    if(this_bottle.getIsViewed()) {
                        continue;
                    }

                    if(this_bottle.getUserID().equals(userID)){
                        continue;
                    }

                    else {
                        String msg = this_bottle.getMessage();
                        String fromUser = this_bottle.getUserID();
                        String city = this_bottle.getCity();
                        String comment = "This is a comment test";

                        TextView messageView = findViewById(R.id.bottle_message_textview);
                        messageView.setText(msg);

                        TextView fromUserView = findViewById(R.id.from_var_textview);
                        fromUserView.setText(fromUser);

        ImageView pictureView = findViewById(R.id.bottle_image);
        //Log.d("url",pictureURL);
        if(pictureURL != null) Picasso.get().load(pictureURL).into(pictureView);

        Button close_button = findViewById(R.id.close_button);
        close_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                        TextView commentView = findViewById(R.id.comment_field_textview);
                        commentView.setText(comment);

                        DatabaseReference this_bottle_data = reference.child(bottleId);
                        Map<String, Object> bottle_update = new HashMap<>();
                        bottle_update.put("isViewed", true);
                        this_bottle_data.updateChildren(bottle_update);

                        break;
                    }

                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        // set viewed to false
    }
}