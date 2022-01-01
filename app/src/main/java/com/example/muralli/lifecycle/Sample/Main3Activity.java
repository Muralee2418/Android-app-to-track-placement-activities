package com.example.muralli.lifecycle.Sample;

import android.annotation.TargetApi;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Build;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityOptionsCompat;
//import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.muralli.lifecycle.R;
import com.example.muralli.lifecycle.SampleProfile;

public class Main3Activity extends AppCompatActivity {
    ImageView iv;
    EditText samp_username,samp_password;
    TextView sample_reg;
    Button sample_submit;
    String uName,pWord;
    String DATABASE_NAME="userinfo";
    SQLiteDatabase mDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main3);
        iv=(ImageView)findViewById(R.id.userpic);
        samp_username=(EditText)findViewById(R.id.usernamesample);
        samp_password=(EditText)findViewById(R.id.samplepassword);
        sample_submit=(Button)findViewById(R.id.samplebutton);
        sample_reg=(TextView)findViewById(R.id.samplereg);
        mDatabase = openOrCreateDatabase("USERDETAILS", MODE_PRIVATE, null);
        mDatabase.execSQL("CREATE TABLE IF NOT EXISTS employees1 (name varchar(10) primary key, Pword varchar(10) );");


        sample_submit.setOnClickListener(new View.OnClickListener() {
            @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
            @Override
            public void onClick(View v) {
                uName = samp_username.getText().toString().trim();
                pWord = samp_password.getText().toString().trim();
                if (retriveData()) {
                    Intent i = new Intent(getBaseContext(), SampleProfile.class);
                    ActivityOptionsCompat oc = ActivityOptionsCompat.makeSceneTransitionAnimation(Main3Activity.this, findViewById(R.id.userpic), "myimage");
                    startActivity(i, oc.toBundle());
                } else {

                    Toast.makeText(Main3Activity.this, "User Credential is incorrect", Toast.LENGTH_SHORT).show();
                }


            }
        });
        sample_reg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                insertData();
            }
        });

    }
    public void insertData(){
        uName = samp_username.getText().toString().trim();
        pWord = samp_password.getText().toString().trim();
        String insertSQL = "INSERT INTO employees1 (name,Pword) VALUES (?,?);";
        mDatabase.execSQL(insertSQL, new String[]{uName,pWord});
        Toast.makeText(this, "Insertion is sucessfull", Toast.LENGTH_SHORT).show();
    }
    public boolean retriveData ()
    {
        Cursor cursorEmployees = mDatabase.rawQuery("SELECT * FROM employees1 where name=? and Pword=?", new String[]{uName,pWord});

        if (cursorEmployees.moveToFirst()) {
            //looping through all the records
            do {
                //retrieving data from cursor

                String id=cursorEmployees.getString(0);
                String name=cursorEmployees.getString(1);

                Toast.makeText(Main3Activity.this, id+name, Toast.LENGTH_SHORT).show();


            } while (cursorEmployees.moveToNext());
            cursorEmployees.close();
            return true;
        }
        else{
            cursorEmployees.close();
            return false;

        }

    }
}




