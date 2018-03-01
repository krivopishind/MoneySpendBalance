package com.example.kryvopyshyn.mytestapplication.activity;

import android.content.Context;
import android.test.mock.MockContext;

import com.example.kryvopyshyn.mytestapplication.dto.SmsDto;
import com.example.kryvopyshyn.mytestapplication.persistance.DataAgregator;
import com.example.kryvopyshyn.mytestapplication.persistance.IOHelper;
import com.example.kryvopyshyn.mytestapplication.persistance.JsonUtils;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * Created by Dmytro_Kryvopyshyn on 22-Nov-17.
 */
@RunWith(MockitoJUnitRunner.class)
public class DataAgregatorTest {

    private DataAgregator sut = new DataAgregator();

    @Test
    public void groupedByMonth() throws Exception {
        Map<String, Map<Boolean, Double>> groupedByMonth = sut.groupedByMonth(createTestData());

        for (String yearMonth : groupedByMonth.keySet()) {
            System.out.println("Year month: " + yearMonth);
            Map<Boolean, Double> data = groupedByMonth.get(yearMonth);
            System.out.println("Splata: " + convertDouble(data.get(false)));
            System.out.println("Popovnenya: " + convertDouble(data.get(true)));
        }
    }

    @Test
    public void groupByDay() {
        Map<String, Map<Boolean, Double>> groupedByDay = sut.groupedByDay(createTestData());
        for (String yearMonth : groupedByDay.keySet()) {
            Map<Boolean, Double> data = groupedByDay.get(yearMonth);
            System.out.println("Day: " + yearMonth + " Splata: " + convertDouble(data.get(false)) + " Popovnenya: " + convertDouble(data.get(true)));
        }
    }

    @Test
    public void groupByDay_dataFromFile() throws Exception {
        Context context = setUpContext();
        List<SmsDto> dtos = JsonUtils.getMessagesFromFile(context);

        Map<String, Map<Boolean, Double>> groupedByDay = sut.groupedByDay(dtos);
        for (String yearMonth : groupedByDay.keySet()) {
            Map<Boolean, Double> data = groupedByDay.get(yearMonth);
            System.out.println("Day: " + yearMonth + " Splata: " + convertDouble(data.get(false)) + " Popovnenya: " + convertDouble(data.get(true)));
        }
    }

    @Test
    public void debuggingTest() throws Exception {
        Context context = setUpContext();
        List<SmsDto> dtos = JsonUtils.getMessagesFromFile(context);
    }

    @Test
    public void groupByOneDay(){
        Map<String, Map<Boolean, Double>> groupedByDay = sut.groupedByDay(createTestDataForDay());
        Map<Boolean, Double> oneDayMap = groupedByDay.get("12/01/2018");
        assertThat(oneDayMap.get(true), is(9000.0));
        assertThat(oneDayMap.get(false), is(5617.08));
    }

    private Context setUpContext() throws Exception {
        Context context = mock(Context.class);
        ClassLoader classLoader = getClass().getClassLoader();
        FileInputStream fileInputStream = new FileInputStream("C:\\Users\\Dmytro_Kryvopyshyn\\AndroidStudioProjects\\MyTestApplication\\" +
                "app\\src\\test\\java\\com\\example\\kryvopyshyn\\mytestapplication\\transactions.json");

        when(context.openFileInput(anyString())).thenReturn(fileInputStream);
        return context;
    }

    private String computeAction(boolean action){
        return action ? "Popovnenya" : "Splata";
    }

    private String convertDouble(Double value) {
        return value == null ? "" : String.format("%.0f", value);
    }

    private List<SmsDto> createTestData() {
        List<SmsDto> dtos = new ArrayList<>();
        SmsDto splata1 = createDto(1503070490000L, 276, false);
        SmsDto splata2 = createDto(1503084641000L, 1121.27, false);
        SmsDto splata3 = createDto(1503144212000L, 584, false);
        SmsDto splata4 = createDto(1503257022000L, 656.07, false);
        SmsDto splata5 = createDto(1503257202000L, 1066, false);
        SmsDto splata6 = createDto(1503389388000L, 2224.93, false);
        SmsDto splata7 = createDto(1504089064000L, 2901, false);
        SmsDto splata8 = createDto(1504168560000L, 75, false);
        SmsDto splata9 = createDto(1504180911000L, 98.25, false);
        SmsDto popovnennya1 = createDto(1504180911000L, 15000, true);
        SmsDto splata10 = createDto(1504190473000L, 0, false);
        SmsDto splata11 = createDto(1504190928000L, 6109.38, false);
        dtos.add(splata1);
        dtos.add(splata2);
        dtos.add(splata3);
        dtos.add(splata4);
        dtos.add(splata5);
        dtos.add(splata6);
        dtos.add(splata7);
        dtos.add(splata8);
        dtos.add(splata9);
        dtos.add(popovnennya1);
        dtos.add(splata10);
        dtos.add(splata11);
        return dtos;
    }

    private List<SmsDto> createTestDataForDay(){
        List<SmsDto> dtos = new ArrayList<>();
        dtos.add(createDto(1515769980000L, 5000, false));
        dtos.add(createDto(1515769980000L, 9000, true));
        dtos.add(createDto(1515769980000L, 82, false));
        dtos.add(createDto(1515769980000L, 535.0799999999999, false));
        return dtos;
    }

    private SmsDto createDto(long timestamp, double sum, boolean addAction) {
        SmsDto smsDto = new SmsDto();
        smsDto.setTimestamp(timestamp);
        smsDto.setSum(sum);
        smsDto.setAddAction(addAction);
        return smsDto;
    }

}