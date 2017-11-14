package com.example.morya.primedroid;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
    public void onClickOneNumber(View view) {
        Intent i = new Intent(this,ActivityOneNumber.class);
        startActivity(i);
    }
    public void onClickTwoNumbers(View view) {
        Intent i = new Intent(this,ActivityTwoNumbers.class);
        startActivity(i);
    }
    public void onClickIntervalOfNumbers(View view) {
        Intent i = new Intent(this,ActivityIntervalOfNumbers.class);
        startActivity(i);
    }

}
