package com.example.kryvopyshyn.mytestapplication.persistance;

import android.os.Build;
import android.support.annotation.RequiresApi;

import com.example.kryvopyshyn.mytestapplication.dto.SmsDto;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Created by Dmytro_Kryvopyshyn on 04-Dec-17.
 */

public class DataAgregator {

    @RequiresApi(api = Build.VERSION_CODES.N)
    public static Map<String, Map<Boolean, Double>> groupedByMonth(List<SmsDto> messages) {
        return messages.stream().collect(
                Collectors.groupingBy(SmsDto::getMonthYear, LinkedHashMap::new,
                        Collectors.groupingBy(SmsDto::isAddAction,
                                Collectors.summingDouble(SmsDto::getSum))));
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    public static Map<String, Map<Boolean, Double>> groupedByDay(List<SmsDto> messages) {
        return messages.stream().collect(
                Collectors.groupingBy(SmsDto::getDate, LinkedHashMap::new,
                        Collectors.groupingBy(SmsDto::isAddAction,
                                Collectors.summingDouble(SmsDto::getSum))));
    }
}
