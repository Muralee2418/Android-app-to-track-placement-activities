package com.example.muralli.lifecycle.ReportGenration;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Environment;
//import android.support.annotation.Nullable;

import androidx.annotation.Nullable;
//import androidx.core.app.Fragment;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.Toast;

import com.example.muralli.lifecycle.R;
import com.example.muralli.lifecycle.StudentDetails.StudentAllDetails;
import com.example.muralli.lifecycle.Taskstodo.DriveModelnew;
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

/**
 * Created by Muralli on 29-03-2018.
 */
public class placementReport extends Fragment implements View.OnClickListener {
    CheckBox name,roll,company,salary,interncomp,stip,placed,notplaced,intern, willing, notwilling,exnotelig,x,xii,dip,ug,his,arear,gender,email,phone;
    LinearLayout ly,lywilling;
    ScrollView categorylist;
    DatabaseReference student_fireb;
    DatabaseReference drive_fireb;
    SharedPreferences selected_yr;
    ImageView iv_down,iv_down2;
    File f, f1;
    StudentAllDetails s;
    int downState=0,downState1=1;
    List<StudentAllDetails> willingStudent = new ArrayList<>();
    List<StudentAllDetails> willingStudent_new = new ArrayList<>();
    List<StudentAllDetails> std_placed_filtered = new ArrayList<>();
    List<StudentAllDetails> std_filtered = new ArrayList<>();
    List<StudentAllDetails> std_notplaced_filtered = new ArrayList<>();
    List<StudentAllDetails> std_notwilling_filtered = new ArrayList<>();
    List<StudentAllDetails> std_not_plac_elig = new ArrayList<>();
    List<StudentAllDetails> std_gonefor_Intern = new ArrayList<>();
    List<StudentAllDetails> std_gonefor_IntTemp = new ArrayList<>();
    List<String> req_fields = new ArrayList<>();
    List<String> dmName_list = new ArrayList<>();
    List<DriveModelnew> dm_list = new ArrayList<>();
    ScrollView fields,willingfeilds;
    RelativeLayout down,chos_catgry;
    LinearLayout category;
    WorkbookSettings wbSettings;
    WritableWorkbook workbook;
    WritableSheet sheet;
    WritableCellFormat cellFormat1,cellFormat,cell_mid;
    int row = 0, i = 0,slno;
    int col = 0;
    Button genList;
    String batch;
    public placementReport(){}

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.listgen, container, false);
        iv_down=(ImageView)rootView.findViewById(R.id.iv_down);
        iv_down2=(ImageView)rootView.findViewById(R.id.iv_down2);
        down=(RelativeLayout)rootView.findViewById(R.id.dropdownbtn);
        chos_catgry=(RelativeLayout)rootView.findViewById(R.id.dropdownbtncatgry);
        category=(LinearLayout)rootView.findViewById(R.id.willingnotwilling);
        lywilling=(LinearLayout)rootView.findViewById(R.id.lnrlayoutplaced);
        fields=(ScrollView)rootView.findViewById(R.id.fields);
        categorylist=(ScrollView)rootView.findViewById(R.id.categorylist);
        ly=(LinearLayout)rootView.findViewById(R.id.LnrLayout);
        name=(CheckBox)rootView.findViewById(R.id.listgenname);
        roll=(CheckBox)rootView.findViewById(R.id.listgenroll);
        placed=(CheckBox)rootView.findViewById(R.id.listgenplac);
        notplaced=(CheckBox)rootView.findViewById(R.id.listgennotplac);
        exnotelig=(CheckBox) rootView.findViewById(R.id.listgenhavearear);
        intern=(CheckBox)rootView.findViewById(R.id.listgenintern);
        company=(CheckBox)rootView.findViewById(R.id.listgenCompany);
        salary=(CheckBox)rootView.findViewById(R.id.listgensal);
        //interncomp=(CheckBox)rootView.findViewById(R.id.listgeninterncomp);
        //stip=(CheckBox)rootView.findViewById(R.id.listgenstip);
        willing =(CheckBox)rootView.findViewById(R.id.listgenwilling);
        notwilling =(CheckBox)rootView.findViewById(R.id.listgennotwilling);
        x = (CheckBox) rootView.findViewById(R.id.listgenx);
        xii = (CheckBox) rootView.findViewById(R.id.listgenxii);
        dip = (CheckBox) rootView.findViewById(R.id.listgendip);
        ug = (CheckBox) rootView.findViewById(R.id.listgenug);
        gender=(CheckBox) rootView.findViewById(R.id.listgengender);
        his=(CheckBox) rootView.findViewById(R.id.listgenhis);
        arear=(CheckBox) rootView.findViewById(R.id.listgenarear);
        genList=(Button)rootView.findViewById(R.id.listgenbtn);
        email=(CheckBox) rootView.findViewById(R.id.listgenemail);
        phone=(CheckBox) rootView.findViewById(R.id.listgenphone);
        //willingfeilds=(ScrollView)rootView.findViewById(R.id.fields1);
       // student_fireb = FirebaseDatabase.getInstance().getReference("Students");

        name.setOnClickListener(this);
        roll.setOnClickListener(this);
        x.setOnClickListener(this);
        xii.setOnClickListener(this);
        dip.setOnClickListener(this);
        his.setOnClickListener(this);
        arear.setOnClickListener(this);
        gender.setOnClickListener(this);
        email.setOnClickListener(this);
        phone.setOnClickListener(this);
        placed.setOnClickListener(this);
        notplaced.setOnClickListener(this);
        exnotelig.setOnClickListener(this);
        intern.setOnClickListener(this);
        company.setOnClickListener(this);
        //interncomp.setOnClickListener(this);
        //stip.setOnClickListener(this);
        genList.setOnClickListener(this);
        down.setOnClickListener(this);
        chos_catgry.setOnClickListener(this);
        willing.setOnClickListener(this);
        notwilling.setOnClickListener(this);
        salary.setOnClickListener(this);
        selected_yr=getContext().getSharedPreferences("SelectedYear", Context.MODE_PRIVATE);

        batch=selected_yr.getString("Year",null);
        // FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fabReport);
        if(batch.equals("2018")){
            student_fireb = FirebaseDatabase.getInstance().getReference("Students");
            drive_fireb = FirebaseDatabase.getInstance().getReference("DriveDetails");
        }
        else
        {
            student_fireb = FirebaseDatabase.getInstance().getReference("Students"+batch.trim());
            drive_fireb = FirebaseDatabase.getInstance().getReference("DriveDetails"+batch.trim());
        }

        student_fireb.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                    StudentAllDetails student = postSnapshot.getValue(StudentAllDetails.class);
                    if (student.getPlacementstatus().equals("Placement Willing")) {
                        student.setRollno(postSnapshot.getKey());
                        willingStudent.add(student);
                    }
                    else{
                        std_notwilling_filtered.add(student);
                    }
                }
                //Toast.makeText(getBaseContext(),Integer.valueOf(willingStudent.size()).toString(),Toast.LENGTH_SHORT ).show();
                //Log.d("check", Integer.valueOf(willingStudent.size()).toString());

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {


            }
        });

       return rootView;
    }
    public void onClick(View v){

        switch (v.getId()){
            case R.id.listgenname:
                if(((CheckBox)v).isChecked()){
                    req_fields.add("name");
                }
                else{
                    req_fields.remove("name");
                }

                //Log.d("Check","name click");
                break;
            case R.id.listgenroll:
                if(((CheckBox)v).isChecked()){
                    req_fields.add("roll");
                }
                else{
                    req_fields.remove("roll");
                }

                //Log.d("Check","roll click");
                break;
            case R.id.listgenx:
                if(((CheckBox)v).isChecked()){
                    req_fields.add("x");
                }
                else{
                    req_fields.remove("x");
                }

                //Log.d("Check","name click");
                break;
            case R.id.listgenxii:
                if(((CheckBox)v).isChecked()){
                    req_fields.add("xii");
                }
                else{
                    req_fields.remove("xii");
                }

                //Log.d("Check","name click");
                break;
            case R.id.listgendip:
                if(((CheckBox)v).isChecked()){
                    req_fields.add("diplamo");
                }
                else{
                    req_fields.remove("diplamo");
                }

                //Log.d("Check","name click");
                break;
            case R.id.listgenug:
                if(((CheckBox)v).isChecked()){
                    req_fields.add("ug");
                }
                else{
                    req_fields.remove("ug");
                }

                //Log.d("Check","name click");
                break;
            case R.id.listgenarear:

                if(((CheckBox)v).isChecked()){
                    req_fields.add("arear");
                }
                else{
                    req_fields.remove("arear");
                }

                //Log.d("Check","name click");
                break;
            case R.id.listgenhis:
                if(((CheckBox)v).isChecked()){
                    req_fields.add("history");
                }
                else{
                    req_fields.remove("history");
                }

                //Log.d("Check","name click");
                break;
            case R.id.listgengender:
                if(((CheckBox)v).isChecked()){
                    req_fields.add("gender");
                }
                else{
                    req_fields.remove("gender");
                }

                //Log.d("Check","name click");
                break;
            case R.id.listgenphone:
                if(((CheckBox)v).isChecked()){
                    req_fields.add("phone");
                }
                else{
                    req_fields.remove("phone");
                }

                //Log.d("Check","name click");
                break;
            case R.id.listgenemail:
                if(((CheckBox)v).isChecked()){
                    req_fields.add("email");
                }
                else{
                    req_fields.remove("email");
                }

                //Log.d("Check","name click");
                break;

            case R.id.listgenplac:
                if(((CheckBox)v).isChecked()){
                    ly.setVisibility(View.VISIBLE);
                    std_placed_filtered.clear();
                    for (i = 0; i < willingStudent.size(); i++) {
                        Log.d("Check", "loop" + Integer.valueOf(i).toString().trim());
                        if(willingStudent.get(i).getPlacementcompany().equals("0")){
                            continue;
                        }
                        else{
                            std_placed_filtered.add(willingStudent.get(i));
                        }
                    }
                    std_filtered.addAll(std_placed_filtered);
                }
                else{
                    ly.setVisibility(View.GONE);
                    std_filtered.removeAll(std_placed_filtered);

                }
                //Log.d("Check", "placed"+std_placed_filtered.size());



                break;
            case R.id.listgenwilling:
                if(((CheckBox)v).isChecked()){
                    lywilling.setVisibility(View.VISIBLE);

                }
                else{
                    lywilling.setVisibility(View.GONE);


                }
                //Log.d("Check", "placed"+std_placed_filtered.size());



                break;
            case R.id.listgennotwilling:

                if(((CheckBox)v).isChecked()) {

                    std_filtered.addAll(std_notwilling_filtered);
                    Log.d("Check","NotWilling size:"+Integer.valueOf(std_notwilling_filtered.size()).toString());
                }
                else{
                    std_filtered.removeAll(std_notwilling_filtered);
                }



                //Log.d("Check", "placed"+std_placed_filtered.size());



                break;
            case R.id.listgennotplac:
                if(((CheckBox)v).isChecked()) {
                    exnotelig.setVisibility(View.VISIBLE);
                    std_notplaced_filtered.clear();
                    for (i = 0; i < willingStudent.size(); i++) {
                        //Log.d("Check", "loop" + Integer.valueOf(i).toString().trim());
                        if (willingStudent.get(i).getPlacementcompany().equalsIgnoreCase("0")) {
                            std_notplaced_filtered.add(willingStudent.get(i));
                        }
                    }
                    std_filtered.addAll(std_notplaced_filtered);
                }
                else{
                    exnotelig.setVisibility(View.GONE);
                    std_filtered.removeAll(std_notplaced_filtered);
                }
               //Log.d("Check","notplaced"+std_notplaced_filtered.size());
                break;
            case R.id.listgenhavearear:
                if(((CheckBox)v).isChecked()) {
                    exnotelig.setVisibility(View.VISIBLE);
                    //std_filtered.clear();
                    std_not_plac_elig.clear();
                    //req_fields.add("company");
                    if(std_notplaced_filtered.size()>0){
                        std_filtered.removeAll(std_notplaced_filtered);
                    }
                    for (i = 0; i < willingStudent.size(); i++) {
                        //Log.d("Check", "loop" + Integer.valueOf(i).toString().trim());
                        if (willingStudent.get(i).getArear()==0&&willingStudent.get(i).getPlacementcompany().equalsIgnoreCase("0")) {
                            std_not_plac_elig.add(willingStudent.get(i));
                        }
                    }
                    std_filtered.addAll(std_not_plac_elig);
                }
                else{
                    exnotelig.setVisibility(View.GONE);
                    std_filtered.removeAll(std_not_plac_elig);
                }
                break;

            case R.id.listgenintern:
               // Log.d("Check","intern click");
                //exnotelig.setVisibility(View.VISIBLE);
                //std_filtered.clear();
                if(((CheckBox)v).isChecked()){
                std_gonefor_Intern.clear();
                //req_fields.add("company");
                    Log.d("Check",Integer.valueOf(std_filtered.size()   ).toString().trim());

                for (i = 0; i < std_filtered.size(); i++) {
                    //Log.d("Check", "loop" + Integer.valueOf(i).toString().trim());
                    if (std_filtered.get(i).getInternshipcompany()!=null) {

                    if (!std_filtered.get(i).getInternshipcompany().equalsIgnoreCase("0")) {
                        std_gonefor_Intern.add(std_filtered.get(i));
                    }
                }}
                std_gonefor_IntTemp.addAll(std_filtered);
                std_filtered.clear();
                std_filtered.addAll(std_gonefor_Intern);
                req_fields.add("interncomp");
                req_fields.add("internfrom");
                req_fields.add("internto");}
                else
                {
                    std_filtered.clear();
                    std_filtered.addAll(std_gonefor_IntTemp);
                    std_gonefor_IntTemp.clear();
                    req_fields.remove("interncomp");
                    req_fields.remove("internfrom");
                    req_fields.remove("internto");
                }

                break;

            case R.id.listgenCompany:
                if(((CheckBox)v).isChecked()){
                    req_fields.add("company");
                }
                else{
                    req_fields.remove("company");
                }
                break;
            case R.id.listgensal:
                if(((CheckBox)v).isChecked()){
                    Log.d("check","Salary clicked");
                    req_fields.add("salary");
                }
                else{
                    req_fields.remove("salary");
                }
                break;
            case R.id.listgenbtn:
                //Log.d("Check","button click");
                //slno++;
                slno=0;
                try {

                    File folder=new File(Environment.getExternalStorageDirectory() + "/IapcCse");
                    if(!folder.exists()){
                        folder.mkdir();
                    }
                    f = new File(folder,  "Student_list.xls");

                    //Toast.makeText(getBaseContext(), "File Created", Toast.LENGTH_SHORT).show();

                    wbSettings = new WorkbookSettings();
                    wbSettings.setLocale(new Locale("en", "EN"));
                    workbook = Workbook.createWorkbook(f, wbSettings);

                    sheet = workbook.createSheet("StudentList", 0);

                    cellFormat1 = new WritableCellFormat();
                    cellFormat1.setBorder(Border.ALL, BorderLineStyle.THIN);
                    cellFormat1.setAlignment(Alignment.CENTRE);
                    cellFormat1.setBackground(Colour.LIGHT_BLUE);

                    cellFormat = new WritableCellFormat();
                    cellFormat.setWrap(true);
                    cellFormat.setBorder(Border.ALL, BorderLineStyle.THIN);

                    cell_mid = new WritableCellFormat();
                    cell_mid.setBorder(Border.ALL, BorderLineStyle.THIN);
                    cell_mid.setAlignment(Alignment.CENTRE);

                    sheet.setColumnView(col, 10);
                    sheet.addCell(new Label(col++, row, "sl.no",cellFormat1));

                    for (int i = 0; i < req_fields.size(); i++) {

                        switch (req_fields.get(i)) {
                            case "name":
                                sheet.setColumnView(col, 20);
                                sheet.addCell(new Label(col++, row, req_fields.get(i).toString().trim().toUpperCase(),cellFormat1));
                                break;
                            case "roll":
                                sheet.setColumnView(col, 15);
                                sheet.addCell(new Label(col++, row, req_fields.get(i).toString().toUpperCase().trim(),cellFormat1));
                                break;
                            case "x":
                                sheet.setColumnView(col, 10);
                                sheet.addCell(new Label(col++, row, req_fields.get(i).toString().toUpperCase().trim(),cellFormat1));
                                break;
                            case "xii":
                                sheet.setColumnView(col, 10);
                                sheet.addCell(new Label(col++, row, req_fields.get(i).toString().toUpperCase().trim(),cellFormat1));
                                break;
                            case "diplamo":
                                sheet.setColumnView(col, 10);
                                sheet.addCell(new Label(col++, row, req_fields.get(i).toString().toUpperCase().trim(),cellFormat1));
                                break;
                            case "ug":
                                sheet.setColumnView(col, 10);
                                sheet.addCell(new Label(col++, row, req_fields.get(i).toString().toUpperCase().trim(),cellFormat1));
                                break;
                            case "history":
                                sheet.setColumnView(col, 15);
                                sheet.addCell(new Label(col++, row, req_fields.get(i).toString().toUpperCase().trim(),cellFormat1));
                                break;
                            case "arear":
                                sheet.setColumnView(col, 15);
                                sheet.addCell(new Label(col++, row, req_fields.get(i).toString().toUpperCase().trim(),cellFormat1));
                                break;
                            case "gender":
                                sheet.setColumnView(col, 15);
                                sheet.addCell(new Label(col++, row, req_fields.get(i).toString().trim().toUpperCase(),cellFormat1));
                                break;
                            case "interncomp":
                                sheet.setColumnView(col, 20);
                                sheet.addCell(new Label(col++, row, req_fields.get(i).toString().toUpperCase().trim(),cellFormat1));
                                break;
                            case "internfrom":
                                sheet.setColumnView(col, 20);
                                sheet.addCell(new Label(col++, row, req_fields.get(i).toString().toUpperCase().trim(),cellFormat1));
                                break;
                            case "internto":
                                sheet.setColumnView(col, 20);
                                sheet.addCell(new Label(col++, row, req_fields.get(i).toString().toUpperCase().trim(),cellFormat1));
                                break;
                            case "company":
                                sheet.setColumnView(col, 20);
                                sheet.addCell(new Label(col++, row, req_fields.get(i).toString().toUpperCase().trim(),cellFormat1));
                                break;
                            case "salary":
                                sheet.setColumnView(col, 15);
                                sheet.addCell(new Label(col++, row, req_fields.get(i).toString().toUpperCase().trim(),cellFormat1));
                                break;
                            case "phone":
                                sheet.setColumnView(col, 25);
                                sheet.addCell(new Label(col++, row, req_fields.get(i).toString().toUpperCase().trim(),cellFormat1));
                                break;
                            case "email":
                                sheet.setColumnView(col, 35);
                                sheet.addCell(new Label(col++, row, req_fields.get(i).toString().toUpperCase().trim(),cellFormat1));
                                break;



                        }
                    }

                    // sheet.addCell(new Label(col, row, "RollNo"));
                    // sheet.addCell(new Label(++col, row, "Name"));
                    col = 0;
                    row++;
                    //Log.d("Check", "file created");

                } catch (Exception e) {
                    e.printStackTrace();
                }
                if (std_filtered.isEmpty()) {
                    //willingStudent_new.addAll(willingStudent);
                }
                else
                {
                    willingStudent_new.addAll(std_filtered);
                }
                for (i = 0; i < willingStudent_new.size(); i++) {
                   // Log.d("Check", "loop" + Integer.valueOf(i).toString().trim());
                    s = willingStudent_new.get(i);


                    if (true) {
                        try {
                           // requiredDrive.addCandidate(s.getRollno());

                            sheet.addCell(new Label(col++, row, Integer.valueOf(++slno).toString().trim(),cell_mid));
                            for (int j = 0; j < req_fields.size(); j++) {
                                switch (req_fields.get(j)) {
                                    case "name":
                                        sheet.addCell(new Label(col++, row, s.getName().trim(),cellFormat));
                                        break;
                                    case "roll":
                                        sheet.addCell(new Label(col++, row, s.getRollno().trim(),cell_mid));
                                        break;
                                    case "x":
                                        sheet.addCell(new Label(col++, row, Float.valueOf(s.getX()).toString().trim(), cell_mid));
                                        break;
                                    case "xii":

                                        if (Float.valueOf(s.getXii()) < 1) {
                                            sheet.addCell(new Label(col++, row, "NA".toString().trim(), cell_mid));
                                        } else {
                                            sheet.addCell(new Label(col++, row, Float.valueOf(s.getXii()).toString().trim(), cell_mid));
                                        }
                                        break;
                                    case "diplamo":

                                        if (Float.valueOf(s.getDiplamo()) < 1) {
                                            sheet.addCell(new Label(col++, row, "NA".toString().trim(), cell_mid));
                                        } else {
                                            sheet.addCell(new Label(col++, row, Float.valueOf(s.getDiplamo()).toString().trim(), cell_mid));
                                        }
                                        break;
                                    case "ug":
                                        sheet.addCell(new Label(col++, row, Float.valueOf(s.getCgpa()).toString().trim(), cell_mid));
                                        break;
                                    case "gender":
                                        sheet.addCell(new Label(col++, row, s.getGender().toString().toUpperCase().trim(), cell_mid));
                                        break;
                                    case "history":
                                        sheet.addCell(new Label(col++, row, Integer.valueOf(s.getHistory()).toString().trim(), cell_mid));
                                        break;
                                    case "arear":
                                        sheet.addCell(new Label(col++, row, Integer.valueOf(s.getArear()).toString().trim(), cell_mid));
                                        break;
                                    case "company":
                                        //sheet.addCell(new Label(col++, row, s.getPlacementcompany(),cellFormat));
                                        String tempComp=s.getPlacementcompany();
                                        if(tempComp.contains("$")){
                                            char[] tempCompChars = tempComp.toCharArray();
                                            tempCompChars[tempComp.indexOf("$")] = ',';
                                            tempComp = String.valueOf(tempCompChars);

                                        }
                                        sheet.addCell(new Label(col++, row, tempComp.toUpperCase().trim(),cellFormat));
                                        break;
                                    case "interncomp":
                                        sheet.addCell(new Label(col++, row,s.getInternshipcompany(),cellFormat));
                                        break;
                                    case "internfrom":
                                        sheet.addCell(new Label(col++, row,s.getIntern_from(),cellFormat));
                                        break;
                                    case "internto":
                                        sheet.addCell(new Label(col++, row,s.getIntern_to(),cellFormat));
                                        break;
                                    case "salary":
                                        sheet.addCell(new Label(col++, row,Integer.valueOf(s.getIntern_stiphend()).toString(),cellFormat));
                                        break;
                                    case "email":
                                        sheet.addCell(new Label(col++, row,s.getEmail(),cellFormat));
                                        break;
                                    case "phone":
                                        sheet.addCell(new Label(col++, row, Long.valueOf(s.getPhone()).toString().trim(),cell_mid));
                                        break;

                                }
                            }

                            col = 0;
                            row++;
                           // Log.d("Check", "student" + Integer.valueOf(row).toString().trim());
                        } catch (Exception e) {
                            e.printStackTrace();
                        }


                    }

                }
                try {
                    workbook.write();
                    workbook.close();
                    row = 0;
                    col = 0;
                    Toast.makeText(getContext(), " List Created", Toast.LENGTH_SHORT).show();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;
            case R.id.dropdownbtn:
                if(fields.getVisibility()==View.GONE)
                {
                    if(categorylist.getVisibility()==View.VISIBLE){
                        categorylist.setVisibility(View.GONE);
                    }
                    fields.setVisibility(View.VISIBLE);
                    iv_down.setRotation(iv_down.getRotation()+180);
                    //iv_down.setRotation();
                    //downState=1;
                }
                else
                {
                    if(categorylist.getVisibility()==View.VISIBLE){
                        categorylist.setVisibility(View.GONE);
                    }
                    fields.setVisibility(View.GONE);
                    iv_down.setRotation(iv_down.getRotation()-180);
                    downState=0;
                }
                break;
            case R.id.dropdownbtncatgry:
                if(categorylist.getVisibility()==View.GONE)
                {
                    if(fields.getVisibility()==View.VISIBLE){
                        fields.setVisibility(View.GONE);
                    }
                    categorylist.setVisibility(View.VISIBLE);

                    iv_down2.setRotation(iv_down2.getRotation() + 180);
                    //iv_down.setRotation(180);
                    //iv_down.setRotation();
                    //downState1=1;
                }
                else
                {
                    if(fields.getVisibility()==View.VISIBLE){
                        fields.setVisibility(View.GONE);
                    }
                    categorylist.setVisibility(View.GONE);
                    iv_down2.setRotation(iv_down2.getRotation()-180);
                    //iv_down.setRotation(180);
                    downState1=0;
                }
                break;



        }

    }

}
