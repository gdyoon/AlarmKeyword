package com.example.administrator.moamoa_v10.service;

import android.os.Handler;

/**
 * Created by Administrator on 2016-12-23.
 */

public class AlarmServiceThread extends Thread{
    Handler handler;
    boolean isRun = true;

    public AlarmServiceThread(Handler handler){
        this.handler = handler;
    }

    public void stopForever(){
        synchronized (this) {
            this.isRun = false;
        }
    }

    public void run(){
        //반복적으로 수행할 작업을 한다.
        while(isRun){
            handler.sendEmptyMessage(0);//쓰레드에 있는 핸들러에게 메세지를 보냄
            try{
                Thread.sleep(20000); //20초씩 쉰다.
            }catch (Exception e) {}
        }
    }
}
