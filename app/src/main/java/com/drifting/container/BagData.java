package com.drifting.container;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import com.drifting.database.models.Bottle_back;

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

    public static void clear(){
        if(sentBottle != null){
            sentBottle.clear();
        }

        if(pickedBottle != null){
            pickedBottle.clear();
        }

    }
}
