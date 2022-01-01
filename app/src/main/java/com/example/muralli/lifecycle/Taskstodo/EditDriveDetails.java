package com.example.muralli.lifecycle.Taskstodo;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
//import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import com.example.muralli.lifecycle.R;
import com.example.muralli.lifecycle.StudentDetails.StudentAllDetails;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

public class EditDriveDetails extends AppCompatActivity {
    EditText drivecompany,drivedt,drivesalary;
    SharedPreferences selected_yr;
    String oldcompany;
    String batch;
    SharedPreferences.Editor edit;
    List<String> eligbList =new ArrayList<>();
    Calendar myCalendar;
    Button updateDrive;
    DatabaseReference drivedetailedit_fb,studentdetail_fb;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_drive_details);
        eligbList.add("");
        drivecompany=(EditText)findViewById(R.id.et_company_editdrive);
        //drivedate=(DatePicker)findViewById(R.id.adddrive_date);
        drivedt=(EditText)findViewById(R.id.et_date_editdrive);
        updateDrive=(Button)findViewById(R.id.bt_editdrive);
        drivesalary=(EditText)findViewById(R.id.et_salary_editdrive);
        selected_yr=getSharedPreferences("SelectedYear", MODE_PRIVATE);
        batch=selected_yr.getString("Year", null);
        Intent i=getIntent();
        drivecompany.setText(i.getStringExtra("company"));
        oldcompany=i.getStringExtra("company");
        drivedt.setText(i.getStringExtra("companydate"));
        drivesalary.setText(i.getStringExtra("salary"));






            studentdetail_fb= FirebaseDatabase.getInstance().getReference("Students"+batch);

            drivedetailedit_fb= FirebaseDatabase.getInstance().getReference("DriveDetails"+batch);
                //drivedetailadd_fb=FirebaseDatabase.getInstance().getReference("DriveDetails");

        myCalendar = Calendar.getInstance();

        final DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth) {
                // TODO Auto-generated method stub
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, monthOfYear);
                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                updateLabel();
            }

        };

        drivedt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new DatePickerDialog(EditDriveDetails.this, date, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();

            }
        });
        updateDrive.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final List<StudentAllDetails> placedStudents= new ArrayList<StudentAllDetails>();
                if(drivecompany.getText().toString().trim().isEmpty()){
                    drivecompany.setError("Enter Company Name");
                    drivecompany.setFocusable(true);
                    return;
                }
                if(drivedt.getText().toString().trim().isEmpty()){
                    drivedt.setError("Enter Drive Date");
                    drivedt.setFocusable(true);
                    return;
                }
                if(drivesalary.getText().toString().trim().isEmpty()){
                    drivedt.setError("Enter Salary");
                    drivedt.setFocusable(true);
                    return;
                }
                final DriveModelnew dm = new DriveModelnew(eligbList, drivecompany.getText().toString().trim(), 0, drivedt.getText().toString().trim(), Integer.parseInt(drivesalary.getText().toString().trim()));

                drivedetailedit_fb.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                            DriveModelnew drive1 = postSnapshot.getValue(DriveModelnew.class);
                            drive1.setCompany(postSnapshot.getKey());
                            if(drive1.getCompany().equalsIgnoreCase(oldcompany)){
                                drive1.setCompany(dm.getCompany());

                                    drive1.setCompdate(dm.getDate());

                                drive1.setSalary(dm.getSalary());
                                drivedetailedit_fb.child(drive1.getCompany()).setValue(drive1);
                                //drivedetailedit_fb.child(oldcompany).removeValue();
                                break;
                            }

                        }


                        }

                        @Override
                        public void onCancelled (DatabaseError databaseError){

                        }
                    }

                    );



                    drivedetailedit_fb.child(dm.getCompany()).

                    setValue(dm);

                    studentdetail_fb.addListenerForSingleValueEvent(new

                    ValueEventListener() {
                        @Override
                        public void onDataChange (DataSnapshot dataSnapshot){
                            Log.d("check drive", oldcompany);

                            for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {

                                StudentAllDetails student1 = postSnapshot.getValue(StudentAllDetails.class);
                                student1.setRollno(postSnapshot.getKey());
                                if (student1.getPlacementcompany().equalsIgnoreCase(oldcompany)) {
                                    student1.setPlacementcompany(dm.getCompany());
                                    student1.setIntern_stiphend(dm.getSalary());
                                    placedStudents.add(student1);
                                }


                            }
                            Log.d("check drive", Integer.valueOf(placedStudents.size()).toString().trim());
                            if (placedStudents.size() != 0) {
                                for (StudentAllDetails s : placedStudents) {
                                    studentdetail_fb.child(s.getRollno()).setValue(s);
                                }
                            }

                        }

                        @Override
                        public void onCancelled (DatabaseError databaseError){

                        }
                    }

                    );


                    finish();


                }
            });
    }
    private void updateLabel() {
        String myFormat = "MM/dd/yy"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);

        drivedt.setText(sdf.format(myCalendar.getTime()).toString().trim());
    }
}
