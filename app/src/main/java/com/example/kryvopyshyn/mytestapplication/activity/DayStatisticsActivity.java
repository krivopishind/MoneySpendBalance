package com.example.kryvopyshyn.mytestapplication.activity;

import android.content.Context;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.view.ViewGroup;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.example.kryvopyshyn.mytestapplication.R;
import com.example.kryvopyshyn.mytestapplication.dto.SmsDto;
import com.example.kryvopyshyn.mytestapplication.persistance.DataProvider;

import java.util.List;
import java.util.Map;

import static android.view.ViewGroup.LayoutParams.WRAP_CONTENT;
import static com.example.kryvopyshyn.mytestapplication.core.StringUtils.convertDouble;
import static com.example.kryvopyshyn.mytestapplication.persistance.DataAgregator.groupedByDay;

public class DayStatisticsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_day_statistics);

        List<SmsDto> dtoList = DataProvider.getMessagesReverseOrder(this, getContentResolver());
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            drawDayStatistics(dtoList);
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    private void drawDayStatistics(List<SmsDto> messages) {
        TableLayout tableLayout = findViewById(R.id.day_tableLayout_details);
        TableLayout headerTableLayout = findViewById(R.id.dayStatTableLayout);
        int padding = headerTableLayout.getPaddingLeft();
        //group by day
        Map<String, Map<Boolean, Double>> groupedByDay = groupedByDay(messages);
        for (String yearMonth : groupedByDay.keySet()) {
            Map<Boolean, Double> data = groupedByDay.get(yearMonth);

            TableRow tableRow = new TableRow(this);
            tableRow.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT,
                    WRAP_CONTENT));
            tableRow.setBackgroundColor(Color.LTGRAY);

            TextView period = createTextView(yearMonth, 129);
            TextView expenses = createTextView(convertDouble(data.get(false)), 96);
            TextView refill = createTextView(convertDouble(data.get(true)), 127);

            tableRow.addView(period);
            tableRow.addView(expenses);
            tableRow.addView(refill);
            tableLayout.addView(tableRow);
        }
    }

    @NonNull
    private TextView createTextView(String text, int width) {
        TextView textView = new TextView(this);
        textView.setText(text);
        TableRow.LayoutParams params = new TableRow.LayoutParams(dpToPx(16 + width), WRAP_CONTENT);
        textView.setLayoutParams(params);
        return textView;
    }

    private int dpToPx(int dp) {
        float density = this.getResources().getDisplayMetrics().density;
        return Math.round((float) dp * density);
    }

}
