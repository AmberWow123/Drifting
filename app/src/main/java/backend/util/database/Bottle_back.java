package backend.util.database;

import java.util.HashMap;

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
        this.ext = SetDatabase.parseName(filename).second;
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

    public void setIsViewed(Boolean isViewed){
         this.isViewed = isViewed;
    }

    public HashMap<String, Boolean> getPickHistory() {return pickHistory;}
}