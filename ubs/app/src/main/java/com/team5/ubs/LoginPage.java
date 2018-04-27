package com.team5.ubs;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.util.Log;
import android.view.Display;
import android.widget.EditText;
import android.widget.TextView;
import android.view.View;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class LoginPage extends AppCompatActivity {

    private EditText Name;
    private EditText Password;
    private TextView Info;
    private CardView Login;
    private TextView Register;
    private TextView ForgotPassword;
    private int counter = 5;
    private FirebaseAuth mAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_page);

        Name = (EditText)findViewById(R.id.editText);
        Password = (EditText)findViewById(R.id.editText2);
        Info = (TextView)findViewById(R.id.textView6);
        Login = (CardView) findViewById(R.id.logincard);
        Register=(TextView)findViewById(R.id.textView3);
        ForgotPassword=(TextView)findViewById(R.id.textView4);
        Info.setText("No of attempts remaining: 5");
        mAuth = FirebaseAuth.getInstance();
        FirebaseUser MyUser = mAuth.getCurrentUser();
//        Log.d("Current USER", MyUser.getEmail());
        if(MyUser != null){
            Intent i = new Intent(LoginPage.this, feedpage.class);
            startActivity(i);
        }
        Register.setOnClickListener(new View.OnClickListener(){
            public void onClick(View view){
                Intent i = new Intent(LoginPage.this, Regsiter.class);
                startActivity(i) ;
            }

        });
        Login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int validationOutput = validate(Name.getText().toString(), Password.getText().toString());
                if(validationOutput == 1) {
                    signin();
                }

            }
        });
    }

    public void signin(){
        try {
            mAuth.signInWithEmailAndPassword(Name.getText().toString(), Password.getText().toString())
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                // Sign in success, update UI with the signed-in user's information
                                Log.d("FU", "signInWithEmail:success");
                                FirebaseUser user = mAuth.getCurrentUser();
                                Intent i = new Intent(LoginPage.this, feedpage.class);
                                startActivity(i);

                            } else {
                                // If sign in fails, display a message to the user.
                                if(counter!=0){
                                    Toast.makeText(LoginPage.this, "Invalid Credentials", Toast.LENGTH_SHORT).show();
                                }

                                Log.w("FU", "signInWithEmail:failure", task.getException());


                            }

                        }
                    });
        }catch (Exception e){
            e.printStackTrace();
            Toast.makeText(getApplicationContext(),"OOPS!! Something Went Wrong",Toast.LENGTH_SHORT).show();
        }

    }

    public void onButtonClick(View v)
    {
        if(v.getId() == R.id.textView3)
        {
            Intent i = new Intent(LoginPage.this, Regsiter.class);
            startActivity(i);
        }
    }

    private int validate(String userName, String userPassword){
        if((userName.equals("")) || userPassword.equals("")){
            Toast.makeText(LoginPage.this, "Some Fields are empty", Toast.LENGTH_SHORT).show();
            return 0;
        }
        else{
            counter--;

            Info.setText("No of attempts remaining: " + String.valueOf(counter));

            if(counter == 0){
                Toast.makeText(LoginPage.this, "Attempts exhausted", Toast.LENGTH_SHORT).show();
                Login.setEnabled(false);
                return  0;
            }
        }
        return 1;

    }

    @Override
    public void onStart(){
        super.onStart();
        FirebaseUser currentUser = mAuth.getCurrentUser();
        
        if(currentUser != null){
            Intent i = new Intent(LoginPage.this, feedpage.class);
        }


    }

}