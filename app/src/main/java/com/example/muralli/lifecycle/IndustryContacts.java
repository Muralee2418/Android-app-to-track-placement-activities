package com.example.muralli.lifecycle;

import android.content.Intent;
import android.os.Bundle;
//import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

public class IndustryContacts extends AppCompatActivity {
    EditText name, cNo, comName;
    Button submit, viewContact;
    List<IndustryContact> ind_contact = new ArrayList<>();
    ListView listviewContact;
    DatabaseReference industryContacts;
    List<String> viewers=new ArrayList<>();
    private FirebaseAuth uauth;
    DatabaseReference editContacts;
    String logdinId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_industry_contacts);
        name = (EditText) findViewById(R.id.expert_name);
        cNo = (EditText) findViewById(R.id.contact_no);
        comName = (EditText) findViewById(R.id.company_name);
        submit = (Button) findViewById(R.id.contact_sumbmit);

        listviewContact = (ListView) findViewById(R.id.mainlistview);
        industryContacts = FirebaseDatabase.getInstance().getReference("IndustryContacts");
        uauth=FirebaseAuth.getInstance();
        uauth=FirebaseAuth.getInstance();
        FirebaseUser user=uauth.getCurrentUser();
        logdinId=user.getEmail().toString().trim();
        viewers.add(logdinId);
        if (!logdinId.equalsIgnoreCase("hod_cse@drmcet.ac.in")){
            viewers.add("hod_cse@drmcet.ac.in");}
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String i_name = name.getText().toString().trim();
                String i_cno = cNo.getText().toString().trim();
                String i_comname = comName.getText().toString().trim();
                if (i_name.isEmpty()) {
                    name.setError("Please Enter Name");
                    name.setFocusable(true);
                    return;
                }
                if (i_cno.isEmpty()) {
                    cNo.setError("Please Enter Contact no");
                    cNo.setFocusable(true);
                    return;
                }
                if (i_comname.isEmpty()) {
                    comName.setError("please Enter Company Name");
                    comName.setFocusable(true);
                    return;
                }
                if(!i_cno.isEmpty()&i_cno.length()<10)
                {
                    if(i_cno.length()>10)
                    {
                        comName.setError("please Enter Valid Phone Number");
                        comName.setFocusable(true);
                        return;

                    }
                }


                IndustryContact i_contact = new IndustryContact(i_name, i_cno, i_comname,viewers);
                industryContacts.child(i_contact.getExpert_Name()).setValue(i_contact);
                Toast.makeText(getApplicationContext(), "Contact Added Successfully", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(getBaseContext(),ContactPage.class));
                finish();


            }
        });


    }

    public void onBackPressed(){
        finish();
    }
}

