package com.example.kryvopyshyn.mytestapplication.activity;

import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.example.kryvopyshyn.mytestapplication.R;
import com.example.kryvopyshyn.mytestapplication.dto.SmsDto;
import com.example.kryvopyshyn.mytestapplication.persistance.DataAgregator;
import com.example.kryvopyshyn.mytestapplication.persistance.DataProvider;

import java.util.List;
import java.util.Map;

import static com.example.kryvopyshyn.mytestapplication.core.StringUtils.convertDouble;

public class MonthStatisticsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_month_statistics);

        List<SmsDto> dtoList = DataProvider.getMessages(this, getContentResolver());
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            drawMonthStatistics(dtoList);
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    private void drawMonthStatistics(List<SmsDto> messages) {
        TableLayout tableLayout = findViewById(R.id.monthStatTableLayout);
        //group by month
        Map<String, Map<Boolean, Double>> groupedByMonth = DataAgregator.groupedByMonth(messages);
        for (String yearMonth : groupedByMonth.keySet()) {
            TableRow tableRow = new TableRow(this);
            tableRow.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT,
                    TableRow.LayoutParams.WRAP_CONTENT));

            TextView period = new TextView(this);
            period.setText(yearMonth);

            Map<Boolean, Double> data = groupedByMonth.get(yearMonth);
            TextView expenses = new TextView(this);
            expenses.setText(convertDouble(data.get(false)));

            TextView refill = new TextView(this);
            refill.setText(convertDouble(data.get(true)));

            tableRow.addView(period);
            tableRow.addView(expenses);
            tableRow.addView(refill);
            tableLayout.addView(tableRow);
        }
    }

}
