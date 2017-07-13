package com.example.dmrf.filemanager;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

/**
 * Created by DMRF on 2017/7/13.
 */

public class SearchBroadCast extends BroadcastReceiver {
    public static String mServiceKeyWord = "";
    public static String mServiceSerchPath = "";

    @Override
    public void onReceive(Context context, Intent intent) {
//获取Intent
        String mAction = intent.getAction();
        if (MainActivity.KEYWOED_BROADCAST.equals(mAction)) {
            //获得intent传过来的信息
            mServiceKeyWord = intent.getStringExtra("keywords");
            mServiceSerchPath = intent.getStringExtra("searchpath");
        }
    }
}
