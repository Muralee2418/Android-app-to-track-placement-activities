package com.example.muralli.lifecycle;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
//import android.support.design.widget.FloatingActionButton;
//import android.support.v7.app.AppCompatActivity;
//import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class ContactPage extends AppCompatActivity {
  List<IndustryContact> ind_contact= new ArrayList<>();
  // List<StudentInfo> s_list= new ArrayList<>();
  SharedPreferences user_email;

    SharedPreferences.Editor logdinUser;
    String logdinId;
    ListView listviewContact;
    DatabaseReference industryContacts;
    private FirebaseAuth uauth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact_page);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        listviewContact=(ListView)findViewById(R.id.mainlistview);
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        uauth=FirebaseAuth.getInstance();
        FirebaseUser user=uauth.getCurrentUser();
        logdinId=user.getEmail().toString().trim();
        listviewContact.setVisibility(View.VISIBLE);
        /*if(user.getEmail().equals("moralee.r@gmail.com")){
            listviewContact.setVisibility(View.VISIBLE);
        }*/
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               startActivity(new Intent(getBaseContext(),IndustryContacts.class));
            }
        });
    }


    @Override

    protected void onStart(){
        super.onStart();
        //Log.d("Check", "inside Onstart contact page");
        industryContacts= FirebaseDatabase.getInstance().getReference("IndustryContacts");
        industryContacts.keepSynced(true);
        industryContacts.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {


            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        industryContacts.addValueEventListener(new ValueEventListener() {
            @Override

            public void onDataChange(DataSnapshot dataSnapshot) {
             ind_contact.clear();

                //Log.d("Check", "inside onDataChange");
                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                    IndustryContact i_c = postSnapshot.getValue(IndustryContact.class);
                    i_c.expert_Name = postSnapshot.getKey();

                    if(i_c.checkViewers(logdinId)){
                       // Log.d("check",logdinId+"");
                    ind_contact.add(i_c);}


                }
                ContactListAdapter contadapt = new ContactListAdapter(ContactPage.this, ind_contact);

                listviewContact.setAdapter(contadapt);


            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
               // Log.d("Check", databaseError.toString());

            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        //startActivity(new Intent(ContactPage.this,DashBoard.class));
        finish();
    }
}

