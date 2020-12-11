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
import android.widget.Toast;
import android.widget.VideoView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.drifting.AddFriendActivity;
import com.example.drifting.HomeFragment;
import com.example.drifting.NavBar;
import com.example.drifting.R;
import com.squareup.picasso.Picasso;

import backend.util.database.SetDatabase;

import static android.view.View.VISIBLE;

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
        Boolean isAnonymous = false;


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

        Button close_button = findViewById(R.id.close_button);
        close_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                finish();

            }
        });

        // Throw back function
        Button throwBack_button = findViewById(R.id.throw_back_button);
        String finalBottleID = bottleID;
        throwBack_button.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {

                SetDatabase db = new SetDatabase();
                db.throw_bottle_back(finalBottleID);

                Toast.makeText(ViewBottleActivity.this, "Yay you just throw the bottle back!! :D", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(ViewBottleActivity.this, NavBar.class));
                finish();
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

        LinearLayout like_layout = findViewById(R.id.like_layout);
        TextView like_count = findViewById(R.id.like_label_textview);

        int likes = 0;
        // TODO: get num of likes from db through bottle_back
        // like_count.setText(likes+"");
        like_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                like_count.setText((Integer.parseInt((String) like_count.getText()) + 1)+"");
                // TODO: 1. add logic for incrementing like count in db 2. prevent this user from liking again.
            }
        });

        SetDatabase db = new SetDatabase();
        db.view_bottle(bottleID);

    }

    @Nullable
    @Override
    public View onCreatePanelView(int featureId) {
        return super.onCreatePanelView(featureId);
    }
}