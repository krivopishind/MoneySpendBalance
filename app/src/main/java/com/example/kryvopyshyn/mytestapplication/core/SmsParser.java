package com.example.kryvopyshyn.mytestapplication.core;

import android.content.ContentResolver;
import android.database.Cursor;
import android.net.Uri;
import android.provider.Telephony;

import com.example.kryvopyshyn.mytestapplication.dto.SmsDto;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Dmytro_Kryvopyshyn on 16-Nov-17.
 */

public class SmsParser {

    private static final String SPLATA = "Splata za tovar/poslugu.";
    private static final String OTRYMANNYA_GOTIVKY_V_ATM = "Otrymannya gotivky v ATM.";
    private static final String POPOVNENNYA_KARTKY = "Popovnennya kartky. ";
    private static final String POPOVNENNYA_RAHUNKU = "Popovnennya rahunku. ";
    private static final String VYTRATNA_OPERATSIA = "Vytratna operatsiya.";

    public static List<SmsDto> getTransactionsAndCalculateSum(ContentResolver contentResolver, String sender) {
        List<SmsDto> dtos = getSMSFrom(contentResolver, sender, 0);
        populateRecordsWithSum(dtos);
        return dtos;
    }

    public static List<SmsDto> getTransactionsAndCalculateSum(ContentResolver contentResolver, String sender, long id) {
        List<SmsDto> dtos = getSMSFrom(contentResolver, sender, id);
        populateRecordsWithSum(dtos);
        return dtos;
    }

    private static void populateRecordsWithSum(List<SmsDto> dtos) {
        for (int i = 1; i < dtos.size(); i++) {
            SmsDto first = dtos.get(i - 1);
            SmsDto second = dtos.get(i);
            populateRecordSum(first, second);
        }
    }

    public static void populateRecordSum(SmsDto first, SmsDto second) {
        double balance = first.getBalance();
        double newBalance = second.getBalance();
        double sum = newBalance > balance ? newBalance - balance : balance - newBalance;
        second.setSum(sum);
    }

    private static List<SmsDto> getSMSFrom(ContentResolver contentResolver, String sender, long id) {
        List<SmsDto> sms = new ArrayList<>();
        Uri uriSMSURI = Uri.parse("content://sms/inbox");
        final String[] projection = {Telephony.Sms._ID, Telephony.Sms.ADDRESS, Telephony.Sms.BODY, Telephony.Sms.DATE_SENT};
        String selection = Telephony.Sms._ID + " > ? AND " + Telephony.Sms.ADDRESS + " = ?";
        String[] selectionArgs = new String[]{String.valueOf(id), sender};
        String sortOrder = Telephony.Sms._ID + " ASC";
        Cursor cur = contentResolver.query(uriSMSURI, projection, selection, selectionArgs, sortOrder);

        while (cur != null && cur.moveToNext()) {
            SmsDto smsDto = new SmsDto(sender);
            smsDto.setId(cur.getLong(cur.getColumnIndexOrThrow(Telephony.Sms._ID)));
            smsDto.setTimestamp(cur.getLong(cur.getColumnIndexOrThrow(Telephony.Sms.DATE_SENT)));
            String body = cur.getString(cur.getColumnIndexOrThrow(Telephony.Sms.BODY));
            if (!body.contains("\n")) {
                continue;
            }
            String subject = body.split("\n")[1];
            if (SPLATA.equals(subject)
                    || OTRYMANNYA_GOTIVKY_V_ATM.equals(subject)
                    || POPOVNENNYA_KARTKY.equals(subject)
                    || POPOVNENNYA_RAHUNKU.equals(subject)
                    || VYTRATNA_OPERATSIA.equals(subject)) {
                String[] parseBody = VYTRATNA_OPERATSIA.equals(subject) ? parseBodyVo(body) : parseBody(body);
                smsDto.setSubject(parseBody[0]);
                smsDto.setSumString(parseBody[1]);
                smsDto.setPlace(parseBody[2]);
                double balance = Double.valueOf(parseBody[3]);
                smsDto.setBalance(balance);
                if (POPOVNENNYA_KARTKY.equals(subject) || POPOVNENNYA_RAHUNKU.equals(subject)) {
                    smsDto.setAddAction(true);
                }
                sms.add(smsDto);
            }
        }

        if (cur != null) {
            cur.close();
        }
        return sms;
    }

    public static String[] parseBody(String body) {
        String[] splitted = body.split("\n");
        String[] result = new String[4];
        //subject

        result[0] = splitted[1];
        //suma
        result[1] = splitted[3].substring(splitted[3].indexOf(":") + 2);
        //place
        result[2] = splitted[4].substring(splitted[4].indexOf(":") + 2);
        //balance
        String balance = splitted[5].substring(splitted[5].indexOf(":") + 2);
        result[3] = balance.substring(0, balance.indexOf(" "));

        return result;
    }

    public static String[] parseBodyVo(String body) {
        String[] splitted = body.split("\n");
        String[] result = new String[4];
        //subject

        result[0] = splitted[1];
        //suma
        result[1] = splitted[3].substring(0, splitted[3].indexOf(" "));
        //place
        result[2] = splitted[4];
        //balance
        String balance = splitted[5].substring(splitted[5].indexOf(":") + 2);
        result[3] = balance.substring(0, balance.indexOf(" "));

        return result;
    }
}
