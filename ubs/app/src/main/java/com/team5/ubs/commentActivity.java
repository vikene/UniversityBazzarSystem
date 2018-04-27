package com.team5.ubs;

import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;
import android.widget.Toolbar;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.team5.ubs.models.clubCreate;
import com.team5.ubs.models.commentModel;

import java.util.ArrayList;

public class commentActivity extends AppCompatActivity {
    FirebaseDatabase mDatabase;
    ArrayAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comment);
        final String clubName = getIntent().getStringExtra("clubName");
        final String clubTitle = getIntent().getStringExtra("clubTitle");
        ActionBar toolbar = getSupportActionBar();
        toolbar.setTitle("Comments ");
        mDatabase = FirebaseDatabase.getInstance();
        DatabaseReference lClubsRef = mDatabase.getReference("clubfeed");
        final ArrayList<String> clubList =  new ArrayList<>();
        ValueEventListener value =new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                clubList.clear();
                for(DataSnapshot clubSnap: dataSnapshot.getChildren()){
                    commentModel commentModel = clubSnap.getValue(commentModel.class);
                    Log.d("Comment!", commentModel.commentName);
                    clubList.add(commentModel.commentName + " : "+commentModel.commentContent);
                }
                adapter.notifyDataSetChanged();

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        };
        lClubsRef.child(clubName).child(clubTitle).child("comments").addValueEventListener(value);
        adapter= new ArrayAdapter<String>(this,R.layout.listview,clubList);
        ListView listView = (ListView) findViewById(R.id.commentList);
        listView.setAdapter(adapter);
        Button commentButton =  findViewById(R.id.commentButt);
        final EditText commentText = findViewById(R.id.CommentBar);
        commentButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(commentText.getText().toString().length() < 100 && !commentText.getText().toString().isEmpty()) {
                    DatabaseReference commentRef = mDatabase.getReference("clubfeed").child(clubName).child(clubTitle).child("comments");
                    commentRef.child(commentRef.push().getKey()).setValue(new commentModel(FirebaseAuth.getInstance().getCurrentUser().getEmail().split("@")[0].toString(), commentText.getText().toString()));
                }
                else if(commentText.getText().toString().isEmpty()){
                    Toast.makeText(getApplicationContext(),"No Comment Entered!",Toast.LENGTH_LONG).show();
                }
                else if(commentText.getText().toString().length() > 100){
                    Toast.makeText(getApplicationContext(),"Comment is greater than 100 characters!",Toast.LENGTH_LONG).show();

                }
            }
        });


    }
}
