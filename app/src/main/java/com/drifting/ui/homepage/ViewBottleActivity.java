package com.drifting.ui.homepage;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

<<<<<<< Updated upstream:app/src/main/java/com/example/drifting/ui/login/ViewBottleActivity.java
import com.example.drifting.AddFriendActivity;
import com.example.drifting.HomeFragment;
import com.example.drifting.NavBar;
import com.example.drifting.R;
import com.google.firebase.auth.FirebaseAuth;
=======
import com.drifting.ui.chat.AddFriendActivity;
import com.example.drifting.R;
import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.extractor.DefaultExtractorsFactory;
import com.google.android.exoplayer2.extractor.ExtractorsFactory;
import com.google.android.exoplayer2.source.ExtractorMediaSource;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.ui.PlayerView;
import com.google.android.exoplayer2.upstream.DefaultHttpDataSourceFactory;
>>>>>>> Stashed changes:app/src/main/java/com/drifting/ui/homepage/ViewBottleActivity.java
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

<<<<<<< Updated upstream:app/src/main/java/com/example/drifting/ui/login/ViewBottleActivity.java
import java.util.HashMap;
import java.util.Map;
=======
import com.drifting.container.BagData;
import com.drifting.database.models.Bottle_back;
import com.drifting.database.SetDatabase;
>>>>>>> Stashed changes:app/src/main/java/com/drifting/ui/homepage/ViewBottleActivity.java

import static android.view.View.VISIBLE;

public class ViewBottleActivity extends AppCompatActivity {

<<<<<<< Updated upstream:app/src/main/java/com/example/drifting/ui/login/ViewBottleActivity.java
=======
    private static int likes;
    Boolean notliked = true;
    PlayerView playerView;
    DatabaseReference databaseReference;
    FirebaseDatabase database = FirebaseDatabase.getInstance();
>>>>>>> Stashed changes:app/src/main/java/com/drifting/ui/homepage/ViewBottleActivity.java

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
<<<<<<< Updated upstream
=======
        Boolean isAnonymous = false;
>>>>>>> Stashed changes
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
<<<<<<< Updated upstream
=======
             isAnonymous = HomeFragment.currBottle.isAnonymous;

            if (isAnonymous){
                fromUser = "Anonymous";
            }
>>>>>>> Stashed changes
        }


        TextView messageView = findViewById(R.id.bottle_message_textview);
        messageView.setText(msg);

        TextView fromUserView = findViewById(R.id.from_var_textview);
        fromUserView.setText(fromUser);

        TextView locationView = findViewById(R.id.location_var_textview);
        locationView.setText(city);

        TextView commentView = findViewById(R.id.comment_field_textview);
        commentView.setText(comment);

        ImageView pictureView = findViewById(R.id.bottle_image);
        //Log.d("url",pictureURL);
<<<<<<< Updated upstream
        if(pictureURL != null) Picasso.get().load(pictureURL).into(pictureView);
=======
        if(pictureURL != null) {
            pictureView.setVisibility(VISIBLE);
            Picasso.get().load(pictureURL).into(pictureView);
        }

        playerView = findViewById(R.id.exoplayer_item);
        if(videoURL != null) {
<<<<<<< Updated upstream:app/src/main/java/com/example/drifting/ui/login/ViewBottleActivity.java
            Log.d("videourl",videoURL);
            videoView.setVisibility(VISIBLE);
            videoView.setZOrderOnTop(true);


            Uri uri = Uri.parse(videoURL);
            videoView.setVideoURI(uri);
            videoView.setMediaController(new MediaController(this));
            videoView.requestFocus();
            videoView.start();
=======
            playerView.setVisibility(VISIBLE);
            SimpleExoPlayer exoPlayer;
            try {
                exoPlayer = (SimpleExoPlayer) ExoPlayerFactory.newSimpleInstance(getApplication());
                Uri video = Uri.parse(videoURL);
                Log.d("waefawe",videoURL);
                DefaultHttpDataSourceFactory dataSourceFactory = new DefaultHttpDataSourceFactory("video");
                ExtractorsFactory extractorsFactory = new DefaultExtractorsFactory();
                MediaSource mediaSource = new ExtractorMediaSource(video,dataSourceFactory,extractorsFactory,null,null);
                playerView.setPlayer(exoPlayer);
                exoPlayer.prepare(mediaSource);
                exoPlayer.setPlayWhenReady(true);
            } catch (Exception e){
                Log.e("ViewHolder","exoplayer error"+e.toString());
            }
>>>>>>> Stashed changes:app/src/main/java/com/drifting/ui/homepage/ViewBottleActivity.java



        }
>>>>>>> Stashed changes

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
                DatabaseReference reference = FirebaseDatabase.getInstance().getReference().child("bottle");
                DatabaseReference this_bottle_data = reference.child(finalBottleID);

                // set isViewed to false
                Map<String, Object> bottle_update = new HashMap<>();
                bottle_update.put("isViewed", false);
                this_bottle_data.updateChildren(bottle_update);

                //add the user info to bottle history
                final DatabaseReference added_user= this_bottle_data.child("pickHistory").child(current_user);
                added_user.setValue(true);

                //remove the bottle from user's receive list
                DatabaseReference UserRef = FirebaseDatabase.getInstance().getReference().child("user").child(current_user);
                final DatabaseReference added_bottle= UserRef.child("receive_list");
                Map<String, Object> user_update = new HashMap<>();
                user_update.put(finalBottleID, false);
                added_bottle.updateChildren(user_update);

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
        //------------------------------------------------------------------------

        //set isviewed to be true
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference().child("bottle");
        DatabaseReference this_bottle_data = reference.child(bottleID);
        Map<String, Object> bottle_update = new HashMap<>();
        bottle_update.put("isViewed", true);
        this_bottle_data.updateChildren(bottle_update);

        //save the bottle id in user's receive list
        DatabaseReference UserRef = FirebaseDatabase.getInstance().getReference().child("user").child(current_user);
        final DatabaseReference added_bottle= UserRef.child("receive_list");
        Map<String, Object> user_update = new HashMap<>();
        user_update.put(bottleID, true);
        added_bottle.updateChildren(user_update);
    }




    @Nullable
    @Override
    public View onCreatePanelView(int featureId) {
        return super.onCreatePanelView(featureId);
    }

}