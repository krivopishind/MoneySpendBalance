package com.example.kryvopyshyn.mytestapplication;

import com.example.kryvopyshyn.mytestapplication.activity.ListTransactionsActivity;
import com.example.kryvopyshyn.mytestapplication.core.SmsParser;

import org.junit.Test;

/**
 * Created by Dmytro_Kryvopyshyn on 15-Nov-17.
 */
public class ListTransactionsActivityTest {

    private static final String BODY = "Number: OTP Bank .Message: 15/11 14:16\n" +
            "Splata za tovar/poslugu.\n" +
            "Kartka *2279.\n" +
            "Suma: 119 UAH.\n" +
            "Misce: Marketplaza59612FZ0BHG. \n" +
            "Dostupnyi zalyshok: 4039.21 UAH";

    private static final String BODY_VO = "21/08 08:40\n" +
            "Vytratna operatsiya.\n" +
            "Kartka *2279. Suma:\n" +
            "3115.93 UAH. Misce:\n" +
            "OTP_PAYMENTS. \n" +
            "Zalyshok: 6157.14 UAH";

    @Test
    public void parseBody() throws Exception {
        ListTransactionsActivity testInstance = new ListTransactionsActivity();
        String[] parsed = SmsParser.parseBody(BODY);
    }

    @Test
    public void parseBody_Vo() throws Exception {
        ListTransactionsActivity testInstance = new ListTransactionsActivity();
        String[] parsed = SmsParser.parseBodyVo(BODY_VO);
    }

}