package com.example.eld.utils;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

public class CommonUtils {

    public static final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH);

    public static String getCurrentDate(SimpleDateFormat simpleDateFormat) {
        Date d = new Date(System.currentTimeMillis());
        return simpleDateFormat.format(d);
    }

    public static String convertUTCToLocalDate(String dateStr) {
        try {
            SimpleDateFormat df = new SimpleDateFormat("MMM dd, yyyy HH:mm:ss a", Locale.ENGLISH);
            df.setTimeZone(TimeZone.getTimeZone("UTC"));
            Date date = df.parse(dateStr);
            df.setTimeZone(TimeZone.getDefault());
            return df.format(date);
        } catch (Exception e) {
            e.printStackTrace();
            return "-";
        }
    }

    public static String convertDateTime(String originalTime, String inputFormat, String outputFormat) {
        try {
            // Step 1: Parse the original time string into a Date object
            SimpleDateFormat originalFormat = new SimpleDateFormat(inputFormat);
            originalFormat.setTimeZone(TimeZone.getTimeZone("UTC"));
            Date date = originalFormat.parse(originalTime);

            // Step 2: Format the Date object into "HH:mm" format
            SimpleDateFormat targetFormat = new SimpleDateFormat(outputFormat);
            targetFormat.setTimeZone(TimeZone.getDefault());
            String formattedTime = targetFormat.format(date);

            return formattedTime;
        } catch (Exception e) {
            e.printStackTrace();
            return originalTime; // Return the original string if parsing fails
        }
    }
}
