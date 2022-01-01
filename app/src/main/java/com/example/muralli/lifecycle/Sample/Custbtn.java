package com.example.muralli.lifecycle.Sample;

import android.os.Bundle;
//import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.muralli.lifecycle.R;

public class Custbtn extends AppCompatActivity {
    ImageView cb,cb1,cb2,cb3,cb4,cb5;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_custbtn);
        cb=(ImageView)findViewById(R.id.imgcustbtn);
        cb1=(ImageView)findViewById(R.id.imgcustbtn1);
        cb2=(ImageView)findViewById(R.id.imgcustbtn2);
        cb3=(ImageView)findViewById(R.id.imgcustbtn3);
        cb4=(ImageView)findViewById(R.id.imgcustbtn4);
        cb5=(ImageView)findViewById(R.id.imgcustbtn5);
        cb=(ImageView)findViewById(R.id.imgcustbtn6);



        cb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        cb1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        cb2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        cb3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        cb4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        cb5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }
}
