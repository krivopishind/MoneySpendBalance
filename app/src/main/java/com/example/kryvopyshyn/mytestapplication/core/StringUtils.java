package com.example.kryvopyshyn.mytestapplication.core;

/**
 * Created by Dmytro_Kryvopyshyn on 04-Dec-17.
 */

public class StringUtils {

    public static String convertDouble(Double value) {
        return value == null ? "" : String.format("%.0f", value);
    }
}
