package com.example.dell.kvfapp;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


public class MeActivity extends AppCompatActivity {


    private DatabaseReference mDatabase;
    private FirebaseAuth mAuth;
    KVFUser userisL = new KVFUser();

    private TextView MyNameW;
    private TextView MySurW;
    private TextView MyEmailW;
    private TextView MyPhoneW;
    private TextView WasEvents;
    private Button ChangeMe;
//appendix
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_me);

        mAuth = FirebaseAuth.getInstance();

        String UserID = mAuth.getCurrentUser().getUid();

        mDatabase = FirebaseDatabase.getInstance().getReference().child("Users").child(UserID);

        mDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot)
            {
                userisL = dataSnapshot.getValue(KVFUser.class);

                MyNameW = (TextView) findViewById(R.id.myName);
                MySurW = (TextView) findViewById(R.id.MySurname);
                MyEmailW = (TextView) findViewById(R.id.MyEmail);
                MyPhoneW = (TextView) findViewById(R.id.myPhone);

                MyNameW.setText(userisL.getName());
                MySurW.setText(userisL.getSurname());
                MyEmailW.setText(userisL.getEmail());
                MyPhoneW.setText(userisL.getPhone());

            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError)
            {
            }
        });

        ChangeMe = (Button) findViewById(R.id.ChangeMe);
        ChangeMe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent startIntent = new Intent(getApplicationContext(), EditMyself.class);
                startActivity(startIntent);
            }
        });
    }
}
