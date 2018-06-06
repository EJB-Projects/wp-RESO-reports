package ru.reso.common.utils;

import java.sql.Date;
import java.util.Calendar;
import java.util.TimeZone;

public class ResoDateUtils {
    public static Calendar getDateCalendarTime2Calendar(Date admdate, Calendar createDate) {


/**
 *  Это пока тупо заглушка. Тестовый код, просто чтобы хоть как-то собрать проект и оттестить. Возвращает одно и то же число.
 *  Мне просто не дали половину классов, котрые используются в проекте...
 */
        Calendar calendarTest = Calendar.getInstance();
        calendarTest.setTimeZone(TimeZone.getTimeZone("UTC"));
        calendarTest.set(2016, Calendar.JANUARY, 5, 12, 30, 0);
        calendarTest.add(Calendar.DAY_OF_YEAR, -2);



        return calendarTest;
    }
}
