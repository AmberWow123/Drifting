package com.example.drifting.ui.login;

import android.content.Intent;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.drifting.AddFriendActivity;
import com.example.drifting.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.database.annotations.Nullable;

public class ViewBagBottleActivity extends AppCompatActivity {

    public static String msg = "";
    public static String fromUser = "";
    public static String city = "";
    public static String comment = "";
    public static String bottleID = "";
    public static String fromUserID = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_bag_bottle);
        Bundle b = getIntent().getExtras();

        //todo: bottle id from bag fragment
        String bottle_ID = b.getString("BottleID");

        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);

        int width = dm.widthPixels;
        int height = dm.heightPixels;

        // set canvas width and height.
        getWindow().setLayout((int) (width * 1), (int) (height * 0.75));



        /*
        if (HomeFragment.currBottle != null) {
            msg = HomeFragment.currBottle.message;
            fromUser = HomeFragment.currBottle.fromUser;
            city = HomeFragment.currBottle.city;
            comment = HomeFragment.currBottle.comment;
            bottleID = HomeFragment.currBottle.bottleID;
            fromUserID = HomeFragment.currBottle.userID;
        }

         */
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference();
        DatabaseReference bottle_ref = ref.child("bottle").child(bottle_ID);

        bottle_ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                fromUserID = snapshot.child("userID").getValue(String.class);
                msg = snapshot.child("message").getValue(String.class);
                city = snapshot.child("city").getValue(String.class);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        DatabaseReference user_ref = ref.child("user").child(fromUserID);
        user_ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                fromUser = snapshot.child("user_name").getValue(String.class);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });




        TextView messageView = findViewById(R.id.bag_bottle_message_textview);
        messageView.setText(msg);

        TextView fromUserView = findViewById(R.id.bag_from_var_textview);
        fromUserView.setText(fromUser);

        TextView locationView = findViewById(R.id.bag_location_var_textview);
        locationView.setText(city);

        TextView commentView = findViewById(R.id.bag_comment_field_textview);
        commentView.setText(comment);

        Button close_button = findViewById(R.id.bag_close_button);
        close_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                finish();

            }
        });


        LinearLayout fromLayout = findViewById(R.id.bag_from_layout);
        fromLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startActivity(new Intent(ViewBagBottleActivity.this, AddFriendActivity.class));

            }
        });

//        //------------------------------------------------------------------------
//
//        DatabaseReference reference = FirebaseDatabase.getInstance().getReference().child("bottle");
//        DatabaseReference this_bottle_data = reference.child(bottleID);
//        Map<String, Object> bottle_update = new HashMap<>();
//        bottle_update.put("isViewed", true);
//        this_bottle_data.updateChildren(bottle_update);
    }

    @Nullable
    @Override
    public View onCreatePanelView(int featureId) {
        return super.onCreatePanelView(featureId);
    }

}