package com.example.muralli.lifecycle.StudentDetails;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
//import android.support.annotation.NonNull;
//import android.support.design.widget.FloatingActionButton;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
//import android.support.v7.app.AlertDialog;
// android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.example.muralli.lifecycle.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.apache.poi.ss.usermodel.Workbook;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import jxl.Cell;
import jxl.Sheet;
import jxl.WorkbookSettings;
import jxl.write.WritableSheet;

import static android.Manifest.permission.READ_EXTERNAL_STORAGE;

public class StudentInfo extends AppCompatActivity {
    ListView studlistview;
    List<StudentAllDetails> student_list =new ArrayList<>();
    List<StudentAllDetails> student_new_list =new ArrayList<>();
    List<String> student_row=new ArrayList<>();
    List<StudentAllDetails> student_listfilter =new ArrayList<>();
    DatabaseReference student_fireb,student_fireb_new;
    DatabaseReference driveDetail_fireb;
    SharedPreferences selected_yr,iapStatus;
    SharedPreferences.Editor edit,iapedit;
    String batch;
    FloatingActionButton upload;
    File file;
    int state;
    WorkbookSettings wbSettings;
    jxl.Workbook workbook;
    WritableSheet sheet;
    String csvFile="myData.xls";
    int col=0,row=0;
    int rd_row=0,rd_col=0;
    Intent it,itn_choosefyl;
    EditText ipsearch;
    StudentAdapter studentadapt;
    String iapsts;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_info);

        Workbook workbook = null;
        studlistview=(ListView)findViewById(R.id.studentinfolistview);

        ipsearch=(EditText)findViewById(R.id.inputsearch);
        iapStatus=getSharedPreferences("IapStatus", MODE_PRIVATE);
        iapsts=iapStatus.getString("status", null);
       // Log.d("Check info", iapStatus.getString("status", null) + " ");
        //iapedit=iapStatus.edit();
        selected_yr=getSharedPreferences("SelectedYear", Context.MODE_PRIVATE);
        batch=selected_yr.getString("Year", null);
       // Log.d("Check", ""+batch);
        state=0;

        final Intent it=getIntent();
        upload= (FloatingActionButton) findViewById(R.id.fabUpldData);
        student_fireb= FirebaseDatabase.getInstance().getReference("Students"+batch);
        student_fireb.keepSynced(true);

      if(iapsts.equals("on")){

            upload.setVisibility(View.VISIBLE);


        }
        else
        {
            upload.setVisibility(View.GONE);


        }

        upload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final AlertDialog.Builder alertDialog = new AlertDialog.Builder(StudentInfo.this);

                alertDialog.setTitle("Hey Confirm!!");

                alertDialog.setMessage("Are you sure want to update Skillmatrix? It will overwrite existing becarefull");


                alertDialog.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        final AlertDialog.Builder altDylog_ok = new AlertDialog.Builder(StudentInfo.this);
                        alertDialog.setCancelable(false);

                        altDylog_ok.setTitle("Hey Verify");

                        altDylog_ok.setMessage("Please keep your skillmatrix in Device storage inside <IapcCse> folder");


                        altDylog_ok.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                upload_details();
                               // itn_choosefyl = new Intent(Intent.ACTION_GET_CONTENT);
                               // itn_choosefyl.setType("*/*");
                                //startActivityForResult(itn_choosefyl, 7);


                            }
                        });
                        altDylog_ok.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.cancel();
                            }
                        });
                        altDylog_ok.show();




                    }
                });
                alertDialog.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });


                alertDialog.show();


            }
        });


        ipsearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                if (!ipsearch.getText().toString().trim().equals("")) {
                    String str = ipsearch.getText().toString().trim();
                    student_listfilter.clear();
                    state = 1;
                    for (StudentAllDetails student : student_list) {
                        if (student.getName().toLowerCase().contains(s.toString().toLowerCase().trim()) || student.getPlacementcompany().toLowerCase().contains(str.toString().toLowerCase().trim())) {
                            student_listfilter.add(student);

                        }
                    }
                    studentadapt = new StudentAdapter(StudentInfo.this, student_listfilter);
                    studlistview.setAdapter(studentadapt);
                    //Toast.makeText(StudentInfo.this,"before inside if"+str,Toast.LENGTH_LONG).show();

                }


                // Toast.makeText(StudentInfo.this,"before"+s.toString().trim(),Toast.LENGTH_LONG).show();

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                //Toast.makeText(StudentInfo.this,"after",Toast.LENGTH_LONG).show();

            }

            @Override
            public void afterTextChanged(Editable s) {


                if (s == null) {
                    state = 0;

                    studentadapt = new StudentAdapter(StudentInfo.this, student_list);
                    studlistview.setAdapter(studentadapt);
                } else {
                    student_listfilter.clear();
                    state = 1;
                    for (StudentAllDetails student : student_list) {
                        if (student.getName().toLowerCase().contains(s.toString().toLowerCase().trim()) || student.getPlacementcompany().toLowerCase().contains(s.toString().toLowerCase().trim())) {
                            student_listfilter.add(student);

                        }
                    }
                    studentadapt = new StudentAdapter(StudentInfo.this, student_listfilter);
                    studlistview.setAdapter(studentadapt);

                }

            }
        });

       /*Intent it=getIntent();
        String s_item=it.getStringExtra("srch_item").toString().trim();
        if(!s_item.equals(null)){
            ipsearch.setText(s_item);}*/

        student_fireb.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {


                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                    StudentAllDetails student = postSnapshot.getValue(StudentAllDetails.class);
                    student.rollno = postSnapshot.getKey();
                    student_list.add(student);
                    //Log.d("check", student.getRollno().toString().trim());


                }
                if(it.getStringExtra("From").equals("dashboard")||it.getStringExtra("From").equals("edit")){

                    studentadapt = new StudentAdapter(StudentInfo.this, student_list);
                    studlistview.setAdapter(studentadapt);

                }
                else{
                    displayListItem();
                    }





            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        studlistview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent i = new Intent(StudentInfo.this, StudentProfile.class);
                StudentAllDetails student;
                if (state == 1) {
                    student = student_listfilter.get(position);

                } else {
                    student = student_list.get(position);

                }
                Bundle bundle = new Bundle();
                bundle.putString("company", student.getPlacementcompany().toString().trim());

                bundle.putString("name", student.getName().toString().trim());
                bundle.putString("rollno", student.getRollno().toString().trim());
                bundle.putString("gender", student.getGender().toString().trim());
                /*if (student.getPlacementstatus().toString().trim().equals("Placement Willing")) {
                    bundle.putString("placementstatus", "Yes");
                } else {
                    bundle.putString("placementstatus", "No");
                }*/

                bundle.putString("placementstatus", student.getPlacementstatus().toString().trim());

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
               // bundle.putString("internto",student.getIntern_to().toString().trim());
                bundle.putString("x", Float.valueOf(student.getX()).toString().trim());
                bundle.putString("xii", Float.valueOf(student.getXii()).toString().trim());
                bundle.putString("diplamo", Float.valueOf(student.getDiplamo()).toString().trim());
                bundle.putString("history", Integer.valueOf(student.getHistory()).toString().trim());
                bundle.putString("arear",Integer.valueOf(student.getArear()).toString().trim());
                bundle.putString("cgpa", Float.valueOf(student.getCgpa()).toString().trim());
                bundle.putString("dob",student.getDob());
                i.putExtras(bundle);
                startActivity(i);
                finish();
            }
        });

    }



    public int displayListItem() {
        String s_item = "";
        Intent it = getIntent();
        if (it.getStringExtra("srch_item") != null) {
            s_item = it.getStringExtra("srch_item").toString().trim();
            //Log.d("check", s_item + " " + "Srch_item" + "/" + Integer.valueOf(student_list.size()).toString());
            for (StudentAllDetails student : student_list) {
                //Log.d("Check", student.getPlacementcompany().toString().trim());
                if (student.getPlacementcompany().trim().equalsIgnoreCase(s_item)) {
                  //  Log.d("Check inside if", student.getPlacementcompany().toString().trim());
                    student_listfilter.add(student);

                }
                else if(student.getPlacementcompany().contains(s_item+"$")||student.getPlacementcompany().contains("$"+s_item)){
                    student_listfilter.add(student);
                }
                else{}
            }
            //Log.d("student List filter", Integer.valueOf(student_listfilter.size()).toString());
            studentadapt = new StudentAdapter(StudentInfo.this, student_listfilter);
            state = 1;
            studlistview.setAdapter(studentadapt);
            studentadapt.notifyDataSetChanged();
            return 0;
        }

        if (it.getExtras() != null) {
            //Log.d("Check","inside bundle check");
            Bundle bl = it.getExtras();

            for (StudentAllDetails student : student_list) {
                if (bl.containsKey(student.getRollno().toString().trim())) {
              //      Log.d("inside bundle check",student.getRollno().toString().trim());

                    student_listfilter.add(student);

                }
            }

            studentadapt = new StudentAdapter(StudentInfo.this, student_listfilter);
            state = 1;
            studlistview.setAdapter(studentadapt);
            studentadapt.notifyDataSetChanged();
            Toast.makeText(getApplicationContext(),Integer.valueOf(student_listfilter.size()).toString().trim()+" "+"students are Eligible",Toast.LENGTH_SHORT).show();
            return 0;

        }
        //studentadapt = new StudentAdapter(StudentInfo.this, student_list);
        //studlistview.setAdapter(studentadapt);
        return 0;
    }
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        // TODO Auto-generated method stub

        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {

            case 7:

                if (resultCode == RESULT_OK) {

                    Uri uri = data.getData();
                    // Log.d("Check","inside Case7");
                    /*String filePath=null;
                    String fileName=null;

                    String filemanagerstring = selectedMediaUri.getPath();
                    String selectedMediaPath = getPath(selectedMediaUri);
                    if (!selectedMediaPath.equals("")) {
                        filePath = selectedMediaPath;
                    } else if (!filemanagerstring.equals("")) {
                        filePath = filemanagerstring;
                    }
                    int lastIndex = filePath.lastIndexOf("/");
                    fileName = filePath.substring(lastIndex+1);*/

                    //String PathHolder= uri.getPath();
                   /*try {
                       PathHolder = FileUtils.getPath(this, uri);
                    } catch (URISyntaxException e) {
                        e.printStackTrace();
                    }
                    Toast.makeText(this,PathHolder,Toast.LENGTH_SHORT).show();*/
                    try {
                        String patholder = data.getData().getPath();
                        Log.d("Check", patholder);
                        //workbook = jxl.Workbook.getWorkbook(new File(Environment.getExternalStorageDirectory() +"/iapc_cse/StudentDataTemplate2019.xls"));
                        workbook = jxl.Workbook.getWorkbook(new File(patholder));
                        Sheet sheet = workbook.getSheet(0);
                        Log.d("Check", "sheet created");
                        rd_row = 1;


                        while (rd_row <= 200) {
                           /* Cell c=sheet.getCell(rd_row,0);
                            if(c.getContents().isEmpty()){
                                break;
                            }*/
                            if (sheet.getCell(0, rd_row).getContents().isEmpty()) {
                                break;

                            }

                            //   Log.d("Check",Integer.valueOf(rd_row).toString());

                            for (col = 0; col <= 21; col++) {
                                Cell cell = sheet.getCell(col, rd_row);
                                student_row.add(cell.getContents());
                                //     Log.d("Check","("+Integer.valueOf(rd_row).toString()+","+Integer.valueOf(rd_row).toString()+")"+" "+cell.getContents());


                            }
                            StudentAllDetails student = new StudentAllDetails(student_row.get(0), student_row.get(1), student_row.get(2), student_row.get(3), student_row.get(4), student_row.get(5), student_row.get(6), student_row.get(7), student_row.get(8), student_row.get(9), Integer.parseInt(student_row.get(10)), Float.parseFloat(student_row.get(11)), Float.parseFloat(student_row.get(12)), Float.parseFloat(student_row.get(13)), Float.parseFloat(student_row.get(14)), Integer.parseInt(student_row.get(15)), Integer.parseInt(student_row.get(16)), student_row.get(17), Long.valueOf(student_row.get(18)), student_row.get(19), student_row.get(20), student_row.get(21));
                            student_new_list.add(student);
                            student_row.clear();
                            Log.d("Check", student.getRollno());
                            rd_row++;

                        }


                        uploadData(student_new_list);
                        //Log.d("Check", Integer.valueOf(student_new_list.size()).toString());


                    } catch (Exception e) {
                        Log.d("Check", "sheet not created");
                        e.printStackTrace();
                    }

                    //Toast.makeText(StudentInfo.this," " , Toast.LENGTH_LONG).show();

                }
                break;

        }
    }
    public void upload_details(){
      checkPermission(READ_EXTERNAL_STORAGE,3);

    }
    public void checkPermission(String permission, int requestCode)
    {

        // Checking if permission is not granted
        if (ContextCompat.checkSelfPermission(
                StudentInfo.this,
                permission)
                == PackageManager.PERMISSION_DENIED) {
            ActivityCompat
                    .requestPermissions(
                            StudentInfo.this,
                            new String[] { permission },
                            requestCode);
        }
        else {
            exceltofb();
            Toast
                    .makeText(StudentInfo.this,
                            "Permission already granted",
                            Toast.LENGTH_SHORT)
                    .show();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == 3) {
            if (grantResults.length > 0
                    && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                exceltofb();
                Toast.makeText(StudentInfo.this,
                        "Storage Permission Granted",
                        Toast.LENGTH_SHORT)
                        .show();
            }
            else {
                Toast.makeText(StudentInfo.this,
                        "Storage Permission Denied",
                        Toast.LENGTH_SHORT)
                        .show();
            }
        }

    }
    public void exceltofb(){
        try {
            //String patholder=data.getData().getPath();
            //Log.d("Check",patholder);
            workbook = jxl.Workbook.getWorkbook(new File(Environment.getExternalStorageDirectory() +"/iapc_cse/StudentDataTemplate2019.xls"));
            //workbook = jxl.Workbook.getWorkbook(new File(patholder));
            Sheet sheet = workbook.getSheet(0);
            Log.d("Check","sheet created");
            rd_row=1;


            while(rd_row<=50){
                           /* Cell c=sheet.getCell(rd_row,0);
                            if(c.getContents().isEmpty()){
                                break;
                            }*/
                if( sheet.getCell(0,rd_row).getContents().isEmpty()){
                    break;

                }

                //   Log.d("Check",Integer.valueOf(rd_row).toString());

                for(col=0;col<=21;col++){
                    Cell cell=sheet.getCell(col,rd_row);
                    student_row.add(cell.getContents());
                    //     Log.d("Check","("+Integer.valueOf(rd_row).toString()+","+Integer.valueOf(rd_row).toString()+")"+" "+cell.getContents());


                }
                StudentAllDetails student=new StudentAllDetails(student_row.get(0),student_row.get(1),student_row.get(2),student_row.get(3),student_row.get(4),student_row.get(5),student_row.get(6),student_row.get(7),student_row.get(8),student_row.get(9),Integer.parseInt(student_row.get(10)),Float.parseFloat(student_row.get(11)),Float.parseFloat(student_row.get(12)),Float.parseFloat(student_row.get(13)),Float.parseFloat(student_row.get(14)),Integer.parseInt(student_row.get(15)),Integer.parseInt(student_row.get(16)),student_row.get(17),Long.valueOf(student_row.get(18)),student_row.get(19),student_row.get(20),student_row.get(21));
                student_new_list.add(student);
                student_row.clear();
                Log.d("Check",student.getRollno());
                rd_row++;

            }


            uploadData(student_new_list);
            //Log.d("Check", Integer.valueOf(student_new_list.size()).toString());


        }
        catch (Exception e){
            Log.d("Check","sheet not created");
            e.printStackTrace();
        }
    }
    public void uploadData(List<StudentAllDetails> s_new){

        student_fireb_new= FirebaseDatabase.getInstance().getReference("Students"+batch);
        //Log.d("Check", "upload data"+Integer.valueOf(s_new.size()).toString());
        int i=0;
        for(StudentAllDetails s:s_new){
          //  Log.d("Check", s.getRollno().toString());
        student_fireb_new.child(s.getRollno()).setValue(s);}
    }
    public String getPath(Uri uri) {
        String[] projection = { MediaStore.MediaColumns.DATA };
        Cursor cursor = getContentResolver().query(uri, projection, null, null, null);
        if (cursor != null) {
            // HERE YOU WILL GET A NULLPOINTER IF CURSOR IS NULL
            // THIS CAN BE, IF YOU USED OI FILE MANAGER FOR PICKING THE MEDIA
            int column_index = cursor
                    .getColumnIndexOrThrow(MediaStore.MediaColumns.DATA);
            cursor.moveToFirst();
            return cursor.getString(column_index);
        } else
            return "";
    }
}
