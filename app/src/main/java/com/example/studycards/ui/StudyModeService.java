package com.example.studycards.ui;

import android.app.PendingIntent;
import com.firebase.jobdispatcher.JobParameters;
import com.firebase.jobdispatcher.JobService;
import android.content.Intent;

import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import com.example.studycards.R;

public class StudyModeService extends JobService {
    protected int notificationId = 200;
    protected String CHANNEL_ID = "study_mode";

    @Override
    public boolean onStartJob(JobParameters params) {
        showNotification();

        return false;
    }

    @Override
    public boolean onStopJob(JobParameters params) {
        return false;
    }

    public void showNotification() {
        NotificationManagerCompat notificationManagerCompat = NotificationManagerCompat.from(getApplicationContext());

        notificationManagerCompat.notify(notificationId, notificationBuilder().build());
    }

    public NotificationCompat.Builder notificationBuilder() {
        String textTitle = getString(R.string.content_title);
        String textContent = getString(R.string.content_text);

        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        PendingIntent pendingIntent = PendingIntent.getActivity(getApplicationContext(), 0, intent, 0);

        NotificationCompat.Builder builder = new NotificationCompat.Builder(getApplicationContext(), CHANNEL_ID)
                .setSmallIcon(R.drawable.ic_book)
                .setContentTitle(textTitle)
                .setContentText(textContent)
                .setStyle(new NotificationCompat.BigTextStyle().bigText(textContent))
                .setContentIntent(pendingIntent)
                .setAutoCancel(true);

        return builder;
    }



}