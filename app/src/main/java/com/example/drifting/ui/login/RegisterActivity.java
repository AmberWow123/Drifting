package com.example.drifting.ui.login;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.drifting.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;

import backend.util.connectivity.ConnectionChecker;
import backend.util.database.SetDatabase;
import backend.util.database.UserProfile;

public class RegisterActivity extends AppCompatActivity {

    EditText mEmail, mPassword, mRePassword;
    Button mRegisterBtn;
    FirebaseAuth fAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        Context context = getApplicationContext();

        //correspond those buttons/texts
        mEmail = findViewById(R.id.email_register);
        mPassword = findViewById(R.id.password_register);
        mRePassword = findViewById(R.id.re_enter_password_register);
        mRegisterBtn = findViewById(R.id.sign_up_button);
        fAuth = FirebaseAuth.getInstance();

        //TODO: add these code after we have sign out button
       //check if the user is already logged in
//        if(fAuth.getCurrentUser() != null){
//            startActivity(new Intent(getApplicationContext(), MainActivity.class));
//            finish();
//        }

        mRegisterBtn.setOnClickListener((new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean isConnected = ConnectionChecker.isInternetConnected(getApplicationContext());

                if(!isConnected){
                    Toast.makeText(getApplicationContext(), "Please check Internet connection", Toast.LENGTH_SHORT).show();
                    return;
                }

                String email = mEmail.getText().toString().trim();
                String password = mPassword.getText().toString().trim();
                String password_re = mRePassword.getText().toString().trim();

                //valid checks
                if(TextUtils.isEmpty(email)){
                    mEmail.setError("Email is Required! :(");
                    return;
                }

                if(TextUtils.isEmpty(password)){
                    mPassword.setError("Password is Required! :(");
                    return;
                }

                if(!email.contains("@")){
                    mEmail.setError("A valid email is needed! :(");
                }

                if(password.length() < 8 ){
                    mPassword.setError("Password should be >=8! :(");
                    return;
                }

                if(!password.matches("^(?=.*[0-9])(?=.*[[A-Z]|[!@#$%&?]|[a-z]]).{8,}$")){
                    mPassword.setError("Password should contain digits&characters! :(");
                    return;
                }

                if(!TextUtils.equals(password, password_re)){
                    mRePassword.setError("Please enter same passwords! :(");
                }

                //register the user in Firebase
                fAuth.createUserWithEmailAndPassword(email,password) .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            Toast.makeText(RegisterActivity.this, "Yay User Created! :D", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(getApplicationContext(), LoginActivity.class));
                            UserProfile userProfile = new UserProfile(fAuth.getUid(),null, email, null,null,null,null, null,null,null, null, null);
                            SetDatabase set = new SetDatabase();
                            set.addNewUser(userProfile);
                        }
                        else{
                            try{
                                throw task.getException();
                            } catch (Exception e) {
                                if(e instanceof FirebaseAuthUserCollisionException)
                                    Toast.makeText(RegisterActivity.this, "The email already exists! :(", Toast.LENGTH_LONG).show();
                                else{
                                    Toast.makeText(RegisterActivity.this, "An unknown error has occurred! :(", Toast.LENGTH_LONG).show();
                                }
                            } ;
                        }
                    }
                });
            }
        }));

    }
}
