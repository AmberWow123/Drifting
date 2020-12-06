package com.example.drifting.ui.login;

import android.content.Intent;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.drifting.AddFriendActivity;
import com.example.drifting.HomeFragment;
import com.example.drifting.R;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Map;

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
        Boolean isAnonymous = false;

        if (HomeFragment.currBottle != null) {
             msg = HomeFragment.currBottle.message;
             city = HomeFragment.currBottle.city;
             fromUser = HomeFragment.currBottle.fromUser;
             comment = HomeFragment.currBottle.comment;
             bottleID = HomeFragment.currBottle.bottleID;
             fromUserID = HomeFragment.currBottle.userID;
             isAnonymous = HomeFragment.currBottle.isAnonymous;

             if (isAnonymous){
                 fromUser = "Anonymous";
             }
        }


        TextView messageView = findViewById(R.id.bottle_message_textview);
        messageView.setText(msg);

        TextView fromUserView = findViewById(R.id.from_var_textview);
        fromUserView.setText(fromUser);

        TextView locationView = findViewById(R.id.location_var_textview);
        locationView.setText(city);

        TextView commentView = findViewById(R.id.comment_field_textview);
        commentView.setText(comment);

        Button close_button = findViewById(R.id.close_button);
        close_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                finish();

            }
        });

        Button throwBack_button = findViewById(R.id.throw_back_button);
        throwBack_button.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                //TODO: throw back function
            }
        });

        LinearLayout fromLayout = findViewById(R.id.from_layout);

        if (isAnonymous) {

            fromLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(ViewBottleActivity.this, "Bottle is anonymous", Toast.LENGTH_SHORT).show();
                }
            });

        }
        else {
            fromLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ViewBottleActivity.this, AddFriendActivity.class));
            }
        });
        }

        //------------------------------------------------------------------------

        DatabaseReference reference = FirebaseDatabase.getInstance().getReference().child("bottle");
        DatabaseReference this_bottle_data = reference.child(bottleID);
        Map<String, Object> bottle_update = new HashMap<>();
        bottle_update.put("isViewed", true);
        this_bottle_data.updateChildren(bottle_update);
    }

    @Nullable
    @Override
    public View onCreatePanelView(int featureId) {
        return super.onCreatePanelView(featureId);
    }
}