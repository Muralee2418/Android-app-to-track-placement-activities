package com.example.muralli.lifecycle;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.telephony.SmsManager;
import android.util.Log;

/**
 * Created by Muralli on 29-05-2018.
 */

    public class MyAlaram extends BroadcastReceiver {
        int i=0;
        @Override
        public void onReceive(Context context, Intent intent) {
            //i++;
            Log.d("Check","Alarm Raised");
            SmsManager smsManager = SmsManager.getDefault();
            smsManager.sendTextMessage("9943549267", null, "hello "+Integer.valueOf(i).toString(), null, null);
        }
    }

