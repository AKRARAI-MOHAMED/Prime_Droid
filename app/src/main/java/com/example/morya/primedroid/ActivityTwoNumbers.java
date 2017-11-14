package com.example.morya.primedroid;

import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

/**
 * Created by Morya on 08/04/2017.
 */

public class ActivityTwoNumbers extends AppCompatActivity {
    String c1="these two numbers are coprime";
    String c2="these two numbers are not coprime";
    private boolean mbounda = false;
    private ServiceOneNumber myService2;
    private ServiceConnection myConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            myService2 = ((ServiceOneNumber.MyIBinder)service).getService();
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            myService2 = null;
        }
    };
    EditText n1,n2;
    TextView t;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_two_numbers);
        n1 = (EditText)findViewById(R.id.editText);
        n2 = (EditText)findViewById(R.id.editText3);
        t = (TextView) findViewById(R.id.textView9);
    }
    protected void onStart() {
        super.onStart();
        Intent myIntent = new Intent(this,ServiceOneNumber.class);
        bindService(myIntent,myConnection,BIND_AUTO_CREATE);
        mbounda = true ;
    }
    protected void onStop() {
        super.onStop();
        if(mbounda){
            unbindService(myConnection);
            mbounda = false;
        }
    }
    @Override
    protected void onResume() {
        super.onResume();

    }
    public void onClickActivityTwoNumber(final View view){
        final int num1 = Integer.parseInt((n1.getText().toString()));
        final int num2 = Integer.parseInt((n2.getText().toString()));
        new Thread(new Runnable() {
            public void run() {
                // a potentially  time consuming task
                final boolean fact = myService2.compareTwonumbers(num1,num2);
                view.post(new Runnable() {
                    public void run() {
                       //Views
                        if (fact) {
                            t.setText(c1);
                        } else {
                            t.setText(c2);
                        }
                    }
                });
            }
        }).start();
    }
}