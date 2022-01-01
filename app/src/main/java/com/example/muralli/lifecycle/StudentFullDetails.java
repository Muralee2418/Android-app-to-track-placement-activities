package com.example.muralli.lifecycle;

import android.content.Intent;
import android.os.Bundle;
//import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class StudentFullDetails extends AppCompatActivity {
    TextView company_placed,intern,fastrack,gender,name,roll,placementstatus;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_full_details);
        company_placed=(TextView)findViewById(R.id.textviewcompany);
        intern=(TextView)findViewById(R.id.textviewintern);
        fastrack=(TextView)findViewById(R.id.textviewfastrack);
        gender=(TextView)findViewById(R.id.textviewgender);
        name=(TextView)findViewById(R.id.textviewname);
        roll=(TextView)findViewById(R.id.textviewrollno);
        placementstatus=(TextView)findViewById(R.id.textviewstatus);
        Intent i=getIntent();
        company_placed.setText(i.getStringExtra("company"));
        intern.setText(i.getStringExtra("intern"));
        name.setText(i.getStringExtra("name"));
        gender.setText(i.getStringExtra("gender"));
        roll.setText(i.getStringExtra("rollno"));
        placementstatus.setText(i.getStringExtra("placementstatus"));
        fastrack.setText(i.getStringExtra("fastrack"));

    }
}
