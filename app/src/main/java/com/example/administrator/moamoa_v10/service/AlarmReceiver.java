package com.example.administrator.moamoa_v10.service;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

/**
 * Created by Administrator on 2016-12-28.
 */

public class AlarmReceiver extends BroadcastReceiver{

    public AlarmReceiver() {
        super();
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        if (intent.getAction().equals(Intent.ACTION_BOOT_COMPLETED)) {
            Intent i = new Intent(context, AlarmService.class);
            context.startService(i);
        }
    }
}
