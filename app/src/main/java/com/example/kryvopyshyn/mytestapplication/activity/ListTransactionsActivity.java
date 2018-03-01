package com.example.kryvopyshyn.mytestapplication.activity;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.example.kryvopyshyn.mytestapplication.R;
import com.example.kryvopyshyn.mytestapplication.dto.SmsDto;
import com.example.kryvopyshyn.mytestapplication.persistance.DataProvider;

import java.util.List;

public class ListTransactionsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_transactions);

        List<SmsDto> messages = DataProvider.getMessages(this, getContentResolver());
        drawMessages(messages);
    }

    private void drawMessages(List<SmsDto> messages) {
        TableLayout tableLayout = findViewById(R.id.tableLayout_details);
        for (int i = messages.size() - 1; i >= 0; i--) {
            SmsDto smsDto = messages.get(i);
            TableRow tableRow = new TableRow(this);
            tableRow.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT,
                    TableRow.LayoutParams.WRAP_CONTENT));
            tableRow.setBackgroundColor(Color.LTGRAY);

            TextView sunjectView = new TextView(this);
            sunjectView.setText(smsDto.getSubject());
            tableRow.addView(sunjectView);

            TextView sumView = new TextView(this);
            sumView.setText(smsDto.getSumString());
            tableRow.addView(sumView);

            TextView dateView = new TextView(this);
            dateView.setText(smsDto.getDate());
            tableRow.addView(dateView);

            //second row
            TableRow tableRow2 = new TableRow(this);
            tableRow2.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT,
                    TableRow.LayoutParams.WRAP_CONTENT));

            TextView placeView = new TextView(this);
            placeView.setText(smsDto.getPlace());
            tableRow2.addView(placeView);

            TextView balanceView = new TextView(this);
            balanceView.setText(String.valueOf(smsDto.getBalance()));
            tableRow2.addView(balanceView);

            TextView timeView = new TextView(this);
            timeView.setText(smsDto.getTime());
            tableRow2.addView(timeView);

            tableLayout.addView(tableRow);
            tableLayout.addView(tableRow2);
        }
    }

}
