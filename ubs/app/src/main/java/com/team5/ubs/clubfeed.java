package com.team5.ubs;

import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.team5.ubs.models.clubfeedmodel;
import com.team5.ubs.viewHolder.clubFeedAdapter;

import java.util.ArrayList;
import java.util.List;

public class clubfeed extends AppCompatActivity {
    private List<clubfeedmodel> clubfeed = new ArrayList<>();
    private clubFeedAdapter clubFeedAdapter;
    private  RecyclerView mRecyclerView;
    private FirebaseDatabase mDatabase;
    private String clubtitle;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_clubfeed);
        final Intent myint = getIntent();
        clubtitle = myint.getStringExtra("title");
        mRecyclerView = (RecyclerView) findViewById(R.id.clubFeedView);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        clubFeedAdapter  = new clubFeedAdapter(clubfeed);
        mRecyclerView.setAdapter(clubFeedAdapter);
        mDatabase = FirebaseDatabase.getInstance();
        FloatingActionButton mybut = findViewById(R.id.createFeed);
        mybut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent myintt = new Intent(clubfeed.this,add_clubFeed.class);
                myintt.putExtra("clubtitle",clubtitle);
                startActivity(myintt);
            }
        });
        getData();
    }

    public void getData(){
        DatabaseReference myref = mDatabase.getReference("clubfeed");
        ValueEventListener myval = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for(DataSnapshot ss : dataSnapshot.getChildren()){
                    clubfeedmodel mfeed = ss.getValue(clubfeedmodel.class);
                    clubfeed.add(mfeed);
                }
                clubFeedAdapter.notifyDataSetChanged();


            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        };
        if(clubtitle != null) {
            myref.child(clubtitle).addValueEventListener(myval);
        }
    }
}
