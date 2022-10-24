package com.example.dell.kvfapp;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class EventList extends AppCompatActivity {
    private DatabaseReference mDatabase;
    private DatabaseReference aDatabase;
    private DatabaseReference vDatabase;
    private DatabaseReference cDatabase;
    private DatabaseReference fDatabase;

    ListView EventListView;
    ArrayList<Eventas> TruListEvent = new ArrayList<Eventas>();
    Eventas eventas;
    CustomAdapter customAdapter = new CustomAdapter();
    ListView listView;
    FirebaseAuth mAuth;
    KVFUser userisL = new KVFUser();
    boolean IsMod;
    boolean IsVer;
    String UserID;
    ArrayList VolList = new ArrayList();

    ArrayList<UserEvent> EventasList = new ArrayList<UserEvent>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.event_list);

        mDatabase = FirebaseDatabase.getInstance().getReference().child("Event");
        cDatabase = FirebaseDatabase.getInstance().getReference().child("Event");
        listView = (ListView) findViewById(R.id.listElements);

        mAuth = FirebaseAuth.getInstance();
        UserID = mAuth.getCurrentUser().getUid();
        aDatabase = FirebaseDatabase.getInstance().getReference().child("Users").child(UserID);
        aDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot)
            {
                userisL = dataSnapshot.getValue(KVFUser.class);
                IsMod = userisL.getIsMOD();
                IsVer = userisL.getVerified();
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError)
            {
            }
        });

        aDatabase = FirebaseDatabase.getInstance().getReference().child("VolGO");
        aDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot ds: dataSnapshot.getChildren()) {
                    ds.getValue(true);

                    String dummy = ds.getValue().toString();
                    dummy = dummy.substring(1,dummy.length()-1);

                    ArrayList putList = new ArrayList<String>();

                    String[] arr = dummy.split(",");
                    for(int c = 0; c<arr.length; c++)
                    {
                        arr[c]=arr[c].substring(arr[c].indexOf('=')+1);
                        putList.add(arr[c]);
                    }
                    EventasList.add(new UserEvent(ds.getKey(), putList));
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        mDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot)
            {

                for(DataSnapshot child : dataSnapshot.getChildren())
                {
                    Eventas eventas = child.getValue(Eventas.class);
                    TruListEvent.add(eventas);
                }

                SortMe(TruListEvent);
                listView.setAdapter(customAdapter);
////sortinimas


            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError)
            {
            }
        });

    }

    public ArrayList<Eventas> SortMe(ArrayList<Eventas> e)
    {
        for(int fr = 0; fr<e.size()-1; fr++)
        {
            for (int sr = 0; sr<(e.size()-fr-1);sr++) {
                String StringDate1 = e.get(sr).getEventDate();
                String StringDate2 = e.get(sr+1).getEventDate();
                String StringYear1 = StringDate1.substring(0, 4);
                String StringYear2 = StringDate2.substring(0, 4);

                String StringMonth1 = StringDate1.substring(5, 7);
                String StringMonth2 = StringDate2.substring(5, 7);

                String StringDay1 = StringDate1.substring(8);
                String StringDay2 = StringDate2.substring(8);

                int Year1 = Integer.parseInt(StringYear1);
                int Year2 = Integer.parseInt(StringYear2);

                int Month1 = Integer.parseInt(StringMonth1);
                int Month2 = Integer.parseInt(StringMonth2);

                int Day1 = Integer.parseInt(StringDay1);
                int Day2 = Integer.parseInt(StringDay2);

                long Date1 = Year1*10000+Month1*100+Day1;
                long Date2 = Year2*10000+Month2*100+Day2;

                if (Date1>Date2) {
                    Eventas temp = new Eventas();
                    temp = e.get(sr);
                    e.set(sr, e.get(sr + 1));
                    e.set(sr + 1, temp);
                }
            }
        }
        return e;
    }

    private class CustomAdapter extends BaseAdapter {
        @Override
        public int getCount() {
            return TruListEvent.size();
        }


        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        public View getView(final int position, View convertView, ViewGroup viewGroup) {
            convertView = getLayoutInflater().inflate(R.layout.list_item_view, null);

            TextView EventDateView = (TextView) convertView.findViewById(R.id.EventDateView);
            TextView EventTimeView = (TextView) convertView.findViewById(R.id.EventTimeView);
            TextView EventTitleView = (TextView) convertView.findViewById(R.id.EventTitleView);
            TextView VolunNeededView = (TextView) convertView.findViewById(R.id.VolunNeededView);
            TextView InfoTView = (TextView) convertView.findViewById(R.id.InfoTView);

            EventDateView.setText(TruListEvent.get(position).getEventDate());
            EventTimeView.setText(TruListEvent.get(position).getEventTime());
            EventTitleView.setText(TruListEvent.get(position).getTitle());
            VolunNeededView.setText(TruListEvent.get(position).getNeedVolunteers());
            InfoTView.setText(TruListEvent.get(position).getInfo());


            Button GoButton = (Button) convertView.findViewById(R.id.GoButton);
            final Button DontButton = (Button) convertView.findViewById(R.id.DontGO);
            TextView IsGoing = (TextView) convertView.findViewById(R.id.AreGoinText);
            TextView VolAre = (TextView) convertView.findViewById(R.id.volunAreView);

            IsGoing.setText("You are not going");

            //checkBox.setOnClickListener

            aDatabase = FirebaseDatabase.getInstance().getReference().child("Users").child(UserID);


            final Button DelButt = (Button) convertView.findViewById(R.id.DeleteButton);
            DelButt.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    mDatabase.child(TruListEvent.get(position).getId()).removeValue();
                    customAdapter.notifyDataSetChanged();
                    mDatabase = FirebaseDatabase.getInstance().getReference().child("VolGO");
                    mDatabase.child((TruListEvent.get(position)).getId()).removeValue();
                    TruListEvent.clear();
                }
            });

            if(!IsMod)
            {
                DelButt.setVisibility(DelButt.INVISIBLE);
            }

            if(IsVer==false){
                GoButton.setVisibility(View.GONE);
                DelButt.setVisibility(View.GONE);
                DontButton.setVisibility(View.GONE);
                IsGoing.setText("You have to be verified to go");
            }
            //
//Įdėti
            int EventIdInt = -1;
            int VolLength = 0;
            boolean IsVol = false;

            if(EventasList.size()!=0) {
                for (int h = 0; h < EventasList.size(); h++) {
                    if (EventasList.get(h).getEventID().equals(TruListEvent.get(position).getId())) {
                        EventIdInt = h;
                        VolLength = EventasList.get(EventIdInt).getUserID().size();
                    }
                }

                if(EventIdInt>=0) {
                    for (int j = 0; j < VolLength; j++) {
                        if (EventasList.get(EventIdInt).getUserID().get(j).equals(UserID)) {
                            IsVol = true;
                        }
                    }
                }
            }
            final int EventasListID = EventIdInt;

            VolAre.setText("" + VolLength);

            if (IsVol)
            {
                GoButton.setVisibility(GoButton.GONE);
                DontButton.setVisibility(DontButton.VISIBLE);
                IsGoing.setText("You are going");
                customAdapter.notifyDataSetChanged();
            }


            GoButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    cDatabase = FirebaseDatabase.getInstance().getReference().child("VolGO");
                    vDatabase = cDatabase.child(TruListEvent.get(position).getId());
                    vDatabase.child(UserID).setValue(UserID);
                    boolean isInList = false;
                    int index = 0;

                    for (int i = 0; i < EventasList.size(); i++) {
                        if (EventasList.get(i).getEventID().equals(TruListEvent.get(position).getId())) {
                            ArrayList dummy2 = EventasList.get(i).getUserID();
                            dummy2.add(UserID);
                            EventasList.get(i).setUserID(dummy2);
                            isInList = true;
                        }
                    }
                    if (!isInList) {
                        ArrayList dummy3 = new ArrayList<String>();
                        dummy3.add(UserID);
                        EventasList.add(new UserEvent(TruListEvent.get(position).getId(), dummy3));
                    }
                    customAdapter.notifyDataSetChanged();
                }
            });

            DontButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    fDatabase = FirebaseDatabase.getInstance().getReference().child("VolGO").child(TruListEvent.get(position).getId());
                    fDatabase.child(UserID).removeValue();

                    ArrayList<UserEvent> dummy = EventasList.get(EventasListID).getUserID();
                    if (dummy.size() > 1){
                        for (int i = 0; i < dummy.size(); i++){
                            if (dummy.get(i).equals(UserID)){
                                dummy.remove(i);
                              }
                          }
                    }
                    else EventasList.remove(EventasListID);

                    if (EventasList.get(EventasListID).getUserID().size()<=1){
                    EventasList.remove(EventasListID);
                    }else{
                        for (int i = 0; i < EventasList.get(EventasListID).getUserID().size(); i++){
                            if (EventasList.get(EventasListID).getUserID().get(i).equals(UserID)){
                                EventasList.get(EventasListID).getUserID().remove(i);

                              }
                          }
//                        ArrayList<String> temUIDL= new ArrayList<String>();
//                        temUIDL = EventasList.get(EventasListID).getUserID();
//                        EventasList.get(EventasListID).getUserID().clear();
//                        EventasList.get(EventasListID).getUserID().add(temUIDL);
                    }

                    customAdapter.notifyDataSetChanged();
                }
            });
            return convertView;
        }

    }
}
