package com.team5.ubs;

import android.content.Intent;
import android.graphics.Color;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.team5.ubs.models.clubfeedmodel;
import com.team5.ubs.models.marketplacemodel;
import com.team5.ubs.viewHolder.clubFeedAdapter;

import java.util.ArrayList;
import java.util.List;

public class feedpage extends AppCompatActivity {
    private RecyclerView mRecyclerView;
    private List<clubfeedmodel> clubfeed = new ArrayList<>();
    private clubFeedAdapter clubFeedAdapter;
    private DrawerLayout mDrawer;
    private AdView mAdView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feedpage);
        Toolbar mToolbar = findViewById(R.id.toolbar);
        mToolbar.setBackgroundColor(Color.parseColor("#3F51B5"));
        mToolbar.setTitleTextColor(Color.WHITE);
        setSupportActionBar(mToolbar);
        mToolbar.setTitle("Club Feed");
        ActionBar actionbar = getSupportActionBar();
        actionbar.setDisplayHomeAsUpEnabled(true);
        actionbar.setHomeAsUpIndicator(R.drawable.menu);
        mDrawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        MobileAds.initialize(this, "ca-app-pub-3940256099942544~3347511713");
        mAdView = findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);
        navigationView.setNavigationItemSelectedListener(
                new NavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(MenuItem menuItem) {
                        mDrawer.closeDrawers();
                        if(menuItem.getItemId() == R.id.viewProfile){
                            Intent profileView = new Intent(feedpage.this, profile_page.class);
                            startActivity(profileView);
                        }
                        if(menuItem.getItemId() == R.id.viewClubs){
                            Intent cluboptions = new Intent(feedpage.this, com.team5.ubs.cluboptions.class);
                            startActivity(cluboptions);
                        }
                        if(menuItem.getItemId() == R.id.viewMarket){
                            Intent marketOptin = new Intent(feedpage.this, market_feed.class);
                            startActivity(marketOptin);
                        }
                        if(menuItem.getItemId() == R.id.logOut){
                            FirebaseAuth.getInstance().signOut();
                        }

                        menuItem.setChecked(true);



                        return true;
                    }
                });
        mRecyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        clubFeedAdapter  = new clubFeedAdapter(clubfeed);
        mRecyclerView.setAdapter(clubFeedAdapter);
        FloatingActionButton fab = findViewById(R.id.globalfeed);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent xy = new Intent(feedpage.this, add_clubFeed.class);
                xy.putExtra("clubtitle","global");
                startActivity(xy);
            }
        });
        prepareMdck();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.menu,menu);
        return  super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if(item.getItemId() == R.id.home){
            mDrawer.openDrawer(GravityCompat.START);
        }
        return super.onOptionsItemSelected(item);
    }
    public void prepareMdck(){
        final ValueEventListener clubFeed = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                for(DataSnapshot pp : dataSnapshot.getChildren()){
                    Log.d("DaTA", pp.getValue().toString());
                    clubfeedmodel cf = pp.getValue(clubfeedmodel.class);
                    clubfeed.add(cf);
                }
                clubFeedAdapter.notifyDataSetChanged();

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        };
        ValueEventListener clubs = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                clubfeed.clear();
                for(DataSnapshot ss : dataSnapshot.getChildren()){
                    DatabaseReference mref = FirebaseDatabase.getInstance().getReference();
                    mref.child("clubfeed").child(ss.getValue().toString()).addValueEventListener(clubFeed);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        };
        DatabaseReference mref = FirebaseDatabase.getInstance().getReference("UserAccount").child("users").child(FirebaseAuth.getInstance().getCurrentUser().getEmail().split("@")[0]).child("myclubs");
        mref.addValueEventListener(clubs);
        clubFeedAdapter.notifyDataSetChanged();
    }



}
