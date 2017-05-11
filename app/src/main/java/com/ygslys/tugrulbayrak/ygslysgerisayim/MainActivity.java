package com.ygslys.tugrulbayrak.ygslysgerisayim;

import java.text.SimpleDateFormat;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.Date;

public class MainActivity extends AppCompatActivity {
    final Context c = this;
    private TextView txtTimerDay, txtTimerHour, txtTimerMinute, txtTimerSecond;
    private TextView tvEvent;
    private Handler handler;
    private Runnable runnable;
    public Button btnBildirim;
    public Button btnBildirimKaldir;
    Date futureDate;
    Date currentDate;
    long diff;
    long days;
    long hours;
    long minutes;
    long seconds;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();
        countDownStart();
    }

    public void init() {
        txtTimerDay = (TextView) findViewById(R.id.txtTimerDay);
        txtTimerHour = (TextView) findViewById(R.id.txtTimerHour);
        txtTimerMinute = (TextView) findViewById(R.id.txtTimerMinute);
        txtTimerSecond = (TextView) findViewById(R.id.txtTimerSecond);
        tvEvent = (TextView) findViewById(R.id.tvhappyevent);
        btnBildirim = (Button) findViewById(R.id.btnBildirim);
        btnBildirimKaldir = (Button) findViewById(R.id.btnBildirimKaldir);
    }

    public void countDownStart() {
        handler = new Handler();
        runnable = new Runnable() {
            @Override
            public void run() {
                handler.postDelayed(this, 1000);
                try {
                    SimpleDateFormat dateFormat = new SimpleDateFormat(
                            "yyyy-MM-dd hh:mm");

                     futureDate = dateFormat.parse("2018-03-11 10:00");
                     currentDate = new Date();
                    if (!currentDate.after(futureDate)) {
                         diff = futureDate.getTime()
                                - currentDate.getTime();
                         days = diff / (24 * 60 * 60 * 1000);
                        diff -= days * (24 * 60 * 60 * 1000);
                        hours = diff / (60 * 60 * 1000);
                        diff -= hours * (60 * 60 * 1000);
                        minutes = diff / (60 * 1000);
                        diff -= minutes * (60 * 1000);
                        seconds = diff / 1000;
                        txtTimerDay.setText("" + String.format("%02d", days));
                        txtTimerHour.setText("" + String.format("%02d", hours));
                        txtTimerMinute.setText(""
                                + String.format("%02d", minutes));
                        txtTimerSecond.setText(""
                                + String.format("%02d", seconds));

                      /*  NotificationCompat.Builder builder =
                                new NotificationCompat.Builder(c)
                                        .setSmallIcon(R.drawable.ic_launcher)
                                        .setContentTitle("Notifications Example")
                                        .setContentText(Long.toString(days) + " - "+ Long.toString(hours) +" - "+ Long.toString(minutes) + " - " +
                                         Long.toString(seconds));
                        Intent notificationIntent = new Intent(c, MainActivity.class);
                        PendingIntent contentIntent = PendingIntent.getActivity(c, 0, notificationIntent,
                                PendingIntent.FLAG_UPDATE_CURRENT);
                        builder.setContentIntent(contentIntent);
                        // Add as notification
                        NotificationManager manager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
                        manager.notify(0, builder.build());
*/

                    } else {
                        tvEvent.setVisibility(View.VISIBLE);
                        tvEvent.setText("The event started!");
                        textViewGone();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        };
        handler.postDelayed(runnable, 1 * 1000);
    }

    public void textViewGone() {
        findViewById(R.id.LinearLayout10).setVisibility(View.GONE);
        findViewById(R.id.LinearLayout11).setVisibility(View.GONE);
        findViewById(R.id.LinearLayout12).setVisibility(View.GONE);
        findViewById(R.id.LinearLayout13).setVisibility(View.GONE);
    }

    public  void bildirimClick(View v) {
        /*final Intent intent = new Intent("borad.cast");
        sendBroadcast(intent);*/
     //   startService(new Intent(this,BootService.class));
        startService(new Intent(this,BootService.class));
        finish();
    }

}
