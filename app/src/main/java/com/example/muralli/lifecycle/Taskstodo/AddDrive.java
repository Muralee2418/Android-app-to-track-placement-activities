package com.example.muralli.lifecycle.Taskstodo;

import android.app.DatePickerDialog;
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
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

public class AddDrive extends AppCompatActivity {
    EditText drivecompany,drivedt,drivesalary;
    SharedPreferences selected_yr;
    String batch;
    SharedPreferences.Editor edit;
    List<String>eligbList =new ArrayList<>();

    //String str_drivedate;
    //DatePicker drivedate;
    //EditText editText;

    Calendar myCalendar;
    Button addDrive;
    DatabaseReference drivedetailadd_fb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_drive);
        eligbList.add("");
        drivecompany=(EditText)findViewById(R.id.adddrive_company);
        //drivedate=(DatePicker)findViewById(R.id.adddrive_date);
        drivedt=(EditText)findViewById(R.id.adddrive_dt);
        addDrive=(Button)findViewById(R.id.adddrive_addbutn);
        drivesalary=(EditText)findViewById(R.id.adddrive_salary);
        selected_yr=getSharedPreferences("SelectedYear", MODE_PRIVATE);
        batch=selected_yr.getString("Year", null);





        if(batch.equalsIgnoreCase("2018")){

            drivedetailadd_fb= FirebaseDatabase.getInstance().getReference("DriveDetails");}
        else{
            drivedetailadd_fb= FirebaseDatabase.getInstance().getReference("DriveDetails"+batch);
        }
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
                new DatePickerDialog(AddDrive.this, date, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();

            }
        });

       /* drivedate.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {

            @Override
            public void onSelectedDayChange(CalendarView arg0, int year, int month,
                                            int date) {
                str_drivedate=date + "/" + month + "/" + year;
                Toast.makeText(getApplicationContext(), date + "/" + month + "/" + year, Toast.LENGTH_SHORT).show();
            }
        });*/
        /*this.editText.setOnFocusChangeListener(this);
        myCalendar = Calendar.getInstance();
        @Override
        public void onDateSet(DatePicker view,int year,int monthOfYear,int dayOfMonth)     {
            // this.editText.setText();

            String myFormat = "MMM dd, yyyy"; //In which you need put here
            SimpleDateFormat sdformat = new SimpleDateFormat(myFormat, Locale.US);
            myCalendar.set(Calendar.YEAR, year);
            myCalendar.set(Calendar.MONTH, monthOfYear);
            myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);

            editText.setText(sdformat.format(myCalendar.getTime()));

        }

        @Override
        public void onFocusChange(View v, boolean hasFocus) {
            // TODO Auto-generated method stub
            if(hasFocus){
                new DatePickerDialog(ctx, this, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        }*/

        addDrive.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
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
                Log.d("Check",drivedt.getText().toString());

                DriveModelnew dm=new DriveModelnew(eligbList,drivecompany.getText().toString().trim(),0,drivedt.getText().toString().trim(),Integer.parseInt(drivesalary.getText().toString().trim()));
                drivedetailadd_fb.child(dm.getCompany()).setValue(dm);
                //startActivity(new Intent(AddDrive.this,PlacementTasks.class));
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
