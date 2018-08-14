package com.example.android.thaparstudentapp;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.regex.Pattern;

public class SignUpPage extends AppCompatActivity {
final static String TAG = "In Sign up Activity";
    FirebaseAuth mAuth;
    Button signUp;
    Boolean sc1, sc2, sc3, sc4;
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
                         writeToDatabase(email.getText().toString(),name.getText().toString(), rNo.getText().toString(), user.getUid());
                       finish();
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
        sc1 = false;
        sc2 = false;
        sc3 = false;
        sc4 = false;
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
private void writeToDatabase(String email1, String name1, String rollNum, String UID){
    FirebaseDatabase database = FirebaseDatabase.getInstance();
  /*  DatabaseReference myRef = database.getReference();*/
    database.getReference("users").child(UID).child("name").setValue(name1).addOnSuccessListener(new OnSuccessListener<Void>() {
        @Override
        public void onSuccess(Void aVoid) {
            Log.e(TAG, "onSuccess: SUCCESS" );
        }
    })
            .addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Log.e(TAG, "onFailure: FAILURE"

                    );
                }
            });
    database.getReference("users").child(UID).child("email").setValue(email1);/*.addOnSuccessListener(new OnSuccessListener<Void>() {
        @Override
        public void onSuccess(Void aVoid) {
            Log.e(TAG, "onSuccess: SUCCESS" );
        }
    })
            .addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Log.e(TAG, "onFailure: FAILURE"

                    );
                }
            });;*/
    database.getReference("users").child(UID).child("RollNo").setValue(rollNum);
    database.getReference("users").child(UID).child("soc1").setValue(sc1);
    database.getReference("users").child(UID).child("soc2").setValue(sc2);
    database.getReference("users").child(UID).child("soc3").setValue(sc3);
    database.getReference("users").child(UID).child("soc4").setValue(sc4);
    }
    public void onCheckboxClicked(View view){
        boolean checked = ((CheckBox) view).isChecked();
        switch(view.getId()) {
            case R.id.Society1:
                if (checked){
                    sc1 = true;
                }

            else{
                    sc1 = false;
                }

                break;
            case R.id.Society2:
                if (checked){
                    sc2 = true;
                }

            else{
                    sc2 = false;
                }

                break;
            case R.id.Society3:
                if (checked){
                    sc3 = true;
                }

            else{
                    sc3 = false;
                }

                break;
            case R.id.Society4:
                if (checked){
                    sc4 = true;
                }

            else{
                    sc4 = false;
                }

                break;
        }
    }
}
