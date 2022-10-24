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

public class SecondActivity extends AppCompatActivity {

    private Button mNavButt;
    private Button mViewButt;
    private Button mLoutB;
    private Button mUSl;
    private Button ViewM;
    private Button VeriyButt;
    private TextView mViewTex;
    private DatabaseReference mDatabase;
    private FirebaseAuth mAuth;
    KVFUser userisL = new KVFUser();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);
        mAuth = FirebaseAuth.getInstance();

        String UserID = mAuth.getCurrentUser().getUid();

        mDatabase = FirebaseDatabase.getInstance().getReference().child("Users").child(UserID);

        mDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot)
            {
                userisL = dataSnapshot.getValue(KVFUser.class);
                boolean IsMod = userisL.getIsMOD();
                if (IsMod == true) {
                    mNavButt.setVisibility(View.VISIBLE);
                    mUSl.setVisibility(View.VISIBLE);
                    VeriyButt.setVisibility(View.VISIBLE);
                }
                boolean IsVer = userisL.getVerified();
                if(IsVer==false){
                    mViewButt.setVisibility(View.VISIBLE);
                    mViewTex.setVisibility(View.VISIBLE);
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError)
            {
            }
        });

        mViewTex = (TextView) findViewById(R.id.WaitText);
        mNavButt = (Button) findViewById(R.id.nav_button);
        mViewButt = (Button) findViewById(R.id.ViewButton);
        mLoutB = (Button) findViewById(R.id.l_out);
        mUSl = (Button) findViewById(R.id.ViewVOL);
        ViewM = (Button) findViewById(R.id.ViewMe);
        VeriyButt = (Button) findViewById(R.id.VerifyButt);

        VeriyButt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent startIntent = new Intent(getApplicationContext(), VerifyActivity.class);
                startActivity(startIntent);
            }
        });

        mNavButt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent startIntent = new Intent(getApplicationContext(), SaveEvent.class);
                startActivity(startIntent);
            }
        });

        mViewButt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent startIntent = new Intent(getApplicationContext(), EventList.class);
                startActivity(startIntent);
            }
        });

        mLoutB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                logout();

                Intent startIntent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(startIntent);
            }
        });

        mUSl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent startIntent = new Intent(getApplicationContext(), UserList.class);
                startActivity(startIntent);
            }
        });

        ViewM.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent startIntent = new Intent(getApplicationContext(), MeActivity.class);
                startActivity(startIntent);
            }
        });
    }

    private void logout() {
        mAuth.signOut();
    }
}

