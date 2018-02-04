package com.gitlab.graalogosh.cybertask.translator;

import com.gitlab.graalogosh.cybertask.task.Task;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.temporal.TemporalAdjusters;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Anton Mukovozov (graalogosh@gmail.com) on 04.02.2018.
 */
public class Translator {
    static Task getTaskFromString(String input) {
        StringBuilder inputString = new StringBuilder(input);

        //delete junk
        deleteSubStr(inputString, "Напомни мне");
        deleteSubStr(inputString, "напомни мне");
        deleteSubStr(inputString, "Пожалуйста");
        deleteSubStr(inputString, "пожалуйста");

        //time
        final LocalTime time = getTimeFromString(inputString);

        //date
        final LocalDate date = getDateFromString(inputString, time);

        //combine date and time
        final LocalDateTime resultDateTime = LocalDateTime.of(date, time);

        //subject
        String subject = upperCaseFirst(inputString.toString().trim());

        return Task.builder().title(subject).date(resultDateTime).build();
    }

    static private LocalDate getDateFromString(StringBuilder input, final LocalTime time) {
        Pattern todayPattern = Pattern.compile("(сегодня)");
        Matcher m = todayPattern.matcher(input);
        if (m.find()) {
            if (LocalTime.now().isBefore(time)) {
                return LocalDate.now();
            } else {
                return LocalDate.now().plusDays(1);
            }
        }

        Pattern tomorrowPattern = Pattern.compile("(завтра)");
        m = tomorrowPattern.matcher(input);
        if (m.find()) {
            return LocalDate.now().plusDays(1);
        }

        HashMap<String, DayOfWeek> weekdays = new HashMap<>();
        weekdays.put("(в понедельник)", DayOfWeek.MONDAY);
        weekdays.put("(во вторник)", DayOfWeek.TUESDAY);
        weekdays.put("(в среду)", DayOfWeek.WEDNESDAY);
        weekdays.put("(в четверг)", DayOfWeek.THURSDAY);
        weekdays.put("(в пятницу)", DayOfWeek.FRIDAY);
        weekdays.put("(в субботу)", DayOfWeek.SATURDAY);
        weekdays.put("(в воскресенье)", DayOfWeek.SUNDAY);

        for (Map.Entry<String, DayOfWeek> weekday : weekdays.entrySet()) {
            Pattern pattern = Pattern.compile(weekday.getKey());
            m = pattern.matcher(input);
            if (m.find()) {
                deleteSubStr(input, weekday.getKey().replace('(',' ').replace(')',' ').trim());
                if (LocalTime.now().isBefore(time)) {
                    return LocalDate.now().with(TemporalAdjusters.next(weekday.getValue()));
                } else {
                    //if now is 13.00 of monday and user wants to create task on 10.00 of monday - return not today, but next monday
                    return LocalDate.now().plusDays(1).with(TemporalAdjusters.next(weekday.getValue()));
                }
            }
        }

        //else
        if (LocalTime.now().isBefore(time)) {
            return LocalDate.now();
        } else {
            return LocalDate.now().plusDays(1);
        }
    }

    static private LocalTime getTimeFromString(StringBuilder input) {
        Pattern timePattern = Pattern.compile("(([0-9]|0[0-9]|1[0-9]|2[0-3])[:.][0-5][0-9])");
        Matcher matcher = timePattern.matcher(input);
        LocalTime time = null;
        if (matcher.find()) {
            String timeString = matcher.group(1);
            deleteSubStr(input, " в " + timeString);
            timeString = timeString.replace(".", ":");
            time = LocalTime.parse(timeString);
        }
        return time;
    }

    static private void deleteSubStr(StringBuilder string, String subStr) {
        int i = string.indexOf(subStr);
        if (i != -1) {
            string.delete(i, i + subStr.length());
        }
    }

    static private String upperCaseFirst(String value) {
        char[] array = value.toCharArray();
        array[0] = Character.toUpperCase(array[0]);
        return new String(array);
    }
}
