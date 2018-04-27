package com.team5.ubs;

import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.team5.ubs.models.clubfeedmodel;
import com.team5.ubs.models.marketplacemodel;
import com.team5.ubs.viewHolder.clubFeedAdapter;
import com.team5.ubs.viewHolder.marketFeedAdapter;

import java.util.ArrayList;
import java.util.List;

public class market_feed extends AppCompatActivity {

    private List<marketplacemodel> marketfeed = new ArrayList<>();
    private com.team5.ubs.viewHolder.marketFeedAdapter marketFeedAdapter;
    private RecyclerView mRecyclerView;
    private FirebaseDatabase mDatabase;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_market_feed);
        mRecyclerView = (RecyclerView) findViewById(R.id.marketfeedview);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        marketFeedAdapter  = new marketFeedAdapter(marketfeed);
        mRecyclerView.setAdapter(marketFeedAdapter);
        mDatabase = FirebaseDatabase.getInstance();
        FloatingActionButton mybut = findViewById(R.id.createMarketfeed);
        mybut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent myintt = new Intent(market_feed.this,CreateMarketFeed.class);
                startActivity(myintt);
            }
        });
        getData();
    }

    public void getData(){
        DatabaseReference myref = mDatabase.getReference();
        ValueEventListener myval = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for(DataSnapshot ss : dataSnapshot.getChildren()){
                    marketplacemodel mfeed = ss.getValue(marketplacemodel.class);
                    Log.d("MarketFEED",mfeed.name);
                    marketfeed.add(mfeed);
                }
                marketFeedAdapter.notifyDataSetChanged();


            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        };
        myref.child("marketfeed").addValueEventListener(myval);
    }
}
