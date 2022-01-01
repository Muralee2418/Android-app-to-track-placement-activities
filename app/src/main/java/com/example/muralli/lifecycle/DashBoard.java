package com.example.muralli.lifecycle;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
//import android.support.v7.app.AppCompatActivity;
//import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.muralli.lifecycle.ReportGenration.ReportGenrator;
import com.example.muralli.lifecycle.StudentDetails.BatchListNew;
import com.example.muralli.lifecycle.StudentDetails.StudentAllDetails;
import com.example.muralli.lifecycle.StudentDetails.StudentDetail;
import com.example.muralli.lifecycle.Taskstodo.PlacementTasks;
import com.example.muralli.lifecycle.User.UserDetail;
import com.example.muralli.lifecycle.User.UserManagement;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

public class DashBoard extends AppCompatActivity {
    ImageView db_cont,logout,skillmatrix,intern,placementstatis,genrateReport,usr_mng;
   ProgressBar plac_count,plac_perc;
    List<UserDetail>userObjs=new ArrayList<>();
    Toolbar tlbr;
    Spinner year;
    String batch;
    private FirebaseAuth uauth;
    SharedPreferences selected_yr;
    SharedPreferences user_email,iapStatus;
    int backbuttonpress=0;
    //private int progressStatus = 0;
    private Handler handler = new Handler();
    DatabaseReference student_edit,current_batch,batch_list;
    SharedPreferences.Editor edit,logdinUser,iapedit;
    private int total_students;
    double placpercent=0.0,noof_placed=0.0;
    ArrayList<String> bl=new ArrayList<>();
    ArrayList<String> bl1=new ArrayList<>();


    int avgSalary;
    int eligibleStud;
    Date today=new Date();
    int yetto=0;
    StudentDetail student;
    double enrolled=0.0;
    TextView txt_pcount,txt_pperc;
    DatabaseReference users;
    ValueEventListener vf;
    Thread thrd_pc,thrd_pp;
    List<StudentAllDetails> studentlist=  new ArrayList<>();
    static {
        FirebaseDatabase.getInstance().setPersistenceEnabled(true);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_dash_board);

        db_cont=(ImageView)findViewById(R.id.indcontact);
        txt_pcount=(TextView)findViewById(R.id.placcount);
        txt_pperc=(TextView)findViewById(R.id.placperc);
        logout=(ImageView)findViewById(R.id.logout);
        placementstatis=(ImageView)findViewById(R.id.placementstatis);
        skillmatrix=(ImageView)findViewById(R.id.studentskillmatrix);
        intern=(ImageView)findViewById(R.id.internship);
        genrateReport=(ImageView)findViewById(R.id.imgvureport);
        usr_mng=(ImageView)findViewById(R.id.usrmng);
       plac_count=(ProgressBar)findViewById(R.id.circularProgressBar);
        plac_perc=(ProgressBar)findViewById(R.id.circularProgressBar1);
        tlbr=(Toolbar)findViewById(R.id.dashtoolbar);
        uauth=FirebaseAuth.getInstance();
        final FirebaseUser user = uauth.getCurrentUser();
        year=(Spinner)findViewById(R.id.yr);

        user_email=getSharedPreferences("LogdinUser", MODE_PRIVATE);
        iapStatus=getSharedPreferences("IapStatus", MODE_PRIVATE);
        users= FirebaseDatabase.getInstance().getReference("User");
        selected_yr=getSharedPreferences("SelectedYear", MODE_PRIVATE);
        batch=selected_yr.getString("Year", null);
        edit=selected_yr.edit();
        iapedit=iapStatus.edit();
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss a");
        //String dateToStr = format.format(today);
        //Log.d("Check", format.);
        //logdinUser=user_email.edit();
        //logdinUser.putString("UserEmail",user.getEmail().toString().trim());
        //logdinUser.commit();

        batch_list=FirebaseDatabase.getInstance().getReference("BatchList");

        addBatchList();
        bl.add("2018");
        bl.add("2019");
        bl.add("2020");


        ArrayAdapter<String> spinrAdapter=new ArrayAdapter<String>(DashBoard.this,android.R.layout.simple_list_item_1,bl);
        spinrAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        year.setAdapter(spinrAdapter);
        if(user.getEmail().equalsIgnoreCase("hod_cse@drmcet.ac.in")){
            usr_mng.setVisibility(View.VISIBLE);

        }
        usr_mng.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(DashBoard.this, UserManagement.class));

            }
        });
       year.setSelection(1);
        batch="2019";




        student_edit = FirebaseDatabase.getInstance().getReference("Students"+batch);

        current_batch=FirebaseDatabase.getInstance().getReference("current_batch");



        year.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                edit.putString("Year", year.getSelectedItem().toString().trim());
                edit.commit();
                batch=selected_yr.getString("Year", null);
                Log.d("check",batch);

                student_edit = FirebaseDatabase.getInstance().getReference("Students"+batch);
                student_edit.child("123").removeValue();

                student_edit.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {

                        noof_placed = 0;
                        placpercent = 0.0;
                        enrolled = 0;
                        eligibleStud=0;
                        studentlist.clear();
                        int totalSalary=0;

                        for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                            StudentAllDetails std = postSnapshot.getValue(StudentAllDetails.class);
                            std.setRollno(postSnapshot.getKey());
                            studentlist.add(std);
                            enrolled++;
                           if(std.getRollno()=="123")
                           {continue;}




                            if (!std.getPlacementcompany().toString().trim().equals("0")) {

                                noof_placed++;
                                totalSalary=totalSalary+std.getIntern_stiphend();

                            }
                            if(std.getPlacementstatus().equalsIgnoreCase("Placement Willing")&&std.getArear()==0){
                                eligibleStud++;
                            }
                        }
                        placpercent = (noof_placed / total_students) * 100;
                        avgSalary= (int) ((totalSalary/noof_placed));
                        start_progressbar(noof_placed, placpercent);


                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });

                // if(year)
                //finish();
                //startActivity(getIntent());
                //finish();
                //student_edit = FirebaseDatabase.getInstance().getReference("Students"+year.getSelectedItem().toString().trim());
                //getPlacementData();

                //Toast.makeText(DashBoard.this, selected_yr.getString("Year", null), Toast.LENGTH_SHORT).show();

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        //selected_yr=getSharedPreferences("SelectedYear", MODE_PRIVATE);


        //student_edit = FirebaseDatabase.getInstance().getReference("Students"+batch);
        //progressStatus=0;
        noof_placed=0;
        //backbuttonpress=0;
        //startActivity(new Intent(DashBoard.this, Custbtn.class));
        intern.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(DashBoard.this, PlacementTasks.class));
                //finish();
            }
        });
        genrateReport.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(DashBoard.this,ReportGenrator.class));
                //finish();
            }
        });
        placementstatis.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                yetto = (int) (enrolled - noof_placed);


                Intent i = new Intent(DashBoard.this, com.example.muralli.lifecycle.PlacementStatistics.PlacementStatis.class);
                i.putExtra("p_count", Integer.valueOf((int) noof_placed).toString());
                i.putExtra("p_percent", Double.valueOf(new DecimalFormat("##.#").format(placpercent)).toString());
                i.putExtra("p_enrolled", Integer.valueOf((int) enrolled).toString());
                i.putExtra("p_yetto", Integer.valueOf(yetto).toString());
                i.putExtra("p_avgsal", Integer.valueOf((int) avgSalary).toString());
                i.putExtra("p_eligible", Integer.valueOf(eligibleStud).toString());

                startActivity(i);
                //finish();

            }
        });

        users.addValueEventListener(new ValueEventListener() {

                                        @Override
                                        public void onDataChange(DataSnapshot dataSnapshot) {
                                            userObjs.clear();
                                            for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                                                UserDetail curntuser = postSnapshot.getValue(UserDetail.class);
                                                //useNameList.add(curntuser.getName());
                                                //Log.d("Check", "username" + curntuser.getName());
                                               // userObjs.add(curntuser);
                                                if(curntuser.getEmail().equals(user.getEmail().toString())){
                                                    if (curntuser.getRole().equals("iap")){
                                                    iapedit.putString("status","on");
                                                        iapedit.commit();


                                                    //Log.d("check",iapStatus.getString("status",null));
                                                    }
                                                    else
                                                    {
                                                        iapedit.putString("status","off");
                                                        iapedit.commit();
                                                        ///Log.d("check", "iap off");
                                                    }
                                                }
                                            }

                                        }

                                        @Override
                                        public void onCancelled(DatabaseError databaseError) {

                                        }
                                    }

        );


       student_edit.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                noof_placed=0;
                placpercent=0.0;
                enrolled=0;
                eligibleStud=0;
                int totalSalary=0;
                avgSalary=0;
                total_students=0;

                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {

                    StudentAllDetails std = postSnapshot.getValue(StudentAllDetails.class);
                    std.setRollno(postSnapshot.getKey());
                    studentlist.add(std);
                    enrolled++;
                    total_students++;


                    if (!std.getPlacementcompany().toString().trim().equals("0")) {

                        noof_placed++;
                        totalSalary=totalSalary+std.getIntern_stiphend();



                    }
                    if(std.getPlacementstatus().equalsIgnoreCase("Placement Willing")&&std.getArear()==0){
                        eligibleStud++;
                    }
                }

                placpercent=(noof_placed /total_students) * 100;
                avgSalary= (int) ((totalSalary/noof_placed));
                start_progressbar(noof_placed,placpercent);






            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
             db_cont.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getBaseContext(), ContactPage.class));
            }
        });
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAuth fAuth = FirebaseAuth.getInstance();
                fAuth.signOut();
                startActivity(new Intent(getBaseContext(), MainActivity.class));
                finish();
            }
        });
        skillmatrix.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(getBaseContext(), com.example.muralli.lifecycle.StudentDetails.StudentInfo.class);
                i.putExtra("From", "dashboard");
                startActivity(i);
                //finish();

            }
        });

        //getPlacementData();

    }

    public void addBatchList(){
       batch_list.addListenerForSingleValueEvent(new ValueEventListener() {
           @Override
           public void onDataChange(DataSnapshot dataSnapshot) {
               for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                   BatchList b = postSnapshot.getValue(BatchList.class);
                   for(int i=0;i<b.getbList().size();i++){ bl1.add(b.getbList().get(i));
                   Log.d("batchlist",b.getbList().get(i).toString());
                   }
               }
           }

           @Override
           public void onCancelled(DatabaseError databaseError) {

           }
       });
    }



    public void onBackPressed() {
        super.onBackPressed();
        backbuttonpress++;
        if(backbuttonpress==1){
            Toast.makeText(DashBoard.this, "Press again to exit", Toast.LENGTH_SHORT).show();
        }
        if(backbuttonpress==2) {
            finish();
        }
    }
    public void onStart(){
        super.onStart();
        //start_progressbar(noof_placed,placpercent);


    }


    public void start_progressbar(final double placcount, final double placpercentage){
     //  Log.d("Check", "Inside Start progress bar" + Double.valueOf(placcount).toString().trim() + "&" + Double.valueOf(placpercentage).toString().trim());
        final int[] progressStatus = {0};
        final int[] progressStatus2 = {0};
       /* if(thrd_pp.isAlive()){
            thrd_pp.interrupt();
        }
        if(thrd_pc.isAlive())
        {
            thrd_pc.interrupt();
        }*/
        if(thrd_pc!=null){
           // thrd_pc.interrupt();
        }
       thrd_pc= new Thread(new Runnable() {
            public void run() {
                progressStatus[0]=0;
                while (progressStatus[0] < placcount) {
                    progressStatus[0] += 1;

                                      handler.post(new Runnable() {
                        public void run() {
                           // Log.d("Check","count:"+" "+Integer.toString(progressStatus[0]).trim());

                            plac_count.setProgress(progressStatus[0]);
                            txt_pcount.setText(Integer.toString(progressStatus[0]));


                        }
                    });
                    try {

                       Thread.sleep(20);

                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
        thrd_pc.start();
        if(thrd_pp!=null){
            //thrd_pp.interrupt();
        }
        thrd_pp=new Thread(new Runnable() {
            public void run() {
                progressStatus2[0]=0;
                while (progressStatus2[0] < placpercentage) {
                    progressStatus2[0] += 1;


                    handler.post(new Runnable() {
                        public void run() {
                           // Log.d("Check","percent:"+" "+Integer.toString(progressStatus2[0]).trim());

                            plac_perc.setProgress(progressStatus2[0]);
                            txt_pperc.setText(Integer.toString(progressStatus2[0]));
                            if(progressStatus2[0]>=placpercentage){
                                txt_pperc.setText(Double.valueOf(new DecimalFormat("##.#").format(placpercentage)).toString().trim());
                            }
                        }
                    });
                    try {

                        Thread.sleep(20);

                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
        thrd_pp.start();



    }
    
    }

