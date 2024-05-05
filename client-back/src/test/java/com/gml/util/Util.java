package com.gml.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class Util {
    public static Date stringToDate(String dates) {
        SimpleDateFormat formatter = new SimpleDateFormat("MMM-dd-yyyy", Locale.ENGLISH);
        try {
            return formatter.parse(dates);
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
    }

}
