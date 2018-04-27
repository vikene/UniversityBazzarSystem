package com.team5.ubs;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.team5.ubs.models.marketplacemodel;

public class market_info extends AppCompatActivity {
    FirebaseDatabase mdatabase;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent i  = getIntent();
        String marketTitle = i.getStringExtra("marketTitle");
        setContentView(R.layout.activity_market_info);
        final TextView name = findViewById(R.id.dispMName);
        final TextView price = findViewById(R.id.dispMPrice);
        final TextView description = findViewById(R.id.dispMdescription);
        final TextView lending = findViewById(R.id.dispMlending);
        final TextView seller = findViewById(R.id.dispMSeller);
        mdatabase = FirebaseDatabase.getInstance();
        DatabaseReference myref = mdatabase.getReference("marketfeed");
        ValueEventListener marketValue = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                marketplacemodel mp = dataSnapshot.getValue(marketplacemodel.class);
                name.setText("Name: "+mp.name);
                price.setText("Price: "+mp.price);
                description.setText("Description: "+mp.description);
                lending.setText("Lending: "+mp.lending);
                seller.setText("Seller: "+mp.seller);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        };
        myref.child(marketTitle).addValueEventListener(marketValue);

    }
}
