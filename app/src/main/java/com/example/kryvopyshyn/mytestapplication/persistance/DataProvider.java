package com.example.kryvopyshyn.mytestapplication.persistance;

import android.content.ContentResolver;
import android.content.Context;

import com.example.kryvopyshyn.mytestapplication.core.SmsParser;
import com.example.kryvopyshyn.mytestapplication.dto.SmsDto;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Dmytro_Kryvopyshyn on 22-Nov-17.
 */

public class DataProvider {

    private static final String SENDER = "OTP Bank";

    public static List<SmsDto> getMessages(Context context, ContentResolver contentResolver) {
        if (IOHelper.fileAlreadyExist(context)) {
            List<SmsDto> dtos = JsonUtils.getMessagesFromFile(context);
            long lastId = dtos.get(dtos.size() - 1).getId();
            List<SmsDto> newMessages = SmsParser.getTransactionsAndCalculateSum(contentResolver, SENDER, lastId);
            if (!newMessages.isEmpty()) {
                SmsDto first = newMessages.get(0);
                SmsDto last = dtos.get(dtos.size() - 1);
                SmsParser.populateRecordSum(first, last);
                dtos.addAll(newMessages);
                JsonUtils.writeToFile(context, dtos);
            }
            return dtos;
        } else {
            return reWriteFile(context, contentResolver);
        }
    }

    public static List<SmsDto> getMessagesReverseOrder(Context context, ContentResolver contentResolver) {
        List<SmsDto> result = new ArrayList<>();
        List<SmsDto> dtos = getMessages(context, contentResolver);
        for (int i = dtos.size() - 1; i >= 0; i--){
            result.add(dtos.get(i));
        }
        return result;
    }

    public static List<SmsDto> reWriteFile(Context context, ContentResolver contentResolver) {
        List<SmsDto> dtos = SmsParser.getTransactionsAndCalculateSum(contentResolver, SENDER);
        JsonUtils.writeToFile(context, dtos);
        return dtos;
    }
}
