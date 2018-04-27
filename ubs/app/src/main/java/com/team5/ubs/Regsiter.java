package com.team5.ubs;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.LoginFilter;
import android.util.Log;
import android.view.View.OnClickListener;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.regex.Pattern;
import java.util.regex.Matcher;


public class Regsiter extends AppCompatActivity {

    private EditText UserName;
    private EditText Password;
    private EditText RenterPassword;
    private EditText Email;
    private EditText phno;
    private Button Signup;
    private FirebaseAuth mAuth;
// ...

    @Override
    public void onStart(){
        super.onStart();
        mAuth = FirebaseAuth.getInstance();
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        FirebaseDatabase database = FirebaseDatabase.getInstance() ;
        final DatabaseReference reference = database.getReference("university-bazzar-system-team5") ;

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_regsiter);
        UserName = (EditText) findViewById(R.id.editText1);
        Password = (EditText) findViewById(R.id.editText2);
        RenterPassword = (EditText) findViewById(R.id.editText3);
        Email = (EditText) findViewById(R.id.editText4);
        phno = (EditText) findViewById(R.id.editText5);
        Signup = (Button) findViewById(R.id.btnSignup);
        mAuth = FirebaseAuth.getInstance();
        if(mAuth.getCurrentUser() != null){
            Toast.makeText(getApplicationContext(),"User Logged in", Toast.LENGTH_LONG).show();
        }
        Signup.setOnClickListener(new OnClickListener() {
            @Override

            public void onClick(View view) {

                if(validate()){
                    register(UserName.getText().toString(),Password.getText().toString(),Email.getText().toString(), phno.getText().toString());

                }
                else{
                    Log.d("FUC","Sad");
                }



            }

        });
    }

    public void register(final String username, String password, final String Email, final String phone){
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        final DatabaseReference myRef = database.getReference("UserAccount");
        try {
            mAuth.createUserWithEmailAndPassword(Email, password)
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                // Sign in success, update UI with the signed-in user's information
                                FirebaseUser user = mAuth.getCurrentUser();
                                user myuser = new user(username, Email, phone);
                                myRef.child("users").child(username).setValue(myuser);
                                myRef.child("users").child(username).child("myclubs").child("global").setValue("global");
                                Intent i = new Intent(Regsiter.this, LoginPage.class);
                                startActivity(i);
                            }

                        }
                    });
        }catch (Exception e){
            e.printStackTrace();
            Toast.makeText(getApplicationContext(),"OOPS!! Something Went Wrong",Toast.LENGTH_SHORT).show();
        }

    }
    public boolean validate(){

        if (!validateName(UserName.getText().toString())) {
            UserName.setError("Invalid Name");
            UserName.requestFocus();
            return false;


        } else if (!validatePassword(Password.getText().toString())) {
            Password.setError("Invalid Password");
            Password.requestFocus();
            return false;

        }
        else if(!RenterPassword.getText().toString().equals(Password.getText().toString())){
            RenterPassword.setError("Password Doesn't match");
            RenterPassword.requestFocus();
            return false;


        }
        else if(!isEmailValid(Email.getText().toString())){
            Email.setError("Not a Valid Email");
            Email.requestFocus();
            return false;


        }
        else if(!isPhoneValid(phno.getText().toString())){
            phno.setError("Not a Valid Phone Number");
            phno.requestFocus();
            return false;

        }
        return true;
    }
    public boolean isEmailValid(String Email) {
        String expression = "^[\\w\\.-]+@([\\w\\-]+\\.)+[A-Z]{2,4}$";
        Pattern pattern = Pattern.compile(expression, Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(Email);
        return matcher.matches();
    }

    public boolean isPhoneValid(String phno) {
        String expression = "^[0-9]{10,13}$";
        Pattern pattern = Pattern.compile(expression, Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(phno);
        return matcher.matches();
    }


    private boolean validateName(String UserName) {
        if (UserName != null && UserName.length() >= 3) {
            return true;
        } else {
            return false;
        }

    }

    private boolean validatePassword(String Password) {

        if (Password != null && Password.length() >= 6) {
            return true;
        } else {
            return false;
        }

    }




}
