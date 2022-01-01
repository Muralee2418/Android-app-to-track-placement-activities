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
import java.util.Calendar;
import java.util.Locale;

public class AddTask extends AppCompatActivity {
    EditText taskName, task_frm_date,task_to_date, task_yr;
    SharedPreferences selected_yr;
    String batch;
    SharedPreferences.Editor edit;
    //List<String> eligbList =new ArrayList<>();

    //String str_drivedate;
    //DatePicker drivedate;
    //EditText editText;

    Calendar myCalendar;
    Calendar myCalendar2;
    Button addDrive;
    DatabaseReference drivedetailadd_fb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_task);
        //Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        //setSupportActionBar(toolbar);


        //eligbList.add("");
        taskName =(EditText)findViewById(R.id.addtask_name);
        //drivedate=(DatePicker)findViewById(R.id.adddrive_date);
        task_frm_date =(EditText)findViewById(R.id.addtask_frm_dt);
        task_to_date=(EditText)findViewById(R.id.addtask_to_dt);
        addDrive=(Button)findViewById(R.id.addtask_addbutn);
        task_yr =(EditText)findViewById(R.id.addtask_year);
        selected_yr=getSharedPreferences("SelectedYear", MODE_PRIVATE);
        batch=selected_yr.getString("Year", null);





        if(batch.equalsIgnoreCase("2018")){

            drivedetailadd_fb= FirebaseDatabase.getInstance().getReference("TaskDetails");}
        else{
            drivedetailadd_fb= FirebaseDatabase.getInstance().getReference("TaskDetails"+batch);
        }
        //drivedetailadd_fb=FirebaseDatabase.getInstance().getReference("DriveDetails");

        myCalendar = Calendar.getInstance();
        myCalendar2 = Calendar.getInstance();

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

        task_frm_date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new DatePickerDialog(AddTask.this, date, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();
                //updateLabel();

            }
        });
        task_to_date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               new DatePickerDialog(AddTask.this, todate, myCalendar2
                        .get(Calendar.YEAR), myCalendar2.get(Calendar.MONTH),
                        myCalendar2.get(Calendar.DAY_OF_MONTH)).show();
               //updateLabel2();

            }
        });

        addDrive.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(taskName.getText().toString().trim().isEmpty()){
                    taskName.setError("Enter Task Name");
                    taskName.setFocusable(true);
                    return;
                }
                if(task_frm_date.getText().toString().trim().isEmpty()){
                    task_frm_date.setError("Enter from Date");
                    task_frm_date.setFocusable(true);
                    return;
                }
                if(task_to_date.getText().toString().trim().isEmpty()){
                    task_to_date.setText("0");
                }

                Log.d("Check", task_frm_date.getText().toString());

                TaskModel tm=new TaskModel(0,task_to_date.getText().toString().trim(),task_frm_date.getText().toString().trim(),taskName.getText().toString().trim());
                drivedetailadd_fb.child(tm.getTask_name()).setValue(tm);
                //startActivity(new Intent(AddDrive.this,PlacementTasks.class));
                finish();


            }
        });

    }

    private void updateLabel() {
        String myFormat = "MM/dd/yy"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);

        task_frm_date.setText(sdf.format(myCalendar.getTime()).toString().trim());
    }
    private void updateLabel2() {
        String myFormat = "MM/dd/yy"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);

        task_to_date.setText(sdf.format(myCalendar2.getTime()).toString().trim());
    }

}
