package backend.util.authentication;

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

    public LoginAuthenticator(){}

    public String validate(String username, String password){
        if(!username.contains("@")) return "Please enter a valid email address";
        if(password.length() < 8) return "Password must be at least 8 characters long";
        if(!password.matches("^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z|[!@#$%&?]|[a-z]]).{8,}$"))
            return "Password must contain digits and at least one other character";

        // TODO: After validation check, query db for user information

        successFlag = true;

        return "Login successful! Welcome, Drifter.";
    }

    public boolean isSuccessful(){
        return successFlag;
    }

}
