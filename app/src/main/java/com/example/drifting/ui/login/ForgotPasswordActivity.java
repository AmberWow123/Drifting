package com.example.drifting.ui.login;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.drifting.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;


public class ForgotPasswordActivity extends AppCompatActivity {
    EditText mEmail;
    Button mRestBtn;
    FirebaseAuth fAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fogot_password);


        //correspond those buttons/texts
        mEmail = findViewById(R.id.username_forget);
        mRestBtn = findViewById(R.id.reset_button);
        fAuth = FirebaseAuth.getInstance();

        mRestBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = mEmail.getText().toString().trim();

                //valid checks
                if(TextUtils.isEmpty(email)){
                    mEmail.setError("Email is required! :(");
                    return;
                }

                if(!email.contains("@")){
                    mEmail.setError("A valid email is needed! :(");
                }

                //send user a password reset
                fAuth.sendPasswordResetEmail(email)
                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if (task.isSuccessful()){
                                    Toast.makeText(ForgotPasswordActivity.this, "Email Sent Successfully!",Toast.LENGTH_SHORT).show();
                                }else{
                                    Toast.makeText(ForgotPasswordActivity.this, "Failed to Send",Toast.LENGTH_SHORT).show();
                                }

                            }
                        });
            }
        });
    }
}