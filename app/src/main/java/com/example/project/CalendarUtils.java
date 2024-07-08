package com.example.project;

import android.util.Log;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class CalendarUtils{
    public static LocalDateTime todayDateTime;
    public static LocalDateTime selectDate;

    public static List<LocalDate> daysInWeekArray(LocalDateTime date)
    {
        List<LocalDate> days = new ArrayList<>();
        LocalDate current = sundayForDate(date.toLocalDate());
        Log.d("debug util",current.toString());
        LocalDate endDate = current.plusWeeks(1);

        while (current.isBefore(endDate))
        {
            days.add(current);
            current = current.plusDays(1);
        }
        Log.d("debug util",days.toString());

        return days;
    }

    private static LocalDate sundayForDate(LocalDate current)
    {
        LocalDate oneWeekAgo = current.minusWeeks(1);

        while (current.isAfter(oneWeekAgo))
        {
            if(current.getDayOfWeek() == DayOfWeek.MONDAY)
                return current;

            current = current.minusDays(1);
        }

        return null;
    }

}
