package com.example.eld.utils;

import android.util.Log;

import com.google.firebase.Timestamp;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.Locale;

public class TimestampConverter {

    // Method for generating a UTC timestamp
    public static String generateTimestamp() {
        LocalDateTime nowUtc = null;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            nowUtc = LocalDateTime.now(ZoneOffset.UTC);
        }
        DateTimeFormatter formatter = null;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss'Z'");
        }
        String timestamp = null;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            timestamp = nowUtc.format(formatter);
        }
        return timestamp;
    }

    // Method for converting a UTC timestamp to the device's local time zone
    /*public static String convertTimestamp(String utcTimestamp) {
        LocalDateTime utcTime = null;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            utcTime = LocalDateTime.parse(utcTimestamp, DateTimeFormatter.ISO_DATE_TIME);
        }
        ZoneId deviceZone = null;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            deviceZone = ZoneId.systemDefault();
        }
        ZonedDateTime deviceTime = null;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            deviceTime = utcTime.atOffset(ZoneOffset.UTC).atZoneSameInstant(deviceZone);
        }
        DateTimeFormatter formatter = null;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            formatter = DateTimeFormatter.ofPattern("HH:mm");
        }
        String convertedTimestamp = null;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            convertedTimestamp = deviceTime.format(formatter);
        }
        return convertedTimestamp;
    }*/

    public static String convertTimestampforx(String utcTimestamp) {
        LocalDateTime utcTime = null;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            utcTime = LocalDateTime.parse(utcTimestamp, DateTimeFormatter.ISO_DATE_TIME);
        }
        ZoneId deviceZone = null;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            deviceZone = ZoneId.systemDefault();
        }
        ZonedDateTime deviceTime = null;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            deviceTime = utcTime.atOffset(ZoneOffset.UTC).atZoneSameInstant(deviceZone);
        }
        DateTimeFormatter formatter = null;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            formatter = DateTimeFormatter.ofPattern("HH.mm");
        }
        String convertedTimestamp = null;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            convertedTimestamp = deviceTime.format(formatter);
        }
        return convertedTimestamp;
    }

    public static String convertTimestamp(Timestamp serverTimestamp) {
        LocalDateTime utcTime = null;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            utcTime = serverTimestamp.toDate().toInstant().atZone(ZoneOffset.UTC).toLocalDateTime();
        }
        ZoneId deviceZone = null;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            deviceZone = ZoneId.systemDefault();
        }
        ZonedDateTime deviceTime = null;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            deviceTime = utcTime.atZone(ZoneOffset.UTC).withZoneSameInstant(deviceZone);
        }
        DateTimeFormatter formatter = null;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            formatter = DateTimeFormatter.ofPattern("hh:mm");
        }
        String convertedTimestamp = null;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            convertedTimestamp = deviceTime.format(formatter);
        }
        return convertedTimestamp;
    }

    public static Timestamp getTimestampFromDate(String dateStr, boolean isAM) {
        Log.d("TAG","=========dateStr========="+dateStr);
        SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy hh:mm:ss a", Locale.US);
        Date date;
        try {
            date = dateFormat.parse(dateStr + " " + (isAM ? "12:00:00 AM" : "11:59:59 PM"));
        } catch (ParseException e) {
            e.printStackTrace();
            return null;
        }
        return new Timestamp(date);
    }

}

