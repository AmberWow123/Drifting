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
     public String comment;
     public Boolean isViewed = false;

     public Bottle_back(){
     }

    // construct with a message and bottle index
    public Bottle_back(String msg, String bottleID, String userID, Boolean isAnonymous, String city, String comment, Boolean viewed){
        self = this;
        this.message = msg;
        this.bottleID= bottleID;
        this.userID = userID;
        this.isAnonymous = isAnonymous;
        this.city = city;
        this.comment = comment;
        this.isViewed = viewed;
    }

    public String getMessage(){
        return message;
    }

    public String getUserID(){
        return userID;
    }

    public String getCity(){
        return city;
    }

    public String getBottleID(){
         return bottleID;
    }

    public Boolean getIsAnonymous(){
         return isAnonymous;
    }

    public Boolean getIsViewed(){
         return isViewed;
    }

    public String getComment(){
        return comment;
    }

    public void setComment(String comment){
        this.comment = comment;
    }

    public void setIsViewed(Boolean isViewed){
         this.isViewed = isViewed;
    }
}