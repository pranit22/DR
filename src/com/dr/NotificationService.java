package com.dr;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.IBinder;
import android.widget.Toast;

/**
 * Created by Pranit on 11/23/13.
 */
public class NotificationService extends Service {

    @Override

    public IBinder onBind(Intent intent) {
        return null;
    }


    @Override
    public void onStart(Intent intent, int startId) {
        super.onStart(intent, startId);

        Uri soundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);

        Intent myIntent = new Intent(this, InterviewDetailsActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, myIntent, 0);

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

