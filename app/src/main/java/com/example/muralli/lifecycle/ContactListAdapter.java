package com.example.muralli.lifecycle;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
//import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.List;

/**
 * Created by Muralli on 23-01-2018.
 */
public class ContactListAdapter extends ArrayAdapter {
    private final Activity context;
    List<IndustryContact> ind_contact;

    ImageView callButton, editButton, delButton;
    DatabaseReference delContacts;
    Intent callIntent;

    public ContactListAdapter(Activity context, List<IndustryContact> cont_list) {
        super(context, R.layout.listview_contact, cont_list);

        this.context = context;
        this.ind_contact = cont_list;
    }

    public View getView(int position, final View view, ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();
        View rowView = inflater.inflate(R.layout.listview_contact, null, true);
        TextView nameField = (TextView) rowView.findViewById(R.id.listname);
        TextView mobNoField = (TextView) rowView.findViewById(R.id.listmob);
        TextView comField = (TextView) rowView.findViewById(R.id.listcom);
        callButton = (ImageView) rowView.findViewById(R.id.callbutton);
        editButton = (ImageView) rowView.findViewById(R.id.editbutton);
        delButton = (ImageView) rowView.findViewById(R.id.delbutton);


        delContacts = FirebaseDatabase.getInstance().getReference("IndustryContacts");
        delContacts.keepSynced(true);


        final IndustryContact cont = ind_contact.get(position);


        nameField.setText(cont.getExpert_Name());
        mobNoField.setText(cont.getExpert_Mobile());
        comField.setText(cont.getExpert_Company());

        callButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //Log.d("Check", "inside call button listener");
                callIntent = new Intent(Intent.ACTION_DIAL);
                callIntent.setData(Uri.parse("tel:"+cont.getExpert_Mobile().toString()));
                /*if (ActivityCompat.checkSelfPermission(getContext(), android.Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                    context.startActivity(callIntent);
                }*/


                context.startActivity(callIntent);
            }
        });


        editButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent editIntent = new Intent(context,EditContact.class);
               editIntent.putExtra("ename",cont.getExpert_Name().toString());
                editIntent.putExtra("ecom", cont.getExpert_Company().toString());
                editIntent.putExtra("emob", cont.getExpert_Mobile().toString());
                context.startActivity(editIntent);

            }
        });
        delButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                    final AlertDialog.Builder alertDialog = new AlertDialog.Builder(getContext());

                    alertDialog.setTitle("Confirm");

                    alertDialog.setMessage("Are you sure to Delete");


                    alertDialog.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            delContacts.child(cont.getExpert_Name().toString()).removeValue();
                        }
                    });
                    alertDialog.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.cancel();
                        }
                    });


                    alertDialog.show();


            }
        });

        return rowView;
    }

    }


