package com.example.muralli.lifecycle;

import android.content.Intent;
import android.os.Bundle;
//import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

public class EditContact extends AppCompatActivity {
    EditText editName,editCom,editMob;
    Button updateButton;
    List<String> viewers=new ArrayList<>();
    private FirebaseAuth uauth;
    DatabaseReference editContacts;
    String logdinId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_contact);
        editContacts = FirebaseDatabase.getInstance().getReference("IndustryContacts");
        editName=(EditText)findViewById(R.id.editexpert_name);
        editCom=(EditText)findViewById(R.id.editcompany_name);
        editMob=(EditText)findViewById(R.id.editcontact_no);
        updateButton=(Button)findViewById(R.id.editcontact_sumbmit);
        uauth=FirebaseAuth.getInstance();
        FirebaseUser user=uauth.getCurrentUser();
        logdinId=user.getEmail();
        viewers.add(logdinId);
        if (!logdinId.equalsIgnoreCase("moralee.r@gmail.com")){
        viewers.add("moralee.r@gmail.com");}
        Intent i=getIntent();
        editName.setText(i.getStringExtra("ename"));
        editMob.setText(i.getStringExtra("emob"));
        editCom.setText(i.getStringExtra("ecom"));
        updateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                IndustryContact i_contact = new IndustryContact(editName.getText().toString(), editMob.getText().toString(), editCom.getText().toString(),viewers);
                editContacts.child(editName.getText().toString()).setValue(i_contact);
                Toast.makeText(getApplicationContext(), "Contact Added Successfully", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(EditContact.this,ContactPage.class));
            }
        });
    }

}
