package com.example.dell.kvfapp;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class VerifyActivity extends AppCompatActivity {

    private DatabaseReference mDatabase;
    ArrayList<KVFUser> VerifListUser = new ArrayList<KVFUser>();
    VerifyActivity.CustomAdapter customAdapter = new VerifyActivity.CustomAdapter();
    ListView listView;
    private DatabaseReference uDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_verify);
        listView = (ListView) findViewById(R.id.UserListVerif);

        mDatabase = FirebaseDatabase.getInstance().getReference().child("Users");
        uDatabase = FirebaseDatabase.getInstance().getReference().child("Users");

        mDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot)
            {

                for(DataSnapshot child : dataSnapshot.getChildren())
                {
                    KVFUser useris = child.getValue(KVFUser.class);
                    if(useris.getVerified()==false){
                        VerifListUser.add(useris);
                    }
                    listView.setAdapter(customAdapter);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError)
            {
            }
        });
    }
    private class CustomAdapter extends BaseAdapter {
        @Override
        public int getCount() {
            return VerifListUser.size();
        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        public View getView(int position, View convertView, ViewGroup viewGroup) {

            final int b = position;

            convertView = getLayoutInflater().inflate(R.layout.list_verifus_view, null);

            final TextView NameView = (TextView) convertView.findViewById(R.id.userNameViewV);
            TextView SurnameView = (TextView) convertView.findViewById(R.id.userSurnameViewV);
            TextView EmailView = (TextView) convertView.findViewById(R.id.userEmailViewV);
            TextView PhoneView = (TextView) convertView.findViewById(R.id.userPhoneViewV);

            CheckBox checkBox = (CheckBox) convertView.findViewById(R.id.vefifycb);

            NameView.setText(VerifListUser.get(position).getName());
            SurnameView.setText(VerifListUser.get(position).getSurname());
            EmailView.setText(VerifListUser.get(position).getEmail());
            PhoneView.setText(VerifListUser.get(position).getPhone());


            checkBox.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    String id = VerifListUser.get(b).getId();
                    VerifListUser.get(b).setVerified(true);
                    KVFUser NewUser = VerifListUser.get(b);

                    uDatabase.child(id).setValue(NewUser);
                    VerifListUser.clear();
                    Toast.makeText(VerifyActivity.this, "The person was verified", Toast.LENGTH_SHORT).show();
                }
            });
            //checkBox.setOnClickListener
            return convertView;
        }
    }
}
