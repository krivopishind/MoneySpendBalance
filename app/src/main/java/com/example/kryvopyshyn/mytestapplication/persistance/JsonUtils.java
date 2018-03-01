package com.example.kryvopyshyn.mytestapplication.persistance;

import android.content.Context;

import com.example.kryvopyshyn.mytestapplication.dto.SmsDto;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.List;

/**
 * Created by Dmytro_Kryvopyshyn on 18-Nov-17.
 */

public class JsonUtils {

    public static void writeToFile(Context context, List<SmsDto> messages) {
        Gson gson = new Gson();
        IOHelper.writeToFile(context, gson.toJson(messages));
    }

    public static List<SmsDto> getMessagesFromFile(Context context){
        String json = IOHelper.readFromFile(context);
        Gson gson = new Gson();
        Type type = new TypeToken<List<SmsDto>>(){}.getType();
        return gson.fromJson(json, type);
    }

}
