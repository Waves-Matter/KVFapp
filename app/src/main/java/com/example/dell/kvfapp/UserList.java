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

public class UserList extends AppCompatActivity {

    private DatabaseReference mDatabase;
    ArrayList<KVFUser> TruListUser = new ArrayList<KVFUser>();
    CustomAdapter customAdapter = new CustomAdapter();
    ListView listView;
    private DatabaseReference uDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_list);

        listView = (ListView) findViewById(R.id.UserListW);

        mDatabase = FirebaseDatabase.getInstance().getReference().child("Users");
        uDatabase = FirebaseDatabase.getInstance().getReference().child("Users");

//išėmimas iš duonbazės
        mDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot)
            {

                for(DataSnapshot child : dataSnapshot.getChildren())
                {
                    KVFUser useris = child.getValue(KVFUser.class);
                    if(useris.getVerified()==true){
                        TruListUser.add(useris);
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
            return TruListUser.size();
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

            convertView = getLayoutInflater().inflate(R.layout.list_user_view, null);

            final TextView NameView = (TextView) convertView.findViewById(R.id.UserNameView);
            TextView SurnameView = (TextView) convertView.findViewById(R.id.UserSurnameView);
            TextView EmailView = (TextView) convertView.findViewById(R.id.UserEmailView);
            TextView PhoneView = (TextView) convertView.findViewById(R.id.UserPhoneView);

            CheckBox checkBox = (CheckBox) convertView.findViewById(R.id.MakeModW);

            NameView.setText(TruListUser.get(position).getName());
            SurnameView.setText(TruListUser.get(position).getSurname());
            EmailView.setText(TruListUser.get(position).getEmail());
            PhoneView.setText(TruListUser.get(position).getPhone());

            Boolean Mod;
            Mod = TruListUser.get(position).getIsMOD();

            if(Mod == true)
            {
                checkBox.setVisibility(checkBox.INVISIBLE);
            }

            checkBox.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                   String id = TruListUser.get(b).getId();
                   TruListUser.get(b).setIsMOD(true);
                   KVFUser NewUser = TruListUser.get(b);

                   uDatabase.child(id).setValue(NewUser);
                   TruListUser.clear();
                    Toast.makeText(UserList.this, "The person became a moderator.", Toast.LENGTH_SHORT).show();
                }
            });
            //checkBox.setOnClickListener
            return convertView;
        }
    }
}
//customAdapter.notifyDataSetChanged();