package com.example.muralli.lifecycle.User;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
//import android.support.v7.app.AppCompatActivity;
//import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.example.muralli.lifecycle.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class UserManagement extends AppCompatActivity {
    ListView userList;
    List<UserDetail>userObjs=new ArrayList<>();

    DatabaseReference users;
    //private FirebaseAuth uauth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_management);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        userList = (ListView) findViewById(R.id.userManagement);
        users = FirebaseDatabase.getInstance().getReference("User");

        users.addValueEventListener(new ValueEventListener() {

                                        @Override
                                        public void onDataChange(DataSnapshot dataSnapshot) {
                                            userObjs.clear();
                                            for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                                                UserDetail curntuser = postSnapshot.getValue(UserDetail.class);
                                                //useNameList.add(curntuser.getName());
                                                //Log.d("Check", "username" + curntuser.getName());
                                                userObjs.add(curntuser);
                                            }

                                        }

                                        @Override
                                        public void onCancelled(DatabaseError databaseError) {

                                        }
                                    }

        );

        Log.d("Check", "No of user" + Integer.valueOf(userObjs.size()).toString());

        UserListAdapter usrAdptr = new UserListAdapter(UserManagement.this, userObjs);
        userList.setAdapter(usrAdptr);

        userList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {
                Log.d("Check", userObjs.get(position).getName());

                final AlertDialog.Builder alertDialog = new AlertDialog.Builder(getBaseContext());
                alertDialog.setIcon(R.drawable.delfin);

                alertDialog.setTitle("Confirm");

                alertDialog.setMessage("Are you sure to Delete");


                alertDialog.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        users.child(userObjs.get(position).getName()).removeValue();
                    }
                });

                // Log.d("Check", "No of Users" + Integer.valueOf(userObjs.size()).toString());


            }

        });
    }}
