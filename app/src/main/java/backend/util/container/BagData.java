package backend.util.container;

import com.example.drifting.HomeFragment;

import java.util.ArrayList;

import backend.util.database.Bottle_back;
import backend.util.time.DriftTime;

public class BagData {
    public static ArrayList<String> pickedBottle = new ArrayList<String>();
    public static ArrayList<String> pickedTime = new ArrayList<String>();
    public static ArrayList<String> pickedLocation = new ArrayList<String>();
    public static ArrayList<String> sentBottle = new ArrayList<String>();
    public static ArrayList<String> sentTime = new ArrayList<String>();
    public static ArrayList<String> sentLocation = new ArrayList<String>();

    public static void addPickedFrontendBottle(HomeFragment.Bottle bottle){
        pickedBottle.add(bottle.message);
        pickedTime.add(DriftTime.getDate((new DriftTime().getTimestamp())));
        pickedLocation.add(bottle.city);
    }

    public static void addSentFrontendBottle(HomeFragment.Bottle bottle){
        sentBottle.add(bottle.message);
        sentTime.add(bottle.thrownDate);
        sentLocation.add(bottle.city);
    }

    public static void addPickedBackendBottle(Bottle_back bottle){
        pickedBottle.add(bottle.message);
        pickedTime.add(DriftTime.getDate((new DriftTime().getTimestamp())));
        pickedLocation.add(bottle.city);
    }

    public static void addSentBackendBottle(Bottle_back bottle){
        sentBottle.add(bottle.message);
        sentTime.add(DriftTime.getDate(bottle.timestamp));
        sentLocation.add(bottle.city);
    }

    public static void throwBack(){
        pickedBottle.remove(pickedBottle.size() - 1);
        pickedTime.remove(pickedTime.size() - 1);
        pickedLocation.remove(pickedLocation.size() - 1);
    }
}
