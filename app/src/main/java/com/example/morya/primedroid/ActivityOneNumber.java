package com.example.morya.primedroid;

import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;


/**
 * Created by Morya on 08/04/2017.
 */
     public class ActivityOneNumber extends AppCompatActivity{

    ListView lv;
    int[] deviders = new int[20];
    String[] devidersS = new String[10];

    String s1="this is a prime number";
    String s2="this is not a prime number";
    private boolean mbounda = false;
    private ServiceOneNumber myService;
    private ServiceConnection myConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            myService = ((ServiceOneNumber.MyIBinder)service).getService();
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
           myService = null;
        }
    };
     EditText e;
     TextView t;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_one_number);
         e = (EditText)findViewById(R.id.editText2);
         t = (TextView) findViewById(R.id.textView5);
    }

    @Override
    protected void onStart() {
        super.onStart();
        Intent myIntent = new Intent(this,ServiceOneNumber.class);
        bindService(myIntent,myConnection,BIND_AUTO_CREATE);
        mbounda = true ;

    }

    @Override
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
    public void onClickActivityOneNumber(final View view) {
        final int num = Integer.parseInt((e.getText().toString()));
        new Thread(new Runnable() {
            public void run() {
                // a potentially  time consuming task
                final boolean fact = myService.isPrime(num);
                deviders = myService.searchdeviders(num);
                devidersS = myService.factorsDecomposition(deviders);


                view.post(new Runnable() {
                    public void run() {
                        if (fact) {
                            t.setText(s1);
                        } else {
                            t.setText(s2);
                        }
                        ArrayAdapter<String> adapter = new ArrayAdapter<String>(ActivityOneNumber.this, R.layout.da_deviders, devidersS);
                        lv = (ListView) findViewById(R.id.ListView1);
                        lv.setAdapter(adapter);
                    }
                });
            }
        }).start();
    }
}