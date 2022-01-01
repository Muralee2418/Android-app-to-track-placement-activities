package com.example.muralli.lifecycle;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
//import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.muralli.lifecycle.StudentDetails.StudentAllDetails;
import com.example.muralli.lifecycle.StudentDetails.StudentDetail;
import com.example.muralli.lifecycle.Taskstodo.DriveModelnew;
import com.example.muralli.lifecycle.User.UserDetail;
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

public class StudentEdit extends AppCompatActivity {
    EditText name, roll, email, phone, plac_company, intern, skill,gender1,sec_company,intern_from,intern_to;
    TextView arear;
    Button update;
    List<String> comp_list=new ArrayList<>();
    List<DriveModelnew> drive_list=new ArrayList<>();
    DriveModelnew drive_choice,drive_choice2,sec_comp2;
    //String[] comp_list;
    String e_p_comp,e_roll,e_name,e_email,e_phone,e_intern,e_skill,e_arear,e_gender,e_p_sec_comp;
    int e_salary,e_salary2;
    Spinner p_status, f_status;
    DatabaseReference student_edit,driveDetails;
    StudentDetail selectedStudent;
    StudentAllDetails student;
    String placement_status,batch,first_company,second_company;
    SharedPreferences selected_yr;
    String comp_choice,comp_choice2;
    Long x,xii,dip,cgpa;
    String gender,placement_comp,e_intern_to,e_intern_from;
    Intent to_profile;
    Calendar myCalendar;
    Calendar myCalendar2;
    Bundle bund,bundle;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_edit);
        roll = (EditText) findViewById(R.id.studeditroll1);
        name = (EditText) findViewById(R.id.studeditname);
        email = (EditText) findViewById(R.id.studenteditemail);
        phone = (EditText) findViewById(R.id.studenteditphone);
        plac_company = (EditText) findViewById(R.id.studenteditcompany);
        intern = (EditText) findViewById(R.id.studenteditinterncompany);
        skill = (EditText) findViewById(R.id.studenteditskill);
        update = (Button) findViewById(R.id.updatebutton);
        gender1=(EditText)findViewById(R.id.studeditgender);
        sec_company=(EditText)findViewById(R.id.studenteditsecondcompany);
        intern_from=(EditText)findViewById(R.id.studenteditinternfrom);
        intern_to=(EditText)findViewById(R.id.studenteditinternto);
        selected_yr=getSharedPreferences("SelectedYear", Context.MODE_PRIVATE);
        batch=selected_yr.getString("Year", null);
        arear=(TextView)findViewById(R.id.studenteditarear3);
        student_edit = FirebaseDatabase.getInstance().getReference("Students"+batch);
        driveDetails=FirebaseDatabase.getInstance().getReference("DriveDetails"+batch);
        p_status=(Spinner)findViewById(R.id.stdeditplacemenstatus);
        f_status=(Spinner)findViewById(R.id.stdeditfastrackstatus);



        //arear=(EditText)findViewById(R.id.edit)
        Intent i = getIntent();
        bund=i.getExtras();
        selectedStudent=new StudentDetail();
        String temp_company=bund.getString("company");



        roll.setText(bund.getString("rollno"));
        gender1.setText(bund.getString("gender"));
        name.setText(bund.getString("name"));
        email.setText(bund.getString("email"));
        phone.setText(bund.getString("phone"));
        if(temp_company.contains("$")){
        second_company=temp_company.substring(temp_company.indexOf("$")+1);
        first_company=temp_company.substring(0,temp_company.indexOf("$"));}
        else{
            first_company=bund.getString("company");
            second_company="0";
        }
        plac_company.setText(first_company);
        intern.setText(bund.getString("intern"));
        skill.setText(bund.getString("skill"));
        arear.setText(bund.getString("arear"));
        e_salary=Integer.valueOf(bund.getString("internstiphend"));
        sec_company.setText(second_company);
        intern_from.setText(bund.getString("internfrom"));
        intern_to.setText(bund.getString("internto"));

        myCalendar = Calendar.getInstance();
        myCalendar2 = Calendar.getInstance();

        if(bund.getString("placementstatus").equals("Placement Willing")){
            p_status.setSelection(0);
        }
        else{
            p_status.setSelection(1);
        }
        //arear.setText(bund.getString("arear"));
        final DatePickerDialog.OnDateSetListener fromdate = new DatePickerDialog.OnDateSetListener() {

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
        intern_from.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new DatePickerDialog(StudentEdit.this, fromdate, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();

            }
        });
        final DatePickerDialog.OnDateSetListener todate = new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth) {
                // TODO Auto-generated method stub
                myCalendar2.set(Calendar.YEAR, year);
                myCalendar2.set(Calendar.MONTH, monthOfYear);
                myCalendar2.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                updateLabel2();

            }

        };
        intern_to.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new DatePickerDialog(StudentEdit.this, todate, myCalendar2
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar2.get(Calendar.DAY_OF_MONTH)).show();
            }
        });
        driveDetails.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                //e_salary = 0;
                comp_list.clear();
                drive_list.clear();
                comp_list.add("Not Placed");
                drive_list.add(new DriveModelnew());
                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                    DriveModelnew drive = postSnapshot.getValue(DriveModelnew.class);
                    Log.d("Check driveCompany", drive.getCompany());
                    comp_list.add(drive.getCompany().trim());
                    drive_list.add(drive);
                    if(drive.getCompany().equalsIgnoreCase(second_company)){
                        e_salary2=drive.getSalary();
                    }


                }



            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        plac_company.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(StudentEdit.this);

                builder.setTitle("Choose Action");
                final String[] choice = comp_list.toArray(new String[0]);
                final int checkedItem = 0;
                comp_choice = choice[checkedItem];
                builder.setSingleChoiceItems(choice, checkedItem, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        comp_choice = choice[which];
                        drive_choice = drive_list.get(which);


                    }
                });
                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if (comp_choice.equalsIgnoreCase("Not Placed")) {
                            plac_company.setText("0");
                            e_salary = 0;
                        } else {
                            plac_company.setText(comp_choice.trim());
                            e_salary = drive_choice.getSalary();

                        }

                    }
                });
                builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();

                    }
                });
                AlertDialog dial = builder.create();
                dial.show();

            }
        });

        sec_company.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(StudentEdit.this);

                builder.setTitle("Choose Action");
                final String[] choice = comp_list.toArray(new String[0]);
                final int checkedItem = 0;
                comp_choice2 = choice[checkedItem];
                builder.setSingleChoiceItems(choice, checkedItem, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        comp_choice2 = choice[which];
                        drive_choice2 = drive_list.get(which);


                    }
                });
                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if (comp_choice2.equalsIgnoreCase("Not Placed")) {
                            sec_company.setText("0");
                            e_salary2 = 0;
                        } else {
                            sec_company.setText(comp_choice2.trim());
                            e_salary2 = drive_choice2.getSalary();
                        }


                    }
                });
                builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();

                    }
                });
                AlertDialog dial = builder.create();
                dial.show();


            }
        });


        p_status.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if(p_status.getSelectedItem().toString().trim().equals("Yes")){
                    placement_status="Placement Willing";
                }
                else
                {
                    placement_status="Not Willing";
                }


            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });



        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                e_name=name.getText().toString().trim();
                e_roll=roll.getText().toString().trim();
                e_email=email.getText().toString().trim();
                e_intern=intern.getText().toString().trim();
                e_phone=phone.getText().toString().trim();
                e_p_comp=plac_company.getText().toString().trim();
                e_p_sec_comp=sec_company.getText().toString().trim();
                e_skill=skill.getText().toString().trim();
                e_intern_to=intern_to.getText().toString();
                e_intern_from=intern_from.getText().toString();
              // e_arear=arear.getText().toString().trim();
                placement_comp="0";
                if(e_salary2>e_salary){e_salary=e_salary2;
                    }
                Log.d("Check sal fin",Integer.valueOf(e_salary).toString());
                Log.d("Check e_p_com",e_p_comp);

                if(e_name.equals(null)){
                    name.setError("Enter name");
                }

                else if (e_roll.equals("")){roll.setError("Enter Roll");
                roll.setFocusable(true);
                return;}
                else if(e_email.equals("")){email.setError("Enter email");
                email.setFocusable(true);
                return;}
                else if(e_intern.equals("")){intern.setError("Enter Intern Company");
                intern.setFocusable(true);return;}
                else if(e_intern.equalsIgnoreCase("0")){
                    e_intern_from="0";
                    e_intern_to="0";
                }
                else if(e_phone.equals("")){phone.setError("Enter phone no");
                phone.setFocusable(true);return;}
                else if(e_p_comp.equals("")||e_p_comp.isEmpty()){plac_company.setError("Enter placement company");
                plac_company.setFocusable(true);return;}
                if(e_p_comp.equalsIgnoreCase("0")){
                    //e_p_sec_comp="0";
                    Log.d("Check e_p_com",e_p_comp);
                    placement_comp="0";
                    e_salary=0;
                    /*driveDetails.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            e_salary=0;
                            for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                                DriveModelnew drive = postSnapshot.getValue(DriveModelnew.class);
                                Log.d("Check driveCompany",drive.getCompany());
                                if(drive.getCompany().equalsIgnoreCase(e_p_comp))
                                {
                                    Log.d("Check Companysalary",Integer.valueOf(drive.getSalary()).toString());
                                    e_salary=drive.getSalary();
                                    break;
                                }


                            }
                            Log.d("Check e_salary", Integer.valueOf(e_salary).toString());




                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {

                        }
                    });

                    if(e_salary==0){
                        plac_company.setError("Company Drive is not Available");
                        plac_company.setFocusable(true);return;

                    }*/


                }
               /* else if(e_skill.equals("")){skill.setError("Enter Skill set");
                    skill.setFocusable(true);return;}
                else if(e_arear.equals("")){arear.setError("Enter Skill set");
                    arear.setFocusable(true);return;}*/
                else {
                    if(e_p_sec_comp.isEmpty()||e_p_sec_comp.equalsIgnoreCase("0")){
                        placement_comp=e_p_comp;
                    }
                    else{
                        placement_comp=e_p_comp+"$"+e_p_sec_comp;
                    }

                }
               Log.d("check comp final",placement_comp+" ");
                UserDetail u1=new UserDetail("xyz","123","123");
                student_edit.child(u1.getName()).setValue(u1);

                student = new StudentAllDetails(roll.getText().toString().trim(),name.getText().toString().trim(),gender1.getText().toString().trim(),f_status.getSelectedItem().toString().trim(),placement_status,placement_comp,intern.getText().toString().trim(),e_intern_from,e_intern_to,bund.getString("internlocation"),e_salary,Float.valueOf(bund.getString("x")),Float.valueOf(bund.getString("xii")),Float.valueOf(bund.getString("diplamo")),Float.valueOf(bund.getString("cgpa")), Integer.valueOf(bund.getString("history")), Integer.valueOf(bund.getString("arear")),email.getText().toString().trim(),Long.valueOf(phone.getText().toString().trim()),bund.getString("dob"),bund.getString("skillset"),skill.getText().toString().trim());
                //student = new StudentAllDetails(roll.getText().toString().trim(),name.getText().toString().trim(),gender1.getText().toString().trim(),f_status.getSelectedItem().toString().trim(),placement_status,placement_comp,intern.getText().toString().trim(), bund.getString("internfrom"), bund.getString("internto"),bund.getString("internlocation"),e_salary,Float.valueOf(bund.getString("x")),Float.valueOf(bund.getString("xii")),Float.valueOf(bund.getString("diplamo")),Float.valueOf(bund.getString("cgpa")), Integer.valueOf(bund.getString("history")), Integer.valueOf(bund.getString("arear")),email.getText().toString().trim(),Long.valueOf(phone.getText().toString().trim()),bund.getString("dob"),bund.getString("skillset"),skill.getText().toString().trim());
                student_edit.child(roll.getText().toString()).setValue(student);
                Toast.makeText(getApplicationContext(), "Contact Added Successfully", Toast.LENGTH_SHORT).show();
                to_profile=new Intent(StudentEdit.this, com.example.muralli.lifecycle.StudentDetails.StudentProfile.class);
                set_IntentToProfile();
                to_profile.putExtras(bundle);
                startActivity(to_profile);
                finish();
            }
        });


    }

    public void set_IntentToProfile(){
        bundle = new Bundle();
        bundle.putString("company", student.getPlacementcompany().toString().trim());

        bundle.putString("name", student.getName().toString().trim());
        bundle.putString("rollno", student.getRollno().toString().trim());
        bundle.putString("gender", student.getGender().toString().trim());
                /*if (student.getPlacementstatus().toString().trim().equals("Placement Willing")) {
                    bundle.putString("placementstatus", "Yes");
                } else {
                    bundle.putString("placementstatus", "No");
                }*/bundle.putString("placementstatus", student.getPlacementstatus().toString().trim());

        bundle.putString("email", student.getEmail().toString().trim());
        bundle.putString("phone", student.getPhone().toString().trim());

        bundle.putString("intern", student.getInternshipcompany().toString().trim());
        bundle.putString("fastrack", student.getFastrackdetails().toString().trim());
        bundle.putString("skill", student.getAoi().toString().trim());
        bundle.putString("skillset", student.getSkill_set().toString().trim());
        bundle.putString("internfrom",student.getIntern_from().toString().trim());
        bundle.putString("internto",student.getIntern_to().toString().trim());
        bundle.putString("internlocation",student.getIntern_location().toString().trim());
        bundle.putString("internstiphend",Integer.valueOf(student.getIntern_stiphend()).toString().trim());
        //bundle.putString("internto",student.getIntern_to().toString().trim());
        bundle.putString("x", Float.valueOf(student.getX()).toString().trim());
        bundle.putString("xii", Float.valueOf(student.getXii()).toString().trim());
        bundle.putString("diplamo", Float.valueOf(student.getDiplamo()).toString().trim());
        bundle.putString("history", Integer.valueOf(student.getHistory()).toString().trim());
        bundle.putString("arear",Integer.valueOf(student.getArear()).toString().trim());
        bundle.putString("cgpa", Float.valueOf(student.getCgpa()).toString().trim());
        bundle.putString("dob",student.getDob());
    }
    public void onStart(){
        super.onStart();
        student_edit.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                int i=0;
                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                    StudentDetail student1 = postSnapshot.getValue(StudentDetail.class);
                    student1.setRollno(postSnapshot.getKey());
                    i++;
                    if (student1.getRollno().toString().trim().equals(roll.getText().toString().trim())) {
                        selectedStudent = student1;
                        break;
                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
    private void updateLabel() {
        String myFormat = "dd/MM/yyyy"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);

        intern_from.setText(sdf.format(myCalendar.getTime()).toString().trim());
    }
    private void updateLabel2() {
        String myFormat = "dd/MM/yyyy"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);

        intern_to.setText(sdf.format(myCalendar2.getTime()).toString().trim());
    }
}

