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
public class Tab2 extends Fragment {
    ListView task_Listvu;
    SharedPreferences selected_yr;


    SharedPreferences.Editor edit;
    List<TaskModel> taskModel=new ArrayList<>();
    List<TaskModel> taskModel_fltr=new ArrayList<>();
    DatabaseReference taskDetail_fb;
    Boolean state=true;
    TaskDetailAdaptr taskadapter;
    int tasklistsize;
    String batch;
    View rootView;
    String fltr_choice;
    String action_choice;
    SharedPreferences iapStatus;
    String iapsts;
    public Tab2() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
         final View rootView=inflater.inflate(R.layout.tab2,container,false);
        //rootView=inflater.inflate(R.layout.drivedetails, container, false);
        selected_yr=getContext().getSharedPreferences("SelectedYear", Context.MODE_PRIVATE);
        task_Listvu=(ListView)rootView.findViewById(R.id.listvyu_taskdetails);
        batch=selected_yr.getString("Year", null);
        FloatingActionButton fabfltr = (FloatingActionButton)rootView.findViewById(R.id.fabTaskFltr);
        fabfltr.setAlpha(0.5f);
        FloatingActionButton fabadd = (FloatingActionButton)rootView.findViewById(R.id.fab_taskadd);
        //Log.d("Check", ""+batch);

        taskDetail_fb= FirebaseDatabase.getInstance().getReference("TaskDetails"+batch);

        // driveDetail_fb= FirebaseDatabase.getInstance().getReference("DriveDetails");
        taskDetail_fb.keepSynced(true);
        //Log.d("Check", "inside on tab1 oncreate view")
        iapStatus=getContext().getSharedPreferences("IapStatus", getContext().MODE_PRIVATE);
        iapsts=iapStatus.getString("status", null);

        fabadd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getContext(), AddTask.class));
            }
        });

        fabfltr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder=new AlertDialog.Builder(rootView.getContext());

                builder.setTitle("Filter Drives By");
                final String[] choice={"Planned","In-Progress","Completed","All Tasks"};
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
                                if(fltr_choice!="Planned"&&fltr_choice!="In-Progress"&&fltr_choice!="Completed"&&fltr_choice!="All Tasks")
                                    fltr_choice="All Tasks";

                                switch (fltr_choice){
                                    case "Planned":
                                        taskModel_fltr.clear();
                                        for(TaskModel tm:taskModel){
                                            if(tm.getStatus()==1){
                                                taskModel_fltr.add(tm);
                                            }
                                        }
                                        break;
                                    case "In-Progress":
                                        taskModel_fltr.clear();
                                        for(TaskModel tm:taskModel){
                                            if(tm.getStatus()==2){
                                                taskModel_fltr.add(tm);
                                            }
                                        }
                                        break;
                                    case "Completed":
                                        taskModel_fltr.clear();
                                        for(TaskModel tm:taskModel){
                                            if(tm.getStatus()==3){
                                                taskModel_fltr.add(tm);
                                            }
                                        }
                                        break;
                                    case "All Tasks":
                                        taskModel_fltr.clear();
                                        for(TaskModel tm:taskModel){
                                            taskModel_fltr.add(tm);

                                        }
                                        break;

                                }
                                Toast.makeText(rootView.getContext(), Integer.valueOf(taskModel_fltr.size()).toString() + " Drives", Toast.LENGTH_SHORT).show();
                                taskadapter = new TaskDetailAdaptr(getActivity(), taskModel_fltr);
                                task_Listvu.setAdapter(taskadapter);
                                taskadapter.notifyDataSetChanged();

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

        taskDetail_fb.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                taskModel.clear();

                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                    TaskModel task_model = postSnapshot.getValue(TaskModel.class);

                    task_model.task_name = postSnapshot.getKey();
                    //Log.d("Check", "drive model" + drive_model.getCompany() + " " + drive_model.getStNo() + "  " + drive_model.getDate().toString());
                    taskModel.add(task_model);
                    //Log.d("check cmdt", task_model.getCompd());

                }
                if (tasklistsize == 0) {
                    tasklistsize = taskModel.size();
                }

                displayTask();


            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        if(iapsts.equals("on")) {


            task_Listvu.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
                @Override
                public boolean onItemLongClick(AdapterView<?> parent, View view, final int position, long id) {

                    AlertDialog.Builder builder = new AlertDialog.Builder(rootView.getContext());

                    builder.setTitle("Choose Action");
                    final String[] choice = {"Edit Task", "Delete Task"};
                    final int checkedItem = 3;
                    builder.setSingleChoiceItems(choice, checkedItem, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            action_choice = choice[which];


                        }
                    });
                    builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    //new GetDataTask().execute();
                                    if (action_choice != "Edit Task" && action_choice != "Delete")
                                        fltr_choice = "All Tasks";

                                    switch (action_choice) {
                                        case "Edit Task":
                                            final TaskModel tm_temp1 = taskModel.get(position);
                                            Intent i = new Intent(getContext(), EditTask.class);
                                            Bundle taskBundle = new Bundle();
                                            taskBundle.putString("t_name", tm_temp1.getTask_name());
                                            taskBundle.putString("t_frm_date", tm_temp1.getTask_frm_date());
                                            taskBundle.putString("t_to_date", tm_temp1.getTask_to_date());
                                            taskBundle.putString("t_status", Integer.valueOf(tm_temp1.getStatus()).toString());
                                            i.putExtras(taskBundle);
                                            startActivity(i);
                                            break;
                                        case "Delete Task":
                                            final TaskModel tm_temp = taskModel.get(position);
                                            final AlertDialog.Builder alertDialog = new AlertDialog.Builder(getContext());
                                            alertDialog.setIcon(R.drawable.delfin);

                                            alertDialog.setTitle("Confirm");

                                            alertDialog.setMessage("Are you sure to Delete");


                                            alertDialog.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                                @Override
                                                public void onClick(DialogInterface dialog, int which) {
                                                    taskDetail_fb.child(tm_temp.getTask_name().toString()).removeValue();
                                                    taskModel.remove(position);
                                                    taskadapter.notifyDataSetChanged();
                                                }
                                            });
                                            alertDialog.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                                                @Override
                                                public void onClick(DialogInterface dialog, int which) {
                                                    dialog.cancel();
                                                }
                                            });


                                            alertDialog.show();

                                            break;

                                    }


                                }
                            }
                    );
                    builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                        }
                    });

                    AlertDialog dial = builder.create();
                    dial.show();

                    return false;
                }
            });
        }



                return rootView;
    }

    public void displayTask()
    {
        if(getActivity()!=null) {
            Collections.sort(taskModel, new TaskModelDateComparator());
            Collections.reverse(taskModel);
            //Collections.sort(drivemodels);
            for(TaskModel d:taskModel){
                //Log.d("Check Date", d.getCompany() + ":" + new SimpleDateFormat("mm/dd/yy").format(d.getDate()));
            }
            taskadapter = new TaskDetailAdaptr(getActivity(), taskModel);
            task_Listvu.setAdapter(taskadapter);
            taskadapter.notifyDataSetChanged();
        }
    }

}
class TaskModelDateComparator implements Comparator<TaskModel> {

    @Override
    public int compare(TaskModel t1, TaskModel t2) {
        // Log.d("Check",Integer.valueOf(drv1.getDate().compareTo(drv2.getDate())).toString());
        if(t1.getTask_frm_date()==null||t2.getTask_frm_date()==null)
            return 0;
        return t1.getTask_frm_date().compareTo(t2.getTask_frm_date());
    }
}

