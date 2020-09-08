package com.example.studycards.ui;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.example.studycards.R;
import com.firebase.jobdispatcher.FirebaseJobDispatcher;
import com.firebase.jobdispatcher.GooglePlayDriver;
import com.firebase.jobdispatcher.Job;
import com.firebase.jobdispatcher.Lifetime;
import com.firebase.jobdispatcher.RetryStrategy;
import com.firebase.jobdispatcher.Trigger;

import java.util.concurrent.TimeUnit;


public class BaseActivity extends AppCompatActivity {
    protected SharedPreferences preferences;
    protected String tag = "study_mode";
    protected final int startInterval = (int) TimeUnit.MINUTES.toSeconds(0);
    protected final int endInterval = (int) TimeUnit.MINUTES.toSeconds(5);
    protected FirebaseJobDispatcher jobDispatcher;
    protected String CHANNEL_ID = "study_mode";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        createNotificationChannel();

        preferences = getApplicationContext().getSharedPreferences("study_mode",Context.MODE_PRIVATE);
        jobDispatcher = new FirebaseJobDispatcher(new GooglePlayDriver(getApplicationContext()));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.study_mode,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId() == R.id.studyModeIcon){
            AlertDialog alertDialog = buildDialog().create();
            alertDialog.show();
        }
        return super.onOptionsItemSelected(item);
    }

    public AlertDialog.Builder buildDialog(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        builder.setMessage(R.string.study_dialog_description)
                .setTitle(R.string.content_title)
                .setIcon(R.drawable.ic_study)
                .setPositiveButton(R.string.study_dialog_positive, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        initiateWork();
                        Toast.makeText(getApplicationContext(),R.string.study_snackbar_start,Toast.LENGTH_SHORT).show();
                    }
                })
                .setNegativeButton(R.string.study_dialog_negative, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        if(!preferences.getBoolean("state",false)){
                            dialog.dismiss();
                        }else{
                            stopWork();
                            Toast.makeText(getApplicationContext(),R.string.study_snackbar_cancel,Toast.LENGTH_SHORT).show();
                        }

                    }
                })
        .setNeutralButton(R.string.study_dialog_dismiss, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        return builder;
    }

    public void initiateWork(){
        SharedPreferences.Editor editor = preferences.edit();
        editor.putBoolean("state",true);
        editor.apply();
        Job job = jobDispatcher.newJobBuilder()
                .setService(StudyModeService.class)
                .setTag(tag)
                .setLifetime(Lifetime.FOREVER)
                .setRetryStrategy(RetryStrategy.DEFAULT_LINEAR)
                .setRecurring(true)
                .setReplaceCurrent(false)
                .setTrigger(Trigger.executionWindow(0,60))
                .build();

        jobDispatcher.schedule(job);
    }

    public void stopWork(){
        SharedPreferences.Editor editor = preferences.edit();
        editor.putBoolean("state",false);
        editor.apply();
        jobDispatcher.cancel(tag);
    }

    private void createNotificationChannel(){
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            CharSequence name = getString(R.string.content_title);
            String description = getString(R.string.content_text);
            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel channel = new NotificationChannel(CHANNEL_ID, name, importance);
            channel.setDescription(description);
            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }
    }

}
