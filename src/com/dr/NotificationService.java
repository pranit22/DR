package com.dr;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.IBinder;

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
        myIntent.putExtra("interview", intent.getSerializableExtra("interview"));
        myIntent.putExtra("landingActivity", InterviewsListActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, myIntent, 0);

        Notification mNotification = new Notification.Builder(this)
                .setContentTitle("Upcoming Interview!")
                .setContentText(intent.getStringExtra("message"))
                .setSmallIcon(R.drawable.interviews)
                .setContentIntent(pendingIntent)
                .setSound(soundUri)
                .build();

        NotificationManager notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        notificationManager.notify(0, mNotification);
    }
}

