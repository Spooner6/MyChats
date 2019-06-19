package com.my.mychats.DeviceToDevice;

import android.content.Context;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;

import com.my.mychats.R;

public class NotificationHelper {

    private static final String CHANNEL_ID = "myNotifications";
    private static final String CHANNEL_NAME = "myChatsNotifications";
    private static final String CHANNEL_DESC = "notificationForMyChatsApp";


    public static void displayNotification(Context context, String title, String body) {
        NotificationCompat.Builder builder =
                new NotificationCompat.Builder(context, CHANNEL_ID);
        builder.setContentTitle(title)
                .setSmallIcon(R.drawable.ic_menu_send)
                .setContentText(body)
                .setPriority(NotificationCompat.PRIORITY_DEFAULT);


        NotificationManagerCompat notificationManagerCompat = NotificationManagerCompat.from(context);
        notificationManagerCompat.notify(1, builder.build() );
    }
}
