package backend.util.database;

import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

import com.example.drifting.HomeFragment;
import com.example.drifting.ui.login.ViewBottleActivity;
import java.util.Random;

public class Bottle_back{

     Bottle_back self;
     public String message;
     public String bottleID;
     public String userID;
     public Boolean isAnonymous;
     public String city;
//    ImageView bottleView;
//    int imageSrc;
//    int avail_index;

//    int locationID;
//    AnimationDrawable bottleAnimation;

    // construct with a message and bottle index
    public Bottle_back(String msg, String bottleID, String userID, Boolean isAnonymous, String city){
        self = this;
        this.message = msg;
        this.bottleID= bottleID;
        this.userID = userID;
        this.isAnonymous = isAnonymous;
        this.city = city;
//        locationID = getRandomBottleLocation();
//        bottleView =  getView().findViewById(locationID);
//        imageSrc = getRandomBottleImg();
//        bottleView.setBackgroundResource(imageSrc);
//        bottleAnimation = (AnimationDrawable) bottleView.getBackground();
//        bottleAnimation.start();

    }

//    public void setVisible(){
//        bottleView.setVisibility(View.VISIBLE);
//    }

}