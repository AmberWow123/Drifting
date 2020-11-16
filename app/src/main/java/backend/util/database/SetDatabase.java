package backend.util.database;

import com.example.drifting.HomeFragment;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class SetDatabase {
    private DatabaseReference database = FirebaseDatabase.getInstance().getReference();

    //add a new user to the database
    public void addNewUser(UserProfile userProfile) {
        //System.out.println(database == null);
        DatabaseReference usersRef = database.child("user");
        usersRef.child(userProfile.user_id).setValue(userProfile);
    }

    //add a new bottle to the database
    public void addNewBottle(Bottle_back this_bottle){
        DatabaseReference bottlesRef = database.child("bottle");
        bottlesRef.child(String.valueOf(this_bottle.bottle_index)).setValue(this_bottle);
    }

}
