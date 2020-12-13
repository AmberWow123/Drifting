package backend.test;

import com.google.firebase.auth.FirebaseAuth;

import junit.framework.TestCase;

import com.drifting.database.SetDatabase;
import com.drifting.database.models.UserProfile;

public class SetDatabaseTest extends TestCase {
    private FirebaseAuth mAuth = FirebaseAuth.getInstance();
    public void testNewUser() {
        mAuth.signInWithEmailAndPassword("jit124@ucsd.edu", "Tjy123456!");
        System.out.println("loginSuccess");
        UserProfile newUser = new UserProfile("00001","jit124@ucsd.edu",null,
                            null,"hello", EnumD.gender.MALE,null);
        SetDatabase s = new SetDatabase();
        s.addNewUser(newUser);

    }
}