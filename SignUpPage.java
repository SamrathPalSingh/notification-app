package com.example.android.thaparstudentapp;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.regex.Pattern;

public class SignUpPage extends AppCompatActivity {
final static String TAG = "In Sign up Activity";
    FirebaseAuth mAuth;
    Button signUp;
    EditText name, email, password, rePass, rNo;
    private void createAccount(String username, String password){
        mAuth.createUserWithEmailAndPassword(username, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Toast.makeText(SignUpPage.this, "Created new account",
                                    Toast.LENGTH_SHORT).show();
                            Log.d(TAG, "createUserWithEmail:success");
                            FirebaseUser user = mAuth.getCurrentUser();
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "createUserWithEmail:failure", task.getException());
                            Toast.makeText(SignUpPage.this, "Failed to create a new account",
                                    Toast.LENGTH_SHORT).show();
                        }

                        // ...
                    }
                });
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up_page);
        mAuth = FirebaseAuth.getInstance();
        signUp = findViewById(R.id.signup);
signUp.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
        name = findViewById(R.id.name);
        email = findViewById(R.id.Email);
        password = findViewById(R.id.password);
        rePass = findViewById(R.id.repass);

        rNo = findViewById(R.id.Rn);
        String rNoString = rNo.getText().toString();
        String emailString = email.getText().toString();
        String passwordString = password.getText().toString();
        String rePassString = rePass.getText().toString();
        if (checkEmail(emailString))/*Checks if the email is valid and has a a thapar.edu in it*/ {
    if (rNoString.matches("[0-9]+") && rNoString.length() == 9)/*checks if the r No has 9 digits in it*/ {
createAccount(emailString, passwordString);
    }else{
        Toast.makeText(SignUpPage.this, "Incorrect Roll Number", Toast.LENGTH_SHORT).show();
    }
} else {
    Toast.makeText(SignUpPage.this, "Incorrect Email address use thapar.edu account to sign up", Toast.LENGTH_SHORT).show();
}
    }
});
    }
    private boolean checkEmail(String email) {
        Pattern EMAIL_ADDRESS_PATTERN = Patterns.EMAIL_ADDRESS;
        return (EMAIL_ADDRESS_PATTERN.matcher(email).matches() && email.contains("thapar.edu"));
    }
}
