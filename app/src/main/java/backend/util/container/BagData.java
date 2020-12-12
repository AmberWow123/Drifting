package backend.util.container;

import com.example.drifting.HomeFragment;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

import backend.util.database.Bottle_back;
import backend.util.time.DriftTime;

public class BagData {
    public static ArrayList<Bottle_back> sentBottle = new ArrayList<>();
    public static ArrayList<Bottle_back> pickedBottle = new ArrayList<>();
    public static HashSet<Bottle_back> currentSessionGeneratedBottleSet = new HashSet<>();

    public static void addPickedFrontendBottle(Bottle_back bottle){
        pickedBottle.add(bottle);
    }

    public static void addSentBackendBottle(Bottle_back bottle){
        sentBottle.add(bottle);
    }

    public static void throwBack(){
        pickedBottle.remove(pickedBottle.size() - 1);
    }

    public static void clear(){
        sentBottle.clear();
        pickedBottle.clear();
        currentSessionGeneratedBottleSet.clear();
    }
}
