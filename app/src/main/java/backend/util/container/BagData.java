package backend.util.container;

import com.example.drifting.HomeFragment;

import java.util.ArrayList;
import java.util.HashMap;

import backend.util.database.Bottle_back;
import backend.util.time.DriftTime;

public class BagData {
    public static ArrayList<Bottle_back> sentBottle = new ArrayList<>();
    public static ArrayList<Bottle_back> pickedBottle = new ArrayList<>();

    public static void addPickedFrontendBottle(Bottle_back bottle){
        pickedBottle.add(bottle);
    }

    public static void addSentBackendBottle(Bottle_back bottle){
        sentBottle.add(bottle);
    }

    public static void throwBack(){
        pickedBottle.remove(pickedBottle.size() - 1);
    }
}