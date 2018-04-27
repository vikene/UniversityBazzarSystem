package com.team5.ubs;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.team5.ubs.models.marketplacemodel;

public class CreateMarketFeed extends AppCompatActivity {
    public static final int PICK_IMAGE = 1;
    public  FirebaseStorage firestore;
    public String imageURL;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_market_feed);
        final EditText title = findViewById(R.id.ptitle);
        final EditText price = findViewById(R.id.pPrice);
        final EditText desc = findViewById(R.id.pdescription);
        final EditText lend = findViewById(R.id.plendding);
        Button image = findViewById(R.id.selectImage);
        imageURL = "";
        image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE);
            }
        });
        Button post = findViewById(R.id.postmarket);
        post.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatabaseReference ref = FirebaseDatabase.getInstance().getReference("marketfeed");
                ref.child(title.getText().toString()).setValue(new marketplacemodel(title.getText().toString(),
                        price.getText().toString(), FirebaseAuth.getInstance().getCurrentUser().getEmail().toString().split("@")[0],
                        desc.getText().toString(),lend.getText().toString(),imageURL));
                Intent returnInt = new Intent(CreateMarketFeed.this,market_feed.class);
                startActivity(returnInt);
            }
        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        if (requestCode == PICK_IMAGE) {
            Uri uri = data.getData();
            firestore = FirebaseStorage.getInstance();
            StorageReference riversRef = firestore.getReference().child("images/"+uri.getLastPathSegment());
            UploadTask uploadTask = riversRef.putFile(uri);
            uploadTask.addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    Uri downloadURI = taskSnapshot.getDownloadUrl();
                    Log.d("URL ", downloadURI.toString());
                    imageURL = downloadURI.toString();
                }
            });


        }
    }
}
