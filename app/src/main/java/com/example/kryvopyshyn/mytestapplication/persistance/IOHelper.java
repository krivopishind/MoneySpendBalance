package com.example.kryvopyshyn.mytestapplication.persistance;

import android.content.Context;

import java.io.BufferedReader;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * Created by Dmytro_Kryvopyshyn on 17-Nov-17.
 */

public class IOHelper {

    private static String FILE_NAME = "transactions.json";

    public static void writeToFile(Context context, String data) {
        try {
            FileOutputStream fos = context.openFileOutput(FILE_NAME, Context.MODE_PRIVATE);
            fos.write(data.getBytes());
            fos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static String readFromFile(Context context) {
        String result = "";
        try {
            InputStream inputStream = context.openFileInput(FILE_NAME);

            if (inputStream != null) {
                InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
                BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
                String receiveString = "";
                StringBuilder stringBuilder = new StringBuilder();

                while ((receiveString = bufferedReader.readLine()) != null) {
                    stringBuilder.append(receiveString);
                }

                inputStream.close();
                result = stringBuilder.toString();
            }
        } catch (IOException e){
            e.printStackTrace();
        }
        return result;
    }

    public static boolean fileAlreadyExist(Context context){
        return context.getFileStreamPath(FILE_NAME).exists();
    }
}
