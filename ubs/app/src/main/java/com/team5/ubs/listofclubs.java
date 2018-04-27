package com.team5.ubs;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.team5.ubs.models.clubCreate;

import java.util.ArrayList;
import java.util.Iterator;

public class listofclubs extends AppCompatActivity {
    FirebaseDatabase mDatabase;
    ArrayAdapter adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listofclubs);
        mDatabase = FirebaseDatabase.getInstance();
        DatabaseReference lClubsRef = mDatabase.getReference("clubs");
        final ArrayList<String> clubList =  new ArrayList<>();
        ValueEventListener value =new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                clubList.clear();
                for(DataSnapshot clubSnap: dataSnapshot.getChildren()){
                    clubCreate club = clubSnap.getValue(clubCreate.class);
                    Log.d("CLUber", club.clubName);
                    clubList.add(club.clubName);
                }
                adapter.notifyDataSetChanged();

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        };
        lClubsRef.addValueEventListener(value);
        adapter= new ArrayAdapter<String>(this,R.layout.listview,clubList);
        ListView listView = (ListView) findViewById(R.id.clublists);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent myint = new Intent(listofclubs.this,clubdetails.class);
                myint.putExtra("title",clubList.get(position));
                startActivity(myint);
            }
        });

    }
}
