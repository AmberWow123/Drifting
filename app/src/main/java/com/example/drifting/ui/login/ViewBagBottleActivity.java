package com.example.drifting.ui.login;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.MediaController;
import android.widget.TextView;
import android.widget.VideoView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.drifting.AddFriendActivity;
import com.example.drifting.R;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.annotations.Nullable;
import com.squareup.picasso.Picasso;

import backend.util.database.SetDatabase;

import static android.view.View.VISIBLE;

public class ViewBagBottleActivity extends AppCompatActivity {

    public static String msg = "";
    public static String fromUser = "";
    public static String city = "";
    public static String comment = "";
    public static String bottleID = "";
    public static String fromUserID = "";
    public static String pictureURL;
    public static String videoURL;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_bag_bottle);
        Bundle b = getIntent().getExtras();

        //todo: bottle id from bag fragment
        String userID = b.getString("UserID");
        String username = b.getString("Username");
        String bottleMessage = b.getString("BottleMessage");
        String bottleCity = b.getString("BottleCity");
        String bottleTime = b.getString("BottleTime");
        String bottleID = b.getString("BottleID");
        String bottlePicURL = b.getString("BottlePicURL");
        String bottleVidURL = b.getString("BottleVidURL");
        String bagOrigin = b.getString("BagOrigin");


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

        fromUser = username;
        fromUserID = userID;
        msg = bottleMessage;
        city = bottleCity;
        pictureURL = bottlePicURL;
        videoURL = bottleVidURL;


        LinearLayout like_layout = findViewById(R.id.bag_like_layout);

        TextView like_count = findViewById(R.id.bag_like_label_textview);

        TextView messageView = findViewById(R.id.bag_bottle_message_textview);
        messageView.setText(msg);

        TextView fromUserView = findViewById(R.id.bag_from_var_textview);
        fromUserView.setText(fromUser);

        TextView locationView = findViewById(R.id.bag_location_var_textview);
        locationView.setText(city);

        ImageView pictureView = findViewById(R.id.bottle_image);
        //Log.d("url",pictureURL);
        if(pictureURL != null) {
            pictureView.setVisibility(VISIBLE);
            Picasso.get().load(pictureURL).into(pictureView);
        }

        VideoView videoView = findViewById(R.id.bottle_vedio);
        if(videoURL != null) {
            Log.d("videourl",videoURL);
            videoView.setVisibility(VISIBLE);
            videoView.setZOrderOnTop(true);

            Uri uri = Uri.parse(videoURL);
            videoView.setVideoURI(uri);
            videoView.setMediaController(new MediaController(this));
            videoView.requestFocus();
            videoView.start();

        }



        // get num of likes from db through bottle_back
        SetDatabase db = new SetDatabase();
        //int[] like = new int[1];
        //db.get_likes(finalBottleID, like);
        //like_count.setText(like[0]+"");
        db.get_likes(bottleID,like_count);

        Log.d("BagOrigin", "Bag origin is: " + bagOrigin);
        if(bagOrigin.equals("receive_list")){
            db.view_bottle(bottleID);
        }

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
                Intent addFriendIntent = new Intent(ViewBagBottleActivity.this, AddFriendActivity.class);
                addFriendIntent.putExtra("FriendName", fromUser);
                addFriendIntent.putExtra("FriendID", fromUserID);
                startActivity(addFriendIntent);

            }
        });

    }

    @Nullable
    @Override
    public View onCreatePanelView(int featureId) {
        return super.onCreatePanelView(featureId);
    }

}