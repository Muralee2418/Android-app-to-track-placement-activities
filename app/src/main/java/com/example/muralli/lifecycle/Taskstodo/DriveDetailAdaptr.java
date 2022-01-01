package com.example.muralli.lifecycle.Taskstodo;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.muralli.lifecycle.R;
import com.example.muralli.lifecycle.StudentDetails.StudentAllDetails;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Muralli on 16-03-2018.
 */
public class DriveDetailAdaptr extends ArrayAdapter {
    List<DriveModelnew> drivelist;
    List<String> company=new ArrayList<>();
    private Activity context;
    LayoutInflater inflater;
    DatabaseReference drivestate_fb,student_fb;
    SharedPreferences selected_yr;
    SharedPreferences iapStatus;
    String iapsts,batch;


    public DriveDetailAdaptr(Activity context,List<DriveModelnew> drivelist) {
        super(context, R.layout.taskdetails, drivelist);
        this.drivelist = drivelist;
        this.context = context;


    }

    @Override
    public Object getItem(int position) {
        notifyDataSetChanged();
        return super.getItem(position);
    }

    @TargetApi(Build.VERSION_CODES.M)
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        notifyDataSetChanged();
        DriveViewHolder dh=new DriveViewHolder();
        final DriveModelnew dm=drivelist.get(position);
        //Log.d("Check", "inside getview tab1");

        iapStatus=getContext().getSharedPreferences("IapStatus", getContext().MODE_PRIVATE);
        iapsts=iapStatus.getString("status", null);

        selected_yr=getContext().getSharedPreferences("SelectedYear", getContext().MODE_PRIVATE);
        String batch=selected_yr.getString("Year", null);


       // notifyDataSetChanged();
        /*if(yr.equalsIgnoreCase("2018")){

            drivestate_fb= FirebaseDatabase.getInstance().getReference("DriveDetails");}
        else{
            drivestate_fb= FirebaseDatabase.getInstance().getReference("DriveDetails"+yr);
        }*/

       drivestate_fb= FirebaseDatabase.getInstance().getReference("DriveDetails"+batch);
        student_fb= FirebaseDatabase.getInstance().getReference("Students"+batch);
        student_fb.keepSynced(true);
        drivestate_fb.keepSynced(true);


       if(convertView==null)
        {
           inflater = context.getLayoutInflater();
            convertView = inflater.inflate(R.layout.taskdetails, null, true);
            dh.companyName=(TextView)convertView.findViewById(R.id.drivecompany);
            dh.driveDate=(TextView)convertView.findViewById(R.id.drivedate);
            dh.st_list=(ImageView)convertView.findViewById(R.id.imgtsk_list);
            dh.st_inform=(ImageView)convertView.findViewById(R.id.imgtsk_info);
            dh.st_drive=(ImageView)convertView.findViewById(R.id.imgtsk_drive);
            dh.st_result=(ImageView)convertView.findViewById(R.id.imgtsk_result);
            dh.lis_info=(View)convertView.findViewById(R.id.lis_info);
            dh.info_driv=(View)convertView.findViewById(R.id.info_driv);
            dh.driv_res=(View)convertView.findViewById(R.id.driv_res);
            dh.bt_edit=(ImageView)convertView.findViewById(R.id.bt_drive_edit);
            dh.bt_del=(ImageView)convertView.findViewById(R.id.bt_drive_del);

                        //Log.d("Check","inside getview if");
            //setStColor(dm.getState());
            convertView.setTag(dh);

       }
        else{
            //Log.d("Check","inside list get view else");
            dh=(DriveViewHolder)convertView.getTag();
        }
        try {

        dh.companyName.setText(dm.getCompany().toString().trim());
            dh.driveDate.setText(new SimpleDateFormat("dd/mm/yy").format(new SimpleDateFormat("mm/dd/yy").parse(dm.getDate().toString().trim())));

        setStColor(dm.getStNo(), dh);}
        catch (Exception e){
            e.printStackTrace();
        }

      final DriveViewHolder finalDh = dh;
        if(iapsts.equals("on")){
            dh.bt_edit.setVisibility(View.VISIBLE);
            dh.bt_del.setVisibility(View.VISIBLE);




        }
        else
        {
            dh.st_list.setEnabled(false);
            dh.st_inform.setEnabled(false);
            dh.st_drive.setEnabled(false);
            dh.st_result.setEnabled(false);
            dh.bt_edit.setVisibility(View.GONE);
            dh.bt_del.setVisibility(View.GONE);



        }
     /*   dh.lis_info.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("check","inside onclick lis_info");

            }
        });
        dh.info_driv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });*/
       /* dh.driv_res.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("Check", "inside list onclick");

                setStColor(4, finalDh);
                dm.setState(4);

                drivestate_fb.child(dm.getCompany()).setValue(dm);

            }
        });**/
        dh.st_list.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(iapsts.equals("on")){



                    //fab.setVisibility(View.VISIBLE);


                }
                else
                {
                    // fab.setVisibility(View.GONE);


                }
                //Log.d("Check", "inside list onclick");

               setStColor(1, finalDh);
                dm.setStNo(1);

                //updateDriveStatus(dm);

                drivestate_fb.child(dm.getCompany()).setValue(dm);
              // notifyDataSetChanged();

            }
        });
        dh.st_inform.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              //  Log.d("Check", "inside inform onclick");
                setStColor(2, finalDh);
                dm.setStNo(2);
                drivestate_fb.child(dm.getCompany()).setValue(dm);
              // notifyDataSetChanged();




            }
        });
        dh.st_drive.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Log.d("Check", "inside drive onclick");
                setStColor(3, finalDh);
                dm.setStNo(3);
                drivestate_fb.child(dm.getCompany()).setValue(dm);
               //notifyDataSetChanged();
            }
        });
        dh.st_result.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //break;
                //Log.d("Check", "inside result onclick");
                setStColor(4, finalDh);
                dm.setStNo(4);
                drivestate_fb.child(dm.getCompany()).setValue(dm);
              //notifyDataSetChanged();
            }
        });
        final DriveViewHolder finalDh1 = dh;
        dh.bt_del.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                student_fb.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {

                        company.clear();

                        for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                            StudentAllDetails std = postSnapshot.getValue(StudentAllDetails.class);
                            std.setRollno(postSnapshot.getKey());
                            //Log.d("Check student", std.getPlacementcompany().toString().trim());

                            if (std.getPlacementcompany().equalsIgnoreCase(dm.getCompany().toString().trim()))
                            {
                                //Log.d("Check add", dm.getCompany().toString().trim());
                                company.add(std.getPlacementcompany());
                            }
                            if (company.contains(dm.getCompany().toString().trim())) {
                                //Log.d("Check contains", dm.getCompany().toString().trim());
                                final AlertDialog.Builder alertDialog = new AlertDialog.Builder(getContext());
                                alertDialog.setIcon(R.drawable.delfin);

                                alertDialog.setTitle("Sorry");

                                alertDialog.setMessage("Students got placed in this Company, You can't Delete!!!");


                                alertDialog.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        dialog.cancel();

                                    }
                                });


                                alertDialog.show();
                                break;


                            }

                        }
                        if (!company.contains(dm.getCompany().toString().trim())) {
                           /*final android.support.v7.app.AlertDialog.Builder alertDialog = new android.support.v7.app.AlertDialog.Builder(getContext());

                            alertDialog.setTitle("Confirm");

                            alertDialog.setMessage("Are you sure to Delete");


                            alertDialog.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    drivestate_fb.child(dm.getCompany().toString().trim()).removeValue();
                                }
                            });
                            alertDialog.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.cancel();
                                }
                            });


                            alertDialog.show();*/


                        }


                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });


            }
        });
        dh.bt_edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(context, EditDriveDetails.class);
                i.putExtra("company", dm.getCompany().toString().trim());


                    i.putExtra("companydate", dm.getDate().toString().trim());

                i.putExtra("salary", Integer.valueOf(dm.getSalary()).toString().trim());
                context.startActivity(i);


            }
        });

        //notifyDataSetChanged();

        return convertView;

    }
    public void updateDriveStatus(DriveModelnew dm){

        if(iapsts.equals("on")){

            //fab.setVisibility(View.VISIBLE);


        }
        else
        {
           // fab.setVisibility(View.GONE);


        }

    }
    //@Override
    /*public void notifyDataSetChanged() {
        //size = objects.size();

        super.notifyDataSetChanged();
    }*/
    public static class DriveViewHolder{
        public TextView companyName;
        public TextView driveDate;
        public ImageView st_list,st_inform,st_drive,st_result;
        public View lis_info,info_driv,driv_res;
        ImageView bt_edit,bt_del;

    }
    @TargetApi(Build.VERSION_CODES.M)
    public void setStColor(int st, DriveViewHolder dh){
        //notifyDataSetChanged();
        switch(st){
            case 0:
                dh.st_list.setImageResource(R.mipmap.tasknot);
                dh.st_inform.setImageResource(R.mipmap.tasknot);
                dh.st_drive.setImageResource(R.mipmap.tasknot);
                dh.st_result.setImageResource(R.mipmap.tasknot);
                dh.lis_info.setBackgroundColor(Color.GRAY);
                dh.info_driv.setBackgroundColor(Color.GRAY);
                dh.driv_res.setBackgroundColor(Color.GRAY);
                //this.notifyDataSetChanged();
               // Log.d("Check", "inside list case0"+dh.companyName.getText().toString());
                break;
            case 1:
                dh.st_list.setImageResource(R.mipmap.taskyes);
                dh.st_inform.setImageResource(R.mipmap.tasknot);
                dh.st_drive.setImageResource(R.mipmap.tasknot);
                dh.st_result.setImageResource(R.mipmap.tasknot);
                dh.lis_info.setBackgroundColor(Color.GRAY);
                dh.info_driv.setBackgroundColor(Color.GRAY);
                dh.driv_res.setBackgroundColor(Color.GRAY);
                //this.notifyDataSetChanged();
                //Log.d("Check", "inside case1"+dh.companyName.getText().toString());
                break;
            case 2:
                dh.st_list.setImageResource(R.mipmap.taskyes);
                dh.st_inform.setImageResource(R.mipmap.taskyes);
                dh.st_drive.setImageResource(R.mipmap.tasknot);
                dh.st_result.setImageResource(R.mipmap.tasknot);
                dh.lis_info.setBackgroundColor(Color.GREEN);
                dh.info_driv.setBackgroundColor(Color.GRAY);
                dh.driv_res.setBackgroundColor(Color.GRAY);
                //this.notifyDataSetChanged();
                //Log.d("Check", "inside list case2"+dh.companyName.getText().toString());
                break;
            case 3:
                dh.st_list.setImageResource(R.mipmap.taskyes);
                dh.st_inform.setImageResource(R.mipmap.taskyes);
                dh.st_drive.setImageResource(R.mipmap.taskyes);
                dh.st_result.setImageResource(R.mipmap.tasknot);
                dh.lis_info.setBackgroundColor(Color.GREEN);
                dh.info_driv.setBackgroundColor(Color.GREEN);
                dh.driv_res.setBackgroundColor(Color.GRAY);
                //this.notifyDataSetChanged();
                //Log.d("Check", "inside case3"+dh.companyName.getText().toString());
                break;
            case 4:
                dh.st_list.setImageResource(R.mipmap.taskyes);
                dh.st_inform.setImageResource(R.mipmap.taskyes);
                dh.st_drive.setImageResource(R.mipmap.taskyes);
                dh.st_result.setImageResource(R.mipmap.taskyes);
                dh.lis_info.setBackgroundColor(Color.GREEN);
                dh.info_driv.setBackgroundColor(Color.GREEN);
                dh.driv_res.setBackgroundColor(Color.GREEN);
                //this.notifyDataSetChanged();
                //Log.d("Check", "inside list case4"+dh.companyName.getText().toString());
                break;



        }
      // notifyDataSetChanged();
    }
}
