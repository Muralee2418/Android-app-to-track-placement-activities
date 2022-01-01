package com.example.muralli.lifecycle.Taskstodo;

//import androidx.core.app.Fragment;
import androidx.fragment.app.Fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.muralli.lifecycle.R;

/**
 * Created by Muralli on 16-03-2018.
 */
public class Tab3 extends Fragment {
    public Tab3() {
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView=inflater.inflate(R.layout.tab2,container,false);
        return rootView;

    }
}
