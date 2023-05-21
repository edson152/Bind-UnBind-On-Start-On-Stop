 package cn.edu.zjnu.cs.serviceexample;

import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.os.Vibrator;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {


    private Button btn_vibrator, btn_start, btn_stop, btn_bind, btn_unbind, btn_read;

    private MyService.MyBinder binder;

    private ServiceConnection serviceConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            //get the service' binder object

            binder=(MyService.MyBinder) service;
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            //nothing so far
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btn_vibrator=findViewById(R.id.button_vibrator);
        btn_vibrator.setOnClickListener(this);

        btn_start=findViewById(R.id.button_start_service);
        btn_start.setOnClickListener(this);

        btn_stop=findViewById(R.id.button_stop_service);
        btn_stop.setOnClickListener(this);

        btn_bind=findViewById(R.id.button_bind_service);
        btn_bind.setOnClickListener(this);
        btn_unbind=findViewById(R.id.button_unbind_service);
        btn_unbind.setOnClickListener(this);

        btn_read=findViewById(R.id.button_read_seconds);
        btn_read.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.button_vibrator: // vibrate the device for 1000 milliseconds
                Vibrator vibrator=(Vibrator) getSystemService(VIBRATOR_SERVICE);
                if(vibrator.hasVibrator()){
                   vibrator.vibrate(1000);
                    Toast.makeText(this,"This device has a vibrator",Toast.LENGTH_LONG).show();
                }
                else {
                    Toast.makeText(this, "This device has no vibrator", Toast.LENGTH_LONG).show();
                }
                break;

            case R.id.button_start_service:  // start MyService
                Intent service= new Intent();
                service.setClass(this, MyService.class);
                startService( service);
                Toast.makeText(this, "Start MyService", Toast.LENGTH_LONG).show();
                break;

            case R.id.button_stop_service: //stop MyService
                service= new Intent(this, MyService.class);
                stopService(service);
                Toast.makeText(this, "MyService is stopped", Toast.LENGTH_LONG).show();
                break;

            case R.id.button_bind_service:// bind MyService
                service= new Intent(this, MyService.class);
                bindService( service, serviceConnection, BIND_AUTO_CREATE);
                Toast.makeText(this, "bind MyService", Toast.LENGTH_LONG).show();
                break;

            case R.id.button_unbind_service: //unbind MyService
                unbindService(serviceConnection);
                Toast.makeText(this, "MyService is unbound", Toast.LENGTH_LONG).show();
                break;

            case R.id.button_read_seconds:  // to get the seconds from MyService


                int seconds=binder.getSeconds();
                Toast.makeText(this, "seconds="+seconds,Toast.LENGTH_SHORT).show();
                break;

        }
    }
}
