package com.my.mychats.DeviceToDevice;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.os.Build;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import com.my.mychats.R;

public class Test extends AppCompatActivity {

    private static final String CHANNEL_ID = "myNotifications";
    private static final String CHANNEL_NAME = "myChatsNotifications";
    private static final String CHANNEL_DESC = "notificationForMyChatsApp";


    TextView textView;
    Button notifyButton;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            NotificationChannel notificationChannel = new NotificationChannel(CHANNEL_ID, CHANNEL_NAME, NotificationManager.IMPORTANCE_DEFAULT);
            notificationChannel.setDescription(CHANNEL_DESC);
            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(notificationChannel);

        }

        textView = findViewById(R.id.notification_text_view);


    }


    private void displayNotification() {
        NotificationCompat.Builder builder =
                new NotificationCompat.Builder(this, CHANNEL_ID);
        builder.setContentTitle("Notification In PROGERESSSS")
                .setSmallIcon(R.drawable.ic_menu_send)
                .setContentText("notifikace :D")
                .setPriority(NotificationCompat.PRIORITY_DEFAULT);


        NotificationManagerCompat notificationManagerCompat = NotificationManagerCompat.from(this);
        notificationManagerCompat.notify(1, builder.build() );
    }

}
