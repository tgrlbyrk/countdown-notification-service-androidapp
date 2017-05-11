package com.ygslys.tugrulbayrak.ygslysgerisayim;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.IBinder;
import android.support.v4.app.NotificationCompat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Timer;
import java.util.TimerTask;
/**
 * Created by tugrulbayrak on 9.05.2017.
 */
public class BootService extends Service {
    Context c = this;
    Date futureDate;
    Date currentDate;
    long diff;
    long days;
    long hours;
    long minutes;
    long seconds;
    public static final int notify = 1000;  //interval between two services(Here Service run every 1 seconds)
    public static final int notify2 = 10000;
    int count = 0;  //number of times service is display
    private Handler mHandler = new Handler();   //run on another Thread to avoid crash
    private Timer mTimer = null;    //timer handling
    private Handler handler;
    HashMap<Integer, String> sozBildirim;

    public void init() {
        sozBildirim.put(1, "Kelebek bir defa kanatlandı mı, bir daha asla tırtıl haline gelmez");
        sozBildirim.put(2, "Henüz hedeflediğim yerde olmayabilirim; ancak dün olduğum yerden daha yakınım");
        sozBildirim.put(3, "Mucize, enerjinizi korkularınıza değil, rüyalarınıza verdiğiniz zaman başlar");
        sozBildirim.put(4, "Yerinde duran, geriye gidiyor demektir… İleri, daima ileri!");
        sozBildirim.put(5, "Bir gün değil, her gün istersen olur");
    }


    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onCreate() {
        super.onCreate();
      /*  NotificationCompat.Builder builder =
                new NotificationCompat.Builder(this)
                        .setContentTitle("My Notification Title")
                        .setContentText("Something interesting happened");
        int NOTIFICATION_ID = 12345;
        Intent targetIntent = new Intent(this, MainActivity.class);
        PendingIntent contentIntent = PendingIntent.getActivity(this, 0, targetIntent, PendingIntent.FLAG_UPDATE_CURRENT);
        builder.setContentIntent(contentIntent);
        NotificationManager nManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        nManager.notify(NOTIFICATION_ID, builder.build());
*/
        mTimer = new Timer();   //recreate new
        mTimer.scheduleAtFixedRate(new TimeDisplay(), 0, notify);   //Schedule task

    }


    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    class TimeDisplay extends TimerTask {
        @Override
        public void run() {
            // run on another thread
            mHandler.post(new Runnable() {
                @Override
                public void run() {
                    try {
                        SimpleDateFormat dateFormat = new SimpleDateFormat(
                                "yyyy-MM-dd hh:mm");
                        futureDate = dateFormat.parse("2018-03-11 10:00");
                        currentDate = new Date();

                        diff = futureDate.getTime()
                                - currentDate.getTime();
                        days = diff / (24 * 60 * 60 * 1000);
                        diff -= days * (24 * 60 * 60 * 1000);
                        hours = diff / (60 * 60 * 1000);
                        diff -= hours * (60 * 60 * 1000);
                        minutes = diff / (60 * 1000);
                        diff -= minutes * (60 * 1000);
                        seconds = diff / 1000;
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                    Notification notify = new Notification.Builder(c)
                            .setContentTitle("YGS Geri Sayım")
                            .setColor(0xFF0000)
                            .setContentText(days + " Gün - " + hours + " Saat - "
                                    + minutes + " Dk - " + seconds + " Saniye ")
                            .setSmallIcon(R.drawable.clock)
                            .setAutoCancel(true).build();
                    //notify.defaults |= Notification.DEFAULT_SOUND;
                    // notify.defaults |= Notification.DEFAULT_VIBRATE;
                    NotificationManager notificationManager = (NotificationManager)
                            getSystemService(NOTIFICATION_SERVICE);
                    notificationManager.notify(0, notify);

                    //Toast.makeText(BootService.this, "seconds:" + seconds, Toast.LENGTH_SHORT).show();
                }
            });
        }
    }

}
