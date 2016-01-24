package com.MaNoS.DarcyRibeiro.Tech.Leonardo;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

/**
 * Created by Rubens on 17/08/2015.
 */

public class BootReceiver extends BroadcastReceiver {
    AlarmManager am;

    @Override
    public void onReceive(Context context, Intent intent) {
        if (intent.getAction().equals("android.intent.action.BOOT_COMPLETED")) {
            // Set the alarm here.
            PendingIntent pi = PendingIntent.getService(context, 0, new Intent(context, SetNotification.class), 0);
            am.set(AlarmManager.ELAPSED_REALTIME_WAKEUP, 60 * 1000, pi);

        }
    }
}

