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
 *          corresponding string as feedback. Set status flag to true if inputs are valid.
 *      2. isSuccessful() - Return the latest validation status flag. true if inputs were valid,
 *          otherwise false.
 */

public class CredentialAuthenticator{
    private boolean validFlag = false;
    public CredentialAuthenticator(){
    }

    public String validate(String username, String password){
        if(!username.contains("@")) return "Please enter a valid email address";
        if(password.length() < 8) return "Password must be at least 8 characters long";
        if(!password.matches("^(?=.*[0-9])(?=.*[[A-Z]|[!@#$%&?]|[a-z]]).{8,}$"))
            return "Password must contain digits and at least one other character";

        validFlag = true;
        return "Signing in...";
    }

    public boolean isValid(){
        return validFlag;
    }
}
