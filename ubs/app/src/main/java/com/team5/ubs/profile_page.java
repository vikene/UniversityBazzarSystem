package com.team5.ubs;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.EditText;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class profile_page extends AppCompatActivity {
    FirebaseAuth mFirebaseAuth;
    FirebaseDatabase mDatabase;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_page);
        mFirebaseAuth = FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance();
        final DatabaseReference myRef = mDatabase.getReference("UserAccount");
        final EditText userEmai = findViewById(R.id.profile_userEmaildisp);
        final TextView phoneNumber = findViewById(R.id.phonenumber);

        FirebaseUser MyUser = mFirebaseAuth.getCurrentUser();
        String username = MyUser.getEmail().toString().split("@")[0];
        Log.d("user name" ,username);
        ValueEventListener profileValue = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Log.d("DATA", dataSnapshot.getValue().toString());
                user myuser = dataSnapshot.getValue(user.class);
                Log.d("username", myuser.phone);
                userEmai.setText(myuser.email);
                phoneNumber.setText(myuser.phone);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        };
        myRef.child("users").child(username).addValueEventListener(profileValue);


    }
}
