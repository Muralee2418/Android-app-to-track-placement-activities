package com.example.muralli.lifecycle.User;

import android.app.Activity;
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

import java.util.ArrayList;
import java.util.List;



/**
 * Created by Muralli on 08-06-2018.
 */
public class UserListAdapter extends ArrayAdapter {

    List<UserDetail> userObjs=new ArrayList<>();
    private Activity context;
    DatabaseReference userDb;
    ;


    public UserListAdapter(Activity context, List<UserDetail> objects) {
        super(context, R.layout.userlist, objects);
       // Log.d("Check","Inside userlistadapter");
        this.context =context;
        this.userObjs=objects;
    }

    @Override
    public Object getItem(int position) {
        notifyDataSetChanged();
        return super.getItem(position);
    }

    @Override
    public View getView(final int position, View convertview, ViewGroup parent) {
        notifyDataSetChanged();
        DriveViewHolder dh=new DriveViewHolder();
        final UserDetail usr=userObjs.get(position);
        if(convertview==null) {
            LayoutInflater inflater = context.getLayoutInflater();
            convertview = inflater.inflate(R.layout.userlist, null, true);
            dh.userName = (TextView) convertview.findViewById(R.id.listvuUsrname);
            //dh.iapStatus = (Switch) convertview.findViewById(R.id.iapsts);
            dh.profpic=(ImageView)convertview.findViewById(R.id.profpic);
            dh.iapBtn=(ImageView)convertview.findViewById(R.id.iapid);
            convertview.setTag(dh);
           // Log.d("Check", "Inside getview");
        }
        else
        {
            dh=(DriveViewHolder)convertview.getTag();
           // Log.d("Check", "Inside else");
        }


        userDb= FirebaseDatabase.getInstance().getReference("User");
        userDb.keepSynced(true);


        try {
            //UserDetail userinfo = userObjs.get(position);
            //Log.d("check inside ge00000000tView", studentdetail.getName().toString());
            dh.userName.setText(usr.getName().toString());
           Log.d("Check", "iapstatus: " + usr.getRole().toString() + " " + usr.getName());
            if(usr.getRole().equals("iap")){
                dh.iapOn=1;
                dh.iapBtn.setImageResource(R.mipmap.iaponnew);
                //Log.d("Check", "Inside try checked");
            }
            else{
                dh.iapOn=0;
                dh.iapBtn.setImageResource(R.mipmap.iapofnew);
                //Log.d("Check", "Inside try notchecked");
            }

                   }
        catch(Exception e){
            e.printStackTrace();
        }
        final DriveViewHolder finalDh = dh;
        dh.iapBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(finalDh.iapOn==1){
                    UserDetail userinfo= userObjs.get(position);
                    userinfo.setRole("user");
                    finalDh.iapBtn.setImageResource(R.mipmap.iapofnew);
                    Log.d("Check", "position" + Integer.valueOf(position).toString());
                    Log.d("Check", "switch off"+finalDh.userName.getText().toString());
                    finalDh.iapOn=0;
                    userDb.child(userinfo.getName()).setValue(userinfo);

                }
                else
                {
                    UserDetail userinfo= userObjs.get(position);
                    userinfo.setRole("iap");
                    finalDh.iapBtn.setImageResource(R.mipmap.iaponnew);
                    Log.d("Check", "switch on" + finalDh.userName.getText().toString());
                    finalDh.iapOn=1;
                    userDb.child(userinfo.getName()).setValue(userinfo);

                }

            }
        });

     /*  dh.iapStatus.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               if(dh.iapStatus.isChecked()){
                   UserDetail userinfo= userObjs.get(position);
                   Log.d("Check", "switch on");
                   userinfo.setRole("iap");
                   userDb.child(userinfo.getName()).setValue(userinfo);

               }
               else{
                   UserDetail userinfo= userObjs.get(position);
                   userinfo.setRole("user");
                   Log.d("Check", "switch off");
                   userDb.child(userinfo.getName()).setValue(userinfo);

               }
           }
       });*/

      /*  dh.iapStatus.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    UserDetail userinfo= userObjs.get(position);
                    Log.d("Check", "switch on");
                    userinfo.setRole("iap");
                    userDb.child(userinfo.getName()).setValue(userinfo);

                }
                else{
                    UserDetail userinfo= userObjs.get(position);
                    userinfo.setRole("user");
                    Log.d("Check", "switch off");
                    userDb.child(userinfo.getName()).setValue(userinfo);
                }
            }
        });*/






       // return super.getView(position, convertView, parent);
        return convertview;
    }

    public static class DriveViewHolder{
        public TextView userName;
        //public Switch iapStatus;
        public ImageView profpic;
        public ImageView iapBtn;
        public int iapOn;



    }
}
