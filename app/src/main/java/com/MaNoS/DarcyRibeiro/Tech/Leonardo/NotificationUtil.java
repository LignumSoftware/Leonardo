package com.MaNoS.DarcyRibeiro.Tech.Leonardo;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Notification;
import android.app.Notification.Builder;
import android.app.Notification.InboxStyle;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Build;

/**
 * Created by Mateus on 29/11/2015.
 */
public class NotificationUtil {
    @SuppressWarnings("deprecation")
    @SuppressLint("NewApi")
    public static void create(Context context, CharSequence tickerText, CharSequence title, CharSequence message, int icon, int id, Intent intent, String[] lines) {
        PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, intent, 0);
        Notification n = null;
        int apiLevel = Build.VERSION.SDK_INT;
        if (apiLevel >= 11) {
            Builder builder = new Builder(context)
                    .setContentTitle(tickerText)

                    .setSmallIcon(icon)
                    .setContentIntent(pendingIntent);
            if (apiLevel >= 17) {
                InboxStyle style = new InboxStyle();
                style.setBigContentTitle(tickerText);
                for (String line : lines) {
                    style.addLine(line);
                }
                builder.setStyle(style);
                n = builder.build();

            } else {
                n = builder.setContentText(message).getNotification();
            }
        } else {
            n = new Notification(icon, tickerText, System.currentTimeMillis());
            n.setLatestEventInfo(context, title, message, pendingIntent);
        }
        NotificationManager nm = (NotificationManager) context.getSystemService(Activity.NOTIFICATION_SERVICE);
        nm.notify(id, n);
    }

    public static void cancell(Context context, int id) {
        NotificationManager nm = (NotificationManager) context.getSystemService(Activity.NOTIFICATION_SERVICE);
        nm.cancel(id);
    }


}
