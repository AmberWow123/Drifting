package backend.util.database;

import android.util.Log;

import com.example.drifting.HomeFragment;

import java.util.HashMap;
import java.util.Objects;

import backend.util.time.DriftTime;
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
public class Bottle_back{

     Bottle_back self;
     public String message;
     public String bottleID;
     public String userID;
     public String username;
     public Boolean isAnonymous;
     public String city;
     public double latitude;
     public double longitude;
     public long timestamp;
     public Boolean isViewed = false;
     public String ext = null;
     public boolean isVideo = false;
     public String picture = null;
     public String video = null;
     public int likes = 0;

    //history attribute to track all users picked up the bottles
     public HashMap<String, Boolean> pickHistory = new HashMap<>();

     /*
     ** empty constructor
      */
     public Bottle_back(){
     }

    /*
    ** Real constructor: construct with specific messages
     */
    public Bottle_back(String msg, String bottleID, String userID, String username, Boolean isAnonymous, String city,
                       double latitude, double longitude, long timestamp, Boolean viewed, String filename, boolean isVideo) {

        self = this;
        this.message = msg;
        this.bottleID= bottleID;
        this.userID = userID;
        this.username = username;
        this.isAnonymous = isAnonymous;
        this.city = city;
        this.latitude = latitude;
        this.longitude = longitude;
        this.timestamp = timestamp;
        this.isViewed = viewed;
        this.ext = filename;
        this.isVideo = isVideo;
        this.likes = 0;

        Log.e("Bottle date", "Date of current bottle: " + DriftTime.getDate(this.timestamp));
    }

    public Bottle_back(HomeFragment.Bottle bottleFront){
        self = this;
        this.message = bottleFront.message;
        this.bottleID= bottleFront.bottleID;
        this.userID = bottleFront.userID;
        this.username = bottleFront.fromUser;
        this.isAnonymous = bottleFront.isAnonymous;
        this.city = bottleFront.city;
        this.latitude = 181;
        this.longitude = 181;
        this.timestamp = bottleFront.thrownTimestamp;
        this.isViewed = false;
        this.video = bottleFront.videoDownloadURL;
        this.picture = bottleFront.pictureDownloadURL;
        this.isVideo = bottleFront.isVideo;
        this.likes = bottleFront.likes;
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

    public String getUsername() {return username;}

    public Boolean getIsAnonymous(){
         return isAnonymous;
    }

    public Boolean getIsViewed(){
         return isViewed;
    }

    public void setIsViewed(Boolean isViewed){
         this.isViewed = isViewed;
    }

    public HashMap<String, Boolean> getPickHistory() {return pickHistory;}

    @Override
    public int hashCode(){
        return Objects.hash(message, userID, username, city, latitude, longitude, timestamp);
    }

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

    }
}