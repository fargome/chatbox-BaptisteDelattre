package com.example.cesiapp;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

//Classe permettant une gestion simplifiée des dates et timestamps.
public class DateHelper {

    private static String format = "HH:mm:ss";
    private static SimpleDateFormat formatter = null;

    /**
     * create formatted date from timestamp.
     * @param timestamp
     * @return
     */
    public static String getFormattedDate(long timestamp){
        if(formatter == null){
            formatter = new SimpleDateFormat(format);
        }
        return formatter.format(new Date(timestamp));
    }
}