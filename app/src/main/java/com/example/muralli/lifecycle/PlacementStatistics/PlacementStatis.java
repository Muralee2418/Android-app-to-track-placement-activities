package com.example.muralli.lifecycle.PlacementStatistics;

import android.Manifest;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Typeface;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
//import android.support.design.widget.FloatingActionButton;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
//import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.muralli.lifecycle.GroupByCompany;
import com.example.muralli.lifecycle.R;
import com.example.muralli.lifecycle.StudentDetails.StudentAllDetails;
import com.example.muralli.lifecycle.StudentDetails.StudentDetail;
import com.example.muralli.lifecycle.StudentDetails.StudentInfo;
import com.example.muralli.lifecycle.Taskstodo.DriveModelnew;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import jxl.Workbook;
import jxl.WorkbookSettings;
import jxl.format.Alignment;
import jxl.format.Border;
import jxl.format.BorderLineStyle;
import jxl.format.Colour;
import jxl.write.Label;
import jxl.write.WritableCellFormat;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;

public class PlacementStatis extends AppCompatActivity {
    TextView p_count,p_percent,p_enrolled,p_avgsal,p_eligible,p_yetto;

    ListView companylistvu;
    List<String> companyName= new ArrayList<>();
    List<DriveModelnew> driveList= new ArrayList<>();

    SharedPreferences selected_yr;
    String batch;
    int dualCount;
    GroupByCompany gb1,ytoplaced;
    File f;
    String year;
    WorkbookSettings wbSettings;
    WritableWorkbook workbook;
    WritableSheet sheet;
    int row = 0,slno,total_placed;
    int col = 0;
    Typeface tf;
    DatabaseReference student_edit,driveDetails;
    List<StudentDetail>studentlist=new ArrayList<>();
    List<StudentAllDetails>studentlistnew=new ArrayList<>();
    List<GroupByCompany>companylist=new ArrayList<>();
    Intent i;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_placement_statis);
        p_count=(TextView)findViewById(R.id.p_statuscount);
        p_enrolled=(TextView)findViewById(R.id.p_statusenrolled);
        p_percent=(TextView)findViewById(R.id.p_statuspercent);
        p_avgsal=(TextView)findViewById(R.id.p_statusavgsal);
        p_eligible=(TextView)findViewById(R.id.p_statuseligible);
        p_yetto=(TextView)findViewById(R.id.p_statusyetto);


        companylistvu=(ListView)findViewById(R.id.listvucompanylist);

        tf= Typeface.createFromAsset(getAssets(), "thinno.ttf");
        p_count.setTypeface(tf);
        p_enrolled.setTypeface(tf);
        p_percent.setTypeface(tf);
        p_yetto.setTypeface(tf);

        i=getIntent();

        p_count.setText(i.getStringExtra("p_count"));
        p_enrolled.setText(i.getStringExtra("p_enrolled"));
        p_percent.setText(i.getStringExtra("p_percent"));
        p_yetto.setText(i.getStringExtra("p_yetto"));
        p_eligible.setText(i.getStringExtra("p_eligible"));
        p_avgsal.setText(i.getStringExtra("p_avgsal"));

        selected_yr=getSharedPreferences("SelectedYear", MODE_PRIVATE);
        batch=selected_yr.getString("Year", null);
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fabReport);
        /*if(batch.equals("2018")){
            student_edit = FirebaseDatabase.getInstance().getReference("Students");
        }
        else
        {
            student_edit = FirebaseDatabase.getInstance().getReference("Students"+batch.trim());
        }*/
        student_edit = FirebaseDatabase.getInstance().getReference("Students"+batch);
        driveDetails=FirebaseDatabase.getInstance().getReference("DriveDetails"+batch);
       // Log.d("check batch", batch);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getpermissions();
                //genReport();

            }
        });
        createDriveList();
        student_edit.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                companyName.clear();
                String firstComp,secComp,studComp;

                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {

                    StudentAllDetails student1 = postSnapshot.getValue(StudentAllDetails.class);
                    student1.setRollno(postSnapshot.getKey());
                    studComp=student1.getPlacementcompany();
                    if(studComp.contains("$")) {
                        dualCount++;

                            secComp=studComp.substring(studComp.indexOf("$")+1);
                            firstComp=studComp.substring(0,studComp.indexOf("$"));
                        if (!companyName.contains(firstComp)) {
                            companyName.add(firstComp);
                        }
                        if (!companyName.contains(secComp)) {
                            companyName.add(secComp);
                        }

                    }
                        else{
                        firstComp=student1.getPlacementcompany();
                        if (!companyName.contains(firstComp)) {
                            companyName.add(firstComp);
                        }
                            secComp="0";
                        }

                        // companyName.add(student1.getPlacementcompany());
                        // Log.d("Check students", student1.getPlacementcompany());



                    studentlistnew.add(student1);

                }


                //Toast.makeText(PlacementStatis.this, companyName.get(1).toString().trim(), Toast.LENGTH_LONG).show();
                companylist.clear();
                createDriveList();

                Log.d("Check drive",Integer.valueOf(driveList.size()).toString());

                for (int i = 0; i < companyName.size(); i++) {

                    final int finalI = i;
                    gb1 = new GroupByCompany(studentlistnew, companyName.get(i).toString().trim());
                    for(int j=0; j<driveList.size();j++){
                        if(driveList.get(j).getCompany().equalsIgnoreCase(companyName.get(i))){
                            gb1.setCtc(driveList.get(j).getSalary());
                            Log.d("Check sal",Integer.valueOf(gb1.getCtc()).toString());
                        }
                    }
                    if(gb1.getCompanyName().equalsIgnoreCase("0")){
                        ytoplaced=new GroupByCompany(studentlistnew,gb1.getCompanyName());
                    }
                    else{
                        companylist.add(gb1);
                    }


                    //companylist.add(gb1);


                    Log.d("Check complist", companyName.get(i) + Integer.valueOf(gb1.studentCount()));

                }
                companylist.add(0,ytoplaced);
                generateRecycleView();


            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


        companylistvu.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                GroupByCompany gb=companylist.get(position);
                Intent i=new Intent(PlacementStatis.this, StudentInfo.class);
                i.putExtra("From", "placementstatis");
                i.putExtra("srch_item",gb.getCompanyName().toString().trim());
                startActivity(i);

            }
        });
    }
        public void generateRecycleView() {
          //  Log.d("Check Company list", Integer.valueOf(companyName.size()).toString() + "");
            //Log.d("Check Company list",Integer.valueOf(companylist.size()).toString()+"");


            PlacmentListvuAdapter companyAdapter=new PlacmentListvuAdapter(PlacementStatis.this,companylist,batch);
            companylistvu.setAdapter(companyAdapter);

    }

    public void onBackPressed() {
        super.onBackPressed();
        finish();
        //startActivity(new Intent(PlacementStatis.this, DashBoard.class));
    }

    public void genReport(){
        year=batch;
        total_placed=0;
        try {
            File folder=new File(Environment.getExternalStorageDirectory() + "/IapcCse");
            if(!folder.exists()){
                folder.mkdir();
            }
            f = new File(folder,  "PlacementReport"+year+".xls");

            //Toast.makeText(getBaseContext(), "File Created", Toast.LENGTH_SHORT).show();

            wbSettings = new WorkbookSettings();
            wbSettings.setLocale(new Locale("en", "EN"));
            workbook = Workbook.createWorkbook(f, wbSettings);

            sheet = workbook.createSheet("Placement", 0);
            sheet.addCell(new Label(col, row++, "Dr.Mahalingam College of Engineering & Technology"));
            sheet.addCell(new Label(col, row++, "Department of Computer science & Engineering"));
            sheet.addCell(new Label(col, row++, "Placement Report - "+year));

            row++;
            row++;
            WritableCellFormat cellFormat = new WritableCellFormat();
            cellFormat.setBorder(Border.ALL, BorderLineStyle.THIN);
            //cellFormat.setBackground(Colour.BLUE_GREY);
            WritableCellFormat cellFormat1 = new WritableCellFormat();
            cellFormat1.setBorder(Border.ALL, BorderLineStyle.THIN);
            cellFormat1.setBackground(Colour.LIGHT_BLUE);
            cellFormat1.setAlignment(Alignment.CENTRE);

            WritableCellFormat cell_mid = new WritableCellFormat();
            cell_mid.setBorder(Border.ALL, BorderLineStyle.THIN);
            cell_mid.setAlignment(Alignment.CENTRE);

            //Label label = new Label(col++, row, "Sl.No", cellFormat);
            sheet.setColumnView(col, 10);
            sheet.addCell(new Label(col++, row, "Sl.No", cellFormat1));
            sheet.setColumnView(col, 25);
            sheet.addCell(new Label(col++, row, "Company Name", cellFormat1));
            sheet.setColumnView(col, 25);
            sheet.addCell(new Label(col++, row, "No of Students placed",cellFormat1));
            col=0;
            row++;


            for(int j=0;j<companylist.size();j++){
                GroupByCompany gb=companylist.get(j);
                if(gb.getCompanyName().equalsIgnoreCase("0")){continue;}
                sheet.addCell(new Label(col++, row, Integer.valueOf(++slno).toString().trim(),cell_mid));
                sheet.addCell(new Label(col++, row, gb.getCompanyName().toUpperCase(),cellFormat));
                total_placed=total_placed+gb.studentCount();
                sheet.addCell(new Label(col++, row,Integer.valueOf(gb.studentCount()).toString(),cell_mid));
                col=0;
                row++;
            }
            sheet.addCell(new Label(++col, row, "No of Students Placed", cellFormat));
            sheet.addCell(new Label(++col, row,Integer.valueOf(total_placed-dualCount).toString(),cellFormat));
            col=0;
            row++;
            sheet.addCell(new Label(++col, row, "No of Dual Offers",cellFormat));
            sheet.addCell(new Label(++col, row,Integer.valueOf(dualCount).toString(),cellFormat));
            col=0;
            row++;
            sheet.addCell(new Label(++col, row, "No of Students Enrolled",cellFormat));
            sheet.addCell(new Label(++col, row,i.getStringExtra("p_enrolled"),cellFormat));
            col=0;
            row++;
            sheet.addCell(new Label(++col, row, "Placement % on Roll",cellFormat));
            sheet.addCell(new Label(++col, row,i.getStringExtra("p_percent")+"%",cellFormat));
            col=0;
            row++;
            sheet.addCell(new Label(++col, row, "Avg Salary",cellFormat));
            sheet.addCell(new Label(++col, row,p_avgsal.getText().toString().trim(),cellFormat));


            workbook.write();
            workbook.close();
            row = 0;
            col = 0;
            Toast.makeText(PlacementStatis.this, "Report Genrated", Toast.LENGTH_SHORT).show();



        }
        catch (Exception e){
            e.printStackTrace();
        }
    }

    public  void getpermissions() {
//cheeck id device is Android M

        if (Build.VERSION.SDK_INT >= 23) {
            if(checkSelfPermission(android.Manifest.permission.WRITE_EXTERNAL_STORAGE)
                    == PackageManager.PERMISSION_GRANTED) {
                //your permission is granted
                //Log.d("check","inside 1st if");
                ActivityCompat.requestPermissions(PlacementStatis.this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
                //genReport();
            } else {


                ActivityCompat.requestPermissions(PlacementStatis.this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);

            }
        }
        else {
            //permission is automatically granted on devices lower than android M upon installation

            ActivityCompat.requestPermissions(PlacementStatis.this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
           // genReport();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if((grantResults.length > 0) && (grantResults[0] == PackageManager.PERMISSION_GRANTED)){
           // Log.d("check","inside 1st onrequest if");


            genReport();
        }

    }
    public void createDriveList(){
        driveDetails.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                //e_salary = 0;
                driveList.clear();

                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                    DriveModelnew drive = postSnapshot.getValue(DriveModelnew.class);
                    driveList.add(drive);
                    Log.d("Check drivenm", drive.getCompany());


                }


            }


            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }
}


