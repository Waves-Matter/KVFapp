package com.example.dell.kvfapp;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class RegisterActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private ProgressDialog mProgress;
    private DatabaseReference mDatabase;

    private EditText mNameT;
    private EditText mSurnameT;
    private EditText mEmailT;
    private EditText mPhoneT;
    private EditText mPassT;

    private Button RegButt;
    private Button CancelButt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        mAuth = FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance().getReference().child("Users");
        mProgress = new ProgressDialog(this);

        mNameT = (EditText) findViewById(R.id.USRname);
        mSurnameT = (EditText) findViewById(R.id.USRSurname);
        mEmailT = (EditText) findViewById(R.id.USRemail);
        mPhoneT = (EditText) findViewById(R.id.USRPhone);
        mPassT = (EditText) findViewById(R.id.USRPass);

        RegButt = (Button) findViewById(R.id.UserRegButt);
        RegButt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startRegister();
            }
        });

        CancelButt = (Button) findViewById(R.id.CancelRegBut);
        CancelButt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent startIntent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(startIntent);
            }
        });
    }
    private void startRegister(){
        final String name = mNameT.getText().toString();
        final String email = mEmailT.getText().toString();
        final String phone = mPhoneT.getText().toString();
        final String surname = mSurnameT.getText().toString();
        String password = mPassT.getText().toString();

        if(!TextUtils.isEmpty(name)&& !TextUtils.isEmpty(surname)&&!TextUtils.isEmpty(email)&&!TextUtils.isEmpty(password)&&!TextUtils.isEmpty(phone)) {
            if (password.length() > 6) {
                //indre smart
                mProgress.setMessage("Signing Up");
                mProgress.show();
                mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            String id = mAuth.getCurrentUser().getUid();

                            KVFUser NewUser = new KVFUser(name, surname, email, phone, id );

                            mDatabase.child(id).setValue(NewUser);

                            mProgress.dismiss();

                            Intent mint = new Intent(RegisterActivity.this, SecondActivity.class);
                            mint.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            startActivity(mint);
                        } else {mProgress.dismiss();
                        Toast.makeText(RegisterActivity.this, "Something is wrong", Toast.LENGTH_SHORT).show();}
                    }
                });
            }else Toast.makeText(RegisterActivity.this, "Password is too short", Toast.LENGTH_SHORT).show();
        }
        else {
            mProgress.dismiss();
            Toast.makeText(RegisterActivity.this, "Fill all the blanks", Toast.LENGTH_SHORT).show();
        }
    }
}
