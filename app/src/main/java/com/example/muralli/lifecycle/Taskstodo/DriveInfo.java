package com.example.muralli.lifecycle.Taskstodo;

import android.content.SharedPreferences;
import android.os.Bundle;
//import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.muralli.lifecycle.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class DriveInfo extends AppCompatActivity {
    List<DriveModelnew> drivemodels=new ArrayList<>();
    List<Integer> test=new ArrayList<>();
    DatabaseReference driveDetail_fb;
    ListView driveList;
    DriveDetailAdaptr driveadapter;
    SharedPreferences selected_yr;
    int drivelistsize;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_drive_info);

        selected_yr=getSharedPreferences("SelectedYear", MODE_PRIVATE);
        String yr=selected_yr.getString("Year", null);
        test.add(4);
        test.add(3);
        test.add(8);
        test.add(2);
        if(yr.equalsIgnoreCase("2017-2018")){

            driveDetail_fb= FirebaseDatabase.getInstance().getReference("DriveDetails");}
        else{
            driveDetail_fb= FirebaseDatabase.getInstance().getReference("DriveDetails"+yr);
        }

        driveDetail_fb.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                    DriveModelnew drive_model = postSnapshot.getValue(DriveModelnew.class);

                    drive_model.company = postSnapshot.getKey();
                    drivemodels.add(drive_model);

                }
                if (drivelistsize == 0) {
                    drivelistsize = drivemodels.size();
                }


            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        Log.d("Check", "Drive models size" + drivemodels.size());
        driveList=(ListView)findViewById(R.id.driveinfoListvu);

        if(drivemodels.size()==0||drivemodels.size()== drivelistsize){

        /*DriveModel driveModel1=new DriveModel("05-09-2018",0,"infosys");
        DriveModel driveModel2=new DriveModel("05-09-2018",3,"clorida Technologies");
        drivemodels.add(driveModel1);
        drivemodels.add(driveModel2);*/
            Log.d("Check", "Drive models size inside if" + drivemodels.size());
            Collections.sort(drivemodels, new DriveModelDateComparator());
            for(DriveModelnew d:drivemodels){
                Log.d("Check Date",d.getCompany()+":"+d.getDate());
            }
            driveadapter=new DriveDetailAdaptr(this,drivemodels);
            driveList.setAdapter(driveadapter);


            //driveadapter.notifyDataSetChanged();

        }
        else
        {
            Log.d("Check", "Drive models size inside else" + drivemodels.size());

            drivemodels=drivemodels.subList(0,drivelistsize);
            Collections.sort(drivemodels, new DriveModelDateComparator());
            for(DriveModelnew d:drivemodels){
                Log.d("Check Date",d.getCompany()+":"+d.getDate());
            }

            driveadapter=new DriveDetailAdaptr(this,drivemodels);
            //driveadapter.notifyDataSetChanged();
            driveList.setAdapter(driveadapter);

            //driveadapter.notifyDataSetChanged();

        }



    }
}


