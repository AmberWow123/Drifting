package com.example.drifting.ui.login;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.DisplayMetrics;
import android.widget.TextView;

import com.example.drifting.HomeFragment;
import com.example.drifting.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

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

        //get database reference
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference().child("bottle");
        //get current userID
        FirebaseAuth fAuth;
        fAuth = FirebaseAuth.getInstance();

        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot snapshot1 : snapshot.getChildren()) {
                    Bottle_back this_bottle = snapshot1.getValue(Bottle_back.class);
                    String bottleId = this_bottle.getBottleID();
                    String userID = fAuth.getUid();

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

                        TextView locationView = findViewById(R.id.location_var_textview);
                        locationView.setText(city);

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