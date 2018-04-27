package com.team5.ubs;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.team5.ubs.models.clubCreate;

public class clubdetails extends AppCompatActivity {
    private int toggle;
    private FirebaseDatabase mDatabase;
    private clubCreate cc;
    private  user myuser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_clubdetails);
        mDatabase = FirebaseDatabase.getInstance();
        final DatabaseReference myref = mDatabase.getReference("clubs");
        final DatabaseReference myUserRef = mDatabase.getReference("UserAccount");
        Intent myitent = getIntent();
        final String clubtitle = myitent.getStringExtra("title");
        final Button join = (Button) findViewById(R.id.joinclub);
        ValueEventListener clubDet = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                cc = dataSnapshot.getValue(clubCreate.class);
                TextView clubtit = (TextView) findViewById(R.id.clubid);
                TextView clubdes = (TextView) findViewById(R.id.description);
                clubtit.setText(cc.clubName);
                clubdes.setText(cc.clubInfo);
                if(cc.members.containsKey(FirebaseAuth.getInstance().getCurrentUser().getEmail().toString().split("@")[0])){
                        join.setText("Leave Group");
                        toggle = 1;
                }
                else{
                    join.setText("Join Group");
                    toggle = 0;
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        };
        ValueEventListener USER = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                myuser = dataSnapshot.getValue(user.class);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        };
        myref.child(clubtitle).addValueEventListener(clubDet);
        myUserRef.child("users").child(FirebaseAuth.getInstance().getCurrentUser().getEmail().toString().split("@")[0]).addValueEventListener(USER);
        toggle = 0;
        join.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(toggle == 0){
                    if(myuser != null) {
                        cc.members.put(FirebaseAuth.getInstance().getCurrentUser().getEmail().toString().split("@")[0], FirebaseAuth.getInstance().getCurrentUser().getEmail());
                        myref.child(clubtitle).setValue(cc);
                        myuser.myclubs.put(clubtitle, clubtitle);
                        myUserRef.child("users").child(FirebaseAuth.getInstance().getCurrentUser().getEmail().toString().split("@")[0]).setValue(myuser);
                        join.setText("Leave Group");
                    }
                }
                else{
                    if(myuser != null) {

                        Toast.makeText(getApplicationContext(), "Left Club Sucessfully !", Toast.LENGTH_LONG).show();
                        join.setText("Join Group");
                        cc.members.remove(FirebaseAuth.getInstance().getCurrentUser().getEmail().toString().split("@")[0]);
                        myuser.myclubs.remove(clubtitle);
                        myref.child(clubtitle).setValue(cc);
                        myUserRef.child("users").child(FirebaseAuth.getInstance().getCurrentUser().getEmail().toString().split("@")[0]).setValue(myuser);
                    }
                }

            }
        });
    }
}
