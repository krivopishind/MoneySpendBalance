package com.example.kryvopyshyn.mytestapplication.activity;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.example.kryvopyshyn.mytestapplication.R;
import com.example.kryvopyshyn.mytestapplication.persistance.DataProvider;

public class MainActivity extends AppCompatActivity {

    public static final String EXTRA_MESSAGE = "com.example.myfirstapp.MESSAGE";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

//    /** Called when the user taps the Send button */
//    public void sendMessage(View view) {
//        Intent intent = new Intent(this, DisplayMessageActivity.class);
//        EditText editText = (EditText) findViewById(R.id.editText);
//        String message = editText.getText().toString();
//        intent.putExtra(EXTRA_MESSAGE, message);
//        startActivity(intent);
//    }

    public void listTransactions(View view){
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_SMS) != PackageManager.PERMISSION_GRANTED){

            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                    Manifest.permission.READ_SMS)){
                callListTransactionsActivity();
            } else {
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.READ_SMS},
                        0);
            }

        } else {
            callListTransactionsActivity();
        }
    }

    private void callListTransactionsActivity(){
        Intent intent = new Intent(this, ListTransactionsActivity.class);
        startActivity(intent);
    }

    public void getMonthStatistics(View view){
        Intent intent = new Intent(this, MonthStatisticsActivity.class);
        startActivity(intent);
    }

    public void getDayStatistics(View view){
        Intent intent = new Intent(this, DayStatisticsActivity.class);
        startActivity(intent);
    }

    public void updateDataFromSms(View view){
        DataProvider.reWriteFile(this, getContentResolver());
    }

}
