package com.team5.ubs;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.team5.ubs.models.clubfeedmodel;

public class add_clubFeed extends AppCompatActivity {
    FirebaseDatabase mDatabase;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_club_feed);
        Intent myitent = getIntent();
        final String clubtitle = myitent.getStringExtra("clubtitle");
        mDatabase = FirebaseDatabase.getInstance();
        final DatabaseReference myref = mDatabase.getReference("clubfeed");
        final EditText title = findViewById(R.id.posttitle);
        final EditText description = findViewById(R.id.postDescription);
        Button postButton = findViewById(R.id.postClubFeed);
        postButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!title.getText().toString().isEmpty() && !description.getText().toString().isEmpty()) {
                    myref.child(clubtitle).child(title.getText().toString()).setValue(new clubfeedmodel(clubtitle, title.getText().toString(), description.getText().toString()));
                    Intent myintt = new Intent(add_clubFeed.this, clubfeed.class);
                    startActivity(myintt);
                }
                else{
                    Toast.makeText(getApplicationContext(),"Please Enter Some value", Toast.LENGTH_LONG).show();
                }
            }
        });

    }
}
