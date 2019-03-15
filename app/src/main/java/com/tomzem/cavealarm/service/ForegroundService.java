package com.tomzem.cavealarm.service;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.os.IBinder;
import android.support.v4.app.NotificationCompat;
import android.widget.RemoteViews;

import com.search.baselibrary.log.LogUtils;
import com.search.baselibrary.utils.ToastUtils;
import com.tomzem.cavealarm.R;
import com.tomzem.cavealarm.ui.MainActivity;

/**
 * @author Tomze
 * @time 2019年03月15日 22:31
 * @desc
 */
public class ForegroundService extends Service {


    @Override
    public IBinder onBind(Intent intent) {
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public void onCreate() {
        super.onCreate();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        NotificationCompat.Builder builder = new NotificationCompat.Builder(this);
        builder.setLargeIcon(BitmapFactory.decodeResource(getResources(), R.mipmap.ic_launcher));
        builder.setContentTitle(getResources().getString(R.string.app_name))
                .setSmallIcon(R.mipmap.ic_launcher)
                .setLargeIcon(BitmapFactory.decodeResource(getResources(), R.mipmap.ic_launcher));

        RemoteViews remoteViews = new RemoteViews("com.tomzem.cavealarm", R.layout.layout_notification);
        builder.setContent(remoteViews);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationManager notificationManager = (NotificationManager)getSystemService(NOTIFICATION_SERVICE);
            NotificationChannel channel = new NotificationChannel("3", "3", NotificationManager.IMPORTANCE_MIN);
            notificationManager.createNotificationChannel(channel);
            builder.setChannelId("3");
        }
        startForeground(1, builder.build());

        LogUtils.i("onStartCommand:  启动成功");
        ToastUtils.show("onStartCommand:  启动成功");
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onDestroy() {
        LogUtils.i("onStartCommand:  服务死了");
        ToastUtils.show("onStartCommand:  服务死了");
        stopForeground(true);// 停止前台服务--参数：表示是否移除之前的通知
        super.onDestroy();
    }
}
