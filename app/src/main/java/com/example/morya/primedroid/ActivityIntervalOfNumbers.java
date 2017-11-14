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
import android.widget.Toast;

/**
 * Created by Morya on 08/04/2017.
 */


public class ActivityIntervalOfNumbers extends AppCompatActivity {
    ListView lvn;
    String[] FoundPrimeNumbers = new String[10000];


    DataBasePrime myDb;
    private boolean mboundForInterval = false;
    private ServiceOneNumber myServiceForInterval;
    private ServiceConnection myConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            myServiceForInterval = ((ServiceOneNumber.MyIBinder)service).getService();
        }
        @Override
        public void onServiceDisconnected(ComponentName name) {
            myServiceForInterval = null;
        }
    };
    EditText NumMax,NumMin;



    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.interval_of_numbers);
        NumMin = (EditText)findViewById(R.id.editText6);
        NumMax = (EditText)findViewById(R.id.editText5);
        myDb = new DataBasePrime(this);
        for(int i = 0; i <FoundPrimeNumbers.length ;i++){
            FoundPrimeNumbers[i] = "-";
        }
    }
    @Override
    protected void onStart() {
        super.onStart();
        Intent myIntent = new Intent(this,ServiceOneNumber.class);
        bindService(myIntent,myConnection,BIND_AUTO_CREATE);
        mboundForInterval = true ;


    }

    @Override
    protected void onStop() {
        super.onStop();
        if(mboundForInterval){
            unbindService(myConnection);
            mboundForInterval = false;
        }
    }
    @Override
    protected void onResume() {
        super.onResume();

    }
    public void showToast(final String toast)
    {
        runOnUiThread(new Runnable() {
            public void run()
            {
                Toast.makeText(ActivityIntervalOfNumbers.this, toast, Toast.LENGTH_SHORT).show();
            }
        });
    }


    public void onClickActivityIntervalOfNumbers(final View view){
        final int Nmax = Integer.parseInt((NumMax.getText().toString()));
        final int Nmin = Integer.parseInt((NumMin.getText().toString()));
        new Thread(new Runnable() {
            public void run() {
                // a potentially  time consuming task
                for(int i =0; i<FoundPrimeNumbers.length;i++){
                    if(myServiceForInterval.PrimeNumbersInrange(Nmin,Nmax)[i]==null){
                        break;
                }
                    FoundPrimeNumbers[i] = myServiceForInterval.PrimeNumbersInrange(Nmin,Nmax)[i];
                    myDb.UpdateData(Integer.toString(i+1),myServiceForInterval.PrimeNumbersInrange(Nmin,Nmax)[i]);
                    if(i%20==0 & i>=20){
                        showToast("Latest prime number found is: "+FoundPrimeNumbers[i]);
                    }
                }
                // a potentially  time consuming task
                view.post(new Runnable() {
                    public void run() {

                        ArrayAdapter<String> adapter = new ArrayAdapter<String>(ActivityIntervalOfNumbers.this, R.layout.prime_listing, FoundPrimeNumbers);
                        lvn = (ListView) findViewById(R.id.ListView5);
                        lvn.setAdapter(adapter);
                    }
                });
            }
        }).start();
    }
}