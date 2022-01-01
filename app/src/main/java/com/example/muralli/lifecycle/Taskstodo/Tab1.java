package com.example.muralli.lifecycle.Taskstodo;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
//import android.support.design.widget.FloatingActionButton;
//import androidx.core.app.Fragment;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.example.muralli.lifecycle.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * Created by Muralli on 16-03-2018.
 */
public class Tab1 extends Fragment {
    ListView driveList;
    SharedPreferences selected_yr;

    SharedPreferences.Editor edit;
    List<DriveModelnew> drivemodels=new ArrayList<>();
    List<DriveModelnew> drivemodel_fltr=new ArrayList<>();
    DatabaseReference driveDetail_fb;
    Boolean state=true;
    DriveDetailAdaptr driveadapter;
    int drivelistsize;
    String batch;
    View rootView;
    String fltr_choice;
    //private SectionsPagerAdapter mSectionsPagerAdapter;
    SharedPreferences iapStatus;
    String iapsts;

    public Tab1() {
        //this.driveList = driveList;
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView=inflater.inflate(R.layout.drivedetails, container, false);
        selected_yr=getContext().getSharedPreferences("SelectedYear", Context.MODE_PRIVATE);
        driveList=(ListView)rootView.findViewById(R.id.listvyu_drivedtyl);
        batch=selected_yr.getString("Year", null);
        FloatingActionButton fabfltr = (FloatingActionButton)rootView.findViewById(R.id.fabFltr);
        fabfltr.setAlpha(0.5f);
        FloatingActionButton fab = (FloatingActionButton) rootView.findViewById(R.id.fab);
        //iapStatus=rootView.getSharedPreferences("IapStatus", rootView.MODE_PRIVATE);
        //iapsts=iapStatus.getString("status", null);
        iapsts="on";
        if(iapsts.equals("on")){

            fab.setVisibility(View.VISIBLE);


        }
        else
        {
            fab.setVisibility(View.GONE);


        }
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getContext(), AddDrive.class));

            }
        });
        //Log.d("Check", ""+batch);

            driveDetail_fb= FirebaseDatabase.getInstance().getReference("DriveDetails"+batch);

       // driveDetail_fb= FirebaseDatabase.getInstance().getReference("DriveDetails");
        driveDetail_fb.keepSynced(true);
        //Log.d("Check", "inside on tab1 oncreate view");
        fabfltr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder=new AlertDialog.Builder(rootView.getContext());

                builder.setTitle("Filter Drives By");
                final String[] choice={"Drive Pending","Result Pending","Result Announced","All Drives"};
                final int checkedItem=3;
                builder.setSingleChoiceItems(choice, checkedItem, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        fltr_choice = choice[which];


                    }
                });
                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //new GetDataTask().execute();
                        if(fltr_choice!="Drive Pending"&&fltr_choice!="Result Pending"&&fltr_choice!="Result Announced"&&fltr_choice!="All Drives")
                            fltr_choice="All Drives";

                            switch (fltr_choice){
                                case "Result Announced":
                                    drivemodel_fltr.clear();
                                    for(DriveModelnew dm:drivemodels){
                                        if(dm.getStNo()==4){
                                            drivemodel_fltr.add(dm);
                                        }
                                    }
                                    break;
                                case "Result Pending":
                                    drivemodel_fltr.clear();
                                    for(DriveModelnew dm:drivemodels){
                                        if(dm.getStNo()==3){
                                            drivemodel_fltr.add(dm);
                                        }
                                    }
                                    break;
                                case "Drive Pending":
                                    drivemodel_fltr.clear();
                                    for(DriveModelnew dm:drivemodels){
                                        if(dm.getStNo()==2){
                                            drivemodel_fltr.add(dm);
                                        }
                                    }
                                    break;
                                case "All Drives":
                                    drivemodel_fltr.clear();
                                    for(DriveModelnew dm:drivemodels){
                                        drivemodel_fltr.add(dm);

                                    }
                                    break;

                            }
                        Toast.makeText(rootView.getContext(),Integer.valueOf(drivemodel_fltr.size()).toString()+" Drives",Toast.LENGTH_SHORT).show();
                        driveadapter = new DriveDetailAdaptr(getActivity(), drivemodel_fltr);
                        driveList.setAdapter(driveadapter);
                        driveadapter.notifyDataSetChanged();

                        }
                    }
                );
                builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });

                AlertDialog dial=builder.create();
                dial.show();

            }
        });


        driveDetail_fb.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                drivemodels.clear();

                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                    DriveModelnew drive_model = postSnapshot.getValue(DriveModelnew.class);

                    drive_model.company = postSnapshot.getKey();
                    //Log.d("Check", "drive model" + drive_model.getCompany() + " " + drive_model.getStNo() + "  " + drive_model.getDate().toString());
                    drivemodels.add(drive_model);
                    Log.d("check cmdt",drive_model.getCompdate());

                }
                if (drivelistsize == 0) {
                    drivelistsize = drivemodels.size();
                }

                displayDrive();


            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        //Log.d("Check", "Drive models size2" + drivemodels.size());
        driveList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //Log.d("Check drive click", "fine" );
                //Log.d("Check Drive Click","Working");
                try {
                   // Log.d("Check Drive Click","Working");

                    Intent i=new Intent(getContext(), com.example.muralli.lifecycle.StudentDetails.StudentInfo.class);
                    Bundle el=new Bundle();
                    int k=1;
                    List<String> eligbleRoll=drivemodels.get(position).getEligibleList();
                    for(String roll:eligbleRoll){
                        el.putString(roll,roll);
                        k++;
                    }
                    i.putExtras(el);
                    i.putExtra("From", "drivelist");
                    startActivity(i);

                    Toast.makeText(getContext(), drivemodels.get(position).getCompany().toString().trim() + " " + Integer.valueOf(drivemodels.get(position).getStNo()).toString(), Toast.LENGTH_SHORT).show();
                    //displayDrive();
                    //driveadapter.notifyDataSetChanged();
                } catch (Exception e) {
                    e.printStackTrace();

                }
            }


        });








       /* driveList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Log.d("Check Drive Click","Working");
                try {
                    Log.d("Check Drive Click","Working");

                    Intent i=new Intent(getContext(), StudentInfo.class);
                    Bundle el=new Bundle();
                    int k=1;
                    List<String> eligbleRoll=drivemodels.get(position).getEligibleList();
                    for(String roll:eligbleRoll){
                        el.putString(roll,roll);
                        k++;
                    }
                    i.putExtras(el);
                    i.putExtra("From", "drivelist");
                    startActivity(i);

                    Toast.makeText(getContext(), drivemodels.get(position).getCompany().toString().trim() + " " + Integer.valueOf(drivemodels.get(position).getStNo()).toString(), Toast.LENGTH_SHORT).show();
                    //displayDrive();
                    //driveadapter.notifyDataSetChanged();
                } catch (Exception e) {
                    e.printStackTrace();

                }
            }
        });*/

       /* driveList.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, final int position, long id) {

                final DriveModelnew dm=drivemodels.get(position);

                final AlertDialog.Builder alertDialog = new AlertDialog.Builder(getContext());
                alertDialog.setIcon(R.drawable.delfin);

                alertDialog.setTitle("Confirm");

                alertDialog.setMessage("Are you sure to Delete");


                alertDialog.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        driveDetail_fb.child(dm.getCompany().toString()).removeValue();
                        drivemodels.remove(position);
                        driveadapter.notifyDataSetChanged();
                    }
                });
                alertDialog.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });


                alertDialog.show();
                return false;
            }
        });*/

       // driveadapter.notifyDataSetChanged();

       // drivemodels.clear();
        /*
            driveList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                    return;
                }
            });
        }
        catch (Exception e){
            e.printStackTrace();
        }*/


        return rootView;
    }

    public void displayDrive()
    {
        if(getActivity()!=null) {
            Collections.sort(drivemodels, new DriveModelDateComparator());
            Collections.reverse(drivemodels);
            //Collections.sort(drivemodels);
            for(DriveModelnew d:drivemodels){
                //Log.d("Check Date", d.getCompany() + ":" + new SimpleDateFormat("mm/dd/yy").format(d.getDate()));
            }
            driveadapter = new DriveDetailAdaptr(getActivity(), drivemodels);
            driveList.setAdapter(driveadapter);
            driveadapter.notifyDataSetChanged();
        }
    }
}
class DriveModelDateComparator implements Comparator<DriveModelnew> {

    @Override
    public int compare(DriveModelnew drv1, DriveModelnew drv2) {
       // Log.d("Check",Integer.valueOf(drv1.getDate().compareTo(drv2.getDate())).toString());
        if(drv1.getDate()==null||drv2.getDate()==null)
            return 0;
        return drv1.getDate().compareTo(drv2.getDate());
    }
}

