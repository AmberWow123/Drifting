package backend.util.bottleProvider;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Vector;
import java.util.concurrent.ExecutorService;

import backend.util.container.BagData;
import backend.util.database.Bottle_back;
import backend.util.time.DriftTime;

import static java.lang.Math.abs;

public class BottleProvider {
    private volatile Vector<Bottle_back> randomBottleList;
    private volatile Bottle_back[] nextBottles;  // Shared field
    private DriftTime timer = new DriftTime();
    private FusedLocationProviderClient loc;
    private ExecutorService es;
    private Context context;
    private Activity activity;
    private DatabaseReference reference = FirebaseDatabase.getInstance().getReference().child("bottle");
    private FirebaseAuth fAuth = FirebaseAuth.getInstance();
    private boolean isFetchComplete = false;
    public boolean locationLess = false;    // Change this to true to start the app in location-less mode
    public boolean isPopulateComplete = false;

    double latitude = 181.0;
    double longitude = 181.0;

    public BottleProvider(ExecutorService es, Context context, Activity activity) {
        if (ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.

            ActivityCompat.requestPermissions(activity, new String[]{Manifest.permission.ACCESS_COARSE_LOCATION,
                    Manifest.permission.ACCESS_FINE_LOCATION}, 6666);

        } else {
            Log.e("LOC", "LOC PERMISSION GRANTED");
        }

        randomBottleList = new Vector<Bottle_back>();
        nextBottles = new Bottle_back[7];
        this.context = context;
        this.activity = activity;
        loc = LocationServices.getFusedLocationProviderClient(context);
        this.es = es;
        loc.getLastLocation().addOnSuccessListener(new OnSuccessListener<Location>() {
            @Override
            public void onSuccess(Location location) {
                if(location == null){
                    Toast.makeText(context, "Unable to obtain location. Now using location-less mode.", Toast.LENGTH_LONG).show();
                    Log.e("LOC", "UNABLE TO OBTAIN LOCATION");
                    locationLess = true;
                    return;
                }

                latitude = location.getLatitude();
                longitude = location.getLongitude();
            }
        });

    }

    private void fetchNewBottles() {
        reference.orderByChild("isViewed").equalTo(false).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot snapshot1 : snapshot.getChildren()) {
                    Bottle_back this_bottle = snapshot1.getValue(Bottle_back.class);
                    //Log.d("pictureUrl", this_bottle.picture);
                    //String bottleID = this_bottle.getBottleID();
                    String userID = fAuth.getUid();

                    //check if the bottle is viewed
                    if (this_bottle.getIsViewed()) {
                        Log.d("isViewed", "A viewed bottle was returned");
                        continue;
                    }

                    //check if the bottle has been picked up by the same user before
                    if(this_bottle.pickHistory.containsKey(userID)){
                        Log.d("isPicked","A bottle picked before was returned");
                        continue;
                    }

                    if(BagData.currentSessionGeneratedBottleSet.contains(this_bottle)){
                        continue;
                    }

                    //TODO: comment for test purpose, REUSE for formal product
                    //check if the bottle is from the same user
//                    if (this_bottle.getUserID().equals(userID)) {
//                        continue;
//                    } else {
                        randomBottleList.add(this_bottle);
//                    }
                }
                isFetchComplete = true;
                reference.removeEventListener(this);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void prepareNewBottleList() {
        int i = 0;
        for (Bottle_back b : randomBottleList) {
            if(i == 7){
                return;
            }

            if(BagData.currentSessionGeneratedBottleSet.contains(b)){
                continue;
            }

            if (decideReachable(b)) {
                nextBottles[i++] = b;
                BagData.currentSessionGeneratedBottleSet.add(b);
            }
        }
    }

    private boolean decideReachable(Bottle_back bottle) {

        long currTimestampMillis = timer.getTimestamp();
        double bottleTravelRate = 13.0;     // Increase this variable to make the bottles drift faster. The unit is in degree/hour

        double manhattanDistance =  Math.min((abs(bottle.latitude - latitude) + (abs(bottle.latitude) +
                abs(latitude)) / 180.0 * abs(bottle.longitude - longitude)),
                360 - (abs(bottle.latitude - latitude) + (abs(bottle.latitude) + abs(latitude)) / 180.0
                        * abs(bottle.longitude - longitude)));

        if(manhattanDistance < 0.01){     // Floating point operation: consider two locations the same if their manhattan distance
                                            // is this small.
            return true;
        }
        if(bottleTravelRate / ( manhattanDistance / ((double)currTimestampMillis / 1000.0 / 60.0 / 60.0 - (double)bottle.timestamp / 1000.0 / 60.0 / 60.0)) > 1.0){
            return true;
        }
        return false;
    }

    public void serveNextBottles(){
        es.execute(new Runnable() {
            @Override
            public void run() {
                while(latitude == 181.0 || longitude == 181.0){
                    if(locationLess){
                        return;
                    }
                }
                for(int i = 0; i < 7; i++){
                    nextBottles[i] = null;
                }
                randomBottleList.clear();
                fetchNewBottles();
                while(!isFetchComplete);
                isFetchComplete = false;
                prepareNewBottleList();
            }
        });
    }

    public void populateNextBottles(Bottle_back[] nextBottles){
        for(int i = 0; i < 7; i++){
            nextBottles[i] = this.nextBottles[i];
        }


        isPopulateComplete = true;
    }
}
