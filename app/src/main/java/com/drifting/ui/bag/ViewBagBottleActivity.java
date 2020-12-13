package com.drifting.ui.bag;

import android.content.Intent;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

<<<<<<< Updated upstream:app/src/main/java/com/example/drifting/ui/login/ViewBagBottleActivity.java
import com.example.drifting.AddFriendActivity;
import com.example.drifting.HomeFragment;
import com.example.drifting.R;
import com.google.firebase.database.annotations.Nullable;
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
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.annotations.Nullable;
import com.squareup.picasso.Picasso;

import com.drifting.database.SetDatabase;

import static android.view.View.VISIBLE;
>>>>>>> Stashed changes:app/src/main/java/com/drifting/ui/bag/ViewBagBottleActivity.java

public class ViewBagBottleActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_bag_bottle);

        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);

        int width = dm.widthPixels;
        int height = dm.heightPixels;

        // set canvas width and height.
        getWindow().setLayout((int) (width * 1), (int) (height * 0.75));

        String msg = "";
        String fromUser = "";
        String city = "";
        String comment = "";
        String bottleID = "";
        String fromUserID = "";

        if (HomeFragment.currBottle != null) {
            msg = HomeFragment.currBottle.message;
            fromUser = HomeFragment.currBottle.fromUser;
            city = HomeFragment.currBottle.city;
            comment = HomeFragment.currBottle.comment;
            bottleID = HomeFragment.currBottle.bottleID;
            fromUserID = HomeFragment.currBottle.userID;
        }

        TextView messageView = findViewById(R.id.bag_bottle_message_textview);
        messageView.setText(msg);

        TextView fromUserView = findViewById(R.id.bag_from_var_textview);
        fromUserView.setText(fromUser);

        TextView locationView = findViewById(R.id.bag_location_var_textview);
        locationView.setText(city);

<<<<<<< Updated upstream:app/src/main/java/com/example/drifting/ui/login/ViewBagBottleActivity.java
        TextView commentView = findViewById(R.id.bag_comment_field_textview);
        commentView.setText(comment);
=======
        ImageView pictureView = findViewById(R.id.bottle_image);
        //Log.d("url",pictureURL);
        if(pictureURL != null) {
            pictureView.setVisibility(VISIBLE);
            Picasso.get().load(pictureURL).into(pictureView);
        }

        PlayerView playerView = findViewById(R.id.exoplayer_bag_item);
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
>>>>>>> Stashed changes:app/src/main/java/com/drifting/ui/bag/ViewBagBottleActivity.java

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