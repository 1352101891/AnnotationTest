package com.example.myapplication.broadcast;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

public class CusBroadcast extends BroadcastReceiver {


    @Override
    public void onReceive(Context context, Intent intent) {
        Log.e("CusBroadcast","广播接收器");
    }
}
