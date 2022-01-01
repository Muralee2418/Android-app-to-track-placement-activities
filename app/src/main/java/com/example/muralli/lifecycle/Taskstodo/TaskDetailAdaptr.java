package com.example.muralli.lifecycle.Taskstodo;

import android.app.Activity;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.muralli.lifecycle.R;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Muralli on 08-09-2018.
 */
public class TaskDetailAdaptr extends ArrayAdapter {
    List<TaskModel> tasklist;
    List<String> tasks=new ArrayList<>();
    private Activity context;
    LayoutInflater inflater;
    DatabaseReference taskstate_fb;
    SharedPreferences selected_yr;
    SharedPreferences iapStatus;
    String iapsts,batch;
    public TaskDetailAdaptr(Activity context,List<TaskModel> objects) {
        super(context, R.layout.placement_tasks,objects);
        this.tasklist = objects;
        this.context = context;
    }

    @Override
    public Object getItem(int position) {
        notifyDataSetChanged();
        return super.getItem(position);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        DriveViewHolder dh=new DriveViewHolder();
        final TaskModel dm=tasklist.get(position);
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

        taskstate_fb= FirebaseDatabase.getInstance().getReference("TaskDetails"+batch);
        //student_fb= FirebaseDatabase.getInstance().getReference("Students"+batch);
        //student_fb.keepSynced(true);
        taskstate_fb.keepSynced(true);
        if(convertView==null)
        {
            inflater = context.getLayoutInflater();
            convertView = inflater.inflate(R.layout.placement_tasks, null, true);
            dh.companyName=(TextView)convertView.findViewById(R.id.tv_taskname);
            dh.taskfrmdate =(TextView)convertView.findViewById(R.id.tv_taskdate);
            dh.tasktodate=(TextView)convertView.findViewById(R.id.tv_tasktodate);
            dh.tv_to=(TextView)convertView.findViewById(R.id.tv_to);
            dh.st_plan =(ImageView)convertView.findViewById(R.id.iv_taskplan);
            dh.st_prgrs =(ImageView)convertView.findViewById(R.id.iv_taskprgrs);
            dh.st_compd =(ImageView)convertView.findViewById(R.id.iv_taskcompd);
            dh.plan_prgrs=(View)convertView.findViewById(R.id.v_progres);
            dh.prgrs_compd=(View)convertView.findViewById(R.id.v_compltd);
            //dh.bt_edit=(ImageView)convertView.findViewById(R.id.bt_drive_edit);
           // dh.bt_del=(ImageView)convertView.findViewById(R.id.bt_drive_del);

            //Log.d("Check","inside getview if");
            //setStColor(dm.getState());
            convertView.setTag(dh);

        }
        else{
            //Log.d("Check","inside list get view else");
            dh=(DriveViewHolder)convertView.getTag();
        }
        try {

            dh.companyName.setText(dm.getTask_name().toString().trim());
            if(dm.getTask_to_date().equalsIgnoreCase("0")){
                dh.tasktodate.setVisibility(View.GONE);
                dh.tv_to.setVisibility(View.GONE);
            }
            else{
                dh.tasktodate.setText(new SimpleDateFormat("dd/mm/yy").format(new SimpleDateFormat("mm/dd/yy").parse(dm.getTask_to_date().toString().trim())));
            }
            dh.taskfrmdate.setText(new SimpleDateFormat("dd/mm/yy").format(new SimpleDateFormat("mm/dd/yy").parse(dm.getTask_frm_date().toString().trim())));
              Log.d("Check Task", dm.getTask_name().toString() + Integer.valueOf(dm.getStatus()).toString());
            setStColor(dm.getStatus(), dh);}
        catch (Exception e){
            e.printStackTrace();
        }

        final DriveViewHolder finalDh = dh;
        if(iapsts.equals("on")){
            //dh.bt_edit.setVisibility(View.VISIBLE);
            //dh.bt_del.setVisibility(View.VISIBLE);




        }
        else
        {
            dh.st_plan.setEnabled(false);
            dh.st_prgrs.setEnabled(false);
            dh.st_compd.setEnabled(false);
            //dh.bt_edit.setVisibility(View.GONE);
            //dh.bt_del.setVisibility(View.GONE);



        }
        dh.st_plan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (iapsts.equals("on")) {


                    //fab.setVisibility(View.VISIBLE);


                } else {
                    // fab.setVisibility(View.GONE);


                }
                //Log.d("Check", "inside list onclick");

                setStColor(1, finalDh);
                dm.setStatus(1);

                //updateDriveStatus(dm);

                taskstate_fb.child(dm.getTask_name()).setValue(dm);
                // notifyDataSetChanged();

            }
        });
        dh.st_prgrs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //  Log.d("Check", "inside inform onclick");
                setStColor(2, finalDh);
                dm.setStatus(2);
                taskstate_fb.child(dm.getTask_name()).setValue(dm);
                // notifyDataSetChanged();


            }
        });
        dh.st_compd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Log.d("Check", "inside drive onclick");
                setStColor(3, finalDh);
                dm.setStatus(3);
                taskstate_fb.child(dm.getTask_name()).setValue(dm);
                //notifyDataSetChanged();
            }
        });

        final DriveViewHolder finalDh1 = dh;
        return convertView;
    }
    public static class DriveViewHolder{
        public TextView companyName;
        public TextView taskfrmdate,tasktodate,tv_to;
        public ImageView st_plan, st_prgrs, st_compd;
        public View plan_prgrs,prgrs_compd;
        ImageView bt_edit,bt_del;

    }
    public void setStColor(int st, DriveViewHolder dh){
        //notifyDataSetChanged();
        switch(st){
            case 0:
                dh.st_plan.setImageResource(R.mipmap.tasknot);
                dh.st_prgrs.setImageResource(R.mipmap.tasknot);
                dh.st_compd.setImageResource(R.mipmap.tasknot);
                dh.plan_prgrs.setBackgroundColor(Color.GRAY);
                dh.prgrs_compd.setBackgroundColor(Color.GRAY);


                //this.notifyDataSetChanged();
                // Log.d("Check", "inside list case0"+dh.companyName.getText().toString());
                break;
            case 1:
                dh.st_plan.setImageResource(R.mipmap.taskyes);
                dh.st_prgrs.setImageResource(R.mipmap.tasknot);
                dh.st_compd.setImageResource(R.mipmap.tasknot);
                dh.plan_prgrs.setBackgroundColor(Color.GRAY);
                dh.prgrs_compd.setBackgroundColor(Color.GRAY);
                //this.notifyDataSetChanged();
                //Log.d("Check", "inside case1"+dh.companyName.getText().toString());
                break;
            case 2:
                dh.st_plan.setImageResource(R.mipmap.taskyes);
                dh.st_prgrs.setImageResource(R.mipmap.taskyes);
                dh.st_compd.setImageResource(R.mipmap.tasknot);
                dh.plan_prgrs.setBackgroundColor(Color.GREEN);
                dh.prgrs_compd.setBackgroundColor(Color.GRAY);
                //this.notifyDataSetChanged();
                //Log.d("Check", "inside list case2"+dh.companyName.getText().toString());
                break;
            case 3:
                dh.st_plan.setImageResource(R.mipmap.taskyes);
                dh.st_prgrs.setImageResource(R.mipmap.taskyes);
                dh.st_compd.setImageResource(R.mipmap.taskyes);
                dh.plan_prgrs.setBackgroundColor(Color.GREEN);
                dh.prgrs_compd.setBackgroundColor(Color.GREEN);
                //this.notifyDataSetChanged();
                //Log.d("Check", "inside case3"+dh.companyName.getText().toString());
                break;

        }
        // notifyDataSetChanged();
    }
}
