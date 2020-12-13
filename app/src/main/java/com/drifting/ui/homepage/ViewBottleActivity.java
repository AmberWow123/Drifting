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
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

import com.drifting.container.BagData;
import com.drifting.database.models.Bottle_back;
import com.drifting.database.SetDatabase;

import static android.view.View.VISIBLE;

public class ViewBottleActivity extends AppCompatActivity {

    private static int likes;
    Boolean notliked = true;
    PlayerView playerView;
    DatabaseReference databaseReference;
    FirebaseDatabase database = FirebaseDatabase.getInstance();

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
             Bottle_back currBottle = new Bottle_back(HomeFragment.currBottle);
             BagData.addPickedFrontendBottle(currBottle);
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

        playerView = findViewById(R.id.exoplayer_item);
        if(videoURL != null) {
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
                BagData.throwBack();

                Toast.makeText(ViewBottleActivity.this, "Yay you just throw the bottle back!! :D", Toast.LENGTH_SHORT).show();
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
                    Intent addFriendIntent = new Intent(getApplicationContext(), AddFriendActivity.class);
                    addFriendIntent.putExtra("FriendName", HomeFragment.currBottle.fromUser);
                    addFriendIntent.putExtra("FriendID", HomeFragment.currBottle.userID);
                    startActivity(addFriendIntent);
                }
            });
        }

        LinearLayout like_layout = findViewById(R.id.like_layout);
        TextView like_count = findViewById(R.id.like_label_textview);


        // get num of likes from db through bottle_back
        SetDatabase db = new SetDatabase();
        //int[] like = new int[1];
        //db.get_likes(finalBottleID, like);
        //like_count.setText(like[0]+"");
        db.get_likes(finalBottleID,like_count);

        like_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (notliked) {
                    like_count.setText((Integer.parseInt((String) like_count.getText()) + 1) + "");
                    notliked = false;
                    like_count.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.heart, 0);
                }
                // Incrementing like count in db
                db.update_likes(finalBottleID, Integer.parseInt((String) like_count.getText()));

            }
        });

        db.view_bottle(bottleID);

    }

    @Nullable
    @Override
    public View onCreatePanelView(int featureId) {
        return super.onCreatePanelView(featureId);
    }

}