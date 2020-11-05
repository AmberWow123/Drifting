package backend.util.authentication;


import android.app.Activity;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

/**
 * Class for login info authentication.
 * Input: input not needed for initialization.
 *
 * How to use:
 *      1. validate(String, String) - Takes user's inputs ("username" and "password") as arguments. Check
 *          if username is a valid email address and if password meets the requirement. Return
 *          corresponding string as feedback. Set status flag to true if log in is successful.
 *      2. isSuccessful() - Return the latest login status flag. true if log in was a success,
 *          otherwise false.
 */

public class LoginAuthenticator{
    private boolean successFlag = false;
    public LoginAuthenticator(){
    }

    public String validate(String username, String password){
        if(!username.contains("@")) return "Please enter a valid email address";
        if(password.length() < 8) return "Password must be at least 8 characters long";
        if(!password.matches("^(?=.*[0-9])(?=.*[[A-Z]|[!@#$%&?]|[a-z]]).{8,}$"))
            return "Password must contain digits and at least one other character";

        // TODO: After validation check, query db for user information
        FirebaseAuth mAuth = FirebaseAuth.getInstance();
        Task task = mAuth.signInWithEmailAndPassword(username, password).addOnCompleteListener(new OnCompleteListener<AuthResult>(){
            @Override
            public void onComplete(@NonNull Task<AuthResult> task){

            }
        });

        FirebaseUser currentUser = mAuth.getCurrentUser();

        if(currentUser != null) {
            successFlag = true;
            return "Login successful! Welcome, " + currentUser.getUid();
        }

        return "Login failed. Please check your credentials";
    }

    public boolean isSuccessful(){
        return successFlag;
    }
}
