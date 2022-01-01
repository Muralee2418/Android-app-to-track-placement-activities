package com.example.muralli.lifecycle.StudentDetails;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Filter;
import android.widget.TextView;

import com.example.muralli.lifecycle.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Muralli on 20-02-2018.
 */
public class StudentAdapter extends ArrayAdapter {
    private final Activity context;
    List<StudentAllDetails> studentlist;
    List<StudentAllDetails> studentfilter;

    public StudentAdapter(Activity context, List<StudentAllDetails> s_list) {
        super(context, R.layout.student_list_view, s_list);

        this.context = context;
        this.studentlist = s_list;
        studentfilter=s_list;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        LayoutInflater inflater=context.getLayoutInflater();
        View rowView = inflater.inflate(R.layout.student_list_view, null, true);
        TextView nameField = (TextView) rowView.findViewById(R.id.list_name);
        TextView rollnoField = (TextView) rowView.findViewById(R.id.listrollno);
        TextView genderField = (TextView) rowView.findViewById(R.id.listgender);
try {
    StudentAllDetails studentdetail = studentlist.get(position);
    //Log.d("check inside getView", studentdetail.getName().toString());
    nameField.setText(studentdetail.getName().toString());
    rollnoField.setText(studentdetail.getRollno().toString());
    genderField.setText(studentdetail.getGender().toString());
}
catch(Exception e){
    e.printStackTrace();
}
        return rowView;
    }

    @Override
    public Filter getFilter() {
        return new Filter(){

            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                String searchkey= constraint.toString();
                FilterResults filterResults = new FilterResults();
                if(searchkey.isEmpty()){

                    filterResults.count=studentfilter.size();
                    filterResults.values=studentfilter;
                }
                else
                {
                    ArrayList<StudentAllDetails> filteredList = new ArrayList<>();

                    for (StudentAllDetails stud: studentfilter) {

                        if ( stud.getName().toLowerCase().contains(searchkey)) {

                            filteredList.add(stud);
                        }
                    }


                    filterResults.count=filteredList.size();
                    filterResults.values=filteredList;
                }




                return filterResults;

            }


            @Override
            protected void publishResults(CharSequence constraint, FilterResults filterResults) {
                studentlist= (ArrayList<StudentAllDetails>) filterResults.values;
                notifyDataSetChanged();

            }
        };


    }
}
