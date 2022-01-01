package com.example.muralli.lifecycle;

import android.graphics.Typeface;
import android.os.Bundle;
//import android.support.annotation.NonNull;
//import android.support.v7.app.AppCompatActivity;
//import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.example.muralli.lifecycle.User.UserDetail;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class Main2Activity extends AppCompatActivity {
    EditText un,edit_email,edit_pw,confedit_pw,uName;
    List<String>useNameList =new ArrayList<>();
    Button reg;
    TextView regtitle;
    String email,password,confpassword,username;
    ProgressBar pbar;
    DatabaseReference user;
    private FirebaseAuth auth;
     Typeface tf;
    UserDetail userInfo;
   // private Firebase mRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


       // un=(EditText)findViewById(R.id.uName);
        regtitle=(TextView)findViewById(R.id.regtitle);
        edit_email=(EditText)findViewById(R.id.eMail);
        edit_pw=(EditText)findViewById(R.id.pW);
        pbar=(ProgressBar)findViewById(R.id.pbar1);
        uName=(EditText)findViewById(R.id.regUserName);
        reg=(Button)findViewById(R.id.Register);
        confedit_pw=(EditText)findViewById(R.id.confpW);
        tf= Typeface.createFromAsset(getAssets(), "trench100free.ttf");
        regtitle.setTypeface(tf);
        user = FirebaseDatabase.getInstance().getReference("User");

        user.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                    UserDetail curntuser=postSnapshot.getValue(UserDetail.class);
                    useNameList.add(curntuser.getName());


                }
                }

                @Override
                public void onCancelled (DatabaseError databaseError){

                }
            }

            );


                    reg.setOnClickListener(new View.OnClickListener()

                                           {
                                               @Override
                                               public void onClick(View v) {
                                                   email = edit_email.getText().toString().trim();
                                                   password = edit_pw.getText().toString().trim();
                                                   confpassword = confedit_pw.getText().toString().trim();
                                                   username = uName.getText().toString().trim();
                                                   if (email.isEmpty()) {
                                                       edit_email.setError("Please Enter Email");
                                                       edit_email.setFocusable(true);
                                                       return;
                                                   }
                                                   if (username.isEmpty()) {
                                                       uName.setError("Please Enter Email");
                                                       uName.setFocusable(true);
                                                       return;
                                                   }
                                                   if (password.isEmpty()) {
                                                       edit_pw.setError("Please enter Password");
                                                       edit_pw.setFocusable(true);
                                                       return;
                                                   }
                                                   if (!password.matches(confpassword)) {
                                                       confedit_pw.setError("Password doesn't match");
                                                       confedit_pw.setFocusable(true);
                                                       return;
                                                   }
                                                   if (useNameList.contains(username)) {
                                                       uName.setError("User name already exist");
                                                       uName.setFocusable(true);
                                                       return;
                                                   }

                                                   //Log.d("Check", "before firebase connection");
                                                   pbar.setVisibility(View.VISIBLE);
                                                   userInfo = new UserDetail(email, "user", username);
                                                   auth = FirebaseAuth.getInstance();
                                                   auth.createUserWithEmailAndPassword(email, password)
                                                           .addOnCompleteListener(Main2Activity.this, new OnCompleteListener<AuthResult>() {
                                                               @Override
                                                               public void onComplete(@NonNull Task<AuthResult> task) {
                                                                   if (task.isSuccessful()) {
                                                                       Toast.makeText(getBaseContext(), "Registration Successfull", Toast.LENGTH_SHORT).show();
                                                                       user.child(userInfo.getName()).setValue(userInfo);
                                                                       finish();
                                                                       pbar.setVisibility(View.GONE);
                                                                       //startActivity(new Intent(Main2Activity.this,Main2Activity.class));
                                                                   } else {
                                                                       Toast.makeText(getBaseContext(), "please try again", Toast.LENGTH_SHORT).show();
                                                                       pbar.setVisibility(View.GONE);

                                                                   }
                                                               }
                                                           });


                                               }
                                           }

                    );
        }

    }
