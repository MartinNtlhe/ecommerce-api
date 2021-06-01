package com.example.ecommerce.util;

import java.security.SecureRandom;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.Random;

public class DateUtil {
    private static SimpleDateFormat sdf =new SimpleDateFormat("dd-MM-yyyy");
    public static Date stringToDate(String stringDate){
        if(stringDate != null && !stringDate.isEmpty()){
            try {
                return sdf.parse(stringDate);
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    public static Calendar returnCalendar(Date date) {
        Calendar calendar = Calendar.getInstance(Locale.US);
        calendar.setTime(date);
        return calendar;
    }

    public static Long generateId(){
        long lowerRange = 0;
        long higherRange = 1000000;
        Random random = new Random();
        return lowerRange +	(long)(random.nextDouble()*(higherRange - lowerRange));
    }

    public static String generateReferenceNumber(){
        SecureRandom numberGenerator = new SecureRandom();
        final long msb = 0x8000000000000000L;
        return Long.toHexString(msb | numberGenerator.nextLong()) + Long.toHexString(msb | numberGenerator.nextLong());
    }
}
