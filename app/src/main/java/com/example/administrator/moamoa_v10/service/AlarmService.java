package com.example.administrator.moamoa_v10.service;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;
import android.widget.Toast;

import com.example.administrator.moamoa_v10.Config.DataConfig;
import com.example.administrator.moamoa_v10.MainActivity;
import com.example.administrator.moamoa_v10.R;
import com.facebook.AccessToken;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.HttpMethod;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by Administrator on 2016-12-23.
 */

public class AlarmService extends Service{

    private final String LOG_TAG = AlarmService.class.getSimpleName();
    private NotificationManager m_Notifi_Mgr;
    private AlarmServiceThread m_thread;
    private Notification m_Notifi;
    private GraphRequest request;
    private long latest_post_id = 1;
    private boolean is_initdata = false;

    @Nullable
    @Override
    public IBinder onBind(Intent intent)
    {
        return null;
    }

    @Override
    public void onCreate()
    {
        super.onCreate();

        Log.i("KEYWORD", "알람 서비스 시작 !");

        request = new GraphRequest(AccessToken.getCurrentAccessToken(), "/me/feed", null, HttpMethod.GET,
                new GraphRequest.Callback()
                {
                    public void onCompleted(GraphResponse response)
                    {
                            try
                            {
                                if(AccessToken.getCurrentAccessToken() == null)
                                    return;

                              //  if(response.getJSONObject().getJSONArray("data") == null)
                              //      return;

                                JSONArray jarray = response.getJSONObject().getJSONArray("data");


                                //Log.i(LOG_TAG, AccessToken.getCurrentAccessToken().toString());
                                if( !is_initdata )
                                {
                                    is_initdata = true;
                                    String id = jarray.getJSONObject(0).getString("id");
                                    id = (id.split("_"))[1];
                                    latest_post_id = Long.parseLong(id);
                                    Log.i("KEYWORD", "초기 글 ID 초기화");
                                }


                                for (int i = 0; i < jarray.length(); i++)
                                {
                                    JSONObject responseObject = jarray.getJSONObject(i);
                                    String message = responseObject.getString("message");
                                    long current_post_id = Long.parseLong(responseObject.getString("id").split("_")[1]);

                                    Log.i("KEYWORD", "현재 글 ID : " + current_post_id);
                                    Log.i("KEYWORD", "최근 글 ID : " + latest_post_id);

                                    for (int j = 0; j < DataConfig.KeywordList.size(); j++)
                                    {
                                        //Log.i("KEYWORD", DataConfig.KeywordList.get(j));
                                        if (message.contains(DataConfig.KeywordList.get(j)) && current_post_id > latest_post_id)
                                        {
                                            Log.i("KEYWORD", "키워드 일치 ! !");
                                            triggerNotification(DataConfig.KeywordList.get(j));
                                        }
                                        else
                                        {
                                            String id = jarray.getJSONObject(0).getString("id");
                                            id = (id.split("_"))[1];
                                            latest_post_id = Long.parseLong(id);
                                        }
                                    }

                                }
                            }
                            catch (JSONException e)
                            {
                                e.printStackTrace();
                            }
                    }

                }
        );


    }

    private void triggerNotification(String _keyword)
    {
        Intent intent = new Intent(AlarmService.this, MainActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(AlarmService.this, 0, intent,PendingIntent.FLAG_UPDATE_CURRENT);

        m_Notifi = new Notification.Builder(getApplicationContext())
                .setContentTitle("새로운 글이 감지되었습니다")
                .setContentText("키워드 명 :" + _keyword)
                .setSmallIcon(R.drawable.ic_menu_facebook)
                .setTicker("노티피케이션")
                .setContentIntent(pendingIntent)
                .build();

        m_Notifi.defaults = Notification.DEFAULT_SOUND;
        m_Notifi.flags = Notification.FLAG_ONLY_ALERT_ONCE;
        m_Notifi.flags = Notification.FLAG_AUTO_CANCEL;

        m_Notifi_Mgr.notify(777, m_Notifi);
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


            Bundle parameters = new Bundle();
            parameters.putString("fields", "message");
            request.setParameters(parameters);
            request.executeAsync();


            //토스트 띄우기
            //Toast.makeText(AlarmService.this, "푸쉬 알림", Toast.LENGTH_LONG).show();
        }
    };
}
