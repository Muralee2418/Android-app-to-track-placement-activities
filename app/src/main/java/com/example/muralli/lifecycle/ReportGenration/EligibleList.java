package com.example.muralli.lifecycle.ReportGenration;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Environment;
//import android.support.annotation.NonNull;
//import android.support.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
//import androidx.core.app.Fragment;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
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

import static android.Manifest.permission.WRITE_EXTERNAL_STORAGE;

/**
 * Created by Muralli on 27-03-2018.
 */

public class EligibleList extends Fragment {
    CheckBox x, xii, dip, ug, name, roll, email, phone, gender,history,dept;
    EditText comp_name, ip_x, ip_xii, ip_ug, ip_his, ip_arear, ip_gen, ip_tol,ip_company;
    Button genrate_list;
    String comp_choice,comp_choice2;
    DriveModelnew drive_choice;
    SharedPreferences selected_yr;
    DriveModelnew requiredDrive;
    Float lx, lxii, lug, ldip,ltol;
    int larear=0, lhistory=0;
    List<String> comp_list=new ArrayList<>();
    List<DriveModelnew> drive_list=new ArrayList<>();
    List<StudentAllDetails> willingStudent = new ArrayList<>();
    DatabaseReference student_fireb;
    DatabaseReference drive_fireb;
    DatabaseReference driveDetails;
    File f, f1;
    StudentAllDetails s;
    List<String> req_fields = new ArrayList<>();
    List<String> dmName_list = new ArrayList<>();
    List<DriveModelnew> dm_list = new ArrayList<>();
    WorkbookSettings wbSettings;
    WritableWorkbook workbook;
    WritableSheet sheet;
    String batch;
    String fileName, str_x, str_xii, str_ug, str_arear, str_his, str_gen,str_tol,str_company;
    int row = 0, i = 0,slno;
    int col = 0;

    public EligibleList() {
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.eligiblelistgen, container, false);
        x = (CheckBox) rootView.findViewById(R.id.eligiblex);
        xii = (CheckBox) rootView.findViewById(R.id.eligiblexii);
        dip = (CheckBox) rootView.findViewById(R.id.eligibledip);
        ug = (CheckBox) rootView.findViewById(R.id.eligibleug);
        name = (CheckBox) rootView.findViewById(R.id.eligiblename);
        roll = (CheckBox) rootView.findViewById(R.id.eligibleroll);
        email = (CheckBox) rootView.findViewById(R.id.eligibleemail);
        phone = (CheckBox) rootView.findViewById(R.id.eligiblephone);
        gender = (CheckBox) rootView.findViewById(R.id.eligiblegender);
        history=(CheckBox) rootView.findViewById(R.id.eligiblehis);
        dept=(CheckBox) rootView.findViewById(R.id.eligibledep);


        //arear=(CheckBox) rootView.findViewById(R.id.eligiblearear);
        genrate_list = (Button) rootView.findViewById(R.id.button_gen_list);

        student_fireb = FirebaseDatabase.getInstance().getReference("Students");
        drive_fireb = FirebaseDatabase.getInstance().getReference("DriveDetails");

       //selected_yr=getSharedPreference("Selected",MODE_PRIVATE);
        comp_name = (EditText) rootView.findViewById(R.id.elig_comp_name);
        ip_x = (EditText) rootView.findViewById(R.id.elig_et_x);
        ip_xii = (EditText) rootView.findViewById(R.id.elig_et_xii);
        ip_ug = (EditText) rootView.findViewById(R.id.elig_et_ug);
        ip_his = (EditText) rootView.findViewById(R.id.elig_et_his);
        ip_arear = (EditText) rootView.findViewById(R.id.elig_et_ar);
        ip_gen = (EditText) rootView.findViewById(R.id.elig_et_gen);
        ip_tol=(EditText) rootView.findViewById(R.id.elig_et_tol);
        //ip_company=(EditText)rootView.findViewById(R.id.elig_comp_name);


        f = new File(Environment.getExternalStorageDirectory(), "foldermain");
        // f1= new File(Environment.getExternalStorageDirectory(), "foldermain");
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
            driveDetails = FirebaseDatabase.getInstance().getReference("DriveDetails"+batch.trim());
        }

        if (!f.exists()) {
            f.mkdir();

        }

       // @Override
       /* public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
            super.onRequestPermissionsResult(requestCode, permissions, grantResults);

            if (requestCode == 3) {
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                   // exceltofb();
                    Toast.makeText(getContext(), "Permission granted", Toast.LENGTH_SHORT).show();
                }
                else {
                    Toast.makeText(getContext(),
                            "Storage Permission Denied",
                            Toast.LENGTH_SHORT)
                            .show();
                }
            }

        }*/

        comp_name.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(getContext());

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
                            comp_name.setText("0");
                            //e_salary = 0;
                        } else {
                            comp_name.setText(comp_choice.trim());
                            //e_salary = drive_choice.getSalary();

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



                }



            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        x.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(x.isChecked()){
                req_fields.add("x");}
                else{
                    req_fields.remove("x");
                }

            }
        });
        xii.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(xii.isChecked()){
                req_fields.add("xii");}
                else{
                    req_fields.remove("xii");
                }

            }
        });
        ug.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ug.isChecked()){
                req_fields.add("ug");}
                else{
                    req_fields.remove("ug");
                }

            }
        });
        phone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(phone.isChecked()){
                req_fields.add("phone");}
                else
                {
                    req_fields.remove("phone");
                }

            }
        });
        email.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(email.isChecked()){
                req_fields.add("email");}
                else{
                    req_fields.remove("email");
                }

            }
        });
        roll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (roll.isChecked()){
                req_fields.add("roll");}
                else{req_fields.remove("roll");}

            }
        });
        name.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (name.isChecked()){
                    req_fields.add("name");}
                else{req_fields.remove("name");}

            }
        });
        gender.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (gender.isChecked()){
                    req_fields.add("gender");}
                else{req_fields.remove("gender");}

            }
        });
        dip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (dip.isChecked()){
                    req_fields.add("diplamo");}
                else{req_fields.remove("diplamo");}
            }
        });
        history.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (history.isChecked()){
                    req_fields.add("history");}
                else{req_fields.remove("history");}

            }
        });
        dept.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (dept.isChecked()) {
                    req_fields.add("department");
                } else {
                    req_fields.remove("department");
                }

            }
        });

       /* if (!f1.exists()) {
            f1.mkdir();

        }*/
        /*x.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });*/



        /*public View onCheckboxClicked(View v){
        Boolean checked =((CheckBox)v).isChecked();
        switch(v.getId()){
            case R.id.eligiblename:
                req_fields.add("Name");
                return;
            case R.id.eligibleroll:
                req_fields.add("RollNo");
                return;
            case R.id.eligiblex:
                req_fields.add("X");
                return;
            case R.id.eligiblexii:
                req_fields.add("Xii");
                return;
            case R.id.eligibledip:
                req_fields.add("Dip");
                return;
            case R.id.eligibleug:
                req_fields.add("Ug");
                return;
            case R.id.eligibleemail:
                req_fields.add("Email");
                return;
            case R.id.eligiblephone:
                req_fields.add("Phone");
                return;
            default:
                req_fields.add("Name");
                req_fields.add("RollNo");
                req_fields.add("X");
                req_fields.add("Xii");
                req_fields.add("Dip");
                req_fields.add("Ug");
                req_fields.add("Email");
                req_fields.add("Phone");
                return;

        }

    }*/



        student_fireb.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                    StudentAllDetails student = postSnapshot.getValue(StudentAllDetails.class);
                    if (student.getPlacementstatus().equals("Placement Willing")) {
                        student.setRollno(postSnapshot.getKey());
                        willingStudent.add(student);
                    }
                }
                //Toast.makeText(getBaseContext(),Integer.valueOf(willingStudent.size()).toString(),Toast.LENGTH_SHORT ).show();
                //Log.d("check", "Placment Willing"+Integer.valueOf(willingStudent.size()).toString());

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {


            }
        });
        drive_fireb.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                    DriveModelnew dm = postSnapshot.getValue(DriveModelnew.class);
                    dm_list.add(dm);
                    dmName_list.add(dm.getCompany());
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        genrate_list.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                str_x = ip_x.getText().toString().trim();
                str_xii = ip_xii.getText().toString().trim();
                str_ug = ip_ug.getText().toString().trim();
                str_arear = ip_arear.getText().toString().trim();
                str_his = ip_his.getText().toString().trim();
                str_gen = ip_gen.getText().toString().trim();
                str_tol = ip_tol.getText().toString().trim();
                str_company = comp_name.getText().toString().trim();
                Log.d("check history str",str_his);

                slno = 0;


                if (str_x.isEmpty()) {
                    ip_x.setError("Enter the Xth mark");
                    ip_x.setFocusable(true);
                    return;
                }
                if (str_company.isEmpty()) {
                    comp_name.setError("Enter the company name");
                    comp_name.setFocusable(true);
                    return;

                }
                 else if (str_xii.isEmpty()) {
                    ip_xii.setError("Enter the Xiith or dip mark");
                    ip_xii.setFocusable(true);
                    return;
                } else if (str_ug.isEmpty()) {
                    ip_ug.setError("Enter the UG Mark");
                    ip_ug.setFocusable(true);
                    return;
                }

                 if (str_his.isEmpty()) {
                    /*ip_his.setError("Enter the UG Mark");
                    ip_his.setFocusable(true);
                    return;*/
                    Log.d("Check his empty", "empty");
                    lhistory = 45;
                }
                else
                {
                    lhistory = Integer.parseInt(str_his);
                    Log.d("Check his",Integer.valueOf(lhistory).toString().trim());
                }
                if (str_arear.isEmpty()) {
                    /*ip_arear.setError("Enter the UG Mark");
                    ip_arear.setFocusable(true);
                    return;*/
                    larear = 0;
                } else
                {
                    //larear = Integer.valueOf(str_arear);
                    larear = Integer.parseInt(str_arear);
                }
                 if (str_tol.isEmpty()) {
                    /*ip_arear.setError("Enter the UG Mark");
                    ip_arear.setFocusable(true);
                    return;*/

                    str_tol="0.0";
                    //  Log.d("Check Tol mt", ltol.toString());
                } else
                 {
                    //larear = Integer.valueOf(str_arear);
                    ltol = Float.parseFloat(str_tol);
                    Log.d("Check Tol !mt", ltol.toString());
                }


                lx = Float.parseFloat(str_x);
                //ip_x.ge
                lxii = Float.parseFloat(str_xii);
                //ldip = Long.parseLong(ip_x.getText().toString());
                lug = Float.parseFloat(str_ug) - Float.parseFloat(str_tol);

                if (!str_company.isEmpty()) {
                    for (int k = 0; k < dm_list.size(); k++) {
                        if (dm_list.get(k).getCompany().toString().trim().equalsIgnoreCase(comp_name.getText().toString().trim())) {
                            requiredDrive = dm_list.get(k);
                            Log.d("check dm if","if");
                           // generateEligibleList();
                            checkPermission(WRITE_EXTERNAL_STORAGE,2);
                            break;
                        } else {
                            if (k == dm_list.size() - 1) {
                                final AlertDialog.Builder alertDialog = new AlertDialog.Builder(getContext());

                                alertDialog.setTitle("Hello IAPC !!");

                                alertDialog.setMessage("Please create Drive for Entered Company");
                                alertDialog.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        dialog.cancel();


                                    }
                                });
                                alertDialog.show();

                                comp_name.setError("Enter Valid Company");
                                comp_name.setFocusable(true);
                                return;

                            }


                        }

                    }


                }
                //Log.d("Check", Integer.valueOf(larear).toString() + " history:" + Integer.valueOf(lhistory).toString());


            }
        });




        return rootView;
    }
    public void checkPermission(String permission, int requestCode)
    {

        // Checking if permission is not granted
        if (ContextCompat.checkSelfPermission(
                getContext(),
                permission)
                == PackageManager.PERMISSION_DENIED) {
            ActivityCompat
                    .requestPermissions(
                            getActivity(),
                            new String[] { permission },
                            requestCode);
        }
        else {
            generateEligibleList();
            //Toast.makeText(EligibleList.this,"Permission already granted",Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 3) {
            if (grantResults.length > 0
                    && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                generateEligibleList();
                Toast.makeText(getContext(),
                        "Storage Permission Granted",
                        Toast.LENGTH_SHORT)
                        .show();
            }
            else {
                Toast.makeText(getContext(),
                        "Storage Permission Denied",
                        Toast.LENGTH_SHORT)
                        .show();
            }
        }
    }




    public void generateEligibleList(){

        try {
            File folder=new File(Environment.getExternalStorageDirectory() + "/IapcCse");
            if(!folder.exists()){
                folder.mkdir();
            }
            f = new File(folder, comp_name.getText().toString().trim() + "_CSE_list.xls");

            //Toast.makeText(getBaseContext(), "File Created", Toast.LENGTH_SHORT).show();

            wbSettings = new WorkbookSettings();
            wbSettings.setLocale(new Locale("en", "EN"));
            workbook = Workbook.createWorkbook(f, wbSettings);

            sheet = workbook.createSheet("userList", 0);
            WritableCellFormat cellFormat1 = new WritableCellFormat();
            cellFormat1.setBorder(Border.ALL, BorderLineStyle.THIN);
            cellFormat1.setBackground(Colour.LIGHT_BLUE);
            cellFormat1.setAlignment(Alignment.CENTRE);


            sheet.addCell(new Label(col++, row, "sl.no", cellFormat1));

            for (int i = 0; i < req_fields.size(); i++) {

                switch (req_fields.get(i)) {
                    case "name":
                        sheet.addCell(new Label(col++, row, req_fields.get(i).toString().trim(), cellFormat1));
                        break;
                    case "roll":
                        sheet.addCell(new Label(col++, row, req_fields.get(i).toString().trim(), cellFormat1));
                        break;
                    case "x":
                        sheet.addCell(new Label(col++, row, req_fields.get(i).toString().trim(), cellFormat1));
                        break;
                    case "xii":
                        sheet.addCell(new Label(col++, row, req_fields.get(i).toString().trim(), cellFormat1));
                        break;
                    case "email":
                        sheet.addCell(new Label(col++, row, req_fields.get(i).toString().trim(), cellFormat1));
                        break;
                    case "phone":
                        sheet.addCell(new Label(col++, row, req_fields.get(i).toString().trim(), cellFormat1));
                        break;
                    case "ug":
                        sheet.addCell(new Label(col++, row, req_fields.get(i).toString().trim(), cellFormat1));
                        break;
                    case "gender":
                        sheet.addCell(new Label(col++, row, req_fields.get(i).toString().trim(), cellFormat1));
                        break;
                    case "diplamo":
                        sheet.addCell(new Label(col++, row, req_fields.get(i).toString().trim(), cellFormat1));
                        break;
                    case "history":
                        sheet.addCell(new Label(col++, row, req_fields.get(i).toString().trim(), cellFormat1));
                        break;
                    case "arear":
                        sheet.addCell(new Label(col++, row, req_fields.get(i).toString().trim(), cellFormat1));
                        break;
                    case "department":
                        sheet.addCell(new Label(col++, row, req_fields.get(i).toString().trim(), cellFormat1));
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


        for (i = 0; i < willingStudent.size(); i++) {
           // Log.d("Check", "loop" + Integer.valueOf(i).toString().trim());
            s = willingStudent.get(i);

            if (s.getX() >= lx && (s.getXii() >= lxii || s.getDiplamo() >= lxii) && s.getCgpa() >= lug && s.getArear() <= larear && s.getHistory() <= lhistory && s.getPlacementcompany().equalsIgnoreCase("0")) {
                try {
                    requiredDrive.addCandidate(s.getRollno());
                    WritableCellFormat cellFormat = new WritableCellFormat();
                    cellFormat.setBorder(Border.ALL, BorderLineStyle.THIN);
                    cellFormat.setShrinkToFit(true);

                    WritableCellFormat cell_mid = new WritableCellFormat();
                    cell_mid.setBorder(Border.ALL, BorderLineStyle.THIN);
                    cell_mid.setAlignment(Alignment.CENTRE);

                    sheet.addCell(new Label(col++, row, Integer.valueOf(++slno).toString().trim(), cell_mid));
                    for (int j = 0; j < req_fields.size(); j++) {
                        switch (req_fields.get(j)) {
                            case "name":
                                sheet.setColumnView(col, 25);
                                sheet.addCell(new Label(col++, row, s.getName().trim(), cellFormat));
                                break;
                            case "roll":sheet.setColumnView(col, 15);
                                sheet.addCell(new Label(col++, row, s.getRollno().trim(), cell_mid));
                                break;
                            case "x":
                                sheet.setColumnView(col, 10);
                                sheet.addCell(new Label(col++, row, Float.valueOf(s.getX()).toString().trim(), cell_mid));
                                break;
                            case "xii":
                                sheet.setColumnView(col, 10);

                                if (Float.valueOf(s.getXii()) < 1) {
                                    sheet.addCell(new Label(col++, row, "NA".toString().trim(), cell_mid));
                                } else {
                                    sheet.addCell(new Label(col++, row, Float.valueOf(s.getXii()).toString().trim(), cell_mid));
                                }
                                break;
                            case "diplamo":
                                sheet.setColumnView(col, 10);

                                if (Float.valueOf(s.getDiplamo()) < 1) {
                                    sheet.addCell(new Label(col++, row, "NA".toString().trim(), cell_mid));
                                } else {
                                    sheet.addCell(new Label(col++, row, Float.valueOf(s.getDiplamo()).toString().trim(), cell_mid));
                                }
                                break;
                            case "ug":
                                sheet.setColumnView(col, 10);
                                sheet.addCell(new Label(col++, row, Float.valueOf(s.getCgpa()).toString().trim(), cell_mid));
                                break;
                            case "email":
                                sheet.setColumnView(col, 35);
                                sheet.addCell(new Label(col++, row, s.getEmail().toString().trim(), cellFormat));
                                break;
                            case "phone":
                                sheet.setColumnView(col, 20);
                                sheet.addCell(new Label(col++, row, Long.valueOf(s.getPhone()).toString().trim(), cell_mid));
                                break;
                            case "gender":
                                sheet.setColumnView(col, 15);
                                sheet.addCell(new Label(col++, row, s.getGender().toString().toUpperCase().trim(), cell_mid));
                                break;
                            case "history":
                                sheet.setColumnView(col, 15);
                                sheet.addCell(new Label(col++, row, Integer.valueOf(s.getHistory()).toString().trim(), cell_mid));
                                break;
                            case "arear":
                                sheet.setColumnView(col, 10);
                                sheet.addCell(new Label(col++, row, Integer.valueOf(s.getArear()).toString().trim(), cell_mid));
                                break;
                            case "department":
                                sheet.setColumnView(col, 10);
                                sheet.addCell(new Label(col++, row,getString(R.string.department), cell_mid));
                                break;

                        }
                    }
                    //requiredDrive.setStNo(1);



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
            Toast.makeText(getContext(), comp_name.getText().toString() + " List Created", Toast.LENGTH_SHORT).show();
        } catch (Exception e) {
            e.printStackTrace();
        }

        drive_fireb.child(requiredDrive.getCompany()).setValue(requiredDrive);
    }
}
