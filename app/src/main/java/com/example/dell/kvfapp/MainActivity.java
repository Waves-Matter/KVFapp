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
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class MainActivity extends AppCompatActivity {

private EditText mEmailField;
private EditText mPasswordField;
private ProgressDialog nProgress;

private Button mLoginBT;
private Button mRegButt;

private FirebaseAuth mAuth;
private DatabaseReference mDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mAuth = FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance().getReference().child("Users");
        nProgress = new ProgressDialog(this);

        mEmailField = (EditText) findViewById(R.id.EmailTextEntry);
        mPasswordField = (EditText) findViewById(R.id.PasswordTextEntry);


        mLoginBT = (Button) findViewById(R.id.LogInBT);

        mLoginBT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                checkLogin();
            }
        });

        mRegButt = (Button) findViewById(R.id.RegButt);
        mRegButt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent startIntent = new Intent(getApplicationContext(),RegisterActivity.class);
                startActivity(startIntent);
            }
        });

    }

    private  void startSingIn()
    {
        String email = mEmailField.getText().toString();
        String password = mPasswordField.getText().toString();
    }
// vieną po kito įdėti
    private void checkLogin(){
        String email = mEmailField.getText().toString().trim();
        String pass = mPasswordField.getText().toString().trim();

        if (!TextUtils.isEmpty(email) && !TextUtils.isEmpty(pass)){
            nProgress.setMessage("Logging In");
            nProgress.show();
            mAuth.signInWithEmailAndPassword(email, pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if(task.isSuccessful()){
                        checkUserExist();
                        nProgress.dismiss();

                    }else{
                        nProgress.dismiss();
                        Toast.makeText(MainActivity.this, "Error Login", Toast.LENGTH_LONG).show();
                    }
                }
            });
        }
        else {nProgress.dismiss();
            Toast.makeText(MainActivity.this, "Fill in all the blanks", Toast.LENGTH_LONG).show();
        }
    }

    private void checkUserExist(){
        final String uid = mAuth.getCurrentUser().getUid();
        mDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.hasChild(uid)){

                    Intent loginIntent = new Intent(MainActivity.this, SecondActivity.class);
                    loginIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(loginIntent);

                } else Toast.makeText(MainActivity.this, "Hm, maybe create an account first..?", Toast.LENGTH_LONG).show();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

}
