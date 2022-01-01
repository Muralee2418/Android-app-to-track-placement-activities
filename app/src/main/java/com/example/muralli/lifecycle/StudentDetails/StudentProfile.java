package com.example.muralli.lifecycle.StudentDetails;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
//import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.muralli.lifecycle.R;
import com.example.muralli.lifecycle.StudentEdit;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class StudentProfile extends AppCompatActivity {
    TextView company_placed,intern,fastrack,name,roll,placementstatus, email, phone,gender,skill,arear,sec_comp,intern_from,intern_to;
    Button editButton;
    SharedPreferences selected_yr,iapStatus;
    ImageView callbutton;
    String temp_company,second_company,first_company;
    private FirebaseAuth uauth;
    Bundle bund;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_profile);
        arear=(TextView)findViewById(R.id.studentprofilearearcount);
        editButton=(Button)findViewById(R.id.studenteditbutton1);
        company_placed=(TextView)findViewById(R.id.studentprofilecompany);
        intern=(TextView)findViewById(R.id.studentinterncompany);
        fastrack=(TextView)findViewById(R.id.studentprofilefastrack);
        gender=(TextView)findViewById(R.id.studprofilegender);
        name=(TextView)findViewById(R.id.studprofilename);
        roll=(TextView)findViewById(R.id.studprofileroll1);
        placementstatus=(TextView)findViewById(R.id.stdprofileplacemenstatus1);
        email=(TextView)findViewById(R.id.studentprofileemail);
        phone=(TextView)findViewById(R.id.studentprofilephone);
        callbutton=(ImageView)findViewById(R.id.studentprofilecall);
        skill=(TextView)findViewById(R.id.studentprofileskill);
        sec_comp=(TextView)findViewById(R.id.studentprof2company);
        intern_from=(TextView)findViewById(R.id.studentprofintfrom);
        intern_to=(TextView)findViewById(R.id.studentprofintto);
        iapStatus=getSharedPreferences("IapStatus", MODE_PRIVATE);
        callbutton.setAlpha(0.75f);
        Intent i=getIntent();
        bund=i.getExtras();
        //company_placed.setText(bund.getString("company"));
        intern.setText(bund.getString("intern"));
        name.setText(bund.getString("name"));
        arear.setText(bund.getString("arear"));

       roll.setText(bund.getString("rollno"));
        gender.setText(bund.getString("gender"));
        intern_from.setText(bund.getString("internfrom"));
        intern_to.setText(bund.getString("internto"));

        temp_company=bund.getString("company");

        if(temp_company.contains("$")){
            second_company=temp_company.substring(temp_company.indexOf("$")+1);
            first_company=temp_company.substring(0,temp_company.indexOf("$"));
            company_placed.setText(first_company);
            sec_comp.setText(second_company);
        }
        else{
            first_company=bund.getString("company");
            second_company="0";
            company_placed.setText(first_company);
            sec_comp.setText(second_company);
        }
        if(bund.getString("placementstatus").equals("Placement Willing")){
            placementstatus.setText("Yes");
        }
        else{
            placementstatus.setText("No");
        }


        fastrack.setText(bund.getString("fastrack"));
        email.setText(bund.getString("email"));
        phone.setText(bund.getString("phone"));
        skill.setText(bund.getString("skill"));
        uauth = FirebaseAuth.getInstance();
        FirebaseUser user = uauth.getCurrentUser();
        //Log.d("Check",iapStatus.getString("status",null)+ " ");

        if(iapStatus.getString("status",null).equals("on")){
            editButton.setVisibility(View.VISIBLE);
        }
        else{
            editButton.setVisibility(View.GONE);
        }

        editButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent editIntet = new Intent(StudentProfile.this, StudentEdit.class);

               /* editIntet.putExtra("roll", roll.getText().toString().trim());
                editIntet.putExtra("gender", gender.getText().toString().trim());
                editIntet.putExtra("name", name.getText().toString().trim());
                editIntet.putExtra("email", email.getText().toString().trim());
                editIntet.putExtra("phone", phone.getText().toString().trim());
                editIntet.putExtra("plac_company", company_placed.getText().toString().trim());
                editIntet.putExtra("intern", intern.getText().toString().trim());
                editIntet.putExtra("skill", skill.getText().toString().trim());*/
                editIntet.putExtras(bund);

                startActivity(editIntet);
                finish();
            }
        });
        callbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Log.d("Check", "inside call button listener");
                Intent callIntent = new Intent(Intent.ACTION_DIAL);
                callIntent.setData(Uri.parse("tel:" + phone.getText().toString().trim()));
                /*if (ActivityCompat.checkSelfPermission(getBaseContext(), android.Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {

                    return;
                }*/
                StudentProfile.this.startActivity(callIntent);
            }
        });


    }
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
        //startActivity(new Intent(StudentProfile.this, com.example.muralli.lifecycle.StudentDetails.StudentInfo.class));
    }






}
