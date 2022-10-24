package com.example.dell.kvfapp;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class SaveEvent extends AppCompatActivity {

    private Button CancelButt;
    private Button mSaveEventButton;
    private DatabaseReference mDatabase;
    private EditText mTitleField;
    private EditText mInfoText;
    private EditText mEvenDate;
    private EditText mEvenTime;
    private EditText mNeedVol;
    private TextView mDisplayDate;
    private DatePickerDialog.OnDateSetListener mDateSetListener;

    Date eventDate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.create_event_page);

        mSaveEventButton = (Button) findViewById(R.id.save_event_button);
        CancelButt = (Button) findViewById(R.id.cancel_event_button);
        mDatabase = FirebaseDatabase.getInstance().getReference();

        mTitleField = (EditText) findViewById(R.id.create_event_name);
        mInfoText = (EditText) findViewById(R.id.create_event_info);
     //   mEvenDate = (EditText) findViewById(R.id.create_event_date);
        mEvenTime = (EditText) findViewById(R.id.create_event_time);
        mNeedVol = (EditText) findViewById(R.id.create_event_needvol);
        mDisplayDate = (TextView) findViewById(R.id.DisplayDate);

        mDisplayDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar cal = Calendar.getInstance();
                int year = cal.get(Calendar.YEAR);
                int month = cal.get(Calendar.MONTH);
                int day = cal.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog dialog = new DatePickerDialog(
                        SaveEvent.this,
                        android.R.style.Theme_Holo_Light_Dialog_MinWidth,
                        mDateSetListener,
                        year, month, day);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.show();
            }
        });

        CancelButt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent startIntent = new Intent(getApplicationContext(), SecondActivity.class);
                startActivity(startIntent);
            }
        });

        mSaveEventButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String title = mTitleField.getText().toString().trim();
                String info = mInfoText.getText().toString().trim();
                String eventDate = mDisplayDate.getText().toString();
                String eventTime = mEvenTime.getText().toString().trim();
                String needVolunteers = mNeedVol.getText().toString().trim();
                String areVolunteers = "0";
                String id = mDatabase.push().getKey();

                if(!TextUtils.isEmpty(title)&&!TextUtils.isEmpty(eventTime)){
                    if(eventDate.equals("Choose the Date")==false) {

                        Eventas NewEvent = new Eventas(title, info, eventDate, eventTime, needVolunteers, areVolunteers, id);

                        mDatabase.child("Event").child(id).setValue(NewEvent);
                        Toast.makeText(SaveEvent.this, "Event Saved", Toast.LENGTH_SHORT).show();
                        Intent startIntent = new Intent(getApplicationContext(), SecondActivity.class);
                        startActivity(startIntent);
                    } else Toast.makeText(SaveEvent.this, "Pick the date", Toast.LENGTH_SHORT).show();
                }else Toast.makeText(SaveEvent.this, "Fill in the necessary blanks", Toast.LENGTH_SHORT).show();
            }

        });

        mDateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                String m;
                month = month+1;
                if(month<10){
                    m="0"+month;
                }else m=""+month;
                String d;
                if(day<10){
                    d="0"+day;
                }else d=""+day;
                String date = year +"/"+m+"/"+d;
                mDisplayDate.setText(date);
            }
        };
    }
}
