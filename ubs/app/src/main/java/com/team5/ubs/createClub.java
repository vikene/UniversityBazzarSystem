package com.team5.ubs;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.team5.ubs.models.clubCreate;

import java.util.HashMap;
import java.util.Map;

public class createClub extends AppCompatActivity {
    private DatabaseReference mDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_club);
        final Button createClub = (Button) findViewById(R.id.clubCreateSubmit);
        mDatabase = FirebaseDatabase.getInstance().getReference();

        createClub.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                submitData();
                Intent HomeIntet = new Intent(createClub.this,feedpage.class);
                startActivity(HomeIntet);
            }
        });
    }

    public void submitData(){
        EditText clubName = (EditText) findViewById(R.id.clubEditName);
        EditText clubDes = (EditText) findViewById(R.id.cdesc);
        EditText clubType = (EditText) findViewById(R.id.clubType);
        clubCreate cCreate = new clubCreate(clubName.getText().toString(),clubDes.getText().toString(),clubType.getText().toString());
        Map<String, Object> clubpost = cCreate.toMap();
        Map<String, Object> childUpdates = new HashMap<>();
        childUpdates.put("/clubs/"+clubName.getText().toString(),clubpost);

        mDatabase.updateChildren(childUpdates);
    }
}
