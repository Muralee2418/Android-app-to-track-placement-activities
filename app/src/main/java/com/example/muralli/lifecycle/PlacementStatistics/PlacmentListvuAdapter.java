package com.example.muralli.lifecycle.PlacementStatistics;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.muralli.lifecycle.GroupByCompany;
import com.example.muralli.lifecycle.R;
import com.example.muralli.lifecycle.StudentDetails.StudentAllDetails;

import java.util.List;

/**
 * Created by Muralli on 08-03-2018.
 */
public class PlacmentListvuAdapter extends ArrayAdapter{
    private Activity context;
    List<GroupByCompany> gbyCompany;
    LayoutInflater inflater;
    String batch;


    public PlacmentListvuAdapter(Activity context, List<GroupByCompany> gbyCompany,String batch) {
        super(context,R.layout.companydetails,gbyCompany);
        this.context = context;
        this.gbyCompany = gbyCompany;
        this.batch=batch;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        CompanyViewHolder compvuholder=new CompanyViewHolder();
        GroupByCompany gbc=gbyCompany.get(position);
        if(gbc.getCompanyName().toString().trim()=="0")
        {return null;
        }

        if (convertView == null) {

        inflater = context.getLayoutInflater();
        convertView = inflater.inflate(R.layout.companydetails, null, true);
        compvuholder.compName = (TextView) convertView.findViewById(R.id.companycardview);
        compvuholder.compCount= (TextView) convertView.findViewById(R.id.countcardview);
            compvuholder.salary= (TextView) convertView.findViewById(R.id.salarycardview);

            convertView.setTag(compvuholder);
    }else{
            compvuholder=(CompanyViewHolder)convertView.getTag();
        }

        String comp_name=gbc.getCompanyName().toString().trim();
        if(comp_name.equals("")){
            compvuholder.compName.setText(gbc.getCompanyName().toString().trim().toUpperCase());
            List<StudentAllDetails> s_list=gbc.studentPlaced();
            compvuholder.compCount.setText(s_list.get(1).getName().toString().trim()+s_list.get(2).getName().toString().trim());
            return null;
        }
        else if (comp_name.equals("0")){
            compvuholder.compName.setText("Yet to be placed");
            compvuholder.compCount.setText(Integer.valueOf(gbc.studentCount()).toString().trim());
            compvuholder.salary.setText("NA");
            return convertView;
        }
        else{
        compvuholder.compName.setText(gbc.getCompanyName().toString().trim().toUpperCase());
        compvuholder.compCount.setText(Integer.valueOf(gbc.studentCount()).toString().trim());
            if(batch.equalsIgnoreCase("2018")){compvuholder.salary.setText(Integer.valueOf(gbc.studentPlaced().get(0).getIntern_stiphend()).toString().trim());}
            else{
            compvuholder.salary.setText(Integer.valueOf(gbc.getCtc()).toString().trim());}
        return convertView;}

    }
    private  static class CompanyViewHolder{
        public TextView compName;
        public TextView compCount;
        public TextView salary;

    }

    @Override
    public int getCount() {
        return super.getCount();
    }
}
