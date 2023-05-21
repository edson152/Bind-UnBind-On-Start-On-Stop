package cn.edu.zjnu.cs.serviceexample;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.util.Log;

public class MyService extends Service {  // to implement a seconds-counting function -- time-consuming job


    private static final String TAG="MyService";
    private int seconds=0;
    private boolean isWorking= true;

    //a Binder class to return seconds
    public class MyBinder extends Binder{
        //to return seconds
        public int getSeconds(){
            return seconds;
        }
    }

    public MyService() { //constructor
    }

    @Override
    public IBinder onBind(Intent intent) {  //return seconds to its client
       return  new MyBinder();
    }


    @Override
    public boolean onUnbind(Intent intent) {

        Log.i(TAG, "onUnbind()");
        return super.onUnbind(intent);
    }

    @Override
    public void onCreate() {  // to count seconds repeatedly
        super.onCreate();
        Log.i(TAG,"onCreate()");

        //create a new thread to perform the thread.sleep() function
        new Thread(new Runnable() {
            @Override
            public void run() {
                // put down the time-consuming task here
                while(isWorking){
                    try {
                        Thread.sleep(1000);//let the current thread sleep for 1 second
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    seconds++;  //?
                    Log.i(TAG, "seconds= "+seconds);
                }
            }
        }).start();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.i(TAG, "onStartCommand()");

        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

        Log.i(TAG, "onDestroy()");

        isWorking= false;
    }
}
