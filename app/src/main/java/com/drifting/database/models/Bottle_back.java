package com.drifting.database.models;

<<<<<<< Updated upstream:app/src/main/java/backend/util/database/Bottle_back.java
import java.util.HashMap;

=======
import android.util.Log;

import com.drifting.util.time.DriftTime;
import com.drifting.ui.homepage.HomeFragment;

import java.util.HashMap;
import java.util.Objects;

/*
    * Bottle_back class: A class that defines the bottle for backend uses. Contains all the needed
    * attributes of the bottle
    * message: the message sent by the user
    * bottleID: the unique bottleID created by combinations of userID and timeStamp
    * userID: the user who sent the bottle
    * isAnonymous: whether the user is anonymous
    * city: the user's city when sent the message
    * latitude: the user's location
    * longitude: the user's location
    * timestamp: timestamp of when the bottle is sent
    * isViewed: whether the bottle has been viewed by someone
    * ext: filename of uploaded file
    * isVideo: whether the bottle contains a video
    * picture: the address of the uploaded image
    * video: the address of the uploaded video
    * likes: the likes received by others on the bottle
 */
>>>>>>> Stashed changes:app/src/main/java/com/drifting/database/models/Bottle_back.java
public class Bottle_back{

     Bottle_back self;
     public String message;
     public String bottleID;
     public String userID;
     public Boolean isAnonymous;
     public String city;
     public double latitude;
     public double longitude;
     public long timestamp;
     public String comment;
     public Boolean isViewed = false;
     public String ext = null;
     public boolean isVideo = false;
     public String picture = null;
     public String video = null;

    //history attribute to track all users picked up the bottles
     public HashMap<String, Boolean> pickHistory = new HashMap<>();

     public Bottle_back(){
     }

    // construct with a message and bottle index

    public Bottle_back(String msg, String bottleID, String userID, Boolean isAnonymous, String city,
                       double latitude, double longitude, long timestamp, String comment, Boolean viewed, String filename, boolean isVideo) {

        self = this;
        this.message = msg;
        this.bottleID= bottleID;
        this.userID = userID;
        this.isAnonymous = isAnonymous;
        this.city = city;
        this.latitude = latitude;
        this.longitude = longitude;
        this.timestamp = timestamp;
        this.comment = comment;
        this.isViewed = viewed;
        this.ext = filename;
        this.isVideo = isVideo;
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

    public double getLatitude() {return latitude;}

    public double getLongitude() {return longitude;}


    public double getTimestamp() {return timestamp;}

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

<<<<<<< Updated upstream:app/src/main/java/backend/util/database/Bottle_back.java
    public void setIsViewed(Boolean isViewed){
         this.isViewed = isViewed;
=======
    @Override
    public boolean equals(Object a){
        if(!(a instanceof Bottle_back)){
            return false;
        }

        Bottle_back theOther = (Bottle_back) a;

        if(theOther.message == null || theOther.userID == null || theOther.username == null || theOther.city == null) {
            return false;
        }

        return theOther.message.equals(message) && theOther.userID.equals(userID) &&
                theOther.username.equals(username) && theOther.city.equals(city) &&
                theOther.latitude == latitude && theOther.longitude == longitude &&
                theOther.timestamp == timestamp;

>>>>>>> Stashed changes:app/src/main/java/com/drifting/database/models/Bottle_back.java
    }

    public HashMap<String, Boolean> getPickHistory() {return pickHistory;}
}