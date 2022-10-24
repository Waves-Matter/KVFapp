package com.example.dell.kvfapp;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.jar.Attributes;

public class EditMyself extends AppCompatActivity {

    private DatabaseReference mDatabase;
    private FirebaseAuth mAuth;
    KVFUser userisL = new KVFUser();

    private Button ChangeMe;
    private Button CancelMe;
    private EditText NameEdit;
    private EditText SurameEdit;
    private EditText PhoneEdit;
    private EditText EmailEdit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_myself);

        mAuth = FirebaseAuth.getInstance();

        final String UserID = mAuth.getCurrentUser().getUid();

        mDatabase = FirebaseDatabase.getInstance().getReference().child("Users").child(UserID);//Prieš moterį

        CancelMe = (Button) findViewById(R.id.CancelButt);
        ChangeMe = (Button) findViewById(R.id.ChangeButt);

        CancelMe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent startIntent = new Intent(getApplicationContext(), SecondActivity.class);
                startActivity(startIntent);
            }
        });

        mDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot)
            {
                userisL = dataSnapshot.getValue(KVFUser.class);

                NameEdit = (EditText) findViewById(R.id.NameEdit);
                SurameEdit = (EditText) findViewById(R.id.SurnameEdit);
                EmailEdit = (EditText) findViewById(R.id.EmailEdit);
                PhoneEdit = (EditText) findViewById(R.id.PhoneEdit);

                NameEdit.setText(userisL.getName());
                SurameEdit.setText(userisL.getSurname());
                EmailEdit.setText(userisL.getEmail());
                PhoneEdit.setText(userisL.getPhone());

                ChangeMe.setOnClickListener(new View.OnClickListener() {//AŠ MOTERIS
                    @Override
                    public void onClick(View view) {
                        final String name = NameEdit.getText().toString();
                        final String email = EmailEdit.getText().toString();
                        final String phone = PhoneEdit.getText().toString();
                        final String surname = SurameEdit.getText().toString();

                        final String id = userisL.getId();

                        userisL.setEmail(email);
                        userisL.setName(name);
                        userisL.setPhone(phone);
                        userisL.setSurname(surname);

                        mDatabase.setValue(userisL);
                        Toast.makeText(EditMyself.this, "You successfully changed the data.", Toast.LENGTH_SHORT).show();

                        Intent startIntent = new Intent(getApplicationContext(), SecondActivity.class);
                        startActivity(startIntent);
                    }
                });

            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError)
            {
            }
        });
    }
}
