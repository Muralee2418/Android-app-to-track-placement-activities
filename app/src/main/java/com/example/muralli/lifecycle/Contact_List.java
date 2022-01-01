package com.example.muralli.lifecycle;

//import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.List;

public class Contact_List extends AppCompatActivity {
    String[] nameArray = {"Octopus","Pig","Sheep","Rabbit","Snake","Spider" };
    String[] mobNo={"9988556644","1088995544","5566447788","6677889955","1122334455","4455661122"};
    ListView contactList;
    List<IndustryContact> ic;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact__list);


    }
    @Override
    public void onStart(){
        super.onStart();


    }
}
