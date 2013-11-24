package com.dr;

import android.app.Activity;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Bundle;

public class InterviewsListActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_interviews_list);

        Uri soundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);

        Intent intent = new Intent(this, InterviewDetailsActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, 0);

        Notification mNotification = new Notification.Builder(this)
                .setContentTitle("Upcoming Interview!")
                .setContentText("With Amazon at 4:50pm")
                .setSmallIcon(R.drawable.interviews)
                .setContentIntent(pendingIntent)
                .setSound(soundUri)
                .addAction(R.drawable.interviews, "View", pendingIntent)
                .addAction(0, "Remind", pendingIntent)
                .build();

        NotificationManager notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        notificationManager.notify(0, mNotification);
    }


}