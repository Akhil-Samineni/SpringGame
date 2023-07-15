package com.example.game.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.Calendar;
import java.util.Date;

public class DateTimeUtility {
    private static final Logger logger = LoggerFactory.getLogger(DateTimeUtility.class);

    public static String getCurrentDate() {
        String formattedDate = "";
        try {
            Calendar calendar = Calendar.getInstance();
            Date currentDate = calendar.getTime();
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
            formattedDate = formatter.format(currentDate);
        } catch (Exception e) {
            logger.error("Error while getting today's date {}", e.getMessage());
        }
        return formattedDate;
    }

    public static String getPreviousDate(int previousDays) {
        String formattedDate = "";
        try {
            Calendar calendar = Calendar.getInstance();
            calendar.add(Calendar.DAY_OF_YEAR, -1 * previousDays);
            Date currentDate = calendar.getTime();
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
            formattedDate = formatter.format(currentDate);
        } catch (Exception e) {
            logger.error("Error while getting previous date {}", e.getMessage());
        }
        return formattedDate;
    }

    public static int getCurrentHour() {
        LocalDateTime currentDate = LocalDateTime.now();
        return currentDate.getHour();
    }
}
