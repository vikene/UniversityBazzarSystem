package com.team5.ubs;

import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class cluboptions extends AppCompatActivity {
    private Button createClub, joinClub;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cluboptions);
        ActionBar mActionbar = getSupportActionBar();
        mActionbar.setTitle("Club Options");
        createClub = (Button) findViewById(R.id.createclub);
        joinClub = (Button) findViewById(R.id.joinclub);
        Button mycubs = findViewById(R.id.myclubs);

        createClub.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent toCreateClub = new Intent(cluboptions.this,createClub.class);
                startActivity(toCreateClub);
            }
        });

        joinClub.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent myitent = new Intent(cluboptions.this, listofclubs.class);
                startActivity(myitent);
            }
        });

        mycubs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent myint = new Intent(cluboptions.this, myclubs.class);
                startActivity(myint);
            }
        });
    }
}
