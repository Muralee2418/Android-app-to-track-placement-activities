package com.example.muralli.lifecycle.Taskstodo;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
//import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import com.example.muralli.lifecycle.R;
import com.example.muralli.lifecycle.StudentDetails.StudentAllDetails;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

public class EditTask extends AppCompatActivity {
    EditText taskName, task_frm_date, task_to_date;
    SharedPreferences selected_yr;
    String oldcompany;
    String batch;
    SharedPreferences.Editor edit;
    List<String> eligbList =new ArrayList<>();
    Calendar myCalendar;
    Calendar myCalendar2;
    Button updateTask;
    DatabaseReference taskdetailedit_fb;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_task);
        eligbList.add("");
        taskName =(EditText)findViewById(R.id.edittask_name);
        //drivedate=(DatePicker)findViewById(R.id.adddrive_date);
        task_frm_date =(EditText)findViewById(R.id.edittask_frm_dt);
        updateTask =(Button)findViewById(R.id.edittask_butn);
        task_to_date =(EditText)findViewById(R.id.edittask_to_dt);
        selected_yr=getSharedPreferences("SelectedYear", MODE_PRIVATE);
        batch=selected_yr.getString("Year", null);
        Intent i=getIntent();
        final Bundle task_bundle=i.getExtras();
        taskName.setText(task_bundle.getString("t_name"));
        oldcompany=i.getStringExtra("t_name");
        task_frm_date.setText(task_bundle.getString("t_frm_date").trim());
        task_to_date.setText(task_bundle.getString("t_to_date"));
        //final TaskModel tm_old = new TaskModel(Integer.parseInt(task_bundle.getString("t_status")),task_frm_date.getText().toString().trim(),task_to_date.getText().toString().trim(),taskName.getText().toString().trim());






        //studentdetail_fb= FirebaseDatabase.getInstance().getReference("Students"+batch);

        taskdetailedit_fb= FirebaseDatabase.getInstance().getReference("TaskDetails"+batch);
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
                new DatePickerDialog(EditTask.this, date, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();


            }
        });
        task_to_date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               new DatePickerDialog(EditTask.this, todate, myCalendar2
                        .get(Calendar.YEAR), myCalendar2.get(Calendar.MONTH),
                        myCalendar2.get(Calendar.DAY_OF_MONTH)).show();

            }
        });
        updateTask.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String date_temp=task_to_date.getText().toString().trim();

                final List<StudentAllDetails> placedStudents = new ArrayList<StudentAllDetails>();
                if (taskName.getText().toString().trim().isEmpty()) {
                    taskName.setError("Enter Task Name");
                    taskName.setFocusable(true);
                    return;
                }
                if (task_frm_date.getText().toString().trim().isEmpty()) {
                    task_frm_date.setError("Enter Drive from Date");
                    task_frm_date.setFocusable(true);
                    return;
                }
                if (task_to_date.getText().toString().trim().isEmpty()) {
                    date_temp="0";

                    //return;
                }
                final TaskModel tm = new TaskModel(Integer.parseInt(task_bundle.getString("t_status")),date_temp,task_frm_date.getText().toString().trim(),taskName.getText().toString().trim());




                taskdetailedit_fb.child(tm.getTask_name()).setValue(tm);
                if(!tm.getTask_name().equalsIgnoreCase(oldcompany)){
                    taskdetailedit_fb.child(oldcompany).removeValue();

                }


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



