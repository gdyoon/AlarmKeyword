package com.example.administrator.moamoa_v10.service;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.widget.Toast;

import com.example.administrator.moamoa_v10.MainActivity;
import com.example.administrator.moamoa_v10.R;

/**
 * Created by Administrator on 2016-12-23.
 */

public class AlarmService extends Service{

    private NotificationManager m_Notifi_Mgr;
    private AlarmServiceThread m_thread;
    private Notification m_Notifi;

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        m_Notifi_Mgr = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        myServiceHandler handler = new myServiceHandler();
        m_thread = new AlarmServiceThread(handler);
        m_thread.start();
        return START_STICKY;
    }

    //서비스가 종료될 때 할 작업
    public void onDestroy() {
        m_thread.stopForever();
        m_thread = null;
    }

    class myServiceHandler extends Handler {
        @Override
        public void handleMessage(android.os.Message msg) {
            Intent intent = new Intent(AlarmService.this, MainActivity.class);
            PendingIntent pendingIntent = PendingIntent.getActivity(AlarmService.this, 0, intent,PendingIntent.FLAG_UPDATE_CURRENT);

            m_Notifi = new Notification.Builder(getApplicationContext())
                    .setContentTitle("Content Title")
                    .setContentText("Content Text")
                    .setSmallIcon(R.drawable.ic_menu_facebook)
                    .setTicker("알림!!!")
                    .setContentIntent(pendingIntent)
                    .build();

            //소리추가
            m_Notifi.defaults = Notification.DEFAULT_SOUND;

            //알림 소리를 한번만 내도록
            m_Notifi.flags = Notification.FLAG_ONLY_ALERT_ONCE;

            //확인하면 자동으로 알림이 제거 되도록
            m_Notifi.flags = Notification.FLAG_AUTO_CANCEL;


            m_Notifi_Mgr.notify( 777 , m_Notifi);

            //토스트 띄우기
            Toast.makeText(AlarmService.this, "푸쉬 알림", Toast.LENGTH_LONG).show();
        }
    };
}
