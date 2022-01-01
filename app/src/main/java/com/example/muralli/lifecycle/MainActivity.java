package com.example.muralli.lifecycle;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
//import android.support.annotation.NonNull;
//import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.FirebaseDatabase;

public class MainActivity extends AppCompatActivity {
 TextView eid,pw,signup,title,forgotPw;
    //ImageView bg;
   // AnimationDrawable bganim;
    Button b,strt_srvc,stp_srvc;
    Intent i;
    String email,password;
    private FirebaseAuth uauth;
    ProgressBar pbar;
    Typeface tf;
    static {
        FirebaseDatabase.getInstance().setPersistenceEnabled(true);
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //bg=(ImageView)findViewById(R.id.bgimg_id);
        eid=(TextView)findViewById(R.id.emailid);
        pw=(TextView)findViewById(R.id.password);
        signup=(TextView)findViewById(R.id.Registartion);
        pbar=(ProgressBar)findViewById(R.id.loginProgress);
        title=(TextView)findViewById(R.id.title1);
        forgotPw=(TextView)findViewById(R.id.forgotpw);
        tf=Typeface.createFromAsset(getAssets(),"Pasajero.otf");
        title.setTypeface(tf);
//        bg.setBackgroundResource(R.drawable.bganim);
  //      bganim=(AnimationDrawable)bg.getBackground();
        //pbar.setProgressDrawable();


        Log.d("Life Cycle Check", "Inside oncreate");

        b=(Button)findViewById(R.id.login);
        strt_srvc=(Button)findViewById(R.id.strtservice);
        stp_srvc=(Button)findViewById(R.id.stopservice);
        uauth=FirebaseAuth.getInstance();
        FirebaseUser user = uauth.getCurrentUser();

       //startActivity(new Intent(getApplicationContext(), DashBoard.class));
        if (user == null) {

        } else{

            startActivity(new Intent(getBaseContext(), DashBoard.class));
        }

        strt_srvc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
             startActivity(new Intent(MainActivity.this,AlrmServiceSample.class));
            }
        });


        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), Main2Activity.class));
            }
        });
        forgotPw.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = eid.getText().toString().trim();
                String passw=pw.getText().toString().trim();
                if(email.isEmpty()){
                    eid.setError("Enter Email");
                    eid.setFocusable(true);
                    return;
                }
                if(passw.isEmpty()){
                    pw.setError("Enter Password");
                    eid.setFocusable(true);
                    return;
                }

                if (TextUtils.isEmpty(email)) {
                    Toast.makeText(getApplication(), "Enter your registered email id", Toast.LENGTH_SHORT).show();
                    return;
                }
                //bg.setBackgroundResource(R.drawable.bganim);
                //bganim=(AnimationDrawable)bg.getBackground();
                //bganim.start();
                pbar.setVisibility(View.VISIBLE);
                uauth.sendPasswordResetEmail(email).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            Toast.makeText(MainActivity.this, "We have sent you instructions to reset your password!", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(MainActivity.this, "Failed to send reset email!", Toast.LENGTH_SHORT).show();
                        }
                  //      bganim.stop();
                  pbar.setVisibility(View.GONE);
                    }
                });
            }
        });
        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               pbar.setVisibility(View.VISIBLE);

                //bganim.start();
                email=eid.getText().toString().trim();
                password=pw.getText().toString().trim();
                uauth=FirebaseAuth.getInstance();
                //Log.d("Check","inside login on click");
                //Log.d("Check",email+password);
                uauth.signInWithEmailAndPassword(email,password)
                        .addOnCompleteListener(MainActivity.this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {
                                    finish();
                                   // Log.d("Check", "inside sigin taskcompletion");
                                    pbar.setVisibility(View.GONE);
                                    Toast.makeText(getApplicationContext(), "Login Sucessfull", Toast.LENGTH_SHORT).show();
                                    startActivity(new Intent(getApplicationContext(), DashBoard.class));
                  //                  bganim.stop();

                                }
                                else{
                                    Toast.makeText(getApplicationContext(), "Login Failed", Toast.LENGTH_SHORT).show();
                                    pbar.setVisibility(View.GONE);
                                    //bganim.stop();

                                }
                            }
                        });



            }
        });




    }
    @Override
    protected void onStart()
    {
        super.onStart();

    }
    protected void onResume() {
        super.onResume();


    }
    protected void onPause(){
        super.onPause();

    }

}
