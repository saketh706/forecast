package com.example.forecast.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Date;

public class DateUtils {

    public static Date formatDate(Date date) throws ParseException {
        String format = "M/d/yyyy";
        SimpleDateFormat formatter = new SimpleDateFormat(format);
        return parseDate(formatter.format(date));
    }

    public static Date parseDate(String date) throws ParseException {
        SimpleDateFormat dateFormat = new SimpleDateFormat("M/d/yyyy");
        return dateFormat.parse(date);
    }

    public static Date addDate(Date date, int number) throws ParseException {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(formatDate(date));
        calendar.add(Calendar.DAY_OF_YEAR, number);
        return calendar.getTime();
    }

    public static String formatDate(String dateStr){
        DateTimeFormatter inputFormatter = DateTimeFormatter.ofPattern("M/d/yyyy");
        LocalDate date = LocalDate.parse(dateStr, inputFormatter);
        DateTimeFormatter outputFormatter = DateTimeFormatter.ofPattern("MM-dd-yyyy");
        return outputFormatter.format(date);
    }

}

